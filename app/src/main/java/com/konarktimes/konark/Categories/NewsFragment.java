package com.konarktimes.konark.Categories;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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
import com.konarktimes.konark.R;
import com.konarktimes.konark.ServerConvig.ServerHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class NewsFragment extends Fragment {
    View view;
    List<Posts> news;
    int lang_id;

    public static NewsFragment newInstance(int category_id ){
        NewsFragment fragment=new NewsFragment();
        Bundle args=new Bundle();
        args.putInt("category_id",category_id);
        fragment.setArguments(args);
        return fragment;
    }

    int category_id;
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_news,container,false);
        SharedPreferences sharedPreferences =this.getActivity().getSharedPreferences("lang_id",MODE_PRIVATE);
        lang_id=sharedPreferences.getInt("lang_id",-1);
        category_id =getArguments().getInt("category_id",0);

        recyclerView=view.findViewById(R.id.news);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new HorizontalSpacingItemDecoration(8));
        recyclerView.addItemDecoration(new VerticalSpacingItemDecoration(10));
        news=new ArrayList<>();
        loadCategoryWise();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if(savedInstanceState==null){
            getFragmentManager().beginTransaction().add(R.id.frame,new NewsFragment()).commit();


        }*/

    }

    public void loadCategoryWise(){
        String url= ServerHelper.getUrl()+ServerHelper.getCategoryWiseUrl();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson=new Gson();
                        Type type= new TypeToken<ArrayList<Posts>>(){}.getType();
                        news=gson.fromJson(response,type);
                        recyclerView.setAdapter(new NewsAdapter(getActivity(), (ArrayList<Posts>) news,-1));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            protected Map<String,String> getParams()throws AuthFailureError{
                Map<String,String> params=new HashMap<String,String>();
                params.put("lang_id",Integer.toString(lang_id));
                params.put("category_id",Integer.toString(category_id));
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


}

