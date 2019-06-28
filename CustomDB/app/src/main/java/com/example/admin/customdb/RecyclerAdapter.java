package com.example.admin.customdb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by admin on 2017-12-27.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    ArrayList<Info> infoArr;

    @Override
    public int getItemCount() {
        return infoArr.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(infoArr.get(position).getTitle());
        holder.subInfo.setText(infoArr.get(position).getSubInfo());
    }

    public ArrayList<Info> getInfoArr() {
        return infoArr;
    }

    public void setInfoArr(ArrayList<Info> infoArr) {
        this.infoArr = infoArr;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView subInfo;
        private TextView period;
        private TextView link;
        private TextView day;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.subInfo = itemView.findViewById(R.id.subInfo);
//            this.period =
//            this.link =
//            this.day =
        }
    }
}
