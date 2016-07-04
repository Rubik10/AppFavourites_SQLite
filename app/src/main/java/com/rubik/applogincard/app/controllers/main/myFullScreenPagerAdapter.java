package com.rubik.applogincard.app.controllers.main;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.rubik.applogincard.R;
import com.rubik.applogincard.app.Utils.TouchImageView;
import com.rubik.applogincard.model.Images;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

    /**
         * Created by Rubik on 27/6/16.
     */
    public class myFullScreenPagerAdapter extends PagerAdapter {
        private Activity _activity;
        private List<Images> listImages;

        public myFullScreenPagerAdapter (Activity activity, List<Images> listImages) {
            this._activity = activity;
            this.listImages = listImages;
        }

             @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = inflater.inflate(R.layout.img_fullscreen_layouts, container,false);
            TouchImageView imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
            Button btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

                //load image list
            Picasso.with(_activity).load(listImages.get(position).getUrl()).fit().into(imgDisplay, new Callback() {
                                @Override
                                public void onSuccess() {
                                    //  Log.d("Picasso", "  Carga de imagenes correcta  :" + image.getName());
                                }

                                @Override
                                public void onError() {
                                  //  Log.d("Picasso", "  Error en la carga de imagenes : " + image.getName());
                                }
                            });
                // close button click event
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _activity.finish();
                }
            });

            ((ViewPager) container).addView(viewLayout);

            return viewLayout;
        }

            @Override
        public int getCount() {
            return listImages.size();
        }

            @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

            @Override
        public CharSequence getPageTitle(int position) {
            return listImages.get(position).getName();
        }

                @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);
        }


    }
