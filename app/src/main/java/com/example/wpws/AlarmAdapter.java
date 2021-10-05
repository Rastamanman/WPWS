package com.example.wpws;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    List<AlarmItem> alarmItems;
    private static ClickListener clickListener;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener)
    {
        AlarmAdapter.clickListener = clickListener;
    }

    public AlarmAdapter(List<AlarmItem> alarmItems) { this.alarmItems = alarmItems; }

    public static class AlarmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView alarmName;
        private TextView alarmLocation;

        public AlarmViewHolder(View view)
        {
            super(view);
            alarmName = view.findViewById(R.id.alarm_item_name);
            alarmLocation = view.findViewById(R.id.alarm_item_location);
            itemView.setOnClickListener(this);
        }

        private void setData(AlarmItem alarm)
        {
            alarmName.setText(alarm.getAlarmName());
            alarmLocation.setText(alarm.getAlarmLocation());
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
        AlarmItem alarm = alarmItems.get(position);
        holder.setData(alarm);
    }

    @Override
    public int getItemCount() {
        return alarmItems.size();
    }
}
