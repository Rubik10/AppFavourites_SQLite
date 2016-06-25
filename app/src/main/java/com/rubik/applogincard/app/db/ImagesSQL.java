package com.rubik.applogincard.app.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.rubik.applogincard.app.db.SQLiteManager.SQLiteManager;
import com.rubik.applogincard.model.Images;

import java.util.ArrayList;
import java.util.List;

    /**
     * Created by Rubik on 22/6/16.
     */
    public class ImagesSQL {
        private static final String TAG = ImagesSQL.class.getSimpleName();
        private Images image;

        //Columns Table
        private static final String ID = "idImg";
        private static final String NAME = "nameIMG";
        private static final String URL = "urlIMG";
        private static final String FK_CATEGOTY = "idCat";

        //Table
        public static final String IMG_TABLE = "IMAGES";
        private static final String CREATE_TABLE =
                "CREATE TABLE " + IMG_TABLE + " (\n" +
                        ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                        NAME + " TEXT UNIQUE NOT NULL ,\n" +
                        URL + " TEXT , \n" +
                        FK_CATEGOTY + " INTEGER NOT NULL , \n" +
                        " FOREIGN KEY (" + FK_CATEGOTY + ") REFERENCES " + CategoriesSQL.CAT_TABLE + " (idCat)" +
                        ")";



        public ImagesSQL () {image = new Images();}

        public static String createTableImages () {
            return CREATE_TABLE;
        }


                         /*
                            -------------------------------------------
                                             CRUD
                            -------------------------------------------
                         */

        public void addImage ( Images images) {
            SQLiteDatabase db = SQLiteManager.getConexion().connect("write");

            ContentValues values = new ContentValues();
            values.put(NAME, images.getName());
            values.put(URL, images.getUrl());
            values.put(FK_CATEGOTY , images.getCat() );

            //Insert
            db.insert(IMG_TABLE,null,values);
            SQLiteManager.getConexion().closeDB();

            Log.d(TAG, "Insert a Image whit name = " + images.getName() + " into sqlite");
        }

        public int updateImage ( Images images) {
            SQLiteDatabase db = SQLiteManager.getConexion().connect("write");

            ContentValues values = new ContentValues();
            values.put(NAME, images.getName());
            values.put(URL, images.getUrl());

                        /* update */
            return db.update(
                    IMG_TABLE ,
                    values ,
                    ID + " = ?",
                    new String[] { String.valueOf(images.getIdImage()) }
            );

        }

        public void deleteImage ( int id) {
            SQLiteDatabase db = SQLiteManager.getConexion().connect("write");

            db.delete(
                    IMG_TABLE ,
                    ID + " = ?",
                    new String[] {String.valueOf(id)}
            );
            SQLiteManager.getConexion().closeDB();
            Log.d(TAG, "Deleted Image from sqlite");
        }

        public void deleteAllImage () {
            SQLiteDatabase db = SQLiteManager.getConexion().connect("write");
            db.delete(IMG_TABLE,null,null);
            SQLiteManager.getConexion().closeDB();
            Log.d(TAG, "Deleted all Image info from sqlite");
        }

        /*
            * Cogemos la url de la imagen guardad en la db.
            * * Ver como con la URl descargar una imagen de forma asincrona y cargarla en un grid.
            * -> Libreria Picasso.
         */
        public Images getImage (int id) {
            SQLiteDatabase db = SQLiteManager.getConexion().connect("read");

            try {
                Cursor cursor = db.query(
                        IMG_TABLE,
                        new String[]{ID, NAME, URL,FK_CATEGOTY},
                        ID + "=?",
                        new String[] { String.valueOf(id)}, null, null, null, null
                );

                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                       /* image = new Images(
                                Integer.parseInt(cursor.getString(0)), //ID
                                cursor.getString(1),    //NAme
                                cursor.getString(2) ,    //URL
                                cursor.getShort(3)
                        );*/
                    image = new Images();
                    image.setIdImage(cursor.getShort(0));
                    image.setName(cursor.getString(1));
                    image.setUrl(cursor.getString(3));
                    // image.getCat().setIdCategory(4);
                    cursor.close();
                }
            } catch (SQLiteException ex) {
                ex.printStackTrace();
            } finally {
                SQLiteManager.getConexion().closeDB();
            }

            return image;
        }

        public List<Images> getAllImage () {
            String query = "SELECT " + ID + "," + NAME + "," + URL + " FROM IMAGES ";
            List<Images> listImages = new ArrayList<Images>();

            SQLiteDatabase db = SQLiteManager.getConexion().connect("read");
            try {
                Cursor cursor = db.rawQuery(query, null);

                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            image = new Images();
                            image.setIdImage(Integer.parseInt(cursor.getString(0)));
                            image.setName(cursor.getString(1));
                            image.setUrl(cursor.getString(2));
                            //add to list
                            listImages.add(image);
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
            } catch (SQLiteException ex) {
                ex.printStackTrace();
            } finally {
                SQLiteManager.getConexion().closeDB();
            }

            return listImages;
        }

             /*
                Get all the Image of the Category selected

                /*
                   * Cogemos la url de la imagen guardad en la db.
                   * * Ver como con la URl descargar una imagen de forma asincrona y cargarla en un grid.
                   * -> Libreria Picasso.
            */

        public List<Images> getAllImagesByCategory (int idCat) {
            String query = "SELECT idImg, nameIMG , URLIMG " +
                    "FROM IMAGES " +
                    "WHERE idCat = " + idCat;
            List<Images> listImagesByCat = new ArrayList<Images>();

            try {
                SQLiteDatabase db = SQLiteManager.getConexion().connect("read");
                Cursor cursor = db.rawQuery(query, null);

                if (cursor != null && cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Images image = new Images();
                            image.setIdImage(Integer.parseInt(cursor.getString(0)));
                            image.setName(cursor.getString(1));
                            image.setUrl(cursor.getString(2));

                            //add to list
                            listImagesByCat.add(image);
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
            } catch (SQLiteException ex) {
                ex.printStackTrace();
            } finally {
                SQLiteManager.getConexion().closeDB();
            }

            return listImagesByCat;
        }


    }

