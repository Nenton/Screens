package com.nenton.schools.ui.screens.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nenton.schools.R;
import com.nenton.schools.data.storage.realm.EntityHistoryPassRealm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by serge on 06.01.2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<EntityHistoryPassRealm> mEntities;

    public HistoryAdapter() {
        mEntities = new ArrayList<>();
    }

    public void addEntity(RealmList<EntityHistoryPassRealm> entity) {
        mEntities = entity;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTeacher.setText(mEntities.get(position).getTeacher());
        holder.mRoom.setText(mEntities.get(position).getRoom());
        SimpleDateFormat format = new SimpleDateFormat("ss:mm:hh dd MMM");

        holder.mDateStart.setText(format.format(new Date(mEntities.get(position).getDateStart())));
        holder.mDateEnd.setText(format.format(new Date(mEntities.get(position).getDateEnd())));
    }

    @Override
    public int getItemCount() {
        return mEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //        TextView mName;
        TextView mTeacher;
        TextView mRoom;
        TextView mDateStart;
        TextView mDateEnd;

        public ViewHolder(View itemView) {
            super(itemView);
//            mName = (TextView)itemView.findViewById(R.id.item_history_name);
            mTeacher = (TextView) itemView.findViewById(R.id.item_history_teacher);
            mRoom = (TextView) itemView.findViewById(R.id.item_history_room);
            mDateStart = (TextView) itemView.findViewById(R.id.item_history_date_start);
            mDateEnd = (TextView) itemView.findViewById(R.id.item_history_date_end);
        }
    }
}
