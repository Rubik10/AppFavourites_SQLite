package com.rubik.applogincard.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.rubik.applogincard.R;
import com.rubik.applogincard.app.controllers.Register.RegisterListenerHandler;
import com.rubik.applogincard.app.db.SQLiteManager.SQLiteHandler;
import com.rubik.applogincard.app.db.SQLiteManager.SQLiteManager;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister, btnToLogin;
    private CheckBox chkPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initDatabase();
        initControls();
    }

    /*
    * Create the the db and his Tables. Also create a instance from controls the behavior of
      conecctions to the db in the app
 */
    private void initDatabase () {
        SQLiteHandler sqlHandler = new SQLiteHandler(getApplicationContext());
        SQLiteManager.initialize(sqlHandler);
        // sqlHandler.getWritableDatabase();
    }


    private void initControls () {

        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        chkPass = (CheckBox) findViewById(R.id.chkPass);

        View.OnClickListener listener = new RegisterListenerHandler(this);
        btnRegister.setOnClickListener(listener);
        chkPass.setOnClickListener(listener);
        btnToLogin.setOnClickListener(listener);
    }

}
