package com.konarktimes.konark.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.konarktimes.konark.Categories.Entertainment;
import com.konarktimes.konark.Categories.Fashion;
import com.konarktimes.konark.Categories.Food;
import com.konarktimes.konark.Categories.Lifestyle;
import com.konarktimes.konark.Categories.Music;
import com.konarktimes.konark.Categories.News;
import com.konarktimes.konark.Categories.NewsUpdates;
import com.konarktimes.konark.Categories.Politics;
import com.konarktimes.konark.Categories.Sports;

public class CategoryAdapter extends FragmentStatePagerAdapter {

    int noOfTabs;

    public CategoryAdapter(FragmentManager fm, int n) {
        super(fm);
        this.noOfTabs=n;

    }

    @Override
    public Fragment getItem(int i) {
       /* switch (i) {
            case 0:
                Entertainment entertainment = new Entertainment();
                return entertainment;
            case 1:
                Fashion fashion = new Fashion();
                return fashion;
            case 2:
                Food food = new Food();
                return food;
            case 3:
                Lifestyle lifestyle=new Lifestyle();
                return lifestyle;
            case 4:
                Music music=new Music();
                return music;
            case 5:
                News news=new News();
                return news;
            case 6:
                NewsUpdates newsUpdates=new NewsUpdates();
                return  newsUpdates;
            case 7:
                Politics politics=new Politics();
                return politics;
            case 8:
                Sports sports=new Sports();
                return sports;
            default:
                return null;
        }*/
       return  null;
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
