package com.konarktimes.konark;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
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
import com.konarktimes.konark.Adapters.NewsAdapter;
import com.konarktimes.konark.Common.HorizontalSpacingItemDecoration;
import com.konarktimes.konark.Common.VerticalSpacingItemDecoration;
import com.konarktimes.konark.Model.Posts;
import com.konarktimes.konark.ServerConvig.ServerHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView searchResults;
    int lang_id;
    ArrayList<Posts> results;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchResults=findViewById(R.id.searchResults);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        results=new ArrayList<>();




        SharedPreferences sharedPreferences = getSharedPreferences("lang_id",MODE_PRIVATE);
        lang_id=sharedPreferences.getInt("lang_id",-1);

        searchResults.setLayoutManager(new GridLayoutManager(SearchActivity.this,2));
        searchResults.setHasFixedSize(true);

        HorizontalSpacingItemDecoration horizontalSpacingItemDecoration=new HorizontalSpacingItemDecoration(10);
        searchResults.addItemDecoration(horizontalSpacingItemDecoration);

        VerticalSpacingItemDecoration verticalSpacingItemDecoration=new VerticalSpacingItemDecoration(15);
        searchResults.addItemDecoration(verticalSpacingItemDecoration);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.search_menu,menu);

        MenuItem searchItem=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search here");
        searchView.onActionViewExpanded();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()){
                    loadSearchResults(newText);
                }

                return false;
            }
        });
        return true;
    }

    public void loadSearchResults(final String newText){
        String url= ServerHelper.getUrl()+ServerHelper.getSearchResults();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson=new Gson();
                        Type type=new TypeToken<ArrayList<Posts>>(){}.getType();
                        results=gson.fromJson(response,type);
                        searchResults.setAdapter(new NewsAdapter(SearchActivity.this,results,20));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchActivity.this, "Error :(\n"+error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            protected Map<String,String> getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("lang_id",Integer.toString(lang_id));
                params.put("search",newText);
                return params;

            }
        };

        RequestQueue queue= Volley.newRequestQueue(SearchActivity.this);
        queue.add(stringRequest);
    }


}
