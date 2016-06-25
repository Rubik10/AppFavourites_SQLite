package com.rubik.applogincard.app.controllers.main;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rubik.applogincard.Activity.ImageActivity;
import com.rubik.applogincard.Activity.MainActivity;
import com.rubik.applogincard.R;
import com.rubik.applogincard.app.Utils.SessionManager;
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
        private List<Images> listImages;
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

                // Thumbail
            Picasso.with(context.getApplicationContext()).load(
                    image.getUrl())
                    .resize(240, 300)
                    .into(holder.imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                    Log.d("Picasso", "  Carga de imagenes correcta  :" + image.getName());
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
                    Toast.makeText(context, "Recycle Click " + holder.txtImage.getText().toString().toUpperCase(), Toast.LENGTH_SHORT).show();
                    intentImageActivity(image);
                }
            });

                // Fav Button
            holder.imgFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                public void onClick(View v) {
                        //Check the Text on the top for choose the accion -> insert/delete
                    TextView txtTitleAPP = (TextView) MainActivity.activity.findViewById(R.id.love_music);
                    CharSequence titleAPP = txtTitleAPP.getText();

                    if (titleAPP.equals("MASTER APP FAVOURITES")) {
                        deleteFavourite(holder, image);
                    } else if (titleAPP.toString().equals("MASTER APP")){
                        insertFavourite(holder,image);
                    }
                }
            });
        }


        private void insertFavourite(MyViewHolder holder,Images image) {
            FavoritesSQL favs = new FavoritesSQL();
            idUserLoged = Integer.valueOf(MainActivity.userLogued.get(SessionManager.ID_USER)); // get the login user ID

            if (!favs.isExistFavorite(idUserLoged, image.getIdImage())  ) { //Check the favourite
                favs.addFavorite(new Favorites(new Users(idUserLoged), new Images(image.getIdImage())));
                    Log.d("Insertar Favorito ", "nuevo favorito insertado");
                holder.imgFav.setImageResource(R.drawable.star_fav);
                Snackbar.make(holder.imgFav, "New favorite inserted : " + holder.txtImage.getText(), Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(holder.imgFav, "Favorite " + holder.txtImage.getText().toString().toUpperCase() + " is already exist in yout Favourite list", Snackbar.LENGTH_LONG).show();
            }
        }

        private void deleteFavourite(MyViewHolder holder, Images image ) {
            FavoritesSQL favs = new FavoritesSQL();

            idUserLoged = Integer.valueOf(MainActivity.userLogued.get(SessionManager.ID_USER));
            favs.deleteFavorite(idUserLoged, image.getIdImage());
                Log.d("Eliminar Favorito ", " favorito eliminado");

            holder.imgFav.setImageResource(R.drawable.star_fav);
            Snackbar.make(holder.imgFav, "Favorite deleted : " + holder.txtImage.getText() , Snackbar.LENGTH_LONG).show();
        }

            //Show selected image in new Activity
        private void intentImageActivity (Images image) {
            Intent intent = new Intent(context.getApplicationContext(),ImageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            intent.putExtra("url", image.getUrl());
            intent.putExtra("name", image.getName());
            context.getApplicationContext().startActivity(intent);
        }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
                private TextView txtImage;
                private ImageView imageView;
                 public ImageView imgFav;

                public MyViewHolder(View view) {
                    super(view);
                    // view.refreshDrawableState();

                    txtImage = (TextView) view.findViewById(R.id.title);
                    imageView = (ImageView) view.findViewById(R.id.thumbnail);
                    imgFav = (ImageView) view.findViewById(R.id.fav);

                }

            }
        }
