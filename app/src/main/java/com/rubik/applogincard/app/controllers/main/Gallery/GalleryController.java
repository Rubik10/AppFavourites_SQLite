package com.rubik.applogincard.app.controllers.main.Gallery;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.rubik.applogincard.Activity.GalleryActivity;
import com.rubik.applogincard.R;
import com.rubik.applogincard.model.Images;

import java.util.List;

    /**
     * Created by Rubik on 4/7/16.
     */
    public class GalleryController {
        public static final String TAG = GalleryController.class.getSimpleName();
        private GalleryActivity galleryActiviy;

        private RecyclerView recyclerView;
        private MyRecycleGalleryAdapter myAdapter;
        private ViewPager viewPager;
        private List<Images> listImages;

        public static int pagerPosition = 0;

        public GalleryController(GalleryActivity galleryActivity, List<Images> listImages, int positionSelec) {
            this.galleryActiviy = galleryActivity;
            this.listImages = listImages;

            pagerPosition = positionSelec; //
            initGallery();
        }

        private  void initGallery() {
            InitViewPager();
            initRecycleView();
        }


        private void InitViewPager () {
            viewPager = (ViewPager) galleryActiviy.findViewById(R.id.pager);
            final MyViewPagerGalleryAdapter pagerAdapter = new MyViewPagerGalleryAdapter(galleryActiviy,listImages);
            viewPager.setAdapter(pagerAdapter);
            viewPager.setOffscreenPageLimit(listImages.size()); // set the limit of images
            setCurrentItem(pagerPosition); // current position

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                     @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    pagerPosition = position;   //
                    displayImageInfo(position); //  Display Text ant pos of the image selected
                    recyclerView.scrollToPosition(position); // moves the gallery (recicleView) to the selected positon
                    myAdapter.setSelected(position);    // select the position of the page selected in the gallery with the sharedPref
                }

                @Override
                public void onPageSelected(int position) {}

                @Override
                public void onPageScrollStateChanged(int state) {}
            });
        }

        private void displayImageInfo(int position) {
            TextView lblCount = (TextView) galleryActiviy.findViewById(R.id.count);
            TextView lblTittle = (TextView) galleryActiviy.findViewById(R.id.titleImg);

            lblCount.setText((position + 1) + " of " + listImages.size());
            lblTittle.setText(listImages.get(position).getName());
        }

        public void setCurrentItem(int position) {
            viewPager.setCurrentItem(position,true);
            displayImageInfo(pagerPosition);
        }

        private void initRecycleView() {
            recyclerView = (RecyclerView) galleryActiviy.findViewById(R.id.recycler_view);

            myAdapter = new MyRecycleGalleryAdapter(galleryActiviy, listImages);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(galleryActiviy.getApplicationContext(), 1, GridLayout.HORIZONTAL,false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(myAdapter);

            myAdapter.setSelected(pagerPosition); // select the image when the gallery is opened for the first time

            recyclerView.addOnItemTouchListener(
                    new RecycleItemClickListener(galleryActiviy.getApplicationContext(), new RecycleItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                myAdapter.setSelected(position);    // select the selected image by touch
                                viewPager.setCurrentItem(position); // select the current image to display int the ViewPager
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })
            );
            myAdapter.notifyDataSetChanged();
        }



    }
