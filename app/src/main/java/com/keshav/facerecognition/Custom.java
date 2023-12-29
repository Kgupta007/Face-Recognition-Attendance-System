package com.keshav.facerecognition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class Custom extends BaseAdapter {
    private ArrayList<UserInfo> modelArrayList;
    private Context context;
    private int layout;
//generqate constructor

    public Custom(ArrayList<UserInfo> modelArrayList, Context context, int layout) {
        this.modelArrayList = modelArrayList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //create view holder innter class
    private class ViewHolder {
        TextView name, entry, exit, count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(layout, null);
        //id type casting
        viewHolder.name = convertView.findViewById(R.id.name);
        viewHolder.entry = convertView.findViewById(R.id.entry);
        viewHolder.exit = convertView.findViewById(R.id.exit);
        viewHolder.count = convertView.findViewById(R.id.count);

        //set position
        UserInfo model = modelArrayList.get(position);
        viewHolder.name.setText(model.name);
        viewHolder.entry.setText(model.entry);
        viewHolder.exit.setText(model.exit);
        viewHolder.count.setText(model.count);
        return convertView;
    }
}