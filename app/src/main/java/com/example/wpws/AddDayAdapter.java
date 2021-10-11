package com.example.wpws;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddDayAdapter extends RecyclerView.Adapter<AddDayAdapter.AddDayViewHolder> {

    private List<AddDayItem> daysList;

    public AddDayAdapter(List<AddDayItem> dayslist) { this.daysList = dayslist; }

    public static class AddDayViewHolder extends RecyclerView.ViewHolder {
        private TextView dayNumber;
        private TextView tempText;
        private Spinner tempMode;
        private EditText tempValue;
        private TextView tempSymbol;
        private TextView minTempText;
        private Spinner minTempMode;
        private EditText minTempValue;
        private TextView minTempSymbol;
        private TextView maxTempText;
        private Spinner maxTempMode;
        private EditText maxTempValue;
        private TextView maxTempSymbol;
        private TextView rhText;
        private Spinner rhMode;
        private EditText rhValue;
        private TextView rhSymbol;
        private TextView precipitationText;
        private Spinner precipitationMode;
        private EditText precipitationValue;
        private TextView precipitationSymbol;
        private TextView rainText;
        private Spinner rainMode;
        private EditText rainValue;
        private TextView rainSymbol;
        private TextView snowText;
        private Spinner snowMode;
        private EditText snowValue;
        private TextView snowSymbol;
        private TextView pressureText;
        private Spinner pressureMode;
        private EditText pressureValue;
        private TextView pressureSymbol;
        private TextView cloudsText;
        private Spinner cloudsMode;
        private EditText cloudsValue;
        private TextView cloudsSymbol;
        private TextView windSpeedText;
        private Spinner windSpeddMode;
        private EditText windSpeedValue;
        private TextView windSpeedSymbol;

        public AddDayViewHolder(View view)
        {
            super(view);
            dayNumber = view.findViewById(R.id.day_text);
            //temp
            tempText = view.findViewById(R.id.temp_text);
            tempMode = view.findViewById(R.id.temp_spinner);
            tempValue = view.findViewById(R.id.temp_edit);
            tempSymbol = view.findViewById(R.id.temp_symbol);
            //min temp
            minTempText = view.findViewById(R.id.min_temp_text);
            minTempMode = view.findViewById(R.id.min_temp_spinner);
            minTempValue = view.findViewById(R.id.min_temp_edit);
            minTempSymbol = view.findViewById(R.id.min_temp_symbol);
            //max temp
            maxTempText = view.findViewById(R.id.max_temp_text);
            maxTempMode = view.findViewById(R.id.max_temp_spinner);
            maxTempValue = view.findViewById(R.id.max_temp_edit);
            maxTempSymbol = view.findViewById(R.id.max_temp_symbol);
            //rh
            rhText = view.findViewById(R.id.rh_text);
            rhMode = view.findViewById(R.id.rh_spinner);
            rhValue = view.findViewById(R.id.rh_edit);
            rhSymbol = view.findViewById(R.id.rh_symbol);
            //precip
            precipitationText = view.findViewById(R.id.precipitation_text);
            precipitationMode = view.findViewById(R.id.precipitation_spinner);
            precipitationValue = view.findViewById(R.id.precipitation_edit);
            precipitationSymbol = view.findViewById(R.id.precipitation_symbol);
            //chance of rain
            rainText = view.findViewById(R.id.rain_text);
            rainMode = view.findViewById(R.id.rain_spinner);
            rainValue = view.findViewById(R.id.rain_edit);
            rainSymbol = view.findViewById(R.id.rain_symbol);
            //snow
            snowText = view.findViewById(R.id.snow_text);
            snowMode = view.findViewById(R.id.snow_spinner);
            snowValue = view.findViewById(R.id.snow_edit);
            snowSymbol = view.findViewById(R.id.snow_symbol);
            //pres
            pressureText = view.findViewById(R.id.pressure_text);
            pressureMode = view.findViewById(R.id.pressure_spinner);
            pressureValue = view.findViewById(R.id.pressure_edit);
            pressureSymbol = view.findViewById(R.id.pressure_symbol);
            //clouds
            cloudsText = view.findViewById(R.id.clouds_text);
            cloudsMode = view.findViewById(R.id.clouds_spinner);
            cloudsValue = view.findViewById(R.id.clouds_edit);
            cloudsSymbol = view.findViewById(R.id.clouds_symbol);
            //wind speed
            windSpeedText = view.findViewById(R.id.wind_speed_text);
            windSpeddMode = view.findViewById(R.id.wind_speed_spinner);
            windSpeedValue = view.findViewById(R.id.wind_speed_edit);
            windSpeedSymbol = view.findViewById(R.id.wind_speed_symbol);
        }

        public void setData(AddDayItem day)
        {
            //day nr
            dayNumber.setText(day.getDayNumber());
            //temp
            tempText.setText(day.getTempText());
            tempMode.setAdapter(day.getAdapter());
            tempMode.setSelection(day.getTempMode());
            tempMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setTempMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            if(day.getTempValue() != 0)
                tempValue.setText("" + day.getTempValue());
            tempValue.addTextChangedListener(new MyTextWatcher<EditText>(tempValue) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setTempValue((float)(Math.round(Double.parseDouble(target.getText().toString())
                                * 100.0) / 100.0));
                }
            });
            tempSymbol.setText(day.getTempSymbol());
            //min temp
            minTempText.setText(day.getMinTempText());
            minTempMode.setAdapter(day.getAdapter());
            minTempMode.setSelection(day.getMinTempMode());
            minTempMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setMinTempMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            if(day.getMinTempValue() != 0)
                minTempValue.setText("" + day.getMinTempValue());
            minTempValue.addTextChangedListener(new MyTextWatcher<EditText>(minTempValue) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setMinTempValue((float)(Math.round(Double.parseDouble(target.getText().toString())
                                * 100.0) / 100.0));
                }
            });
            minTempSymbol.setText(day.getMinTempSymbol());
            //max temp
            maxTempText.setText(day.getMaxTempText());
            maxTempMode.setAdapter(day.getAdapter());
            maxTempMode.setSelection(day.getMaxTempMode());
            maxTempMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setMaxTempMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            if(day.getMaxTempValue() != 0)
                maxTempValue.setText("" + day.getMaxTempValue());
            maxTempValue.addTextChangedListener(new MyTextWatcher<EditText>(maxTempValue) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setMaxTempValue(Float.parseFloat(maxTempValue.getText().toString()));
                }
            });
            maxTempSymbol.setText(day.getMaxTempSymbol());
            //rh
            rhText.setText(day.getRhText());
            rhMode.setAdapter(day.getAdapter());
            rhMode.setSelection(day.getRhMode());
            rhMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setRhMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            if(day.getRhValue() != 0)
                rhValue.setText("" + day.getRhValue());
            rhValue.addTextChangedListener(new MyTextWatcher<EditText>(rhValue) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setRhValue((float)(Math.round(Double.parseDouble(target.getText().toString())
                                * 100.0) / 100.0));
                }
            });
            rhSymbol.setText(day.getRhSymbol());
            //precip
            precipitationText.setText(day.getPrecipitationText());
            precipitationMode.setAdapter(day.getAdapter());
            precipitationMode.setSelection(day.getPrecipitationMode());
            precipitationMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setPrecipitationMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            if(day.getPrecipitationValue() != 0)
                precipitationValue.setText("" + day.getPrecipitationValue());
            precipitationValue.addTextChangedListener(new MyTextWatcher<EditText>(precipitationValue) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setPrecipitationValue((float)(Math.round(Double.parseDouble(target.getText().toString())
                                * 100.0) / 100.0));
                }
            });
            precipitationSymbol.setText(day.getPrecipitationSymbol());
            //chance of rain
            rainText.setText(day.getRainText());
            rainMode.setAdapter(day.getAdapter());
            rainMode.setSelection(day.getRainMode());
            rainMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setRainMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            if(day.getRainValue() != 0)
                rainValue.setText("" + day.getRainValue());
            rainValue.addTextChangedListener(new MyTextWatcher<EditText>(rainValue) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setRainValue((float)(Math.round(Double.parseDouble(target.getText().toString())
                                * 100.0) / 100.0));
                }
            });
            rainSymbol.setText(day.getRainSymbol());
            //snow
            snowText.setText(day.getSnowText());
            snowMode.setAdapter(day.getAdapter());
            snowMode.setSelection(day.getSnowMode());
            snowMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setSnowMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            if(day.getSnowValue() != 0)
                snowValue.setText("" + day.getSnowValue());
            snowValue.addTextChangedListener(new MyTextWatcher<EditText>(snowValue) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setSnowValue((float)(Math.round(Double.parseDouble(target.getText().toString())
                                * 100.0) / 100.0));
                }
            });
            snowSymbol.setText(day.getSnowSymbol());
            //pres
            pressureText.setText(day.getPressureText());
            pressureMode.setAdapter(day.getAdapter());
            pressureMode.setSelection(day.getPressureMode());
            pressureMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setPressureMode(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            if(day.getPressureValue() != 0)
                pressureValue.setText("" + day.getPressureValue());
            pressureValue.addTextChangedListener(new MyTextWatcher<EditText>(pressureValue) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setPressureValue((float)(Math.round(Double.parseDouble(target.getText().toString())
                                * 100.0) / 100.0));
                }
            });
            //clouds coverage
            cloudsText.setText(day.getCloudsText());
            cloudsMode.setAdapter(day.getAdapter());
            cloudsMode.setSelection(day.getCloudsMode());
            cloudsMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setCloudsMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            if(day.getCloudsValue() != 0)
                cloudsValue.setText("" + day.getCloudsValue());
            cloudsValue.addTextChangedListener(new MyTextWatcher<EditText>(cloudsValue) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setCloudsValue((float)(Math.round(Double.parseDouble(target.getText().toString())
                                * 100.0) / 100.0));
                }
            });
            cloudsSymbol.setText(day.getCloudsSymbol());
            //wind speed
            windSpeedText.setText(day.getWindSpeedText());
            windSpeddMode.setAdapter(day.getAdapter());
            windSpeddMode.setSelection(day.getWindSpeedMode());
            windSpeddMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    day.setWindSpeedMode(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            if(day.getWindSpeedValue() != 0)
                windSpeedValue.setText("" + day.getWindSpeedValue());
            windSpeedValue.addTextChangedListener(new MyTextWatcher<EditText>(windSpeedValue) {
                @Override
                public void onTextChanged(EditText target, Editable s){
                    if(target.getText().toString().isEmpty() == false)
                        day.setWindSpeedValue((float)(Math.round(Double.parseDouble(target.getText().toString())
                        * 100.0) / 100.0));
                }
            });
            windSpeedSymbol.setText(day.getWindSpeedSymbol());
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
