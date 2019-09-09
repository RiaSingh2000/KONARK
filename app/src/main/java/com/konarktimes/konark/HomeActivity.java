package com.konarktimes.konark;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.konarktimes.konark.Adapters.CategoryAdapter;
import com.konarktimes.konark.Adapters.FeatureAdapter;
import com.konarktimes.konark.Adapters.NewsAdapter;
import com.konarktimes.konark.Categories.NewsFragment;
import com.konarktimes.konark.Common.HorizontalSpacingItemDecoration;
import com.konarktimes.konark.Common.VerticalSpacingItemDecoration;
import com.konarktimes.konark.Model.Categories;
import com.konarktimes.konark.Model.Posts;
import com.konarktimes.konark.ServerConvig.ServerHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    RecyclerView feature;
    RecyclerView latest;
    ArrayList<Posts> latest_posts;
    List<Posts> featured_posts;
       int lang_id;
    DrawerLayout drawer;
    NavigationView navigationView;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView seeFeatured,seeLatest;
    LinearLayout btnLayout;
    List<Categories> categoriesList;
   // Button cat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        swipeRefreshLayout=findViewById(R.id.swipe);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer =findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        //cat=findViewById(R.id.cat);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);
        btnLayout=findViewById(R.id.btnlayout);

        categoriesList=new ArrayList<>();
        seeFeatured=findViewById(R.id.seeFeatured);
        seeLatest=findViewById(R.id.seeLatest);

        seeLatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,SeeMoreLatestActivity.class));
            }
        });

        seeFeatured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,SeeMoreFeaturedActivity.class));
            }
        });
        loadCategories();



        SharedPreferences sharedPreferences = getSharedPreferences("lang_id",MODE_PRIVATE);
        lang_id=sharedPreferences.getInt("lang_id",-1);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawer,toolbar,
                R.string.nav_drawer_open,R.string.nav_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        feature = findViewById(R.id.featured);
        latest = findViewById(R.id.latest);

        toolbar.setTitleTextColor(Color.parseColor("#000000"));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFeatured();
                loadNews();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000);

            }
        });

       /* cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,CategoryActivity.class));
            }
        });*/




        latest_posts = new ArrayList<>();
        featured_posts=new ArrayList<>();

        feature.setHasFixedSize(true);
        latest.setHasFixedSize(true);

        latest.setLayoutManager(new GridLayoutManager(HomeActivity.this,2));
        loadNews();
        latest.setAdapter(new NewsAdapter(HomeActivity.this, latest_posts,20));
        VerticalSpacingItemDecoration itemDecoration=new VerticalSpacingItemDecoration(10);
        latest.addItemDecoration(itemDecoration);

        HorizontalSpacingItemDecoration horizontalSpacingItemDecoration=new HorizontalSpacingItemDecoration(15);
        feature.addItemDecoration(horizontalSpacingItemDecoration);
        HorizontalSpacingItemDecoration horizontalSpacingItemDecoration2=new HorizontalSpacingItemDecoration(8);
        latest.addItemDecoration(horizontalSpacingItemDecoration2);

        feature.setLayoutManager(new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.HORIZONTAL,false));
        loadFeatured();


    }

    public void loadNews() {
        String url = ServerHelper.getUrl()+ServerHelper.getLatestUrl();
         StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       Gson gson=new Gson();
                        Type type=new TypeToken<ArrayList<Posts>>(){}.getType();
                        latest_posts=gson.fromJson(response,type);
                        latest.setAdapter(new NewsAdapter(HomeActivity.this, latest_posts,20));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeActivity.this, "ERROR :(\n"+error, Toast.LENGTH_LONG).show();
                    }
                })
        {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("lang_id", Integer.toString(lang_id));
                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(HomeActivity.this);
        queue.add(stringRequest);
    }

    public void loadFeatured(){
        String url=ServerHelper.getUrl()+ServerHelper.getFeaturedUrl();
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson=new Gson();
                        Type type=new TypeToken<ArrayList<Posts>>(){}.getType();
                        featured_posts=gson.fromJson(response,type);
                        feature.setAdapter(new FeatureAdapter(HomeActivity.this, featured_posts,5));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeActivity.this, "Error :(\n"+error, Toast.LENGTH_SHORT).show();

                    }
                })
        {
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("lang_id",Integer.toString(lang_id));
                return params;

            }
        };
        RequestQueue queue=Volley.newRequestQueue(HomeActivity.this);
        queue.add(request);


    }

    @Override
    public void onBackPressed() {
    if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
        super.onBackPressed();
    }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.home:startActivity(new Intent(this,HomeActivity.class));
                break;
            case  R.id.bookmark:
                break;
            case R.id.categories:startActivity(new Intent(this,CategoryActivity.class));
                break;
            case R.id.home_categories:
                break;
            case R.id.instagram:
                break;
            case R.id.facebook:
                break;
            case R.id.messenger:
                break;
            case R.id.email:
                break;
            case R.id.tAc:
                break;
            case R.id.policy:
                break;
            case R.id.FAQ:
                break;
            case R.id.share:
                break;
            case R.id.rate:
                break;
            case R.id.settings:
                break;
            case R.id.exit:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:startActivity(new Intent(HomeActivity.this,SearchActivity.class));
            break;
            case R.id.notify:startActivity(new Intent(HomeActivity.this,NotificationActivity.class));
            break;

        }
        return true;
    }

    public void loadCategories(){
        String url= ServerHelper.getUrl()+ServerHelper.getCategoryUrl();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson=new Gson();
                        Type type=new TypeToken<ArrayList<Categories>>(){}.getType();
                        categoriesList=gson.fromJson(response,type);

                        final Button btnArray[]=new Button[categoriesList.size()];


                        for(int i=0;i<categoriesList.size();i++){
                          String[] colorArray=getResources().getStringArray(R.array.mdcolor_random);
                          String rnCol=colorArray[new Random().nextInt(colorArray.length)];
                           final Categories obj=categoriesList.get(i);
                            btnArray[i]=new Button(HomeActivity.this);
                            btnArray[i].setText(obj.getName());
                            btnArray[i].setBackgroundColor(Color.parseColor(rnCol));
                            btnArray[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            btnArray[i].setTextColor(Color.WHITE);
                            //btnArray[i].layout(10,10,10,10);
                            btnLayout.addView(btnArray[i]);

                            btnArray[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(HomeActivity.this,SelectedCategoryActivity.class);
                                    intent.putExtra("category_id",obj.getId());
                                    intent.putExtra("category_name",obj.getName());
                                    startActivity(intent);
                                }
                            });
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                SharedPreferences sharedPreferences=getSharedPreferences("lang_id",MODE_PRIVATE);
                params.put("lang_id",Integer.toString(sharedPreferences.getInt("lang_id",-1)));
                return  params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(stringRequest);

    }
}
