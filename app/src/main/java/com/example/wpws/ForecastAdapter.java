package com.example.wpws;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<ForecastItem> forecastItems;
    private static ClickListener clickListener;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener)
    {
        ForecastAdapter.clickListener = clickListener;
    }

    public void clear()
    {
        forecastItems.clear();
        notifyDataSetChanged();
    }

    public ForecastAdapter(List<ForecastItem> forecastItems)
    {
        this.forecastItems = forecastItems;
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView forecastName;
        private TextView forecastLocation;
        private TextView forecastDescription;
        private TextView forecastTemperature;
        private TextView forecastRh;
        private TextView forecastWindSpeed;
        private TextView forecastRain;
        private TextView forecastRealFeel;
        private TextView forecastUv;

        public ForecastViewHolder(View view)
        {
            super(view);
            forecastName = view.findViewById(R.id.forecast_item_name);
            forecastLocation = view.findViewById(R.id.forecast_item_location);
            forecastDescription = view.findViewById(R.id.forecast_item_description);
            forecastTemperature = view.findViewById(R.id.forecast_item_temperature);
            forecastRh = view.findViewById(R.id.forecast_Item_rh);
            forecastWindSpeed = view.findViewById(R.id.forecast_item_wind_speed);
            forecastRain = view.findViewById(R.id.forecast_item_rain);
            forecastRealFeel = view.findViewById(R.id.forecast_item_real_feel);
            forecastUv = view.findViewById(R.id.forecast_item_uv);
            itemView.setOnClickListener(this);
        }

        public void setData(ForecastItem forecast)
        {
            forecastName.setText(forecast.getForecastName());
            forecastLocation.setText(forecast.getForecastLocation());
            forecastDescription.setText(forecast.getForecastDescription());
            forecastTemperature.setText(forecast.getForecastTemperature());
            forecastRh.setText(forecast.getForecastRh());
            forecastWindSpeed.setText(forecast.getForecastWindSpeed());
            forecastRain.setText(forecast.getForecastRain());
            forecastRealFeel.setText(forecast.getForecastRealFeel());
            forecastUv.setText(forecast.getForecastUv());
        }

        @Override
        public void onClick(View v)
        {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastAdapter.ForecastViewHolder holder, int position)
    {
        ForecastItem forecast = forecastItems.get(position);
        holder.setData(forecast);
    }

    @Override
    public int getItemCount()
    {
        return forecastItems.size();
    }
}
