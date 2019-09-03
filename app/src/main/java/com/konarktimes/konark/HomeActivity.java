package com.konarktimes.konark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.konarktimes.konark.Adapters.featureAdapter;
import com.konarktimes.konark.Adapters.latestAdapter;
import com.konarktimes.konark.Common.HorizontalSpacingItemDecoration;
import com.konarktimes.konark.Common.VerticalSpacingItemDecoration;
import com.konarktimes.konark.Model.Posts;
import com.konarktimes.konark.ServerConvig.ServerHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    RecyclerView feature;
    RecyclerView latest;
    ArrayList<Posts> latest_posts;
    List<Posts> featured_posts;
       int lang_id;
    DrawerLayout drawer;
    NavigationView navigationView;
   // Button cat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer =findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        //cat=findViewById(R.id.cat);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);

        SharedPreferences sharedPreferences = getSharedPreferences("lang_id",MODE_PRIVATE);
        lang_id=sharedPreferences.getInt("lang_id",-1);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawer,toolbar,
                R.string.nav_drawer_open,R.string.nav_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        feature = findViewById(R.id.featured);
        latest = findViewById(R.id.latest);

        toolbar.setTitleTextColor(Color.parseColor("#000000"));

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
        loadLatest();
        latest.setAdapter(new latestAdapter(HomeActivity.this, latest_posts));
        VerticalSpacingItemDecoration itemDecoration=new VerticalSpacingItemDecoration(10);
        latest.addItemDecoration(itemDecoration);

        HorizontalSpacingItemDecoration horizontalSpacingItemDecoration=new HorizontalSpacingItemDecoration(15);
        feature.addItemDecoration(horizontalSpacingItemDecoration);
        HorizontalSpacingItemDecoration horizontalSpacingItemDecoration2=new HorizontalSpacingItemDecoration(8);
        latest.addItemDecoration(horizontalSpacingItemDecoration2);

        feature.setLayoutManager(new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.HORIZONTAL,false));
        loadFeatured();


    }

    public void loadLatest() {
        String url = ServerHelper.getUrl()+ServerHelper.getLatestUrl();
         StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       Gson gson=new Gson();
                        Type type=new TypeToken<ArrayList<Posts>>(){}.getType();
                        latest_posts=gson.fromJson(response,type);
                        latest.setAdapter(new latestAdapter(HomeActivity.this, latest_posts));

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
                        feature.setAdapter(new featureAdapter(HomeActivity.this, featured_posts));
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
}
