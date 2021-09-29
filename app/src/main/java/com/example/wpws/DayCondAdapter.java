package com.example.wpws;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DayCondAdapter extends RecyclerView.Adapter<DayCondAdapter.DayCondViewHolder> {

    private List<DayCondItem> dcList;

    public DayCondAdapter(List<DayCondItem> dcList) { this.dcList = dcList; }

    public class DayCondViewHolder extends RecyclerView.ViewHolder {
        private TextView dc_dayNr;
        private TextView dc_temp;
        private TextView dc_rh;
        private TextView dc_pres;
        private TextView dc_minTemp;
        private TextView dc_maxTemp;
        private TextView dc_snow;
        private TextView dc_snowDepth;
        private TextView dc_clouds;
        private TextView dc_wSpeed;
        private TextView dc_precip;
        private TextView dc_pop;

        public DayCondViewHolder(View view)
        {
            super(view);
            dc_dayNr = view.findViewById(R.id.dc_day_nr);
            dc_temp = view.findViewById(R.id.dc_temp);
            dc_rh = view.findViewById(R.id.dc_rh);
            dc_pres = view.findViewById(R.id.dc_pres);
            dc_minTemp = view.findViewById(R.id.dc_min_temp);
            dc_maxTemp = view.findViewById(R.id.dc_max_temp);
            dc_snow = view.findViewById(R.id.dc_snow);
            dc_snowDepth = view.findViewById(R.id.dc_snow_depth);
            dc_clouds = view.findViewById(R.id.dc_clouds);
            dc_wSpeed = view.findViewById(R.id.dc_wspeed);
            dc_precip = view.findViewById(R.id.dc_precip);
            dc_pop = view.findViewById(R.id.dc_pop);
        }

        public void setData(DayCondItem dc)
        {
            dc_dayNr.setText(dc.getDcDayNr());
            dc_temp.setText(dc.getDcTemp());
            dc_rh.setText(dc.getDcRh());
            dc_pres.setText(dc.getDcPres());
            dc_minTemp.setText(dc.getDcMinTemp());
            dc_maxTemp.setText(dc.getDcMaxTemp());
            dc_snow.setText(dc.getDcSnow());
            dc_snowDepth.setText(dc.getDcSnowDepth());
            dc_clouds.setText(dc.getDcClouds());
            dc_wSpeed.setText(dc.getDcWSpeed());
            dc_precip.setText(dc.getDcPrecip());
            dc_pop.setText(dc.getDcPop());
        }
    }

    @Override
    public DayCondAdapter.DayCondViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_cond_item, parent, false);
        return new DayCondViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayCondAdapter.DayCondViewHolder holder, int position) {
        DayCondItem dc = dcList.get(position);
        holder.setData(dc);
    }

    @Override
    public int getItemCount() {
        return dcList.size();
    }
}
