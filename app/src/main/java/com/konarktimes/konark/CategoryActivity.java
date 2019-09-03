package com.konarktimes.konark;

import android.app.DownloadManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

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
import com.konarktimes.konark.Categories.Entertainment;
import com.konarktimes.konark.Categories.Fashion;
import com.konarktimes.konark.Categories.Food;
import com.konarktimes.konark.Categories.Lifestyle;
import com.konarktimes.konark.Categories.Music;
import com.konarktimes.konark.Categories.News;
import com.konarktimes.konark.Categories.NewsUpdates;
import com.konarktimes.konark.Categories.Politics;
import com.konarktimes.konark.Categories.Sports;
import com.konarktimes.konark.Model.Categories;
import com.konarktimes.konark.ServerConvig.ServerHelper;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity /*implements Entertainment.OnFragmentInteractionListener, Fashion.OnFragmentInteractionListener, Food.OnFragmentInteractionListener,
        Lifestyle.OnFragmentInteractionListener, Music.OnFragmentInteractionListener, News.OnFragmentInteractionListener,
        NewsUpdates.OnFragmentInteractionListener, Politics.OnFragmentInteractionListener, Sports.OnFragmentInteractionListener*/{

    TabLayout tabLayout;
    ViewPager viewPager;
    CategoryAdapter categoryAdapter;
    Toolbar toolbar;
    List<Categories> categoriesList;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        tabLayout=findViewById(R.id.tabLayout);
        toolbar=findViewById(R.id.toolbar);
        frameLayout=findViewById(R.id.frame);

        Entertainment entertainment=new Entertainment();


        setSupportActionBar(toolbar);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        categoriesList=new ArrayList<>();
        loadCategories();

       /* tabLayout.addTab(tabLayout.newTab().setText("Entertainment"));
        tabLayout.addTab(tabLayout.newTab().setText("Fashion"));
        tabLayout.addTab(tabLayout.newTab().setText("Food"));
        tabLayout.addTab(tabLayout.newTab().setText("Lifestyle"));
        tabLayout.addTab(tabLayout.newTab().setText("Music"));
        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.addTab(tabLayout.newTab().setText("News Updates"));
        tabLayout.addTab(tabLayout.newTab().setText("Politics"));
        tabLayout.addTab(tabLayout.newTab().setText("Sports"));*/

       for(int i=0;i<categoriesList.size();i++) {
          Categories obj=categoriesList.get(i);
           tabLayout.addTab(tabLayout.newTab()
                   .setText(obj.getName()));
       }



        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setBackgroundResource(R.color.overlay_light_90);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager=findViewById(R.id.pager);
        categoryAdapter =new CategoryAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(categoryAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }


   /*@Override
    public void onFragmentInteraction(Uri uri) {

    }*/


    public void loadCategories(){
        String url= ServerHelper.getUrl()+ServerHelper.getCategoryUrl();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson=new Gson();
                        Type type=new TypeToken<ArrayList<Categories>>(){}.getType();
                        categoriesList=gson.fromJson(response,type);

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
        RequestQueue requestQueue= Volley.newRequestQueue(CategoryActivity.this);
        requestQueue.add(stringRequest);

    }
}
