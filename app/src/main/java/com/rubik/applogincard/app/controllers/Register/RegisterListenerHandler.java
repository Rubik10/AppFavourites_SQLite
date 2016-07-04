package com.rubik.applogincard.app.controllers.Register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.rubik.applogincard.Activity.RegisterActivity;
import com.rubik.applogincard.Activity.LoginActivity;
import com.rubik.applogincard.R;
import com.rubik.applogincard.app.Utils.ValidateInputs;
import com.rubik.applogincard.app.db.UserSQL;
import com.rubik.applogincard.model.Users;

/**
     * Created by Rubik on 20/6/16.
     */
    public class RegisterListenerHandler implements View.OnClickListener{
        private static final String TAG = RegisterListenerHandler.class.getSimpleName();
        private Activity activity;
        private Context cxt;
        private UserSQL userSQL;

        private EditText txtUser;
        private EditText txtMail;
        private EditText txtPass;
        private EditText txtPassConfirm;
        private CheckBox chkPass;


        public RegisterListenerHandler(Context context) {
            this.cxt = context;
            userSQL = new UserSQL();
            initControls();
        }

        private void initControls () {
            activity = (RegisterActivity) cxt;

            txtUser = (EditText) activity.findViewById(R.id.txtName);
            txtMail = (EditText) activity.findViewById(R.id.txtMail);
            txtPass = (EditText) activity.findViewById(R.id.txtPass);
            txtPassConfirm = (EditText) activity.findViewById(R.id.txtPass2);
            chkPass = (CheckBox) activity.findViewById((R.id.chkPass));

        }

        @Override
        public void onClick(View view) {
            int viewSelected = view.getId();

            switch (viewSelected) {

                case R.id.btnRegister :

                    ValidateInputs validate = new ValidateInputs();
                    RegisterController  register = new RegisterController(cxt);

                    String user = txtUser.getText().toString().trim();
                    String mail = txtMail.getText().toString().trim();
                    String pass = txtPass.getText().toString().trim();
                    String pass2 = txtPassConfirm.getText().toString().trim();

                    Users _user = userSQL.getUserByMail(mail); // Check if the user exist in the db by the mail entered by the user.

                    if (_user.getMail()==null) {
                      if (validate.isValidate(mail,pass,txtMail,txtPass) && validate.validateUser(user,txtUser) && validate.isSamePass(pass,pass2)  ) { //!mail.isEmpty() && !pass.isEmpty()  && pass == pass2
                         register.checkRegisterUser (user,mail,pass);  //Register user
                         Toast.makeText(cxt,mail + " is register",Toast.LENGTH_LONG).show();
                       } else {registerFallShowError(view);}
                    } else {registerFallShowError(view);}

                    break;

                case R.id.btnLinkToLoginScreen :

                    Intent intent = new Intent(cxt, LoginActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
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

            private void registerFallShowError (View view) {
                ValidateInputs.getAnimationFallLogin(view);
                // Toast.makeText(cxt,"Enter a valid credentials for login",Toast.LENGTH_LONG).show();
                Snackbar.make(view, "Register error", Snackbar.LENGTH_LONG).show();
            }

        }
