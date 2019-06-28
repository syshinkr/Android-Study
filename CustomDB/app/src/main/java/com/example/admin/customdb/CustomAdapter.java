package com.example.admin.customdb;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by admin on 2017-12-27.
 */

public class CustomAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<Info> infoArr;
    private ViewHolder viewHolder;

    public CustomAdapter(Context c, ArrayList<Info> arr) {
        this.inflater = LayoutInflater.from(c);
        this.infoArr = arr;
    }

    @Override
    public int getCount() {
        return infoArr.size();
    }

    @Override
    public Object getItem(int arg) {
        return null;
    }

    @Override
    public long getItemId(int arg) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        if(v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_row, null);
            viewHolder.title = v.findViewById(R.id.title);
            viewHolder.subInfo = v.findViewById(R.id.subInfo);

            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.title.setText(infoArr.get(position).getTitle());
        viewHolder.subInfo.setText(infoArr.get(position).getSubInfo());

        return v;
    }

    public ArrayList<Info> getInfoArr() {
        return infoArr;
    }

    public void setInfoArr(ArrayList<Info> infoArr) {
        this.infoArr = infoArr;
    }

    private class ViewHolder {
        TextView title;
        TextView subInfo;
        TextView period;
        TextView link;
        TextView day;
    }
}
