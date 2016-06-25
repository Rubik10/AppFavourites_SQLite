package com.rubik.applogincard.app.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.rubik.applogincard.app.db.SQLiteManager.SQLiteManager;
import com.rubik.applogincard.model.Categories;

import java.util.ArrayList;
import java.util.List;

;

    /**
        *   Created by Rubik on 22/6/16.
     */
    public class CategoriesSQL {

    private static final String TAG = CategoriesSQL.class.getSimpleName();
    public static final String CAT_TABLE = "CATEGORIES";
    //Columns Table
    private static final String ID = "idCategory";
    private static final String CATEGORY = "category";

    //Table
    private static final String CREATE_TABLE =
            "CREATE TABLE " + CAT_TABLE + " (\n" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                    CATEGORY + " TEXT UNIQUE NOT NULL \n" +
                    ")";

    private Categories category;

    public CategoriesSQL () {category=new Categories();}

    public static String createTableCategories () {
        return CREATE_TABLE;
    }


            /*
                -------------------------------------------
                                 CRUD
                -------------------------------------------
             */

    public void addCategory (Categories categories) {
        SQLiteDatabase db = SQLiteManager.getConexion().connect("write");
        ContentValues values = new ContentValues();
        values.put(CATEGORY, categories.getCategory());
        // values.put(IDIMG, String.valueOf(categories.getImage().getIdImage()) );

        //Insert
        db.insert(CAT_TABLE,null,values);
        SQLiteManager.getConexion().closeDB();

        Log.d(TAG, "Insert a Image whit name = " + categories.getCategory() + " into sqlite");
    }

    public int updateCategory (Categories categories) {
        SQLiteDatabase db = SQLiteManager.getConexion().connect("write");

        ContentValues values = new ContentValues();
        values.put(CATEGORY, categories.getCategory());
        // values.put(IDIMG, String.valueOf(categories.getImage().getIdImage()) );

            /* update */
        return db.update(
                CAT_TABLE ,
                values ,
                ID + " = ?",
                new String[] {  String.valueOf(categories.getIdCategory())  }
        );

    }

    public void deleteCategory (int id) {
        SQLiteDatabase db = SQLiteManager.getConexion().connect("write");

        db.delete(
                CAT_TABLE ,
                ID + " = ?",
                new String[] { String.valueOf(id) }
        );

        SQLiteManager.getConexion().closeDB();
        Log.d(TAG, "Deleted Category from sqlite");
    }

    public void deleteAllCategories () {
        SQLiteDatabase db = SQLiteManager.getConexion().connect("write");;
        db.delete(CAT_TABLE,null,null);
        SQLiteManager.getConexion().closeDB();
        Log.d(TAG, "Deleted all Categories info from sqlite");
    }


    public Categories getCategory (int id) {
        SQLiteDatabase db = SQLiteManager.getConexion().connect("read");

        try {
            Cursor cursor = db.query(
                    CAT_TABLE,
                    new String[]{
                            ID, CATEGORY},
                    ID + "=?",
                    new String[]{
                            String.valueOf(id)}, null, null, null, null
            );

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                category = new Categories(
                        Integer.parseInt(cursor.getString(0)), //ID
                        cursor.getString(1)   //NAme
                );
                cursor.close();
            }
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        } finally {
            SQLiteManager.getConexion().closeDB();
        }

        return category;
    }

    public List<String> getAllCategories () {
        String query = "SELECT idCategory, category FROM CATEGORIES";
        List<String> listCategories = new ArrayList<String>();

        try {
            SQLiteDatabase db = SQLiteManager.getConexion().connect("read");
            Cursor cursor = db.rawQuery(query,null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do{
                        category = new Categories();
                        category.setIdCategory(Integer.parseInt(cursor.getString(0)));
                        category.setCategory(cursor.getString(1));
                        //add to list
                        listCategories.add(category.getCategory());
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        } finally {
            SQLiteManager.getConexion().closeDB();
        }

        return listCategories;
    }

    public List<Categories> getAllCategories2 () {
        String query = "SELECT idCategory, category FROM CATEGORIES";
        List<Categories> listCategories = new ArrayList<Categories>();

        try {
            SQLiteDatabase db = SQLiteManager.getConexion().connect("read");
            Cursor cursor = db.rawQuery(query,null);

            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do{
                        category = new Categories();
                        category.setIdCategory(Integer.parseInt(cursor.getString(0)));
                        category.setCategory(cursor.getString(1));
                        //add to list
                        listCategories.add(category);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        } finally {
            SQLiteManager.getConexion().closeDB();
        }

        return listCategories;
    }


    }

