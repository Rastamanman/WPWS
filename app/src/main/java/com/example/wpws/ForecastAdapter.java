package com.example.wpws;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<ForecastItem> fcList;
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
        fcList.clear();
        notifyDataSetChanged();
    }

    public ForecastAdapter(List<ForecastItem> fcList)
    {
        this.fcList = fcList;
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView fc_name;
        private TextView fc_loc;
        private TextView fc_desc;
        private TextView fc_temp;
        private TextView fc_rh;
        private TextView fc_wspeed;
        private TextView fc_pop;
        private TextView fc_rf;
        private TextView fc_uv;

        public ForecastViewHolder(View view)
        {
            super(view);
            fc_name = view.findViewById(R.id.fc_name);
            fc_loc = view.findViewById(R.id.fc_location);
            fc_desc = view.findViewById(R.id.fc_description);
            fc_temp = view.findViewById(R.id.fc_temp);
            fc_rh = view.findViewById(R.id.fc_rh);
            fc_wspeed = view.findViewById(R.id.fc_wspeed);
            fc_pop = view.findViewById(R.id.fc_pop);
            fc_rf = view.findViewById(R.id.fc_rf);
            fc_uv = view.findViewById(R.id.fc_uv);
            itemView.setOnClickListener(this);
        }

        public void setData(ForecastItem fc)
        {
            fc_name.setText(fc.getFcName());
            fc_loc.setText(fc.getFcLocation());
            fc_desc.setText(fc.getFcDesc());
            fc_temp.setText(fc.getFcTemp());
            fc_rh.setText(fc.getFcRh());
            fc_wspeed.setText(fc.getFcWspeed());
            fc_pop.setText(fc.getFcPop());
            fc_rf.setText(fc.getFcRealFeel());
            fc_uv.setText(fc.getFcUv());
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
        ForecastItem fc = fcList.get(position);
        holder.setData(fc);
    }

    @Override
    public int getItemCount()
    {
        return fcList.size();
    }
}
