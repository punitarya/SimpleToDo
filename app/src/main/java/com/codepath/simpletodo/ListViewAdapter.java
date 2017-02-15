package com.codepath.simpletodo;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by puarya on 2/14/17.
 * Custom list view adapter
 */

public class ListViewAdapter extends BaseAdapter{
    public List<TaskItem> list;
    Activity activity;
    TextView txtFirst;
    TextView txtSecond;

    public ListViewAdapter(Activity activity, List<TaskItem> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lst_row_col_view, null);
            txtFirst = (TextView) convertView.findViewById(R.id.txtItemName);
            txtSecond = (TextView) convertView.findViewById(R.id.txtItemDate);
        }

        TaskItem item = list.get(position);
        txtFirst.setText(item.getItemName());
        txtSecond.setText(item.getItemPriority());

        return convertView;
    }

    public void updateList(List<TaskItem> taskItems) {
        list = taskItems;
        this.notifyDataSetChanged();
    }
}

