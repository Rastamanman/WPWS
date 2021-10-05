package com.example.wpws;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DayConditionAdapter extends RecyclerView.Adapter<DayConditionAdapter.DayCondViewHolder> {

    private List<DayConditionItem> daysList;

    public DayConditionAdapter(List<DayConditionItem> daysList) { this.daysList = daysList; }

    public class DayCondViewHolder extends RecyclerView.ViewHolder {
        private TextView dayNumber;
        private TextView temperature;
        private TextView rh;
        private TextView pressure;
        private TextView minTemp;
        private TextView maxTemp;
        private TextView snow;
        private TextView snowDepth;
        private TextView clouds;
        private TextView windSpeed;
        private TextView precipitation;
        private TextView rain;

        public DayCondViewHolder(View view)
        {
            super(view);
            dayNumber = view.findViewById(R.id.day_condition_number);
            temperature = view.findViewById(R.id.day_condition_temp);
            rh = view.findViewById(R.id.day_condition_rh);
            pressure = view.findViewById(R.id.day_condition_pressure);
            minTemp = view.findViewById(R.id.day_condition_min_temp);
            maxTemp = view.findViewById(R.id.day_condition_max_temp);
            snow = view.findViewById(R.id.day_condition_snow);
            clouds = view.findViewById(R.id.day_condition_clouds);
            windSpeed = view.findViewById(R.id.day_condition_wind_speed);
            precipitation = view.findViewById(R.id.day_condition_precipitation);
            rain = view.findViewById(R.id.day_condition_rain);
        }

        public void setData(DayConditionItem dc)
        {
            dayNumber.setText(dc.getDayNumber());
            temperature.setText(dc.getTemperature());
            rh.setText(dc.getRh());
            pressure.setText(dc.getPressure());
            minTemp.setText(dc.getMinTemp());
            maxTemp.setText(dc.getMaxTemp());
            snow.setText(dc.getSnow());
            clouds.setText(dc.getClouds());
            windSpeed.setText(dc.getWindSpeed());
            precipitation.setText(dc.getPrecipitation());
            rain.setText(dc.getRain());
        }
    }

    @Override
    public DayConditionAdapter.DayCondViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_cond_item, parent, false);
        return new DayCondViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayConditionAdapter.DayCondViewHolder holder, int position) {
        DayConditionItem day = daysList.get(position);
        holder.setData(day);
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }
}
