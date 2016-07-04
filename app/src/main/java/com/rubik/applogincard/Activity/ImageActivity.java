package com.rubik.applogincard.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.rubik.applogincard.R;
import com.rubik.applogincard.app.controllers.main.myFullScreenPagerAdapter;
import com.rubik.applogincard.app.controllers.main.myReciclerViewAdapter;

public class ImageActivity extends AppCompatActivity {

            @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_image_fullscreen);

            int pos = getIntent().getIntExtra("pos", 0);

            ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            myFullScreenPagerAdapter pagerAdapter = new myFullScreenPagerAdapter(this, myReciclerViewAdapter.listImages );
            viewPager.setAdapter(pagerAdapter);
            viewPager.setCurrentItem(pos);
            viewPager.setOffscreenPageLimit(myReciclerViewAdapter.listImages.size());

        }

    }
