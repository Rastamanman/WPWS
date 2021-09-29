package com.example.wpws;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddDayAdapter extends RecyclerView.Adapter<AddDayAdapter.AddDayViewHolder> {

    private List<AddDayItem> daysList;

    public AddDayAdapter(List<AddDayItem> dayslist) { this.daysList = dayslist; }

    public static class AddDayViewHolder extends RecyclerView.ViewHolder {
        private TextView day_nr;
        private TextView day_temp_text;
        private Spinner day_temp_mode;
        private EditText day_temp_val;
        private TextView day_temp_symb;
        private TextView day_minTemp_text;
        private Spinner day_minTemp_mode;
        private EditText day_minTemp_val;
        private TextView day_minTemp_symb;
        private TextView day_maxTemp_text;
        private Spinner day_maxTemp_mode;
        private EditText day_maxTemp_val;
        private TextView day_maxTemp_symb;
        private TextView day_rh_text;
        private Spinner day_rh_mode;
        private EditText day_rh_val;
        private TextView day_rh_symb;
        private TextView day_precip_text;
        private Spinner day_precip_mode;
        private EditText day_precip_val;
        private TextView day_precip_symb;
        private TextView day_cor_text;
        private Spinner day_cor_mode;
        private EditText day_cor_val;
        private TextView day_cor_symb;
        private TextView day_snow_text;
        private Spinner day_snow_mode;
        private EditText day_snow_val;
        private TextView day_snow_symb;
        private TextView day_pres_text;
        private Spinner day_pres_mode;
        private EditText day_pres_val;
        private TextView day_pres_symb;
        private TextView day_clouds_text;
        private Spinner day_clouds_mode;
        private EditText day_clouds_val;
        private TextView day_clouds_symb;
        private TextView day_wsp_text;
        private Spinner day_wsp_mode;
        private EditText day_wsp_val;
        private TextView day_wsp_symb;

        public AddDayViewHolder(View view)
        {
            super(view);
            day_nr = view.findViewById(R.id.day_text);
            //temp
            day_temp_text = view.findViewById(R.id.temp_text);
            day_temp_mode = view.findViewById(R.id.temp_spinner);
            day_temp_val = view.findViewById(R.id.temp_edit);
            day_temp_symb = view.findViewById(R.id.temp_symbol);
            //min temp
            day_minTemp_text = view.findViewById(R.id.min_temp_text);
            day_minTemp_mode = view.findViewById(R.id.min_temp_spinner);
            day_minTemp_val = view.findViewById(R.id.min_temp_edit);
            day_minTemp_symb = view.findViewById(R.id.min_temp_symbol);
            //max temp
            day_maxTemp_text = view.findViewById(R.id.max_temp_text);
            day_maxTemp_mode = view.findViewById(R.id.max_temp_spinner);
            day_maxTemp_val = view.findViewById(R.id.max_temp_edit);
            day_maxTemp_symb = view.findViewById(R.id.max_temp_symbol);
            //rh
            day_rh_text = view.findViewById(R.id.rh_text);
            day_rh_mode = view.findViewById(R.id.rh_spinner);
            day_rh_val = view.findViewById(R.id.rh_edit);
            day_rh_symb = view.findViewById(R.id.rh_symbol);
            //precip
            day_precip_text = view.findViewById(R.id.precip_text);
            day_precip_mode = view.findViewById(R.id.precip_spinner);
            day_precip_val = view.findViewById(R.id.precip_edit);
            day_precip_symb = view.findViewById(R.id.precip_symbol);
            //chance of rain
            day_cor_text = view.findViewById(R.id.cor_text);
            day_cor_mode = view.findViewById(R.id.cor_spinner);
            day_cor_val = view.findViewById(R.id.cor_edit);
            day_cor_symb = view.findViewById(R.id.cor_symbol);
            //snow
            day_snow_text = view.findViewById(R.id.snow_text);
            day_snow_mode = view.findViewById(R.id.snow_spinner);
            day_snow_val = view.findViewById(R.id.snow_edit);
            day_snow_symb = view.findViewById(R.id.snow_symbol);
            //pres
            day_pres_text = view.findViewById(R.id.pres_text);
            day_pres_mode = view.findViewById(R.id.pres_spinner);
            day_pres_val = view.findViewById(R.id.pres_edit);
            day_pres_symb = view.findViewById(R.id.pres_symbol);
            //clouds
            day_clouds_text = view.findViewById(R.id.clouds_text);
            day_clouds_mode = view.findViewById(R.id.clouds_spinner);
            day_clouds_val = view.findViewById(R.id.clouds_edit);
            day_clouds_symb = view.findViewById(R.id.clouds_symbol);
            //wind speed
            day_wsp_text = view.findViewById(R.id.wsp_text);
            day_wsp_mode = view.findViewById(R.id.wsp_spinner);
            day_wsp_val = view.findViewById(R.id.wsp_edit);
            day_wsp_symb = view.findViewById(R.id.wsp_symbol);
        }

        public void setData(AddDayItem day)
        {
            day_nr.setText(day.getDayNr());
            //temp
            day_temp_text.setText(day.getTempText());
            day_temp_mode.setAdapter(day.getAdapter());
            day_temp_mode.setSelection(day.getTempMode());
            day_temp_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setTempMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            day_temp_val.setText("" + day.getTempVal());
            day_temp_val.addTextChangedListener(new MyTextWatcher<EditText>(day_temp_val) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setTempVal(Float.parseFloat(target.getText().toString()));
                }
            });
            day_temp_symb.setText(day.getTempSymb());
            //min temp
            day_minTemp_text.setText(day.getMinTempText());
            day_minTemp_mode.setAdapter(day.getAdapter());
            day_minTemp_mode.setSelection(day.getMaxTempMode());
            day_minTemp_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setMinTempMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            day_minTemp_val.setText("" + day.getMinTempVal());
            day_minTemp_val.addTextChangedListener(new MyTextWatcher<EditText>(day_minTemp_val) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setMinTempVal(Float.parseFloat(target.getText().toString()));
                }
            });
            day_minTemp_symb.setText(day.getMinTempSymb());
            //max temp
            day_maxTemp_text.setText(day.getMaxTempText());
            day_maxTemp_mode.setAdapter(day.getAdapter());
            day_maxTemp_mode.setSelection(day.getMaxTempMode());
            day_maxTemp_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setMaxTempMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            day_maxTemp_val.setText("" + day.getMaxTempVal());
            day_maxTemp_val.addTextChangedListener(new MyTextWatcher<EditText>(day_maxTemp_val) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setMaxTempVal(Float.parseFloat(target.getText().toString()));
                }
            });
            day_maxTemp_symb.setText(day.getMaxTempSymb());
            //rh
            day_rh_text.setText(day.getRhText());
            day_rh_mode.setAdapter(day.getAdapter());
            day_rh_mode.setSelection(day.getRhMode());
            day_rh_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setRhMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            day_rh_val.setText("" + day.getRhVal());
            day_rh_val.addTextChangedListener(new MyTextWatcher<EditText>(day_rh_val) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setRhVal(Float.parseFloat(target.getText().toString()));
                }
            });
            day_rh_symb.setText(day.getRhSymb());
            //precip
            day_precip_text.setText(day.getPrecipText());
            day_precip_mode.setAdapter(day.getAdapter());
            day_precip_mode.setSelection(day.getPrecipMode());
            day_precip_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setPrecipMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            day_precip_val.setText("" + day.getPrecipVal());
            day_precip_val.addTextChangedListener(new MyTextWatcher<EditText>(day_precip_val) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setPrecipVal(Float.parseFloat(target.getText().toString()));
                }
            });
            day_precip_symb.setText(day.getPrecipSymb());
            //chance of rain
            day_cor_text.setText(day.getCorText());
            day_cor_mode.setAdapter(day.getAdapter());
            day_cor_mode.setSelection(day.getCorMode());
            day_cor_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setCorMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            day_cor_val.setText("" + day.getCorVal());
            day_cor_val.addTextChangedListener(new MyTextWatcher<EditText>(day_cor_val) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setCorVal(Float.parseFloat(target.getText().toString()));
                }
            });
            day_cor_symb.setText(day.getCorSymb());
            //snow
            day_snow_text.setText(day.getSnowText());
            day_snow_mode.setAdapter(day.getAdapter());
            day_snow_mode.setSelection(day.getSnowMode());
            day_snow_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setSnowMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            day_snow_val.setText("" + day.getSnowVal());
            day_snow_val.addTextChangedListener(new MyTextWatcher<EditText>(day_snow_val) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setSnowVal(Float.parseFloat(target.getText().toString()));
                }
            });
            day_snow_symb.setText(day.getSnowSymb());
            //pres
            day_pres_text.setText(day.getPresText());
            day_pres_mode.setAdapter(day.getAdapter());
            day_pres_mode.setSelection(day.getPresMode());
            day_pres_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setPresMode(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            day_pres_val.setText("" + day.getPresVal());
            day_pres_val.addTextChangedListener(new MyTextWatcher<EditText>(day_pres_val) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setPresVal(Float.parseFloat(target.getText().toString()));
                }
            });
            //clouds coverage
            day_clouds_text.setText(day.getCloudsText());
            day_clouds_mode.setAdapter(day.getAdapter());
            day_clouds_mode.setSelection(day.getCloudsMode());
            day_clouds_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setCloudsMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            day_clouds_val.setText("" + day.getCloudsVal());
            day_clouds_val.addTextChangedListener(new MyTextWatcher<EditText>(day_clouds_val) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setCloudsVal(Float.parseFloat(target.getText().toString()));
                }
            });
            day_clouds_symb.setText(day.getCloudsSymb());
            //wind speed
            day_wsp_text.setText(day.getWspText());
            day_wsp_mode.setAdapter(day.getAdapter());
            day_wsp_mode.setSelection(day.getWspMode());
            day_wsp_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setWspMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            day_wsp_val.setText("" + day.getWspVal());
            day_wsp_val.addTextChangedListener(new MyTextWatcher<EditText>(day_wsp_val) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setWspVal(Float.parseFloat(target.getText().toString()));
                }
            });
            day_wsp_symb.setText(day.getWspSymb());
        }
    }

    @Override
    public AddDayAdapter.AddDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_day_item, parent, false);
        return new AddDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddDayAdapter.AddDayViewHolder holder, int position)
    {
        AddDayItem day = daysList.get(position);
        holder.setData(day);
    }

    @Override
    public int getItemCount()
    {
        return daysList.size();
    }

}
