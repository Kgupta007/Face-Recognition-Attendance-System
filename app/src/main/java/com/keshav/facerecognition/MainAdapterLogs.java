package com.keshav.facerecognition;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapterLogs extends FirebaseRecyclerAdapter<LogModel, MainAdapterLogs.myViewHolder> {

    public MainAdapterLogs(@NonNull FirebaseRecyclerOptions<LogModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull LogModel model) {
        holder.name.setText(model.getName());
        holder.entry.setText(model.getEntry());
        holder.exit.setText(model.getExit());
        holder.count.setText(model.getCount());
    }
    
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView name, entry, exit, count;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nametext);
            entry = (TextView) itemView.findViewById(R.id.entrytext);
            exit = (TextView) itemView.findViewById(R.id.exittext);
            count = (TextView) itemView.findViewById(R.id.counttext);
        }
    }
}
