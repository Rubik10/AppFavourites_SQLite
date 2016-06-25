package com.rubik.applogincard.app.controllers.Register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.rubik.applogincard.Activity.RegisterActivity;
import com.rubik.applogincard.LoginActivity;
import com.rubik.applogincard.app.db.UserSQL;
import com.rubik.applogincard.model.Users;

/**
         * Created by Rubik on 20/6/16.
     */
    public class RegisterController {
        private static final String TAG = RegisterController.class.getSimpleName();
        private Activity activity;
        private Context context;

        public RegisterController (Context context) {
                 //Cast the activity
            this.context = context;
            activity = (RegisterActivity) context;
        }

        /**
         * Function to store user in MySQL database will post params(tag, name,
         * email, password) to register url
         * */
        public void checkRegisterUser(final String name, final String mail, final String pass) {
            UserSQL userSQL = new UserSQL();
            userSQL.addUser(new Users(name, mail, pass)); //  // Inserting row in users table
            Toast.makeText(context, "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(context, LoginActivity.class);
            activity.startActivity(intent);
            activity.finish();

        }

        private void httpMYSQLRegister (final String name, final String email, final String pass) {

            /*
                String taq_cancelRequest = "cancel_reqRegiter";

                dialog.setMessage("Registering User ...");
                showDialog();

                StringRequest strReq = new StringRequest(
                        Request.Method.POST,
                        com.rubik.applogincard.app.db.AppCongifMySQL.URL_REGISTER,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                Log.d(TAG, "Register Response: " + response);
                                hideDialog();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    boolean error = jsonObj.getBoolean("error");

                                    if (!error) {
                                        // User successfully stored in MySQL -> Now store the user in sqlite
                                        JSONObject userJSON = jsonObj.getJSONObject("user");
                                        user = new Users();

                                        user.setName( userJSON.getString("name"));
                                        user.setMail( userJSON.getString("email"));
                                        user.setPass( userJSON.getString("pass"));

                                        // Inserting row in users table
                                        userSQL.addUser(user);
                                        Toast.makeText(context, "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                                        // Launch login activity
                                        Intent intent = new Intent(activity, LoginActivity.class);
                                        activity.startActivity(intent);
                                        activity.finish();

                                    } else {
                                        // Error occurred in registration. Get the error message
                                        String errorMsg = jsonObj.getString("error_msg");
                                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, "Registration Error: " + error.getMessage());
                                Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show();
                                hideDialog();
                            }
                        }
                )
                {
                    @Override
                    protected Map<String, String> getParams() {
                        // Posting params to register url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", name);
                        params.put("mail", email);
                        params.put("pass", pass);

                        return params;
                    }

                };

                // Adding request to request queue
                AppControllerVolley.getInstance().addToRequestQueue(strReq, taq_cancelRequest);*/
            }
    }
