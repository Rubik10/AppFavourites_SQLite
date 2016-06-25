package com.rubik.applogincard.app.Utils;

import android.graphics.Color;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

    /**
        * Created by Rubik on 19/6/16.
    */
    public class ValidateInputs {
        private Pattern pattern;
        private Matcher matcher;

        private static final String PASS_PATTERN =  "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%*_&]).{8,30})";

        public ValidateInputs(){
            pattern = Pattern.compile(PASS_PATTERN);
        }


        public boolean isValidate(String mail, String pass, EditText txtMail, EditText txtPass) {
            boolean valid = true;

            validateMail(mail,txtMail);

            if (!validatePass(pass) || pass.isEmpty()) {
                String msg = "Wrong Pass!! The password must be 8 to 30 characters and at least one : \n" +
                        " Number character \n" +
                        " Lowercase character \n" +
                        " Uppercasse character \n" +
                        " @#$%*_&";
                txtPass.setError(msg);
                txtPass.setBackgroundColor(Color.LTGRAY);
                valid = false;
            } else {
                txtPass.setError(null);
                txtPass.setBackgroundColor(Color.WHITE);
            }

            return valid;
        }



        /**
         * Validate password with regular expression
         # Start of group
            (?=.*\d)		#   must contains one digit from 0-9
            (?=.*[a-z])		#   must contains one lowercase characters
            (?=.*[A-Z])		#   must contains one uppercase characters
            (?=.*[@#$%*_&])		#   must contains one special symbols in the list "@#$%"
         #     match anything with previous condition checking
            {8,16}	#        length at least 6 characters and maximum of 20
         )			# End of group
         * @param pass password for validation
         * @return true valid password, false invalid password
         */
        private boolean validatePass(final String pass){
            matcher = pattern.matcher(pass);
            return matcher.matches();

        }

        private boolean validateMail (String mail, EditText txtMail) {
            if (mail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                txtMail.setError("Enter a valid email address. Ex -> mail@domain.com");
                txtMail.setBackgroundColor(Color.LTGRAY);
                return false;
            } else {
                txtMail.setError(null);
                txtMail.setBackgroundColor(Color.WHITE);
                return true;
            }
        }

        public  boolean isSamePass (String pass, String comfirmPass) {
            return pass.equals(comfirmPass);
        }

        public  boolean validateUser (String user,EditText txtUser) {
            if (user.isEmpty()) {
                txtUser.setError("Insert a name.");
                txtUser.setBackgroundColor(Color.LTGRAY);
                return false;
            } else {
                txtUser.setError(null);
                txtUser.setBackgroundColor(Color.WHITE);
                return true;
            }
        }



        public static void getAnimationFallLogin (View view) {
            TranslateAnimation animation = new TranslateAnimation(-70.0f, 70.0f,
                    0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
            animation.setDuration(100);  // animation duration in miliseconds
            animation.setRepeatCount(2);  // animation repeat count
            animation.setRepeatMode(2);   // repeat animation (left to right, right to left )

            view.startAnimation(animation);  // start animation
        }




    }
