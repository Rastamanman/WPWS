package com.example.wpws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AlarmActivity extends AppCompatActivity {

    private List<DayCondItem> dcList = new ArrayList<>();
    private RecyclerView mainRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private DayCondAdapter dcAdapter;
    private Alarm al = new Alarm();
    private TextView alTextName;
    private TextView alTextLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        //get alarm by alarm name
        String alName = getIntent().getStringExtra("AlarmName");
        al = MainActivity.getAlarmByName(alName);

        //make dcList
        makeDcList();

        //set name and location texts
        alTextName = findViewById(R.id.al_name);
        alTextName.setText(al.getName());
        alTextLoc = findViewById(R.id.al_location);
        alTextLoc.setText("@" + al.getLocation().getName() + "(" + al.getLocation().getCity_name() +
                 ", " + al.getLocation().getCountry_name() + ")");

        //set onClick delete button
        ImageButton alButton = findViewById(R.id.al_delete_button);
        alButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.putExtra("WhatToDo", "DELETE_ALARM");
                intent.putExtra("AlarmName", alTextName.getText().toString());
                setResult(2, intent);
                finish();
            }
        });

        //set recycler view
        buildRecycler();
    }

    //done
    private void makeDcList()
    {
        dcList.clear();
        for(int position = 0; position < al.getDays().size(); position++)
        {
            dcList.add(new DayCondItem(al.getDays().get(position), position + 1));
        }
    }

    //done
    private void buildRecycler()
    {
        mainRecycler = findViewById(R.id.dc_recycler);
        mainRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        dcAdapter = new DayCondAdapter(dcList);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecycler.setAdapter(dcAdapter);
    }
}