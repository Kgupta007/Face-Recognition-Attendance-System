package com.keshav.facerecognition;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends  AppCompatActivity {

    Button AddFaceButton, DetectFaceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AddFaceButton = findViewById(R.id.add_face);
        DetectFaceButton = findViewById(R.id.detect_face);

        AddFaceButton.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        DetectFaceButton.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DetectFace.class));
            }
        });



    }

}
