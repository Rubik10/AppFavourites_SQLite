package com.rubik.applogincard.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubik.applogincard.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        initImage();

        String name = getIntent().getStringExtra("name");
        TextView txtTitle = (TextView) findViewById(R.id.titleImage);
        txtTitle.setText(name);
    }

    private void initImage() {
        ImageView img = (ImageView) findViewById(R.id.imgDetalle);

        Picasso.with(getApplicationContext()).load(
                getIntent().getStringExtra("url"))
                .resize(240, 300)
                .into(img, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d("Picasso", "  Carga de imagenes correcta  " );
                            }

                            @Override
                            public void onError() {
                                Log.d("Picasso", "  Error en la carga de imagenes  " );
                            }
                        }
                );
    }
}
