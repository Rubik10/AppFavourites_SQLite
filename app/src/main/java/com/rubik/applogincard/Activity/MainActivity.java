package com.rubik.applogincard.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rubik.applogincard.R;
import com.rubik.applogincard.app.Utils.SessionManager;
import com.rubik.applogincard.app.Utils.UtilsApp;
import com.rubik.applogincard.app.controllers.Login.LoginController;
import com.rubik.applogincard.app.controllers.main.MainController;

    public class MainActivity extends AppCompatActivity {
        private static final String TAG = MainActivity.class.getSimpleName();
        public static Activity activity;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            activity = this; // Refence activity
            MainController main = new MainController(this);
            initToolbar();
            initCollapsingToolbar();
        }


        private void initToolbar() {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }

        private void initCollapsingToolbar() {
            final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) activity.findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle(" ");

            AppBarLayout appBarLayout = (AppBarLayout) activity.findViewById(R.id.appbar);
            appBarLayout.setExpanded(true);

                // hiding & showing the title when toolbar expanded & collapsed
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                boolean isShow = false;
                int scrollRange = -1;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }
                    if (scrollRange + verticalOffset == 0) {
                        collapsingToolbar.setTitle("MasterAPP");
                        isShow = true;
                    } else if (isShow) {
                        collapsingToolbar.setTitle(" ");
                        isShow = false;
                    }
                }
            });
        }


            @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_item, menu);
            return true;
        }

            @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            switch (id) {
               /* case R.id.action_settings:
                    UtilsApp.showSnackBar(findViewById(R.id.action_settings), "Se abren los ajustes");

                    return true;*/
                case R.id.action_logOut:

                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Comfirm Log Out?");
                    alert.setMessage("Are you sure want to log out?");
                    alert.setCancelable(true);
                    alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        MainController.sessionUser = new SessionManager(getApplicationContext());
                        LoginController.logoutUser(MainController.sessionUser,getApplicationContext());
                        UtilsApp.showSnackBar(findViewById(R.id.action_logOut), "Log Out");
                        }
                    });
                    alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                    alert.show();

                    return true;
                case R.id.action_find:
                    UtilsApp.showSnackBar(findViewById(R.id.action_find), "Buscar");
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

        }



    }
