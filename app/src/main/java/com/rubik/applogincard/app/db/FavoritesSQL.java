package com.rubik.applogincard.app.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.rubik.applogincard.app.db.SQLiteManager.SQLiteManager;
import com.rubik.applogincard.model.Favorites;
import com.rubik.applogincard.model.Images;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rubik on 19/6/16.
 */
public class FavoritesSQL { //extends SQLiteHandler

    private static final String TAG = FavoritesSQL.class.getSimpleName();
    //Columns Table
    private static final String FAV_ID = "idFav";
    private static final String USER_ID = "idUser";
    private static final String IMAGE_ID = "idImg";
    //Table
    public static final String FAV_TABLE = "FAVORITES";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + FAV_TABLE + " (\n" +
                    FAV_ID + " INTEGER PRIMARY KEY NOT NULL,\n" +
                    USER_ID + " INTEGER NOT NULL,\n" +
                    IMAGE_ID + " INTEGER NOT NULL ,\n" +
                    " UNIQUE (" + USER_ID + "," + IMAGE_ID + ") , \n" +
                    " FOREIGN KEY (" + IMAGE_ID + ") REFERENCES IMAGES (" + IMAGE_ID + ") , \n" +
                    " FOREIGN KEY (" + USER_ID + ") REFERENCES USERS (" + USER_ID + ") \n" +
                    ")";

    private Favorites favorite;


    public FavoritesSQL() {favorite = new Favorites();}

    public static String createTableFavorites() {
        return CREATE_TABLE;
    }


                 /*
                    -------------------------------------------
                                     CRUD
                    -------------------------------------------
                 */

    public void addFavorite (Favorites favorites) {
        SQLiteDatabase db = SQLiteManager.getConexion().connect("write");

        ContentValues values = new ContentValues();
        values.put(USER_ID, String.valueOf(favorites.getUser().getIdUser()) );
        values.put(IMAGE_ID, String.valueOf(favorites.getImage().getIdImage()) );

        //Insert
        db.insert(FAV_TABLE,null,values);
        SQLiteManager.getConexion().closeDB();

        Log.d(TAG, "Insert a new Favorite image  into sqlite");
    }


    public void deleteFavorite (int idUser, int idImg) {
        SQLiteDatabase db = SQLiteManager.getConexion().connect("write");

        db.delete(
                FAV_TABLE ,
                USER_ID + " = ? AND " + IMAGE_ID + " = ?",
                new String[] { String.valueOf(idUser), String.valueOf(idImg) }
        );

        SQLiteManager.getConexion().closeDB();
        Log.d(TAG, "Deleted Favorites  from sqlite");
    }

    public void deleteAllFavorites () {
        SQLiteDatabase db = SQLiteManager.getConexion().connect("write");
        db.delete(FAV_TABLE,null,null);

        Log.d(TAG, "Deleted all Favorites info from sqlite");
    }

    public boolean isExistFavorite (int idUser, int idImg) {
        boolean existFav = true;
        SQLiteDatabase db = SQLiteManager.getConexion().connect("read");
        String query = "SELECT " + USER_ID + ", " + IMAGE_ID + "\n" +
                " FROM FAVORITES \n" +
                " WHERE " + USER_ID + " = " + idUser + " " +
                " AND " + IMAGE_ID + " = " + idImg + " LIMIT 1";

        try {
            Cursor cursor = db.rawQuery(query,null);
            int rows = cursor.getCount();
            if (rows == 0) {
                existFav = false; // Favourite is already exist
            }
            cursor.close();
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        } finally {
            SQLiteManager.getConexion().closeDB();
        }
        return existFav;
    }


    public List<Images> getAllFavorites () {
        String query = "SELECT I.idImg AS imageID , nameIMG, URLIMG  \n" +
                " FROM FAVORITES F , IMAGES I \n" +
                " WHERE F.idImg = I.idImg\n";
        List<Images> listFavorites = new ArrayList<Images>();

        try {
            SQLiteDatabase db = SQLiteManager.getConexion().connect("read");
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                       /* favorite.setIdFav(Integer.parseInt(cursor.getString(0)));
                        favorite.getUser().setIdUser(Integer.parseInt(cursor.getString(1)));
                        favorite.getImage().setIdImage(Integer.parseInt(cursor.getString(2)));*/

                        Favorites fav = new Favorites();
                        fav.setImage(new Images(
                                Integer.parseInt(cursor.getString(0)) ,
                                cursor.getString(1) ,
                                cursor.getString(2)
                        ));

                             //add to list
                        listFavorites.add(fav.getImage());
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        } finally {
            SQLiteManager.getConexion().closeDB();
        }

        return listFavorites;
    }


            /*
               * Cogemos la url de la imagen guardad en la db.
               * * Ver como con la URl descargar una imagen de forma asincrona y cargarla en un grid.
               * -> Libreria Picasso.
            */

    public List<Images> getAllFavoritesByUser (int idUser) {
        String query = "SELECT I.idImg AS imageID , nameIMG, URLIMG  \n" +
                " FROM FAVORITES F , IMAGES I \n" +
                " WHERE F.idImg = I.idImg\n" +
                " AND idUser = " + idUser;

        List<Images> listFavsByUser = new ArrayList<Images>();

        try {
            SQLiteDatabase db = SQLiteManager.getConexion().connect("read");
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Favorites fav = new Favorites();
                        fav.setImage(new Images(
                                Integer.parseInt(cursor.getString(0)) ,
                                cursor.getString(1) ,
                                cursor.getString(2)
                        ));
                       /* Images image = new Images();
                        image.setIdImage(Integer.parseInt(cursor.getString(0)));
                        image.setName(cursor.getString(1));
                        image.setUrl(cursor.getString(2));*/

                        /*fav.getImage().setIdImage(Integer.parseInt(cursor.getString(0)) );
                        fav.getImage().setName(cursor.getString(1));
                        fav.getImage().setUrl(cursor.getString(2));*/

                            //add to list
                        listFavsByUser.add(fav.getImage());
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        } finally {
            SQLiteManager.getConexion().closeDB();
        }

        return listFavsByUser;
    }

    public List<Images> getAllFavoritesByUserPerCategory (int idUser, int idCategory) {

        String query = "SELECT i.idImg, nameIMG , URLIMG " +
                " FROM FAVORITES F , IMAGES I, CATEGORIES C \n" +
                " WHERE F.idImg = I.idImg\n" +
                " AND i.idCat = c.idCategory " +
                " AND idUser = " + idUser +
                " AND idCat = " + idCategory;

        List<Images> listFavsByUser = new ArrayList<Images>();

        try {
            SQLiteDatabase db = SQLiteManager.getConexion().connect("read");
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Favorites fav = new Favorites();
                        fav.setImage(new Images(
                                Integer.parseInt(cursor.getString(0)) ,
                                cursor.getString(1) ,
                                cursor.getString(2)
                        ));
                          /*  Images image = new Images();
                            image.setIdImage(Integer.parseInt(cursor.getString(0)));
                            image.setName(cursor.getString(1));
                            image.setUrl(cursor.getString(2));*/
                                //add to list
                            listFavsByUser.add(fav.getImage());
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        } finally {
            SQLiteManager.getConexion().closeDB();
        }

        return listFavsByUser;
    }



}

