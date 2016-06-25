package com.rubik.applogincard.app.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.rubik.applogincard.app.db.SQLiteManager.SQLiteManager;
import com.rubik.applogincard.model.Users;

import java.util.ArrayList;
import java.util.List;

    /**
     * Created by Rubik on 19/6/16.
     */

    //Chek TIMESPAN for create_at column

    public class UserSQL  { //extends SQLiteHandler
        private static final String TAG = UserSQL.class.getSimpleName();
        private static Users user;
            //Columns Table
        private static final String ID = "idUser";
        private static final String NAME = "name";
        private static final String MAIL = "mail";
        private static final String PASS = "pass";
        private static final String CREATED_AT = "created_at";
             //Table
        public static final String USER_TABLE = "USERS";
        private static final String CREATE_TABLE =
                "CREATE TABLE " + USER_TABLE + " (\n" +
                        ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
                        NAME + " TEXT NOT NULL,\n" +
                        MAIL + " TEXT UNIQUE , \n" +
                        PASS + " TEXT ,\n" +
                        CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL" +
                        ")";


        public UserSQL() {user = new Users();}

        public static String createTableUser() {
            return CREATE_TABLE;
        }


                    /*
                        -------------------------------------------
                                         CRUD
                        -------------------------------------------
                     */

        public void addUser (Users users) {
            try {
                SQLiteDatabase db = SQLiteManager.getConexion().connect("write");
                ContentValues values = new ContentValues();
                values.put(NAME, users.getName());
                values.put(MAIL, users.getMail());
                values.put(PASS, users.getPass());
                //Insert
                db.insert(USER_TABLE, null, values);
            } catch (SQLiteException ex) {
                ex.printStackTrace();
            } finally {
               // db.gegetConexion().closeDB();

            }

            Log.d(TAG, "Insert a User whit name = " + users.getName() + " into sqlite");
        }

        public int updateUser (Users users) {
            SQLiteDatabase db = SQLiteManager.getConexion().connect("write");
            ContentValues values = new ContentValues();
            values.put(NAME, users.getName());
            values.put(MAIL, users.getMail() );

                    /* update */
            return db.update (
                    USER_TABLE ,
                    values ,
                    ID + " = ?" ,
                    new String[] {String.valueOf(users.getIdUser())}
            );
        }

        public void deleteUser (int id) {
            try {
                SQLiteDatabase db = SQLiteManager.getConexion().connect("write");
                db.delete(USER_TABLE , ID + " = ?", new String[] {String.valueOf(id)});
            } catch (SQLiteException ex) {
                ex.printStackTrace();
            } finally {
                SQLiteManager.getConexion().closeDB();
            }

            Log.d(TAG, "Deleted User from sqlite");
        }

        public void deleteAllUsers () {
            SQLiteDatabase db = SQLiteManager.getConexion().connect("write");
            db.delete(USER_TABLE,null,null);
            SQLiteManager.getConexion().closeDB();

            Log.d(TAG, "Deleted all Users info from sqlite");
        }

        public boolean isUserExist (String mail) {
            try {
                SQLiteDatabase db = SQLiteManager.getConexion().connect("read");
                Cursor cursor = db.query (
                        USER_TABLE,
                        new String[] {ID} ,
                        MAIL + "=?",
                        new String[] {String.valueOf(mail) },null, null, null, null
                );

                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    cursor.close();
                    return true;
                }

            } catch (SQLiteException ex) {
                ex.printStackTrace();
            } finally {
                SQLiteManager.getConexion().closeDB();
            }

            return false;
        }

        public static Users getUser (int id) {
            try {
                SQLiteDatabase db = SQLiteManager.getConexion().connect("read");
                Cursor cursor = db.query (
                        USER_TABLE,
                        new String[] {ID, NAME, MAIL, PASS } ,
                        ID + "=?",
                        new String[] {String.valueOf(id) },null, null, null, null
                );

                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    user = new Users(
                            Integer.parseInt(cursor.getString(0)), //ID
                            cursor.getString(1) ,    //NAme
                            cursor.getString(2) ,    //MAIl
                            cursor.getString(3)      //PASS
                    );
                    cursor.close();
                }

            } catch (SQLiteException ex) {
                ex.printStackTrace();
            } finally {
                SQLiteManager.getConexion().closeDB();
            }

            return user;
        }

        public Users getUserByMail (String mail) {
            try {
                SQLiteDatabase db = SQLiteManager.getConexion().connect("read");

                Cursor cursor = db.query (
                        USER_TABLE,
                        new String[] {ID, NAME, MAIL, PASS } ,
                        MAIL + "=?",
                        new String[] {mail},null, null, null, null
                );

                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();

                    user = new Users(
                            Integer.parseInt(cursor.getString(0)), //ID
                            cursor.getString(1) ,    //NAme
                            cursor.getString(2) ,    //MAIl
                            cursor.getString(3)      //PASS
                    );
                    cursor.close();
                }

            } catch (SQLiteException ex) {
                ex.printStackTrace();
            } finally {
              SQLiteManager.getConexion().closeDB();
            }

            return user;
        }

        public List<Users> getAllUsers () {
            String query = "SELECT idUser, name, mail, pass FROM USERS";
            List<Users> listUsers = new ArrayList<Users>();

            try {
                SQLiteDatabase db = SQLiteManager.getConexion().connect("read");
                Cursor cursor = db.rawQuery(query,null);

                if (cursor != null && cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    do{
                        user = new Users();
                        user.setIdUser(Integer.parseInt(cursor.getString(0)));
                        user.setName(cursor.getString(1));
                        user.setMail(cursor.getString(2));
                        user.setPass(cursor.getString(3));
                        //add to list
                        listUsers.add(user);
                    } while (cursor.moveToNext());
                    cursor.close();
                }
            } catch (SQLiteException ex) {
                ex.printStackTrace();
            } finally {
                SQLiteManager.getConexion().closeDB();
            }

            return listUsers;
        }


    }

