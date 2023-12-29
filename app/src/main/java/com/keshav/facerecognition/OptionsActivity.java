package com.keshav.facerecognition;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OptionsActivity extends AppCompatActivity {

    Button logsButton, userDetailsButton;
    static String TAG = "RESULT-->";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);
        logsButton = findViewById(R.id.user_logs);
        userDetailsButton = findViewById(R.id.user_details);

        logsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(OptionsActivity.this, ChooseDate.class));
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });

        userDetailsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(OptionsActivity.this, AdminActivity.class));
            }
        });


    }

}
