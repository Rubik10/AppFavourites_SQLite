package com.rubik.applogincard.app.controllers.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.rubik.applogincard.Activity.MainActivity;
import com.rubik.applogincard.LoginActivity;
import com.rubik.applogincard.app.Utils.SessionManager;
import com.rubik.applogincard.app.db.UserSQL;
import com.rubik.applogincard.model.Users;

    /**
     * Created by Rubik on 17/6/16.
     */
    public class LoginController {

        private static final String TAG = LoginController.class.getSimpleName();
        private static Activity activity;
        private Context cxt;

        protected boolean isLoggued = false;     // return the login state for manage errors of the login in external class.

        public static SessionManager session;
        private static UserSQL userSQL;

        public LoginController (Context context) {
                 //Cast the activity with their context
            this.cxt = context;
            activity = (LoginActivity) cxt;

                // SQLite User instance
            userSQL = new UserSQL();
        }


        public boolean checkLogin (String mail, String pass) {
            Log.d(TAG , "Check Login of user with mail : " +  mail);

            try {
               Users user = userSQL.getUserByMail(mail); // Check if the user exist in the db by the mail entered by the user.

                if (user.getMail().equals(mail) && user.getPass().equals(pass)) {
                    session.setLogin(true,user.getIdUser(),user.getMail()); //TODO: quitar para pruebas  // set the state of the session so that the log user does not have to login again.

                    Intent intent = new Intent(cxt, MainActivity.class);   // Open the Main Actvity
                    intent.putExtra("mail",user.getMail());
                    activity.startActivity(intent);
                    activity.finish();
                    isLoggued = true; // error handler

                    Log.d(TAG , " The user " + user.getName() +" with mail : " +  user.getMail() + " is log in to the App");


                } else {isLoggued = false;}

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return isLoggued;
        }


        /*
         * Check whether the user attempting to log in is already logged or not.
         * if the user is logged automatically we send it out main antivity.
      */
        public static void isUserLogged(SessionManager session, Context context) {
            if (session.isLogged()) {
                    //User already logged in -> Main Activity

                activity = (LoginActivity) context; // Casting the activity

                Intent intent = new Intent(context, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        }




            // Este Metodo ha de ser llamado desde la clase listener del MainActivity
        /*public static void logOut (SessionManager session) {
            session.setLogin(false);
        }*/

        /**
         * Clear session details
         * */
        public static void logoutUser (SessionManager session, Context context){
            // Clearing all data from Shared Preferences
            session.editor.clear();
            session.editor.commit();

           // activity = (LoginActivity) context; // Casting the activity

                // After logout redirect user to Loing Activity
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Staring Login Activity
            context.startActivity(intent);
        }


    }
