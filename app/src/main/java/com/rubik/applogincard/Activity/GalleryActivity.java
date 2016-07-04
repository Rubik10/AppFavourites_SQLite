package com.rubik.applogincard.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.rubik.applogincard.R;
import com.rubik.applogincard.app.controllers.main.Gallery.GalleryController;
import com.rubik.applogincard.app.controllers.main.myReciclerViewAdapter;
import com.rubik.applogincard.model.Images;

import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    public static final String TAG = GalleryActivity.class.getSimpleName();
    private List<Images> listImages;
    private int posSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        listImages = myReciclerViewAdapter.listImages;//getListImages();
        posSelected = getIntent().getIntExtra("position", 0);  // get the pos selected (cardView) in the main app for pass to GalleryCOntroller
        GalleryController gallery = new GalleryController(this, listImages, posSelected);
        closeGallery();
        fullScreen();
    }

    private void closeGallery() {
        ImageButton close = (ImageButton) findViewById(R.id.btn_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fullScreen () {

        ImageButton fullscreen = (ImageButton) findViewById(R.id.btn_fullScreen);

        fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFullScreentActivity();
            }
        });



    }

    private void startFullScreentActivity() {
        Intent intent = new Intent(this,ImageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.putExtra("pos",GalleryController.pagerPosition);
        startActivity(intent);
    }

}
