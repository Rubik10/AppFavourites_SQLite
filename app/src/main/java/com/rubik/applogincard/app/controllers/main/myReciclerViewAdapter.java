package com.rubik.applogincard.app.controllers.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubik.applogincard.Activity.GalleryActivity;
import com.rubik.applogincard.Activity.ImageActivity;
import com.rubik.applogincard.R;
import com.rubik.applogincard.app.Utils.SessionManager;
import com.rubik.applogincard.app.Utils.UtilsApp;
import com.rubik.applogincard.app.db.FavoritesSQL;
import com.rubik.applogincard.model.Favorites;
import com.rubik.applogincard.model.Images;
import com.rubik.applogincard.model.Users;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
     * Created by Rubik on 24/6/16.
     */
    public class myReciclerViewAdapter extends RecyclerView.Adapter<myReciclerViewAdapter.MyViewHolder>{
        private Context context;
        public static List<Images> listImages;
        private int idUserLoged; // get the user logged ID for the favourites

        public myReciclerViewAdapter (Context cxt, List<Images> list ) {
            this.context = cxt;
            this.listImages = list;
        }

             @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);

             return new MyViewHolder(itemView);
        }


            @Override
        public int getItemCount() {
            return listImages.size();
        }


            @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final Images image = listImages.get(position);
                // text of card
            holder.txtImage.setText(image.getName());

            // setAnimation(holder.container, position);

                // Thumbail
            Picasso.with(context.getApplicationContext()).load(
                    image.getUrl())
                    .resize(240, 300)
                    .into(holder.imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                  //  Log.d("Picasso", "  Carga de imagenes correcta  :" + image.getName());
                                }
                                @Override
                                public void onError() {
                                    Log.d("Picasso", "  Error en la carga de imagenes : " + image.getName());
                                }
                            }
                    );


            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // intentImageActivity(image,holder);
                    intentGalleryActivity(image,holder);
                }
            });


                    // Fav Button

                // Changes the state of the star favs at loading the cards.
            if (MainController.isShowingFav) {
                holder.imgFav.setImageResource(R.drawable.star_fav);
            } else {
                holder.imgFav.setImageResource(R.drawable.star);
            }

            holder.imgFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                public void onClick(View v) {
                if (MainController.isShowingFav) { deleteFavourite(holder, image);
                } else {  insertFavourite(holder,image); }
                }
            });
        }


        private void insertFavourite(MyViewHolder holder,Images image) {
            FavoritesSQL favs = new FavoritesSQL();
            idUserLoged = Integer.valueOf(MainController.userLogued.get(SessionManager.ID_USER)); // get the login user ID

            if (!favs.isExistFavorite(idUserLoged, image.getIdImage())  ) { //Check the favourite
                favs.addFavorite(new Favorites(new Users(idUserLoged), new Images(image.getIdImage())));
                    Log.d("Insertar Favorito ", "nuevo favorito insertado");
                holder.imgFav.setImageResource(R.drawable.star_fav);
                UtilsApp.showSnackBar(holder.imgFav, "New favorite inserted : " + holder.txtImage.getText().toString().toUpperCase());
            } else {
                UtilsApp.showSnackBar(holder.imgFav, "Favorite " + holder.txtImage.getText().toString().toUpperCase() + " is already exist in yout Favourite list" );
            }
        }

        private void deleteFavourite(MyViewHolder holder, Images image ) {
            FavoritesSQL favs = new FavoritesSQL();

            idUserLoged = Integer.valueOf(MainController.userLogued.get(SessionManager.ID_USER));
            favs.deleteFavorite(idUserLoged, image.getIdImage());
                Log.d("Eliminar Favorito ", " favorito eliminado");

            holder.imgFav.setImageResource(R.drawable.star);
            UtilsApp.showSnackBar(holder.imgFav, "Favorite deleted : " + holder.txtImage.getText().toString().toUpperCase());
        }

            //Show selected image in new Activity
        private void intentImageActivity (Images image, MyViewHolder holder) {
            Intent intent = new Intent(context,ImageActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            intent.putExtra("url", image.getUrl());
            intent.putExtra("name", image.getName());
            intent.putExtra("position",holder.getAdapterPosition());
            context.startActivity(intent);
        }

            //Show selected image in new Activity
        private void intentGalleryActivity (Images image, MyViewHolder holder) {
            Intent intent = new Intent(context,GalleryActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            intent.putExtra("position",holder.getAdapterPosition());
            context.startActivity(intent);
        }

        private void setAnimation(ViewGroup container, int position) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            container.startAnimation(animation);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView txtImage;
            private ImageView imageView;
            public ImageView imgFav;

            public MyViewHolder(View view) {
                super(view);

                txtImage = (TextView) view.findViewById(R.id.title);
                imageView = (ImageView) view.findViewById(R.id.thumbnail);
                imgFav = (ImageView) view.findViewById(R.id.fav);
            }

        }


    }
