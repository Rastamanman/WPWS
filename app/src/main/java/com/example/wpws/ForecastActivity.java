package com.example.wpws;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ForecastActivity extends AppCompatActivity {

    private Forecast forecast = new Forecast();
    private TextView forecastName;
    private TextView forecastLocation;
    private List<Day> days = new ArrayList<Day>();

    //selected day info
    private TextView selectedDate;
    private TextView selectedDescription;
    private TextView selectedAvgTemp;
    private TextView selectedMinTemp;
    private TextView selectedMaxTemp;
    private TextView selectedRh;
    private TextView selectedPrecipitation;
    private TextView selectedRain;
    private TextView selectedSnow;
    private TextView selectedPressure;
    private TextView selectedClouds;
    private TextView selectedWindSpeed;

    //grid
    private GridLayout forecastGrid;

    //dialog
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        //get forecast
        String fcName = getIntent().getStringExtra("ForecastName");
        forecast = MainActivity.getForecast(fcName);
        days = forecast.getDays();

        findViews();
        setViews();

        //set dialog builder
        dialogBuilder = new AlertDialog.Builder(ForecastActivity.this);

        //options button
        ImageButton optionsButton = findViewById(R.id.forecast_options_button);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(ForecastActivity.this, optionsButton);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Delete"))
                        {
                            //open are you sure Dialog
                            View view = getLayoutInflater().inflate(R.layout.are_you_sure_dialog
                                    , null);
                            dialogBuilder.setView(view);
                            dialog = dialogBuilder.create();
                            dialog.show();

                            //dialog behaviour
                            Button yesButton = (Button)view.findViewById(R.id.ays_yes);
                            yesButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    Intent intent = new Intent();
                                    //intent.putExtra("WhatToDo", "DELETE_FORECAST");
                                    intent.putExtra("ForecastName", forecastName.getText().toString());
                                    setResult(3, intent);
                                    finish();
                                }
                            });
                            Button noButton = (Button)view.findViewById(R.id.ays_no);
                            noButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        }
                        else
                        {
                            //open edit dialog
                            createEditDialog();
                        }
                        return false;
                    }
                });

                popup.show();
            }
        });
    }

    //done
    private void createEditDialog() {
        View view = getLayoutInflater().inflate(R.layout.add_forecast_dialog, null);
        TextView forecastTitle = (TextView) view.findViewById(R.id.add_forecast_title);
        EditText forecastName = (EditText) view.findViewById(R.id.add_forecast_name);
        EditText forecastLatitude = (EditText) view.findViewById(R.id.add_forecast_latitude);
        EditText forecastLongitude = (EditText) view.findViewById(R.id.add_forecast_longitude);
        TextView forecastSaveButton = (Button) view.findViewById(R.id.add_forecast_button);
        TextView forecastError = (TextView) view.findViewById(R.id.add_forecast_error);

        //set texts
        forecastTitle.setText("Edit Forecast");
        forecastName.setText(forecast.getName());
        forecastLatitude.setText("" + forecast.getLatitude());
        forecastLongitude.setText("" + forecast.getLongitude());

        //show dialog
        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        //save button
        forecastSaveButton.setText("Save forecast");
        forecastSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check name validity
                String newName;
                if(forecastName.getText().toString().isEmpty())
                {
                    forecastError.setText("No name given!");
                    return;
                }
                else
                    newName = forecastName.getText().toString();
                for(Location location : MainActivity.getLocations())
                {
                    if(location.getName().equals(newName) && (forecast.getName().equals(newName)) == false)
                    {
                        forecastError.setText("Location name already exists!");
                        return;
                    }
                }

                //check longitude validity
                float longitude = 999;
                if(forecastLongitude.getText().toString().isEmpty())
                {
                    forecastError.setText("No Longitude input!");
                    return;
                }
                else
                    try {
                        longitude = Float.valueOf(forecastLongitude.getText().toString());
                    }
                    catch(Exception e)
                    {
                        forecastError.setText("Longitude is not a number");
                    }
                if(longitude < -180 || longitude > 180)
                {
                    forecastError.setText("Longitude out of bounds!");
                    return;
                }

                //check latitude validity
                float latitude = 999;
                if(forecastLatitude.getText().toString().isEmpty())
                {
                    forecastError.setText("No Latitude input!");
                    return;
                }
                else
                    try{
                        latitude = Float.valueOf(forecastLatitude.getText().toString());
                    }
                    catch(Exception e)
                    {
                        forecastError.setText("Latitude is not a number");
                    }
                if(latitude < -90 || latitude > 90)
                {
                    forecastError.setText("Latitude out of bounds!");
                    return;
                }

                //if everything good
                Location newLoc = new Location(Float.parseFloat(forecastLatitude.getText().toString())
                                , Float.parseFloat(forecastLongitude.getText().toString()), newName);
                MainActivity.UpdateLocation(forecast.getLocation(), newLoc);
                Forecast editedForecast = new Forecast(newLoc);
                editedForecast.updateForecastLinear();
                MainActivity.UpdateForecast(forecast, editedForecast);
                forecast = editedForecast;
                setViews();
                dialog.dismiss();
            }
        });
    }

    //done
    private void findViews()
    {
        forecastName = findViewById(R.id.forecast_name);
        forecastLocation = findViewById(R.id.forecast_item_location);
        selectedDate = findViewById(R.id.selected_valid_date);
        selectedAvgTemp = findViewById(R.id.selected_average_temp);
        selectedDescription = findViewById(R.id.selected_description);
        selectedMinTemp = findViewById(R.id.selected_min_temp);
        selectedMaxTemp = findViewById(R.id.selected_max_temp);
        selectedRh = findViewById(R.id.selected_rh);
        selectedPrecipitation = findViewById(R.id.selected_precipitation);
        selectedRain = findViewById(R.id.selected_rain);
        selectedSnow = findViewById(R.id.selected_snow);
        selectedPressure = findViewById(R.id.selected_pressure);
        selectedClouds = findViewById(R.id.selected_clouds);
        selectedWindSpeed = findViewById(R.id.selected_wind_speed);
        forecastGrid = findViewById(R.id.forecast_grid);
    }

    //done
    private void setViews()
    {
        //set texts
        forecastName.setText(forecast.getName());
        forecastLocation.setText("@" + forecast.getCityName() + ", " + forecast.getCountryName());

        //set grid and selected
        setSelected(days.get(0));
        setGridItems();
    }

    //done
    private void setSelected(Day day)
    {
        selectedDate.setText("Day: " + day.getValidDate());
        selectedDescription.setText(day.getWeatherDescription());
        selectedAvgTemp.setText("Average Temperature: " + day.getTemperature() + " C");
        selectedMinTemp.setText("Minimum Temperature: " + day.getMinTemp() + " C");
        selectedMaxTemp.setText("Maximum Temperature: " + day.getMaxTemp() + " C");
        selectedRh.setText("Relative Humidity: " + day.getRh() + " %");
        selectedPrecipitation.setText("Precipitation: " + day.getPrecipitation() + " mm");
        selectedRain.setText("Chance of Rain: " + day.getRain() + " %");
        selectedSnow.setText("Snow: " + day.getSnow() + " mm");
        selectedPressure.setText("Pressure: " + day.getPressure() + " mb");
        selectedClouds.setText("Clouds coverage: " + day.getClouds() + " %");
        selectedWindSpeed.setText("Wind speed: " + day.getWindSpeed() + " m/s");
    }

    //done
    private void setGridItems()
    {
        for(int index = 1; index <= days.size(); index++)
        {
            //set image and valid date text
            ImageView image = findViewById(getResources().getIdentifier("day_" + index + "_image",
                    "id", getPackageName()));
            setIcon(image, days.get(index - 1).getWeatherIcon());
            TextView text = findViewById(getResources().getIdentifier("day_" + index + "_text",
                    "id", getPackageName()));
            String date = days.get(index - 1).getValidDate();
            String day = date.substring(8);
            String month = date.substring(5,7);
            switch (month){
                case "01":
                    month = "Jan";
                    break;
                case "02":
                    month = "Feb";
                    break;
                case "03":
                    month = "Mar";
                    break;
                case "04":
                    month = "Apr";
                    break;
                case "05":
                    month = "May";
                    break;
                case "06":
                    month = "Jun";
                    break;
                case "07":
                    month = "Jul";
                    break;
                case "08":
                    month = "Aug";
                    break;
                case "09":
                    month = "Sep";
                    break;
                case "10":
                    month = "Oct";
                    break;
                case "11":
                    month = "Nov";
                    break;
                case "12":
                    month = "Dec";
                    break;
                default:
                    month = "Error";
                    break;
            }

            if(index == 1)
                text.setText("Today");
            else
                text.setText(day + " " + month);

            //set clickable
            LinearLayout view = (LinearLayout) forecastGrid.getChildAt(index - 1);
            final int finalIndex = index;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelected(days.get(finalIndex - 1));
                }
            });
        }
    }

    //done
    private void setIcon(ImageView target, String iconCode)
    {
        target.setImageResource(getResources().getIdentifier(iconCode, "drawable"
                ,getPackageName()));
    }
}