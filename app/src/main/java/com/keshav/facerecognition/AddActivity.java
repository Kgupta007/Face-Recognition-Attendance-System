package com.keshav.facerecognition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    EditText name, id, designation, gender, address;
    Button btnAdd, btnBack, add_faceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = (EditText) findViewById(R.id.txtName);
        id = (EditText) findViewById(R.id.txtUsr);
        designation = (EditText) findViewById(R.id.txtDesignation);
        gender = (EditText) findViewById(R.id.txtGender);
        address = (EditText) findViewById(R.id.txtAddress);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnBack = (Button) findViewById(R.id.btnBack);

        add_faceBtn = findViewById(R.id.btnAddFace);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                clearAll();

            }
        });

        add_faceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddActivity.this, AddFace.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void insertData() {
        Map<String, Object> map = new HashMap<>();
        map.put("Name", name.getText().toString());
        map.put("UserID", id.getText().toString());
        map.put("Gender", gender.getText().toString());
        map.put("Designation", designation.getText().toString());
        map.put("Address", address.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("Users")
                .push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AddActivity.this, "Error while inserting", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void clearAll() {
        name.setText("");
        id.setText("");
        gender.setText("");
        designation.setText("");
        address.setText("");
    }
}