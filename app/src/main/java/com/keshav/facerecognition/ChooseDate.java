package com.keshav.facerecognition;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseDate extends AppCompatActivity {

    DatePicker datePicker;
    Button showLogButton;
//    private Calendar calendar;
//    private TextView dateView;
//    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.datelog);

        datePicker = findViewById(R.id.date_picker);
        showLogButton = findViewById(R.id.LogsButton);

        showLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.chooseDate = GetDate();
                startActivity(new Intent(ChooseDate.this, ShowLog.class));

            }
        });
    }

    @SuppressLint("DefaultLocale")
    String GetDate() {
        return datePicker.getYear() + "/" +
                String.format("%02d", datePicker.getMonth() + 1) + "/" +
                String.format("%02d", datePicker.getDayOfMonth());
    }

}

