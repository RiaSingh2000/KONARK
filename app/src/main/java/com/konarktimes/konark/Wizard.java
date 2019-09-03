package com.konarktimes.konark;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Wizard extends AppCompatActivity {

        private static final int MAX_STEP = 4;

        private ViewPager viewPager;
        private MyViewPagerAdapter myViewPagerAdapter;
        private String about_title_array[] = {
                "Chose from courses",
                "Pay the fee",
                "Enjoy premium content",
                "Avail a lot of free offers"
        };
        private String about_description_array[] = {
                "Choose from a lot of available courses and excel in your domain",
                "Simply pay the fees for the subject you are interested in",
                "Enjoy premium content with our super fast servers.",
                "Take advantage of thousands of free videos alongside!",
        };
        private int about_images_array[] = {
                R.drawable.server,
                R.drawable.rupee,
                R.drawable.education,
                R.drawable.free
        };

        private int bg_images_array[] = {
                R.drawable.icourse,
                R.drawable.ipay,
                R.drawable.iread,
                R.drawable.ifree
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_wizard);
            SharedPreferences pref = getSharedPreferences("skip", Context.MODE_PRIVATE);
            if(pref.getBoolean("activity_executed", false)){
                Intent intent = new Intent(this,LanguageActivity.class);
                startActivity(intent);
                finish();
            }
            viewPager = (ViewPager) findViewById(R.id.view_pager);

            // adding bottom dots
            bottomProgressDots(0);

            myViewPagerAdapter = new MyViewPagerAdapter();
            viewPager.setAdapter(myViewPagerAdapter);
            viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        }


        private void bottomProgressDots(int current_index) {
            LinearLayout dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
            ImageView[] dots = new ImageView[MAX_STEP];

            dotsLayout.removeAllViews();
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new ImageView(this);
                int width_height = 15;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
                params.setMargins(10, 10, 10, 10);
                dots[i].setLayoutParams(params);
                dots[i].setImageResource(R.drawable.shape_circle);
                dots[i].setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
                dotsLayout.addView(dots[i]);
            }

            if (dots.length > 0) {
                dots[current_index].setImageResource(R.drawable.shape_circle);
                dots[current_index].setColorFilter(getResources().getColor(R.color.light_green_600), PorterDuff.Mode.SRC_IN);
            }
        }

        //  viewpager change listener
        ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(final int position) {
                bottomProgressDots(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        };

        /**
         * View pager adapter
         */
      //Remove from here
        public class MyViewPagerAdapter extends PagerAdapter {
            private LayoutInflater layoutInflater;
            private Button btnNext;

            public MyViewPagerAdapter() {
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View view = layoutInflater.inflate(R.layout.item_card_wizard_bg, container, false);
                ((TextView) view.findViewById(R.id.title)).setText(about_title_array[position]);
                ((TextView) view.findViewById(R.id.description)).setText(about_description_array[position]);
                ((ImageView) view.findViewById(R.id.image)).setImageResource(about_images_array[position]);
                ((ImageView) view.findViewById(R.id.image_bg)).setImageResource(bg_images_array[position]);

                btnNext = (Button) view.findViewById(R.id.btn_next);

                if (position == about_title_array.length - 1) {
                    btnNext.setText("Get Started");
                } else {
                    btnNext.setText("Next");
                }


                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int current = viewPager.getCurrentItem() + 1;
                        if (current < MAX_STEP) {
                            // move to next screen
                            viewPager.setCurrentItem(current);
                        } else {
                            SharedPreferences pref = getSharedPreferences("skip", Context.MODE_PRIVATE);
                            SharedPreferences.Editor ed = pref.edit();
                            ed.putBoolean("activity_executed", true);
                            ed.commit();
                            startActivity(new Intent(Wizard.this,LanguageActivity.class));
                            Wizard.this.finish();

                        }
                    }
                });

                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return about_title_array.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object obj) {
                return view == obj;
            }


            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                View view = (View) object;
                container.removeView(view);
            }

    }

}
