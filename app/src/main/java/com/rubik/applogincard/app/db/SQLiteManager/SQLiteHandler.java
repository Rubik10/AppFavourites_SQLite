package com.rubik.applogincard.app.db.SQLiteManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rubik.applogincard.app.db.CategoriesSQL;
import com.rubik.applogincard.app.db.FavoritesSQL;
import com.rubik.applogincard.app.db.ImagesSQL;
import com.rubik.applogincard.app.db.UserSQL;


    /**
        *Created by Rubik on 19/6/16.
            */

    public class SQLiteHandler extends SQLiteOpenHelper {

        private static final String TAG = SQLiteHandler.class.getSimpleName();
        private Context cxt;
        //DB Info
        public static final int DB_VERSION = 1;
        public static final String DB_NAME = "appDB";


        public SQLiteHandler(Context cxt) {
            super(cxt, DB_NAME, null, DB_VERSION);
        }

            @Override
        public void onCreate(SQLiteDatabase db) {
                Log.d(TAG, "---------------------------");
            db.execSQL(CategoriesSQL.createTableCategories());
                Log.d(TAG, "Tabla " + CategoriesSQL.CAT_TABLE + "  creada en DB " + DB_NAME + "");
            db.execSQL(ImagesSQL.createTableImages());
                Log.d(TAG, "Tabla " + ImagesSQL.IMG_TABLE  + "  creada en DB " + DB_NAME + "" );
            db.execSQL(UserSQL.createTableUser());
                Log.d(TAG, "Tabla " +  UserSQL.USER_TABLE + "  creada en DB " + DB_NAME + "" );
            db.execSQL(FavoritesSQL.createTableFavorites());
                Log.d(TAG, "Tabla " + FavoritesSQL.FAV_TABLE + "  creada en DB " + DB_NAME + "" );
                 Log.d(TAG, "---------------------------" );
        }

            @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

            // Drop table if existed, all data will be gone!!!
            db.execSQL("DROP TABLE IF EXISTS " + ImagesSQL.IMG_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + CategoriesSQL.CAT_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + UserSQL.USER_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + FavoritesSQL.FAV_TABLE);

            onCreate(db);
        }

    }
