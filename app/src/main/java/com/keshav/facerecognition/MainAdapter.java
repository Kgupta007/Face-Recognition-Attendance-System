package com.keshav.facerecognition;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.RecursiveAction;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference("map");


    int OUTPUT_SIZE = 192; //Output size of model


    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModel model) {
        holder.name.setText(model.getName());
        holder.id.setText(model.getUserID());
        holder.gender.setText(model.getGender());
        holder.designation.setText(model.getDesignation());
        holder.address.setText(model.getAddress());
//        Glide.with(holder.img.getContext())
//                .load(model.getTurl())
//                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
//                .circleCrop() .error(R.drawable.common_google_signin_btn_icon_dark_normal)
//                .into(holder.img);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 2000)
                        .create();
                dialogPlus.show();
                View view = dialogPlus.getHolderView();
                EditText name = view.findViewById(R.id.txtName);
                EditText id = view.findViewById(R.id.txtUsr);
                EditText designation = view.findViewById(R.id.txtDesignation);
                EditText gender = view.findViewById(R.id.txtGender);
                EditText address = view.findViewById(R.id.txtAddress);
                // EditText turl = view.findViewById(R.id.txtImg);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                name.setText(model.getName());
                id.setText(model.getUserID());
                designation.setText(model.getDesignation());
                gender.setText(model.getGender());
                address.setText(model.getAddress());
                //turl.setText(model.getTurl());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("Name", name.getText().toString());
                        map.put("UserID", id.getText().toString());
                        map.put("Gender", gender.getText().toString());
                        map.put("Designation", designation.getText().toString());
                        map.put("Address", address.getText().toString());
                        //map.put("turl",turl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }

                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error while uploading", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();

                                    }
                                });
                        dialogPlus.dismiss();


                    }
                });


            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be undone");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(Objects.requireNonNull(getRef(position).getKey())).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {

                                    String name = Objects.requireNonNull(task.getResult().child("Name").getValue()).toString();

                                    myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                HashMap<String, SimilarityClassifier.Recognition> registered = readFromFirebase(String.valueOf(task.getResult().getValue())); //saved Faces
                                                Log.i("TAG-->", "BEFORE --> " + registered);
                                                registered.remove(name);
                                                Log.i("TAG-->", "AFTER --> " + registered);
                                                insertToFirebase(registered);


                                            }
                                        }
                                    });

                                }
                            }
                        });


                        FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(getRef(position).getKey()).removeValue();


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();


                    }
                });
                builder.show();
            }
        });
    }

    private void insertToFirebase(HashMap<String, SimilarityClassifier.Recognition> jsonMap) {

        String jsonString = new Gson().toJson(jsonMap);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("map");
        myRef.setValue(jsonString);
    }


    HashMap<String, SimilarityClassifier.Recognition> readFromFirebase(String result) {

        TypeToken<HashMap<String, SimilarityClassifier.Recognition>> token = new TypeToken<HashMap<String, SimilarityClassifier.Recognition>>() {
        };
        HashMap<String, SimilarityClassifier.Recognition> retrievedMap = new Gson().fromJson(result, token.getType());
        for (Map.Entry<String, SimilarityClassifier.Recognition> entry : retrievedMap.entrySet()) {
            float[][] output = new float[1][OUTPUT_SIZE];
            ArrayList arrayList = (ArrayList) entry.getValue().getExtra();
            arrayList = (ArrayList) arrayList.get(0);
            for (int counter = 0; counter < arrayList.size(); counter++) {
                output[0][counter] = ((Double) arrayList.get(counter)).floatValue();
            }
            entry.getValue().setExtra(output);
        }
        return retrievedMap;
        // Write a message to the database
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name, id, designation, gender, address;
        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nametext);
            id = (TextView) itemView.findViewById(R.id.useridtext);
            designation = (TextView) itemView.findViewById(R.id.designationtext);
            gender = (TextView) itemView.findViewById(R.id.gendertext);
            address = (TextView) itemView.findViewById(R.id.addresstext);

            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
        }
    }
}