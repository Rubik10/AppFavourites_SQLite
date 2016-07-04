package com.rubik.applogincard.app.controllers.main.Gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.rubik.applogincard.Activity.GalleryActivity;
import com.rubik.applogincard.R;
import com.rubik.applogincard.model.Images;

import java.util.List;

    /**
     * Created by Rubik on 1/7/16.
     */
    public class MyViewPagerGalleryAdapter extends PagerAdapter {

        private GalleryActivity activity;
        private List<Images> listImages;
        private LayoutInflater inflater;

        public MyViewPagerGalleryAdapter (GalleryActivity galleryActivity, List<Images> listImages) {
            this.activity = galleryActivity;
            inflater = (LayoutInflater) galleryActivity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.listImages = listImages;
        }

            @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = inflater.inflate(R.layout.galley_pager_item, container, false);
            container.addView(itemView);

            final SubsamplingScaleImageView imageView = (SubsamplingScaleImageView) itemView.findViewById(R.id.image);

                // Asynchronously load the image and set the thumbnail of pager view with the Glide lib
           // Picasso.with(activity.getApplicationContext()).load(listImages.get(position).getUrl()).resize(120, 120).into(imageView);
            Glide.with(activity.getApplicationContext())
                    .load(listImages.get(position).getUrl())
                    .asBitmap()
                    .thumbnail(0.2f)
                    .fitCenter()
                    .into(new SimpleTarget<Bitmap>() {
                             @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            imageView.setImage(ImageSource.bitmap(bitmap));
                        }
                    });

            GalleryController.pagerPosition = position; // update the pager position

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent to Full Image
                }
            });

            return itemView;
        }

            @Override
        public int getCount() {
            return listImages.size();
        }

            @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

            @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }


    }
