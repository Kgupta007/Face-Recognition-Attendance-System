package com.keshav.facerecognition;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.Objects;

public class ShowLog extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myLogRef = database.getReference("Timechart");

    //static String TAG = "Test-->";

    final static String None = "None";

    ArrayList<UserInfo> userInfoList = new ArrayList<>();
    ListView lv;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        lv = (ListView) findViewById(R.id.listview);

        //Log.i(TAG, "ShowLog --> " + Data.chooseDate);


        myLogRef.child(Data.chooseDate).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(ShowLog.this, "Logs For the Provided Time stamp not found", Toast.LENGTH_SHORT).show();
                } else {

                    for (DataSnapshot child : task.getResult().getChildren()) {

                        UserInfo userInfo = new UserInfo();
                        userInfo.name = child.getKey();

                        if (userInfo.name.equals("Count")) {
                            userInfo.count = child.getValue().toString();
                            userInfo.exit = userInfo.entry = None;

                            // Save as count
                        } else {
                            // Extract inner child
                            try {
                                userInfo.entry = Objects.requireNonNull(child.child("Entry").getValue()).toString();
                            } catch (Exception e) {
                                userInfo.entry = None;
                            }
                            try {
                                userInfo.exit = (Objects.requireNonNull(child.child("Exit").getValue())).toString();
                            } catch (Exception e) {
                                userInfo.exit = None;
                            }
                            userInfo.count = None;
                        }
                        userInfoList.add(userInfo);
                    }

                    Custom custom = new Custom(userInfoList, ShowLog.this, R.layout.listview_layout);
                    lv.setAdapter(custom);
                }
            }
        });
    }
}
class UserInfo {
    String name;
    String exit;
    String entry;
    String count;
}
