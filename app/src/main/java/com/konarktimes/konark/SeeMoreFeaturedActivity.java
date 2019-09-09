package com.konarktimes.konark;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.konarktimes.konark.Adapters.FeatureAdapter;
import com.konarktimes.konark.Adapters.NewsAdapter;
import com.konarktimes.konark.Model.Posts;
import com.konarktimes.konark.ServerConvig.ServerHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeeMoreFeaturedActivity extends AppCompatActivity {
    RecyclerView all;
    Toolbar toolbar;
    int lang_id;
    ArrayList<Posts> all_latest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more_featured);

        SharedPreferences sharedPreferences = getSharedPreferences("lang_id",MODE_PRIVATE);
        lang_id=sharedPreferences.getInt("lang_id",-1);
        all=findViewById(R.id.all);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        all_latest=new ArrayList<>();

        all.setHasFixedSize(true);
        all.setLayoutManager(new GridLayoutManager(SeeMoreFeaturedActivity.this,2));
        getAll();


    }

    public void getAll(){
        String url= ServerHelper.getUrl()+ServerHelper.getFeaturedUrl();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson=new Gson();
                        Type type=new TypeToken<ArrayList<Posts>>(){}.getType();
                        all_latest=gson.fromJson(response,type);
                        all.setAdapter(new NewsAdapter(SeeMoreFeaturedActivity.this,all_latest,-1));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SeeMoreFeaturedActivity.this, "Error:(\n"+error, Toast.LENGTH_SHORT).show();

                    }
                })

        {
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("lang_id",Integer.toString(lang_id));
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(SeeMoreFeaturedActivity.this);
        requestQueue.add(stringRequest);
    }
}
