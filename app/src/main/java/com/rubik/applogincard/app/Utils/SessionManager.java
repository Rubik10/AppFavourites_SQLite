package com.rubik.applogincard.app.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;

/**
     * Created by Rubik on 19/6/16.
        * Store info of the loged user into local storage with the SharedPreference.
     */
    public class SessionManager {

        private static String TAG = SessionManager.class.getSimpleName();
            //Shared Preference
        private SharedPreferences sharedPref;
        public SharedPreferences.Editor editor;
        Context cxt;
            //Shared Mode
        int PRIVATE_MODE = 0;
            //Shared Files
        private static final String PREF_NAME_FILE = "AppAndroidLoginPref";
            //Shared Key
        private static final String KEY_LOGIN = "isLogged";
            // User log Keys
        public static final String ID_USER = "idUser";
        private static final String MAIL_USER = "mailUser";


        public  SessionManager (Context context) {
            this.cxt = context;
            sharedPref = this.cxt.getSharedPreferences(PREF_NAME_FILE,PRIVATE_MODE);
            editor = sharedPref.edit();
        }

        /*
            * Store seesion data
         */
        public void setLogin (Boolean isLogged, int idUser, String mail) {
            //Put the key of loged user int the file (true/false)
            editor.putBoolean(KEY_LOGIN, isLogged);
            editor.putString(ID_USER,String.valueOf(idUser));
            editor.putString(MAIL_USER, mail);
            //COnfirm changes
            editor.commit();

            Log.d(TAG , "User Login session changes");
        }

        /**
             * Get stored session data
         * */
        public HashMap<String, String> getUserLog(){
            HashMap<String, String> user = new HashMap<String, String>();
                // user id
            user.put(ID_USER, sharedPref.getString(ID_USER, null));
                // user name
            user.put(MAIL_USER, sharedPref.getString(MAIL_USER, null));
                // return user
            return user;
        }

        /*
            * Check Store log
         */
        public boolean isLogged () {
            return sharedPref.getBoolean(KEY_LOGIN,false);
        }

    }
