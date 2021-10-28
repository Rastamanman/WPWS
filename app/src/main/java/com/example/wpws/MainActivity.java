package com.example.wpws;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;
public class MainActivity extends AppCompatActivity {

    //request data
    private static final String FORECAST_URL ="https://api.weatherbit.io/v2.0/forecast/daily?lat=%s&lon=%s&key=%s";
    private static final String CURRENT_URL ="https://api.weatherbit.io/v2.0/current?lat=%s&lon=%s&key=%s";
    private static String API_KEY;

    //data objects
    private static List<Forecast> forecasts = new ArrayList<>();
    private static List<Location> locations = new ArrayList<>();
    private static List<Alarm> alarms = new ArrayList<>();
    private static List<NotificationID> notificationsID = new ArrayList<>();
    private String currentView = ""; //FORECAST or ALARM

    //recycler view objects
    private RecyclerView mainRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<ForecastItem> forecastItems = new ArrayList<>();
    private static ArrayList<AlarmItem> alarmItems = new ArrayList<>();
    private ForecastAdapter forecastAdapter;
    private AlarmAdapter alarmAdapter;
    private SwipeRefreshLayout swipeContainer;

    //dialog objects
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private static ArrayAdapter<String> spinnerValAdapter;
    public static ArrayAdapter<String> getSpinnerValAdapter() {
        return spinnerValAdapter;
    }
    public static String[] spinnerValues = {"d.c.", "<", "<=", "=", "=>", ">"};
    public static String[] periodValues = {"1", "3", "12", "24", "never"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy((policy));

        //load app data
        loadData();

        //create Notification Channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("MyNotif","AlarmNotif",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //get api key
        API_KEY = BuildConfig.API_KEY;

        //set swipeContainer(update on scroll up)
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(currentView == "FORECAST")
                {
                    getAllForecasts();
                    updateForecastItems();
                    forecastAdapter.notifyDataSetChanged();
                    loadForecastRecycler();
                    swipeContainer.setRefreshing(false);
                }
                else if(currentView == "ALARM")
                {
                    //getAllForecasts();
                    updateAlarmItems();
                    alarmAdapter.notifyDataSetChanged();
                    loadAlarmRecycler();
                    swipeContainer.setRefreshing(false);
                }
            }
        });

        //get forecast for all  aka UPDATE
        getAllForecasts();

        //build recycler view
        buildRecycler();

        //button to load forecasts in recycler
        ImageButton forecastsButton = findViewById(R.id.forecasts_button);
        forecastsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadForecastRecycler();
            }
        });

        //button to load alarms in recycler
        ImageButton alarmsButton = findViewById(R.id.alarms_button);
        alarmsButton.setLongClickable(true);
        alarmsButton.setClickable(true);
        alarmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAlarmRecycler();
            }
        });
        alarmsButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, alarmsButton);
                popup.getMenuInflater().inflate(R.menu.popup_alarm, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Alarms settings"))
                        {
                            //open alarm settings dialog
                            View view = getLayoutInflater().inflate(R.layout.alarms_settings, null);
                            dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                            dialogBuilder.setView(view);
                            dialog = dialogBuilder.create();
                            dialog.show();

                            //dialog behaviour
                            Spinner periodChoice = (Spinner) view.findViewById(R.id.notification_choice);
                            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(MainActivity.this,
                                    android.R.layout.simple_list_item_1, periodValues);
                            periodChoice.setAdapter(spinnerAdapter);

                            Button saveButton = (Button) view.findViewById(R.id.save_button);
                            saveButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    cancelJob();
                                    int choicePosition = periodChoice.getSelectedItemPosition();
                                    switch (choicePosition){
                                        case 0:
                                            //1
                                            scheduleJob(1 * 60);
                                            break;
                                        case 1:
                                            //3 hours
                                            scheduleJob(3 * 60);
                                            break;
                                        case 2:
                                            //12 hours
                                            scheduleJob(12 * 60);
                                            break;
                                        case 3:
                                            //24 hours
                                            scheduleJob(24 * 60);
                                            break;
                                        default:
                                            break;
                                    }
                                    dialog.dismiss();
                                }
                            });
                        }
                        else
                        {
                            checkAlarms();
                        }
                        return false;
                    }
                });
                popup.show();
                return false;
            }
        });

        //on click on Forecast item in recycleView
        forecastAdapter.setOnItemClickListener(new ForecastAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String forecastName = forecastItems.get(position).getForecastName();
                openForecastView(forecastName);
            }
        });

        //on click on Alarm item in recyclerView
        alarmAdapter.setOnItemClickListener(new AlarmAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v)
            {
                String alarmName = alarmItems.get(position).getAlarmName();
                Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                intent.putExtra("AlarmName", alarmName);
                startActivityForResult(intent, 2);
            }
        });

        //on click on Add button
        ImageButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentView)
                {
                    case "ALARM":
                        //call AddAlarm dialog
                        createNewAlarmDialog();
                        break;
                    case "FORECAST":
                        //call AddForecast dialog
                        createNewForecastDialog("",0,0);
                        break;
                    default:
                        break;
                }
            }
        });

        //load starting recycler
        loadForecastRecycler();

        //load spinner adapter
        spinnerValAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, spinnerValues);
        spinnerValAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //clearUselessLocations();
        saveData();

        //start from notification behaviour
        if (getIntent().hasExtra("fromNotification")) {
            openForecastView(getIntent().getStringExtra("ForecastName"));
        }

        doNothing();
    }

    //done
    private void cancelOldNotifications()
    {
        if(notificationsID.isEmpty() == false)
        {
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            for(NotificationID notification : notificationsID)
            {
                managerCompat.cancel(notification.getId());
            }
            notificationsID.clear();
        }
    }

    //done
    private void scheduleJob(int minutesPeriod)
    {
        ComponentName componentName = new ComponentName(this, AlarmsJobService.class);
        JobInfo info = new JobInfo.Builder(12, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(minutesPeriod * (60 * 1000))
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if(resultCode == JobScheduler.RESULT_SUCCESS)
            Log.println(Log.INFO,"JOB", "Job scheduled!");
    }

    //done
    private void cancelJob()
    {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(12);
    }

    private void clearUselessLocations()
    {
        Location loc = new Location();
        for(int index = 0; index < locations.size(); index++)
        {
            loc = locations.get(index);
            boolean found = false;
            for(Forecast fc : forecasts)
            {
                if(fc.getName().equals(loc.getName()) == true)
                {
                    found = true;
                    break;
                }
            }
            if(found == false)
                locations.remove(index);
            index--;
        }
    }

    //done
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode==2)
        {
            switch(resultCode)
            {
                case 2:
                    //alarm delete
                    String alarmToDelete = intent.getStringExtra("AlarmName");
                    deleteAlarm(alarmToDelete);
                    break;
                case 3:
                    //forecast delete
                    String forecastToDelete = intent.getStringExtra("ForecastName");
                    deleteForecast(forecastToDelete);
                    break;
                case 4:
                    //set coordinates from dialog
                    Bundle bundle = intent.getExtras();
                    float latitude = (float) bundle.getDouble("latitude", 0.00);
                    float longitude = (float) bundle.getDouble("longitude", 0.00);
                    String name = bundle.getString("name");
                    createNewForecastDialog(name, latitude, longitude);
                    break;
                default:
                    break;
            }
        }
        saveData();
        reloadRecycler();
    }

    //done
    private void updateForecastItems()
    {
        forecastItems.clear();
        if(forecasts.isEmpty())
            return;
        for(Forecast forecast : forecasts)
        {
            forecastItems.add(new ForecastItem(forecast));
        }
    }

    //done
    private void updateAlarmItems()
    {
        alarmItems.clear();
        if(alarms.isEmpty())
            return;
        for(Alarm alarm : alarms)
        {
            alarmItems.add(new AlarmItem(alarm));
        }
    }

    //done
    private void buildRecycler()
    {
        mainRecycler = findViewById(R.id.main_recycler);
        mainRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mainRecycler.setLayoutManager(layoutManager);
        alarmAdapter = new AlarmAdapter(alarmItems);
        forecastAdapter = new ForecastAdapter(forecastItems);
    }

    //done
    private void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json;

        //save forecasts:
        json = gson.toJson(forecasts);
        editor.putString("forecasts", json);
        editor.apply();
        //save locations:
        json = gson.toJson(locations);
        editor.putString("locations", json);
        editor.apply();
        //save alarms:
        json = gson.toJson(alarms);
        editor.putString("alarms", json);
        editor.apply();
        //save notifications IDs:
        json = gson.toJson(notificationsID);
        editor.putString("notifications", json);
        editor.apply();
    }

    //done
    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
        Gson gson = new Gson();
        String json;
        Type type;

        //load forecasts:
        json = sharedPreferences.getString("forecasts", null);
        type = new TypeToken<ArrayList<Forecast>>() {}.getType();
        forecasts = gson.fromJson(json, type);
        if(forecasts == null)
            forecasts = new ArrayList<>();
        //load locations:
        json = sharedPreferences.getString("locations", null);
        type = new TypeToken<ArrayList<Location>>() {}.getType();
        locations = gson.fromJson(json, type);
        if(locations == null)
            locations = new ArrayList<>();
        //load alarms:
        json = sharedPreferences.getString("alarms", null);
        type = new TypeToken<ArrayList<Alarm>>() {}.getType();
        alarms = gson.fromJson(json, type);
        if(alarms == null)
            alarms = new ArrayList<>();
        //load notifications
        json = sharedPreferences.getString("notifications", null);
        type = new TypeToken<ArrayList<NotificationID>>() {}.getType();
        notificationsID = gson.fromJson(json, type);
        if(notificationsID == null)
            notificationsID = new ArrayList<>();
    }

    //done
    private void getAllForecasts()
    {
        for(Forecast forecast : forecasts)
        {
            forecast.updateForecast();
        }
    }

    //done
    private void checkAlarms()
    {
        if(alarms.size() == 0 || forecasts.size() == 0)
            return;

        Location tempAlarmLocation;
        Location tempForecastLocation;
        int dayIndex;
        List<String> daysFound = new ArrayList<>();

        for(Alarm alarm : alarms)
        {
            if(alarm.getDays().isEmpty())
                continue;

            tempAlarmLocation = alarm.getLocation();

            outer:
            for(Forecast fc : forecasts)
            {
                tempForecastLocation = fc.getLocation();
                //alarm loc = forecast loc:
                if(tempAlarmLocation.getLatitude() == tempForecastLocation.getLatitude()
                    && tempAlarmLocation.getLongitude() == tempForecastLocation.getLongitude())
                {
                    dayIndex = 0;
                    daysFound.clear();

                    for(Day zi : fc.getDays())
                    {
                        if(alarm.getDays().get(dayIndex).checkThisDay(zi))
                        {
                            daysFound.add(zi.getValidDate());
                            dayIndex++;
                            //all conditions met:
                            if(dayIndex == alarm.getDays().size())
                            {
                                notifyAlarm(alarm, daysFound);
                                break outer;
                            }
                        }
                        else
                        {
                            dayIndex= 0;
                            daysFound.clear();
                        }
                    }
                }
            }
        }
    }

    //done
    private void notifyAlarm(Alarm alarm, List<String> daysFound)
    {
        //set texts
        String title = "Weather Found!";
        String firstRow = "Conditions found for " + alarm.getName() + " alarm at "
                + alarm.getLocation().getName() + " for the following days:";
        String secondRow = "";
        for(String day : daysFound)
        {
            secondRow += day + ", ";
        }
        secondRow = secondRow.substring(0, secondRow.length() - 2);

        //create builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,
                "MyNotif");
        builder.setContentTitle(title);
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setContentText(firstRow);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(firstRow + "\n" + secondRow));
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(this, MainActivity.class);
        String forecastName = getForecastByLocation(alarm.getLocation()).getName();
        intent.putExtra("ForecastName", forecastName);
        intent.putExtra("fromNotification", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //notify
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        Random rand = new Random();
        int notificationID = rand.nextInt();
        notificationsID.add(new NotificationID(notificationID));
        managerCompat.notify(notificationID, builder.build());

        Log.println(Log.INFO,"NOTIFICATION", firstRow + secondRow);
    }

    //done
    private void requestPermissions()
    {
        ActivityCompat.requestPermissions(this ,new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    //done
    private void loadForecastRecycler()
    {
        updateForecastItems();
        forecastAdapter = new ForecastAdapter(forecastItems);
        mainRecycler.setAdapter(forecastAdapter);
        findViewById(R.id.forecasts_button).setBackgroundResource(R.drawable.fc_button_clicked);
        findViewById(R.id.alarms_button).setBackgroundResource(R.drawable.al_button_idle);
        currentView = "FORECAST";
    }

    //done
    private void loadAlarmRecycler()
    {
        updateAlarmItems();
        alarmAdapter = new AlarmAdapter(alarmItems);
        mainRecycler.setAdapter(alarmAdapter);
        findViewById(R.id.forecasts_button).setBackgroundResource(R.drawable.fc_button_idle);
        findViewById(R.id.alarms_button).setBackgroundResource(R.drawable.al_button_clicked);
        currentView = "ALARM";
    }

    //done
    private void reloadRecycler()
    {
        switch (currentView){
            case "FORECAST":
                loadForecastRecycler();
                break;
            case "ALARM":
                loadAlarmRecycler();
                break;
            default:
                break;
        }
    }

    private void doNothing()
    {
        String thisIsUseless = "";
    }

    //done
    public void createNewForecastDialog(String name, float latitude, float longitude)
    {
        //set dialog and views
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final View newForecastView = getLayoutInflater().inflate(R.layout.add_forecast_dialog, null);
        EditText newForecastName = (EditText) newForecastView.findViewById(R.id.add_forecast_name);
        EditText newForecastLatitude = (EditText) newForecastView.findViewById(R.id.add_forecast_latitude);
        EditText newForecastLongitude = (EditText) newForecastView.findViewById(R.id.add_forecast_longitude);
        Button newForecastButton = (Button) newForecastView.findViewById(R.id.add_forecast_button);
        TextView newForecastError = (TextView) newForecastView.findViewById(R.id.add_forecast_error);

        //build dialog
        dialogBuilder.setView(newForecastView);
        dialog = dialogBuilder.create();
        dialog.show();

        if(name.equals("") == false)
            newForecastName.setText(name);
        if(latitude != 0)
            newForecastLatitude.setText("" + latitude);
        if(longitude != 0)
            newForecastLongitude.setText("" + longitude);

        //map button
        Button mapButton = (Button) newForecastView.findViewById(R.id.map_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open map activity to get coordinates
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                if(newForecastLatitude.getText().toString().isEmpty() == false ||
                        newForecastLongitude.getText().toString().isEmpty() == false ||
                        newForecastName.getText().toString().isEmpty() == false)
                {
                    intent.putExtra("editMode", true);
                    intent.putExtra("latitude", newForecastLatitude.getText().toString());
                    intent.putExtra("longitude", newForecastLongitude.getText().toString());
                    intent.putExtra("name", newForecastName.getText().toString());
                }
                startActivityForResult(intent, 2);
                dialog.dismiss();
            }
        });

        //click on add button
        newForecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check name validity
                String newName;
                if(newForecastName.getText().toString().isEmpty())
                {
                    newForecastError.setText("No name given!");
                    return;
                }
                else
                    newName = newForecastName.getText().toString();
                for(Location location : locations)
                {
                    if(location.getName().equals(newName))
                    {
                        newForecastError.setText("Location name already exists!");
                        return;
                    }
                }

                //check longitude validity
                float longitude = 999;
                if(newForecastLongitude.getText().toString().isEmpty())
                {
                    newForecastError.setText("No Longitude input!");
                    return;
                }
                else
                    try {
                        longitude = Float.valueOf(newForecastLongitude.getText().toString());
                    }
                    catch(Exception e)
                    {
                        newForecastError.setText("Longitude is not a number");
                    }
                if(longitude < -180 || longitude > 180)
                {
                    newForecastError.setText("Longitude out of bounds!");
                    return;
                }

                //check latitude validity
                float latitude = 999;
                if(newForecastLatitude.getText().toString().isEmpty())
                {
                    newForecastError.setText("No Latitude input!");
                    return;
                }
                else
                    try{
                        latitude = Float.valueOf(newForecastLatitude.getText().toString());
                    }
                    catch(Exception e)
                    {
                        newForecastError.setText("Latitude is not a number");
                    }
                if(latitude < -90 || latitude > 90)
                {
                    newForecastError.setText("Latitude out of bounds!");
                    return;
                }

                //if everything good
                Location newLocation = new Location(latitude, longitude, newName);
                locations.add(newLocation);
                Forecast newForecast = new Forecast(newLocation);
                newForecast.updateForecastLinear();
                forecasts.add(newForecast);
                saveData();
                reloadRecycler();
                dialog.dismiss();
            }
        });
    }

    //done
    private void createNewAlarmDialog()
    {
        //set dialog and views
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final View newAlarmView = getLayoutInflater().inflate(R.layout.add_alarm_dialog, null);
        EditText newAlarmName = (EditText) newAlarmView.findViewById(R.id.alarm_dialog_name);
        Spinner newAlarmLocation = (Spinner) newAlarmView.findViewById(R.id.alarm_dialog_locations);
        TextView newAlarmError = (TextView) newAlarmView.findViewById(R.id.alarm_dialog_error);

        //set location spinner
        String[] spinnerLocations = new String[forecasts.size()];
        int index = 0;
        for (Forecast forecast : forecasts) {
            spinnerLocations[index] = forecast.getName();
            index++;
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, spinnerLocations);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newAlarmLocation.setAdapter(spinnerAdapter);

        //recyclerview
        List<AddDayItem> daysList = new ArrayList<>();
        daysList.add(new AddDayItem(1));
        final AddDayAdapter[] daysAdapter = {new AddDayAdapter(daysList)};
        RecyclerView addRecycler;
        addRecycler = newAlarmView.findViewById(R.id.alarm_dialog_recycler);
        addRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        addRecycler.setAdapter(daysAdapter[0]);
        addRecycler.setLayoutManager(layoutManager);

        //show dialog
        dialogBuilder.setView(newAlarmView);
        dialog = dialogBuilder.create();
        dialog.show();

        //buttons
        //add new day button
        Button addDayButton = (Button) newAlarmView.findViewById(R.id.alarm_dialog_add_day);
        addDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add new day in recyclerview
                daysList.add(new AddDayItem(daysList.size() + 1));
                daysAdapter[0] = new AddDayAdapter(daysList);
                addRecycler.setAdapter(daysAdapter[0]);
            }
        });
        //delete last day button
        Button delDayButton = (Button) newAlarmView.findViewById(R.id.alarm_dialog_delete_day);
        delDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete last day in recyclerview
                if (daysList.size() > 1) {
                    daysList.remove(daysList.size() - 1);
                    daysAdapter[0] = new AddDayAdapter(daysList);
                    addRecycler.setAdapter(daysAdapter[0]);
                }
            }
        });
        //add alarm button
        Button addAlarmButton = (Button) newAlarmView.findViewById(R.id.alarm_dialog_button);
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if all good
                String newName;
                if(newAlarmName.getText().toString().isEmpty())
                {
                    newAlarmError.setText("No name given!");
                    return;
                }
                else
                    newName = newAlarmName.getText().toString();
                for(Alarm alarm : alarms)
                {
                    if(alarm.getName() == newName)
                    {
                        newAlarmError.setText("Name already exists!");
                        return;
                    }
                }

                Location newLocation = getLocation(newAlarmLocation.getSelectedItem().toString());
                if(newLocation == null)
                {
                    newAlarmError.setText("Location not found");
                    return;
                }
                //if all good then add to alarms
                Alarm newAlarm = new Alarm();
                newAlarm.setName(newName);
                newAlarm.setLocation(newLocation);
                for (AddDayItem day : daysList) {
                    DayConditions newDay = new DayConditions(day);
                    newAlarm.addDay(newDay);
                }
                alarms.add(newAlarm);
                saveData();
                reloadRecycler();
                dialog.dismiss();
            }
        });
    }

    //done
    public void deleteForecast(String forecastName)
    {
        for(Forecast forecast : forecasts)
        {
            if(forecast.getName().equals(forecastName))
            {
                locations.remove(forecast.getLocation());
                forecasts.remove(forecast);
                saveData();
                reloadRecycler();
                return;
            }
        }
    }

    //done
    public static Forecast getForecast(String forecastName)
    {
        for(Forecast forecast : forecasts)
        {
            if(forecast.getName().equals(forecastName))
                return forecast;
        }
        return null;
    }

    //done
    public void deleteAlarm(String alarmName)
    {
        for(Alarm alarm : alarms)
        {
            if(alarm.getName().equals(alarmName))
            {
                alarms.remove(alarm);
                saveData();
                reloadRecycler();
                return;
            }
        }
    }

    //done
    public static Alarm getAlarm(String alarmName)
    {
        for(Alarm alarm : alarms)
        {
            if(alarm.getName().equals(alarmName))
                return alarm;
        }
        return null;
    }

    //done
    public void deleteLocation(String locationName)
    {
        for(Location location : locations)
        {
            if(location.getName().equals(locationName))
            {
                locations.remove(location);
                saveData();
                reloadRecycler();
                return;
            }
        }
    }

    //done
    public static Location getLocation(String locationName)
    {
        for(Location location : locations)
        {
            if(location.getName().equals(locationName))
                return location;
        }
        return null;
    }

    //done
    public static List<Forecast> getForecasts() {
        return forecasts;
    }

    //done
    public static List<Location> getLocations() {
        return locations;
    }

    //done
    public static List<Alarm> getAlarms() {
        return alarms;
    }

    //done
    public static void UpdateLocation(Location oldLocation, Location newLocation)
    {
        int index = 0;
        for(Location location : locations)
        {
            if(location.getName().equals(oldLocation.getName()) == true)
            {
                locations.remove(location);
                locations.add(index, newLocation);
                return;
            }
            index++;
        }
    }

    //done
    public static void UpdateForecast(Forecast oldForecast, Forecast newForecast)
    {
        int index = 0;
        for(Forecast forecast : forecasts)
        {
            if(forecast.getName().equals(oldForecast.getName()) == true)
            {
                forecasts.remove(forecast);
                forecasts.add(index, newForecast);
                return;
            }
            index++;
        }
    }

    //done
    public static void UpdateAlarm(Alarm oldAlarm, Alarm newAlarm)
    {
        int index = 0;
        for(Alarm alarm : alarms)
        {
            if(alarm.getName().equals(oldAlarm.getName()) == true)
            {
                alarms.remove(alarm);
                alarms.add(index, newAlarm);
                return;
            }
            index++;
        }
    }

    //done
    private Forecast getForecastByLocation(Location location)
    {
        for(Forecast forecast : forecasts)
        {
            if(forecast.getLocation().getName().equals(location.getName()))
                return forecast;
        }
        return null;
    }

    private void openForecastView(String forecastName)
    {
        Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
        intent.putExtra("ForecastName", forecastName);
        startActivityForResult(intent, 2);
    }

}