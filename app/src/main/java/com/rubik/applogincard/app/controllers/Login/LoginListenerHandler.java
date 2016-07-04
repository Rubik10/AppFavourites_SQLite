package com.rubik.applogincard.app.controllers.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.rubik.applogincard.Activity.RegisterActivity;
import com.rubik.applogincard.Activity.LoginActivity;
import com.rubik.applogincard.R;
import com.rubik.applogincard.app.Utils.UtilsApp;
import com.rubik.applogincard.app.Utils.ValidateInputs;

/**
     * Created by Rubik on 17/6/16.
     */

    public class LoginListenerHandler implements View.OnClickListener {

        private static final String TAG = LoginListenerHandler.class.getSimpleName();
        private Activity _view;
        private Context cxt;

        private EditText txtMail;
        private EditText txtPass;
        private CheckBox chkPass;

        public LoginListenerHandler(Context context) {
            this.cxt = context;
            initControls();
        }

        private void initControls () {

            _view = (LoginActivity) cxt;

            txtMail = (EditText) _view.findViewById(R.id.txtMail);
            txtPass = (EditText) _view.findViewById(R.id.txtPass);
            chkPass = (CheckBox) _view.findViewById(R.id.chkPass);
        }

        @Override
        public void onClick(View view) {
            int viewSelected = view.getId();

            switch (viewSelected) {

                case R.id.btnLogin :

                    String mail = txtMail.getText().toString().trim();
                    String pass = txtPass.getText().toString().trim();

                    if (!mail.isEmpty() && !pass.isEmpty()) {
                        LoginController loginController = new LoginController(cxt);
                        if (!loginController.checkLogin (mail,pass)) { //login user
                             loginFallShowError(view);
                             Log.d(TAG," -> The user doesn't exist");
                         } else {Log.d(TAG," The User with mail " + mail + " is log in just now");}
                    } else {  loginFallShowError(view);  }

                    break;
                case R.id.btnToRegister :

                    Intent intent = new Intent(cxt, RegisterActivity.class);
                    _view.startActivity(intent);
                    _view.finish();
                    break;

                case R.id.chkPass :

                    if (chkPass.isChecked()) {
                        txtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else {
                        txtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                    break;
            }
        }

        private void loginFallShowError (View view) {
            ValidateInputs.getAnimationFallLogin(view);
            UtilsApp.showSnackBar(view, "Login error, enter a valid credentials for login");
        }

    }

