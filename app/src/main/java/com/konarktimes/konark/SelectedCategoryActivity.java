package com.konarktimes.konark;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.konarktimes.konark.Adapters.CategoryAdapter;
import com.konarktimes.konark.Model.Categories;

import java.util.ArrayList;
import java.util.List;

public class SelectedCategoryActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    CategoryAdapter categoryAdapter;
    List<Categories> categoriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        toolbar=findViewById(R.id.toolbar);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.view_pager);
        categoriesList=new ArrayList<>();

        int category_id=getIntent().getIntExtra("category_id",0);
        String category_name=getIntent().getStringExtra("category_name");
        categoriesList.add(new Categories(category_name,category_id));
        setSupportActionBar(toolbar);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        toolbar.setTitleTextColor(Color.BLACK);
        toolbar.setBackgroundResource(R.color.overlay_light_90);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout.addTab(tabLayout.newTab()
                .setText(category_name));

        categoryAdapter =new CategoryAdapter(getSupportFragmentManager(),1,categoriesList);
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


    }

