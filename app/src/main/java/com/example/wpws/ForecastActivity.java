package com.example.wpws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ForecastActivity extends AppCompatActivity {

    private Forecast fc = new Forecast();
    private TextView fcTextName;
    private TextView fcTextLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        //get forecast
        String fcName = getIntent().getStringExtra("ForecastName");
        fc = MainActivity.getForecastByName(fcName);

        //set texts
        fcTextName = findViewById(R.id.fc_name);
        fcTextName.setText(fc.getName());
        fcTextLoc = findViewById(R.id.fc_location);
        fcTextLoc.setText("@" + fc.getCityName() + ", " + fc.getCountryName());

        ImageButton alButton = findViewById(R.id.fc_delete_button);
        alButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.putExtra("WhatToDo", "DELETE_FORECAST");
                intent.putExtra("ForecastName", fcTextName.getText().toString());
                setResult(3, intent);
                finish();
            }
        });
    }
}