package com.rubik.applogincard.app.controllers.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.rubik.applogincard.R;
import com.rubik.applogincard.app.Utils.SessionManager;
import com.rubik.applogincard.app.controllers.Login.LoginController;
import com.rubik.applogincard.app.db.CategoriesSQL;
import com.rubik.applogincard.app.db.FavoritesSQL;
import com.rubik.applogincard.app.db.ImagesSQL;
import com.rubik.applogincard.app.db.SQLiteManager.SQLiteHandler;
import com.rubik.applogincard.app.db.SQLiteManager.SQLiteManager;
import com.rubik.applogincard.model.Categories;
import com.rubik.applogincard.model.Images;

import java.util.HashMap;
import java.util.List;

/**
     * Created by Rubik on 26/6/16.
        * * COntroller of the main App
     */
    public class MainController {
        private static final String TAG = MainController.class.getSimpleName();
        private Activity activity;
        private Context context;
            //RecyceView
        private static final int LAYOUT_ORIENTATION  = GridLayout.VERTICAL;
        private static final int LAYOUT_COLUMNS = 2;
        private List<Images> listImages;
            //Control the user logged and the logout accion
        public static SessionManager sessionUser;
        protected static HashMap<String,String> userLogued; //user logued in APP SesionManager
            //Spinner
        protected static List<String> comboCategories;
        private Categories cat; // to pass the seleccted id category (index) of spinner -> recycleView

        protected static boolean isShowingFav = false; //# Favourites control


        public MainController (Context cxt) {
            this.context = cxt;
            activity = (Activity) context;

            initMainApp();
        }


        private void initMainApp() {
                //Ver como lazar un progreesDialog para las carga en 2ºplano de los principlilas datos de la app
            ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Loading");
            dialog.setIndeterminate(false);


            initDatabase();
            getUserLogIn(); // User log in app with Session Manager

            initSpinner();
            initFloatActionButton();
        }


        private void initDatabase() {
            SQLiteHandler sqlHandler = new SQLiteHandler(context);
            SQLiteManager.initialize(sqlHandler);
            // sqlHandler.getWritableDatabase();
        }

        private void getUserLogIn() {
            sessionUser = new SessionManager(activity);
            userLogued = sessionUser.getUserLog(); //TODO Update with Session Manager
            // userLogued = getIntent().getStringExtra("mail"); // Don´t working with Session Manager
        }

        private void initSpinner () {
                //Spinner
            Spinner spinner = (Spinner) activity.findViewById(R.id.spinner);
           // spinner.setEnabled(true);
                //Spinner Custom Adapter
            CategoriesSQL categories = new CategoriesSQL();
            comboCategories = categories.getAllCategories();
            comboCategories.add("Show All");
            spinner.setAdapter(new mySpinnerAdapter(context,R.layout.spinner_layout,comboCategories));

                //Spinner Listener
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int selectIndex = (position + 1);
                     cat = new Categories(selectIndex);
                        Log.d(TAG,"  Selected Item " + cat.getIdCategory() );
                    initRecycleView();
                }

                    @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        private void initRecycleView() {
            RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);

                //Load Images where under contidions selected
            if (isShowingFav) {
                FavoritesSQL favorites = new FavoritesSQL();
                     //TODO : Update with SessionManager
                switch (cat.getIdCategory()) {
                    case 5 :    //Show All Favs
                        listImages = favorites.getAllFavoritesByUser(Integer.valueOf(userLogued.get(SessionManager.ID_USER)) );
                        break;
                    default:    // FAvs per Category
                        listImages = favorites.getAllFavoritesByUserPerCategory(Integer.valueOf(userLogued.get(SessionManager.ID_USER)), cat.getIdCategory());
                        break;
                }
            } else {
                ImagesSQL imagesSQL = new ImagesSQL();
                switch (cat.getIdCategory()) {
                    case 5 :    //Show all image
                        listImages = imagesSQL.getAllImage();
                        break;
                    default:    // //Show all image per category
                        listImages = imagesSQL.getAllImagesByCategory(cat.getIdCategory());
                        break;
                }

            }
                //Adapter
            myReciclerViewAdapter myAdapter = new myReciclerViewAdapter(context, listImages);
            recyclerView.setLayoutManager(new GridLayoutManager(context, LAYOUT_COLUMNS, LAYOUT_ORIENTATION,false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(myAdapter);
        }

        private void initFloatActionButton (){
            final FloatingActionButton floatBtn = (FloatingActionButton) activity.findViewById(R.id.fab);

            floatBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView txtTitleApp = (TextView) activity.findViewById(R.id.love_music);

                    if (!isShowingFav) {     //Favorites
                        floatBtn.setImageResource(R.drawable.star_fav);
                        txtTitleApp.setText("MASTER APP FAVS");
                        isShowingFav = true;
                        initRecycleView();

                    } else {     //ByCategory
                        floatBtn.setImageResource(R.drawable.star);
                        txtTitleApp.setText("MASTER APP");
                        isShowingFav = false;
                        initRecycleView();
                    }
                }
            });

    }



        public  void initLogOut () {

           Button logOut = (Button) activity.findViewById(R.id.action_logOut);
            logOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                public void onClick(View v) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Comfirm Log Out?");
                    alert.setMessage("Are you sure want to log out?");
                    alert.setCancelable(true);
                    alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            LoginController.logoutUser(sessionUser,context);
                        }
                    });
                    alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.setIcon(android.R.drawable.ic_dialog_alert);
                    alert.show();


            /*           new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Comfirm Log Out?")
                                .setContentText("Are you sure want to log out?")
                                .setConfirmText("Yes,delete it!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        LoginController.logoutUser(sessionUser,context);
                                        sDialog
                                                .setTitleText("Success!")
                                                .setContentText("Your imaginary file has been deleted!")
                                                .setConfirmText("OK")
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    }
                                })
                                .show();
*/
                    }
            });


        }


    }
