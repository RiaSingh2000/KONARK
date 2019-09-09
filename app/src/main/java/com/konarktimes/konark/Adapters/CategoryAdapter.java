package com.konarktimes.konark.Adapters;

import android.icu.util.ULocale;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.konarktimes.konark.Categories.NewsFragment;
import com.konarktimes.konark.Model.Categories;
import com.konarktimes.konark.Model.Posts;

import java.util.List;


public class CategoryAdapter extends FragmentStatePagerAdapter {

    int noOfTabs;
    List<Categories> news;

    public CategoryAdapter(FragmentManager fm, int n,List<Categories> news) {
        super(fm);
        this.noOfTabs=n;
        this.news=news;

    }

    @Override
    public Fragment getItem(int i) {
        Categories obj=news.get(i);
         return NewsFragment.newInstance(obj.getId());
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
