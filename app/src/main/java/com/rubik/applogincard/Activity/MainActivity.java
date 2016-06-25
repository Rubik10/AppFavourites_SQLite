package com.rubik.applogincard.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.rubik.applogincard.R;
import com.rubik.applogincard.app.Utils.SessionManager;
import com.rubik.applogincard.app.controllers.Login.LoginController;
import com.rubik.applogincard.app.controllers.main.myReciclerViewAdapter;
import com.rubik.applogincard.app.controllers.main.mySpinnerAdapter;
import com.rubik.applogincard.app.db.CategoriesSQL;
import com.rubik.applogincard.app.db.FavoritesSQL;
import com.rubik.applogincard.app.db.ImagesSQL;
import com.rubik.applogincard.app.db.SQLiteManager.SQLiteHandler;
import com.rubik.applogincard.app.db.SQLiteManager.SQLiteManager;
import com.rubik.applogincard.app.db.UserSQL;
import com.rubik.applogincard.model.Categories;
import com.rubik.applogincard.model.Favorites;
import com.rubik.applogincard.model.Images;
import com.rubik.applogincard.model.Users;

import java.util.HashMap;
import java.util.List;

    public class MainActivity extends AppCompatActivity {
        private static final String TAG = MainActivity.class.getSimpleName();
        public static Activity activity;
            //Control the user logged and the logut accion
        private  SessionManager sessionUser;
        public static HashMap<String,String> userLogued; //user logued in APP SesionManager

            //Spinner
        private Spinner spinner;
        public static List<String> comboCategories;
            //RecicleView
        private RecyclerView recyclerView;
        private ImagesSQL imagesSQL;
        private List<Images> listImages;
            //Toolbar
        private FloatingActionButton floatBtn;
        private Button logOut;

        public static TextView txtTitleApp; // var of favourite control to change the tittle
        public Categories cat; // to pass the seleccted id category (index) of spinner -> recycleView


            @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            activity = this; // Refence activity
            initMainApp();
        }

        private void initMainApp() {
            initDatabase();
            getUserLogIn(); // User log in app with Session Manager
            initCollapsingToolbar();
            // initTestData();
            initSpinner();
            initFloatActionButton();
            initLogOut();
        }

        private void getUserLogIn() {
            sessionUser = new SessionManager(getApplicationContext());
            userLogued = sessionUser.getUserLog(); //TODO Update with Session Manager
            // userLogued = getIntent().getStringExtra("mail"); // Don´t working with Session Manager
        }

        private void initDatabase() {
            SQLiteHandler sqlHandler = new SQLiteHandler(getApplicationContext());
            SQLiteManager.initialize(sqlHandler);
            // sqlHandler.getWritableDatabase();
        }

        private void initSpinner () {
                //Spinner
            spinner = (Spinner) findViewById(R.id.spinner);
            spinner.setEnabled(true);
                //Spinner Custom Adapter
            CategoriesSQL categories = new CategoriesSQL();
            comboCategories = categories.getAllCategories();
            spinner.setAdapter(new mySpinnerAdapter(this,R.layout.spinner_layout,comboCategories));
                //Spinner Listeener
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int selectIndex = (position + 1);
                    cat = new Categories(selectIndex);
                    Log.d(TAG,"  Selected Item " + cat.getIdCategory() );

                    //   spinner.setTag(0,cat.getIdCategory());

                    initRecycleView();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        private void initRecycleView() {

            Log.d(TAG, " Iniciar creacion de grid RecicleView y carda de imagenes en array");
            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            if (!spinner.isEnabled()) {
                Log.d(TAG, " Iniciar creacion de grid RecicleView y carda de imagenes en array");
                FavoritesSQL favorites = new FavoritesSQL();

               // listImages = favorites.getAllFavoritesByUser(userLogued); -> No funciona con el sesion Manager
                listImages = favorites.getAllFavoritesByUser(Integer.valueOf(userLogued.get(SessionManager.ID_USER)) ); //TODO : Update with SessionManager

             /*   myReciclerViewAdapter.MyViewHolder.;
                myReciclerViewAdapter.MyViewHolder.imgFav.setImageResource();*/
            } else {
                ImagesSQL imagesSQL = new ImagesSQL();
                listImages = imagesSQL.getAllImagesByCategory(cat.getIdCategory());
                //listImages = imagesSQL.getAllImage();
            }

            myReciclerViewAdapter myAdapter = new myReciclerViewAdapter(getApplicationContext(), listImages);

            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(myAdapter);
            Log.d(TAG, " Adaptador del grid creado");
        }

        private void initCollapsingToolbar() {
            final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
            collapsingToolbar.setTitle(" ");

            AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
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

        private void initFloatActionButton (){

            floatBtn = (FloatingActionButton) findViewById(R.id.fab);

            floatBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    txtTitleApp = (TextView) findViewById(R.id.love_music);

                    if (spinner.isEnabled()) {
                        //Favorites
                        floatBtn.setImageResource(R.drawable.star_fav);
                        spinner.setEnabled(false);
                        initRecycleView();

                        txtTitleApp.setText("MASTER APP FAVOURITES");
                    } else {
                        //ByCategory
                        floatBtn.setImageResource(R.drawable.star);
                        spinner.setEnabled(true);
                        txtTitleApp.setText("MASTER APP");
                        initRecycleView();
                    }

                }
            });

        }

        private void initLogOut() {
            logOut = (Button) findViewById(R.id.btnLogOut);
            logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginController.logoutUser(sessionUser,getApplicationContext());
                }
            });
        }

        private void initTestData() {
            CategoriesSQL categories = new CategoriesSQL();
            categories.addCategory(new Categories("Movies"));
            categories.addCategory(new Categories("TV Shows"));
            categories.addCategory(new Categories("Cars"));
            categories.addCategory(new Categories("Actors"));


            Log.d(TAG, " Categorys Insertadas");


            imagesSQL = new ImagesSQL();
            imagesSQL.addImage(new Images("The Wolf of Wall Street", "http://ia.media-imdb.com/images/M/MV5BMjIxMjgxNTk0MF5BMl5BanBnXkFtZTgwNjIyOTg2MDE@._V1_SY1000_CR0,0,674,1000_AL_.jpg", 1));
            imagesSQL.addImage(new Images("Captain America: Civil War", "http://www.cosmicbooknews.com/sites/default/files/imagecache/node-gallery-display/civilwarposterimh.jpg", 1));
            imagesSQL.addImage(new Images("The Dark Knight", "http://pics.filmaffinity.com/the_dark_knight-102763119-large.jpg", 1));
            imagesSQL.addImage(new Images("Shutter Island",
                    "http://ia.media-imdb.com/images/M/MV5BMTMxMTIyNzMxMV5BMl5BanBnXkFtZTcwOTc4OTI3Mg@@._V1_.jpg", 1));
            imagesSQL.addImage(new Images("The Revenant", "http://ia.media-imdb.com/images/M/MV5BMjU4NDExNDM1NF5BMl5BanBnXkFtZTgwMDIyMTgxNzE@._V1_SY1000_CR0,0,674,1000_AL_.jpg", 1));
            imagesSQL.addImage(new Images("Suicidade Squad", "http://ia.media-imdb.com/images/M/MV5BOTY1MzU1MDQ1MF5BMl5BanBnXkFtZTgwNjAzMjY3NzE@._V1_.jpg", 1));


            imagesSQL.addImage(new Images("Game of Thrones", "http://ia.media-imdb.com/images/M/MV5BMjM5OTQ1MTY5Nl5BMl5BanBnXkFtZTgwMjM3NzMxODE@._V1_SY1000_CR0,0,674,1000_AL_.jpg,", 2));
            imagesSQL.addImage(new Images("Breaking BaD", "http://ia.media-imdb.com/images/M/MV5BMTQ0ODYzODc0OV5BMl5BanBnXkFtZTgwMDk3OTcyMDE@._V1_SY1000_CR0,0,678,1000_AL_.jpg,", 2));
            imagesSQL.addImage(new Images("The Wire","http://ia.media-imdb.com/images/M/MV5BNjc1NzYwODEyMV5BMl5BanBnXkFtZTcwNTcxMzU1MQ@@._V1_SY1000_CR0,0,735,1000_AL_.jpg",2));

            Log.d(TAG, " Imagenes Insertadas");

            UserSQL users = new UserSQL();
            users.addUser(new Users("Rubik","rubikdjloco@gmail.com","alleniverson"));

            FavoritesSQL favorites = new FavoritesSQL();
            favorites.addFavorite(new Favorites(new Users(1), new Images(1)) );
            favorites.addFavorite(new Favorites (new Users(1), new Images(4)) );
            favorites.addFavorite(new Favorites (new Users(1), new Images(6)) );
            favorites.addFavorite(new Favorites (new Users(1), new Images(7)) );
            favorites.addFavorite(new Favorites (new Users(1), new Images(8)) );
        }


    }
