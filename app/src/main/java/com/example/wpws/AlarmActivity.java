package com.example.wpws;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlarmActivity extends AppCompatActivity {

    private List<DayConditionItem> dayList = new ArrayList<>();
    private RecyclerView daysRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private DayConditionAdapter daysAdapter;
    private Alarm alarm = new Alarm();
    private TextView alarmName;
    private TextView alarmLocation;

    //dialog
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        //get alarm by alarm name
        String name = getIntent().getStringExtra("AlarmName");
        alarm = MainActivity.getAlarm(name);

        //set dialog builder
        dialogBuilder = new AlertDialog.Builder(AlarmActivity.this);

        //make dcList
        makeDayConditionsList();

        findViews();
        setViews();

        //set options button
        ImageButton optionsButton = findViewById(R.id.alarm_options_button);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(AlarmActivity.this, optionsButton);
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
                                    intent.putExtra("AlarmName", alarmName.getText().toString());
                                    setResult(2, intent);
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

        //set recycler view
        buildRecycler();
    }

    //done
    private void findViews()
    {
        daysRecycler = findViewById(R.id.days_recycler);
        alarmLocation = findViewById(R.id.alarm_item_location);
        alarmName = findViewById(R.id.alarm_item_name);
    }

    //done
    private void setViews()
    {
        //set name and location texts
        alarmName.setText(alarm.getName());
        alarmLocation.setText("@" + alarm.getLocation().getName() + "("
                + alarm.getLocation().getCityName() + ", "
                + alarm.getLocation().getCountryName() + ")");
    }

    //done
    private void makeDayConditionsList()
    {
        dayList.clear();
        for(int position = 0; position < alarm.getDays().size(); position++)
        {
            dayList.add(new DayConditionItem(alarm.getDays().get(position), position + 1));
        }
    }

    //done
    private void buildRecycler()
    {
        makeDayConditionsList();
        daysRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        daysAdapter = new DayConditionAdapter(dayList);
        daysRecycler.setLayoutManager(layoutManager);
        daysRecycler.setAdapter(daysAdapter);
    }

    //done
    private void createEditDialog()
    {
        //set dialog and views
        View view = getLayoutInflater().inflate(R.layout.add_alarm_dialog, null);
        EditText alarmName = (EditText) view.findViewById(R.id.alarm_dialog_name);
        Spinner alarmLocation = (Spinner) view.findViewById(R.id.alarm_dialog_locations);
        TextView alarmError = (TextView) view.findViewById(R.id.alarm_dialog_error);

        alarmName.setText(alarm.getName());

        //set spinner
        String[] spinnerLocations = new String[MainActivity.getForecasts().size()];
        int index = 0, selectedIndex = 0;
        for (Forecast forecast : MainActivity.getForecasts()) {
            spinnerLocations[index] = forecast.getName();
            if(forecast.getLocation().getName().equals(alarm.getLocation().getName()) == true)
                selectedIndex = index;
            index++;
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(AlarmActivity.this,
                android.R.layout.simple_list_item_1, spinnerLocations);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alarmLocation.setAdapter(spinnerAdapter);
        alarmLocation.setSelection(selectedIndex);

        //recyclerview
        List<AddDayItem> daysList = new ArrayList<>();
        index = 1;
        for(DayConditions day : alarm.getDays())
        {
            daysList.add(new AddDayItem(index, day));
            index++;
        }
        final AddDayAdapter[] daysAdapter = {new AddDayAdapter(daysList)};
        RecyclerView daysRecycler;
        daysRecycler = view.findViewById(R.id.alarm_dialog_recycler);
        daysRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        daysRecycler.setAdapter(daysAdapter[0]);
        daysRecycler.setLayoutManager(layoutManager);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        //add new day button
        Button addDayButton = (Button) view.findViewById(R.id.alarm_dialog_add_day);
        addDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add new day in recyclerview
                daysList.add(new AddDayItem(daysList.size() + 1));
                daysAdapter[0] = new AddDayAdapter(daysList);
                daysRecycler.setAdapter(daysAdapter[0]);
            }
        });
        //delete last day button
        Button delDayButton = (Button) view.findViewById(R.id.alarm_dialog_delete_day);
        delDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete last day in recyclerview
                if (daysList.size() > 1) {
                    daysList.remove(daysList.size() - 1);
                    daysAdapter[0] = new AddDayAdapter(daysList);
                    daysRecycler.setAdapter(daysAdapter[0]);
                }
            }
        });

        //Save button
        Button saveButton = (Button)view.findViewById(R.id.alarm_dialog_button);
        saveButton.setText("Save alarm");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if all good
                String newName;
                if(alarmName.getText().toString().isEmpty())
                {
                    alarmError.setText("No name given!");
                    return;
                }
                else
                    newName = alarmName.getText().toString();
                for(Alarm alarm : MainActivity.getAlarms())
                {
                    if(alarm.getName() == newName && AlarmActivity.this.alarm.getName().equals(newName) == false)
                    {
                        alarmError.setText("Name already exists!");
                        return;
                    }
                }

                Location newLocation = MainActivity.getLocation(alarmLocation.getSelectedItem().toString());
                if(newLocation == null)
                {
                    alarmError.setText("Location not found");
                    return;
                }
                //if all good then add to alarms
                Alarm editedAlarm = new Alarm();
                editedAlarm.setName(newName);
                editedAlarm.setLocation(newLocation);
                for (AddDayItem day : daysList) {
                    DayConditions newDay = new DayConditions(day);
                    editedAlarm.addDay(newDay);
                }
                MainActivity.UpdateAlarm(alarm, editedAlarm);
                alarm = editedAlarm;
                setViews();
                buildRecycler();
                dialog.dismiss();
            }
        });

    }

}