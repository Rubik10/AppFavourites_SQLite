package com.rubik.applogincard.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.rubik.applogincard.R;
import com.rubik.applogincard.app.Utils.SessionManager;
import com.rubik.applogincard.app.controllers.Login.LoginController;
import com.rubik.applogincard.app.controllers.Login.LoginListenerHandler;
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

    public class LoginActivity extends AppCompatActivity {
        private static final String TAG = LoginActivity.class.getSimpleName();

        private Button btnLogin, btnToRegister;
        private CheckBox chkPass;


            @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            initDatabase();     /* Create the the db and their Tables. Also create a instance from controls the behavior of
                                       conecctions to the db in the app.*/

            getSessionState();  // Check the session state of the user logged. -> session = true -> MainActivy directly
            initControls();     // Initiate the controls and their listener

           // insertTestData();   //TODO : TEST
        }

        /*
            * Create the the db and his Tables. Also create a instance from controls the behavior of
              conecctions to the db in the app
         */
        private void initDatabase () {
            SQLiteHandler sqlHandler = new SQLiteHandler(getApplicationContext());
            SQLiteManager.initialize(sqlHandler);
            // sqlHandler.getWritableDatabase();
            if (SQLiteManager.isDbEmmty()) {
                initTestData();  //TODO : TEST

            }
        }

        /*
           * Check the session state of the user logged. -> session = true -> MainActivy directly
        */
        private void getSessionState() {
            //Check Session Manager
            LoginController.session = new SessionManager(this); // Create instance of SessionManager with the activity context
            LoginController.isUserLogged( LoginController.session,this); //Check the session (boolean)
        }

        /*
            *  Initiate the controls and their listener
         */
        private void initControls () {

            btnLogin = (Button) findViewById(R.id.btnLogin);
            btnToRegister = (Button) findViewById(R.id.btnToRegister);
            chkPass = (CheckBox) findViewById(R.id.chkPass);

            View.OnClickListener listener = new LoginListenerHandler(this);
            btnLogin.setOnClickListener(listener);
            chkPass.setOnClickListener(listener);
            btnToRegister.setOnClickListener(listener);
        }


        private void initTestData() {
            CategoriesSQL categories = new CategoriesSQL();
            categories.addCategory(new Categories("Movies"));
            categories.addCategory(new Categories("TV Shows"));
            categories.addCategory(new Categories("Cars"));
            categories.addCategory(new Categories("Actors"));


            Log.d(TAG, " Categorys Insertadas");

            ImagesSQL imagesSQL = new ImagesSQL();
            imagesSQL.addImage(new Images("The Wolf of Wall Street", "http://ia.media-imdb.com/images/M/MV5BMjIxMjgxNTk0MF5BMl5BanBnXkFtZTgwNjIyOTg2MDE@._V1_SY1000_CR0,0,674,1000_AL_.jpg", 1));
            imagesSQL.addImage(new Images("Captain America: Civil War", "http://www.cosmicbooknews.com/sites/default/files/imagecache/node-gallery-display/civilwarposterimh.jpg", 1));
            imagesSQL.addImage(new Images("The Dark Knight", "http://pics.filmaffinity.com/the_dark_knight-102763119-large.jpg", 1));
            imagesSQL.addImage(new Images("Shutter Island", "http://ia.media-imdb.com/images/M/MV5BMTMxMTIyNzMxMV5BMl5BanBnXkFtZTcwOTc4OTI3Mg@@._V1_.jpg", 1));
            imagesSQL.addImage(new Images("The Revenant", "http://ia.media-imdb.com/images/M/MV5BMjU4NDExNDM1NF5BMl5BanBnXkFtZTgwMDIyMTgxNzE@._V1_SY1000_CR0,0,674,1000_AL_.jpg", 1));
            imagesSQL.addImage(new Images("Suicide Squad", "http://ia.media-imdb.com/images/M/MV5BMTAyMTA5NTEyMzNeQTJeQWpwZ15BbWU4MDE1MTk5Mjkx._V1_SY1000_SX675_AL_.jpg", 1));
            imagesSQL.addImage(new Images("Memento", "http://ia.media-imdb.com/images/M/MV5BMTc4MjUxNDAwN15BMl5BanBnXkFtZTcwMDMwNDg3OA@@._V1_.jpg", 1));
            imagesSQL.addImage(new Images("Inception", "http://ia.media-imdb.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SY1000_CR0,0,675,1000_AL_.jpg", 1));
            imagesSQL.addImage(new Images("Star Wars: Episode VII - The Force Awakens", "http://ia.media-imdb.com/images/M/MV5BOTAzODEzNDAzMl5BMl5BanBnXkFtZTgwMDU1MTgzNzE@._V1_SY1000_CR0,0,677,1000_AL_.jpg", 1));


            imagesSQL.addImage(new Images("Game of Thrones", "http://ia.media-imdb.com/images/M/MV5BMjM5OTQ1MTY5Nl5BMl5BanBnXkFtZTgwMjM3NzMxODE@._V1_SY1000_CR0,0,674,1000_AL_.jpg,", 2));
            imagesSQL.addImage(new Images("Breaking Bad", "http://ia.media-imdb.com/images/M/MV5BMTQ0ODYzODc0OV5BMl5BanBnXkFtZTgwMDk3OTcyMDE@._V1_SY1000_CR0,0,678,1000_AL_.jpg,", 2));
            imagesSQL.addImage(new Images("The Wire","http://ia.media-imdb.com/images/M/MV5BNjc1NzYwODEyMV5BMl5BanBnXkFtZTcwNTcxMzU1MQ@@._V1_SY1000_CR0,0,735,1000_AL_.jpg",2));
            imagesSQL.addImage(new Images("The Leftovers", "http://ia.media-imdb.com/images/M/MV5BMTQ4MzEzNTAxOF5BMl5BanBnXkFtZTgwMjQ1MTY3NjE@._V1_.jpg", 2));
            imagesSQL.addImage(new Images("Mr. Robot", "http://ia.media-imdb.com/images/M/MV5BMTYzMDE2MzI4MF5BMl5BanBnXkFtZTgwNTkxODgxOTE@._V1_SY1000_CR0,0,674,1000_AL_.jpg", 2));
            imagesSQL.addImage(new Images("Fargo", "http://ia.media-imdb.com/images/M/MV5BNDEzOTYzMDkzN15BMl5BanBnXkFtZTgwODkzNTAyNjE@._V1_.jpg", 2));
            imagesSQL.addImage(new Images("Better Call Saul", "http://ia.media-imdb.com/images/M/MV5BNjk5MjYwNjg4NV5BMl5BanBnXkFtZTgwNzAzMzc5NzE@._V1_.jpg", 2));


            imagesSQL.addImage(new Images("Nissan 370z","http://es.hdwall365.com/wallpapers/1604/Nissan-370Z-coupe-orange-car-rear-view_m.jpg",3));
            imagesSQL.addImage(new Images("Camaro SS","http://media.caranddriver.com/images/15q3/660572/2016-chevrolet-camaro-ss-manual-first-drive-review-car-and-driver-photo-661947-s-429x262.jpg",3));
            imagesSQL.addImage(new Images("Aston Martin Vanquish","http://www.ridelust.com/wp-content/uploads/Aston_Martin-DBS_Infa_Red_2008_1280x960_wallpaper_01.jpg",3));

            imagesSQL.addImage(new Images("Leonardo DiCaprio","http://media3.popsugar-assets.com/files/2016/02/29/638/n/1922398/2845304c_edit_img_cover_file_17268548_1456755329_GettyImages-51UmW1V1.xxxlarge/i/Leonardo-DiCaprio-Oscars-Memes-2016.jpg",4));
            imagesSQL.addImage(new Images("Margot Robbie","http://eva.hn/wp-content/uploads/2015/08/Margot-Robbie-Wolf-Wall-Street.jpg",4));
            imagesSQL.addImage(new Images("Tom Hardy","http://www.mtv.co.uk/sites/default/files/styles/image-w-520-h-520-scale/public/mtv_uk/galleries/large/2015/05/13/11-tom-hardy-gallery-fb.jpg?itok=hqfsf16t",4));
            imagesSQL.addImage(new Images("Scarlett Johansson","http://www.celebs101.com/gallery/Scarlett_Johansson/201825/allthatgossip_Scarlett_Johansson_GoldenGlobe_01.jpg",4));
            imagesSQL.addImage(new Images("Blake Lively","http://www.speakerscorner.me/wp-content/uploads/2016/03/blake1.jpg",4));
            imagesSQL.addImage(new Images("Cristian Bale","http://www.lahiguera.net/cinemania/actores/christian_bale/fotos/16700/christian_bale.jpg",4));

            imagesSQL.addImage(new Images("Suicide Squad - Jared Leto 1","http://www.joblo.com/posters/images/full/Suicide-Squad-character-poster-10.jpg",4));
            imagesSQL.addImage(new Images("Suicide Squad - Margot Robbie 1","http://www.joblo.com/posters/images/full/Suicide-Squad-character-poster-2-6.jpg",4));
            imagesSQL.addImage(new Images("Suicide Squad - Jared Leto 2","http://www.sinuousmag.com/sm/wp-content/uploads/2016/01/suicide-squad-2016-jared-leto-as-joker-poster-1b-650x963.jpg",4));
            imagesSQL.addImage(new Images("Suicide Squad - Margot Robbie 2","http://www.joblo.com/posters/images/full/Suicide-Squad-Character-Poster-1.jpg",4));

            Log.d(TAG, " Imagenes Insertadas");

            UserSQL users = new UserSQL();
            users.addUser(new Users("Rubik","rubikdjloco@gmail.com","alleniverson"));

            FavoritesSQL favorites = new FavoritesSQL();
            favorites.addFavorite(new Favorites(new Users(1), new Images(1)) );
            favorites.addFavorite(new Favorites (new Users(1), new Images(4)) );
            favorites.addFavorite(new Favorites (new Users(1), new Images(6)) );
            favorites.addFavorite(new Favorites (new Users(1), new Images(7)) );
            favorites.addFavorite(new Favorites (new Users(1), new Images(8)) );

            Log.d(TAG, " Favorites Insertadas");
        }

    }

