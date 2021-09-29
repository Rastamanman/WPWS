package com.example.wpws;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    List<AlarmItem> alList;
    private static ClickListener clickListener;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener)
    {
        AlarmAdapter.clickListener = clickListener;
    }

    public AlarmAdapter(List<AlarmItem> alList) { this.alList = alList; }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView al_name;
        private TextView al_location;

        public AlarmViewHolder(View view)
        {
            super(view);
            al_name = view.findViewById(R.id.al_name);
            al_location = view.findViewById(R.id.al_location);
            itemView.setOnClickListener(this);
        }

        private void setData(AlarmItem al)
        {
            al_name.setText(al.getAlName());
            al_location.setText(al.getAlLocation());
        }

        @Override
        public void onClick(View v)
        {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    @Override
    public AlarmAdapter.AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlarmAdapter.AlarmViewHolder holder, int position) {
        AlarmItem al = alList.get(position);
        holder.setData(al);
    }

    @Override
    public int getItemCount() {
        return alList.size();
    }
}
