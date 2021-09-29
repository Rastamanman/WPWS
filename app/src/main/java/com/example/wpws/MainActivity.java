package com.example.wpws;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Date;
import java.util.List;


import static android.Manifest.permission.ACCESS_FINE_LOCATION;
public class MainActivity extends AppCompatActivity {

    //request data
    private static final String FORECAST_URL ="https://api.weatherbit.io/v2.0/forecast/daily?lat=%s&lon=%s&key=%s";
    private static final String CURRENT_URL ="https://api.weatherbit.io/v2.0/current?lat=%s&lon=%s&key=%s";
    private static final String API_KEY ="6c5da0a9e9694b94aa82f50bfd5e39d5";

    //data objects
    private static List<Forecast> forecasts = new ArrayList<>();
    private static List<Location> locatii = new ArrayList<>();
    private static List<Alarm> alarme = new ArrayList<>();

    //recycler view objects
    private RecyclerView mainRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<ForecastItem> fcItems = new ArrayList<>();
    private static ArrayList<AlarmItem> alItems = new ArrayList<>();
    private ForecastAdapter fcAdapter;
    private AlarmAdapter alAdapter;
    private SwipeRefreshLayout swipeContainer;

    //dialog objects
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private static ArrayAdapter<String> spinnerValAdapter;
    public static ArrayAdapter<String> getSpinnerValAdapter() {
        return spinnerValAdapter;
    }
    public static String[] spinnerValues = {"d.c.", "<", "<=", "=", "=>", ">"};

    //add button variables
    private String whatToAdd = ""; //FORECAST or ALARM
    private EditText newFc_name, newFc_long, newFc_lat;
    private EditText newAlName;
    private Button newFc_add, newAl_add;
    private Spinner add_al_loc_list;
    private TextView newFc_error, newAl_error;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy((policy));

        //create Notification Channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("MyNotif","AlarmNotif", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //set swipeContainer(update on scroll up)
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(whatToAdd == "FORECAST")
                {
                    getAllForecasts();
                    updateFcItems();
                    fcAdapter.notifyDataSetChanged();
                    loadForecastRecycler();
                    swipeContainer.setRefreshing(false);
                }
                else if(whatToAdd == "ALARM")
                {
                    //getAllForecasts();
                    updateAlItems();
                    alAdapter.notifyDataSetChanged();
                    loadAlarmRecycler();
                    swipeContainer.setRefreshing(false);
                }
            }
        });

        //load app data
        loadData();

        //get forecast for all  aka UPDATE
        getAllForecasts();

        //build recycler view
        buildRecycler();

        //check alarms for every location
        checkAlarms();

        //button to load forecasts in recycler
        ImageButton fcButton = findViewById(R.id.forecasts_button);
        fcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadForecastRecycler();
            }
        });

        //button to load alarms in recycler
        ImageButton alButton = findViewById(R.id.alarms_button);
        alButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAlarmRecycler();
            }
        });

        //on click on Forecast item in recycleView
        fcAdapter.setOnItemClickListener(new ForecastAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                String fcName = fcItems.get(position).getFcName();
                Intent intent = new Intent(MainActivity.this, ForecastActivity.class);
                intent.putExtra("ForecastName", fcName);
                startActivityForResult(intent, 2);
            }
        });

        //on click on Alarm item in recyclerView
        alAdapter.setOnItemClickListener(new AlarmAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v)
            {
                String alName = alItems.get(position).getAlName();
                Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
                intent.putExtra("AlarmName", alName);
                startActivityForResult(intent, 2);
            }
        });

        //on click on Add button
        ImageButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (whatToAdd)
                {
                    case "ALARM":
                        //call AddAlarm dialog
                        createNewAlarmDialog();
                        break;
                    case "FORECAST":
                        //call AddForecast dialog
                        createNewForecastDialog();
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

        saveData();

        doNothing();
    }

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
                    String alarmToDel = intent.getStringExtra("AlarmName");
                    deleteAlarm(alarmToDel);
                    break;
                case 3:
                    //forecast delete
                    String forecastToDel = intent.getStringExtra("ForecastName");
                    deleteForecast(forecastToDel);
                    break;
                default:
                    break;
            }
        }
    }

    //done
    private void updateFcItems()
    {
        fcItems.clear();
        if(forecasts.isEmpty())
            return;
        for(Forecast fc : forecasts)
        {
            fcItems.add(new ForecastItem(fc));
        }
    }

    //done
    private void updateAlItems()
    {
        alItems.clear();
        if(alarme.isEmpty())
            return;
        for(Alarm al : alarme)
        {
            alItems.add(new AlarmItem(al));
        }
    }

    //done
    private void buildRecycler()
    {
        mainRecycler = findViewById(R.id.main_recycler);
        mainRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mainRecycler.setLayoutManager(layoutManager);
        alAdapter = new AlarmAdapter(alItems);
        fcAdapter = new ForecastAdapter(fcItems);
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
        json = gson.toJson(locatii);
        editor.putString("locatii", json);
        editor.apply();
        //save alarms:
        json = gson.toJson(alarme);
        editor.putString("alarme", json);
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
        {
            forecasts = new ArrayList<>();
        }
        //load locations:
        json = sharedPreferences.getString("locatii", null);
        type = new TypeToken<ArrayList<Location>>() {}.getType();
        locatii = gson.fromJson(json, type);
        if(locatii == null)
        {
            locatii = new ArrayList<>();
        }
        //load alarms:
        json = sharedPreferences.getString("alarme", null);
        type = new TypeToken<ArrayList<Alarm>>() {}.getType();
        alarme = gson.fromJson(json, type);
        if(alarme == null)
        {
            alarme = new ArrayList<>();
        }
    }

    //done
    private void getAllForecasts()
    {
        for(Forecast fc : forecasts)
        {
            fc.updateForecast();
        }
    }

    //done
    private void checkAlarms()
    {
        if(alarme.size() == 0 || forecasts.size() == 0)
            return;

        Location tempAlarmLoc;
        Location tempForecastLoc;
        int dayIndex;
        List<String> daysFound = new ArrayList<>();

        for(Alarm alarm : alarme)
        {
            if(alarm.getDays().isEmpty())
                continue;

            tempAlarmLoc = alarm.getLocation();

            outer:
            for(Forecast fc : forecasts)
            {
                tempForecastLoc = fc.getLocation();
                //alarm loc = forecast loc:
                if(tempAlarmLoc.getLatitude() == tempForecastLoc.getLatitude()
                    && tempAlarmLoc.getLongitude() == tempForecastLoc.getLongitude())
                {
                    dayIndex = 0;
                    daysFound.clear();

                    for(Day zi : fc.getDays())
                    {
                        if(alarm.getDays().get(dayIndex).checkThisDay(zi))
                        {
                            daysFound.add(zi.getValid_date());
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

    private void notifyAlarm(Alarm al, List<String> daysFound)
    {
        //set texts
        String title = "Weather Found!";
        String firstRow = "Conditions found for " + al.getName() + " alarm at " + al.getLocation().getName() +
                " for the following days:";
        String secondRow = "";
        for(String day : daysFound)
        {
            secondRow += day + ", ";
        }
        secondRow = secondRow.substring(0, secondRow.length() - 2);

        //create builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyNot");
        builder.setContentTitle(title);
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setContentText(firstRow);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(secondRow));
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //notify
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(1,builder.build());

        //Log.println(Log.INFO,"NOTIFICATION", textToNotify);
    }

    //done
    private static String setWeatherIcon(int actualId, long sunrise, long sunset)
    {
        String icon = "";
        if(actualId == 800)
        {
            long currentTime = new Date().getTime();
            if(currentTime >= sunrise && currentTime <= sunset)
            {
                icon = "&#xf00d;";
            }
            else
                icon = "&#xf02e;";
        }
        else
        {
            switch(actualId){
                case 2:
                    icon = "&#xf01e;";
                    break;
                case 3:
                    icon= "&#xf01c;";
                    break;
                case 7:
                    icon = "&#xf014;";
                    break;
                case 8:
                    icon = "&#xf013;";
                    break;
                case 6:
                    icon = "&#xf01b;";
                    break;
                case 5:
                    icon = "&#xf019;";
                    break;
            }
        }
        return icon;
    }

    //done
    public static JSONObject getWeatherJSON(String lat, String lon, String mode)
    {
        try{
            URL url;
            if(mode == "FORECAST")
                url = new URL(String.format(FORECAST_URL, lat, lon, API_KEY));
            else
                url = new URL(String.format(CURRENT_URL, lat, lon, API_KEY));
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while((tmp = reader.readLine()) != null)
            {
                json.append(tmp).append("\n");
            }
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            /*if(data.getInt("cod") != 200)
                return null;*/
            return data;
        } catch (Exception e){
            Log.d("Error", "Something went wrong", e);
            return null;
        }
    }

    //done
    private void requestPermissions()
    {
        ActivityCompat.requestPermissions(this ,new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    //done
    private void loadForecastRecycler()
    {
        updateFcItems();
        fcAdapter = new ForecastAdapter(fcItems);
        mainRecycler.setAdapter(fcAdapter);
        findViewById(R.id.forecasts_button).setBackgroundResource(R.drawable.fc_button_clicked);
        findViewById(R.id.alarms_button).setBackgroundResource(R.drawable.al_button_idle);
        whatToAdd = "FORECAST";
    }

    //done
    private void loadAlarmRecycler()
    {
        updateAlItems();
        alAdapter = new AlarmAdapter(alItems);
        mainRecycler.setAdapter(alAdapter);
        findViewById(R.id.forecasts_button).setBackgroundResource(R.drawable.fc_button_idle);
        findViewById(R.id.alarms_button).setBackgroundResource(R.drawable.al_button_clicked);
        whatToAdd = "ALARM";
    }

    //done
    private void reloadRecycler()
    {
        switch (whatToAdd){
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
    public void createNewForecastDialog()
    {
        //set dialog and views
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final View newForecastView = getLayoutInflater().inflate(R.layout.add_forecast_dialog, null);
        newFc_name = (EditText) newForecastView.findViewById(R.id.add_fc_name);
        newFc_lat = (EditText) newForecastView.findViewById(R.id.add_fc_lat);
        newFc_long = (EditText) newForecastView.findViewById(R.id.add_fc_long);
        newFc_add = (Button) newForecastView.findViewById(R.id.add_fc_button);
        newFc_error = (TextView) newForecastView.findViewById(R.id.add_fc_error);

        //build dialog
        dialogBuilder.setView(newForecastView);
        dialog = dialogBuilder.create();
        dialog.show();

        //click on add button
        newFc_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check name validity
                String name_value;
                if(newFc_name.getText().toString().isEmpty())
                {
                    newFc_error.setText("No name given!");
                    return;
                }
                else
                    name_value = newFc_name.getText().toString();
                for(Location loc : locatii)
                {
                    if(loc.getName().equals(name_value))
                    {
                        newFc_error.setText("Location name already exists!");
                        return;
                    }
                }

                //check longitude validity
                float long_value = 999;
                if(newFc_long.getText().toString().isEmpty())
                {
                    newFc_error.setText("No Longitude input!");
                    return;
                }
                else
                    try {
                        long_value = Float.valueOf(newFc_long.getText().toString());
                    }
                    catch(Exception e)
                    {
                        newFc_error.setText("Longitude is not a number");
                    }
                if(long_value < -180 || long_value > 180)
                {
                    newFc_error.setText("Longitude out of bounds!");
                    return;
                }

                //check latitude validity
                float lat_value = 999;
                if(newFc_lat.getText().toString().isEmpty())
                {
                    newFc_error.setText("No Latitude input!");
                    return;
                }
                else
                    try{
                        lat_value = Float.valueOf(newFc_lat.getText().toString());
                    }
                    catch(Exception e)
                    {
                        newFc_error.setText("Latitude is not a number");
                    }
                if(lat_value < -90 || lat_value > 90)
                {
                    newFc_error.setText("Latitude out of bounds!");
                    return;
                }

                //if everything good
                Location newLoc = new Location(lat_value, long_value, "", "", name_value);
                locatii.add(newLoc);
                Forecast fcToAdd = new Forecast(newLoc);
                fcToAdd.updateForecastLinear();
                forecasts.add(fcToAdd);
                saveData();
                reloadRecycler();
                dialog.dismiss();
            }
        });
    }

    private void createNewAlarmDialog()
    {
        //set dialog and views
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final View newAlarmView = getLayoutInflater().inflate(R.layout.add_alarm_dialog, null);
        newAlName = (EditText) newAlarmView.findViewById(R.id.add_al_name);
        add_al_loc_list = (Spinner) newAlarmView.findViewById(R.id.add_al_loc_list);
        TextView newAl_error = (TextView) newAlarmView.findViewById(R.id.new_al_error);

        //set and build things(spinner, recycler, buttons)
        {
            //set spinner
            String[] spinner_locations = new String[forecasts.size()];
            int index = 0;
            for (Forecast fc : forecasts) {
                spinner_locations[index] = fc.getName();
                index++;
            }
            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, spinner_locations);
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            add_al_loc_list.setAdapter(myAdapter);

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

            //buttons
            //add new day button
            Button addDayB = (Button) newAlarmView.findViewById(R.id.al_dialog_add_day);
            addDayB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add new day in recyclerview
                    daysList.add(new AddDayItem(daysList.size() + 1));
                    daysAdapter[0] = new AddDayAdapter(daysList);
                    addRecycler.setAdapter(daysAdapter[0]);
                }
            });
            //delete last day button
            Button delDayB = (Button) newAlarmView.findViewById(R.id.al_dialog_del_day);
            delDayB.setOnClickListener(new View.OnClickListener() {
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
            Button add_al_button = (Button) newAlarmView.findViewById(R.id.add_al_button);
            add_al_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //check if all good
                    String name_value;
                    if(newAlName.getText().toString().isEmpty())
                    {
                        newAl_error.setText("No name given!");
                        return;
                    }
                    else
                        name_value = newAlName.getText().toString();
                    Location locToAdd = getLocationByName(add_al_loc_list.getSelectedItem().toString());
                    if(locToAdd == null)
                    {
                        newAl_error.setText("Location not found");
                        return;
                    }
                    //if all good then add to alarms
                    Alarm alToAdd = new Alarm();
                    alToAdd.setName(name_value);
                    alToAdd.setLocation(locToAdd);
                    for (AddDayItem d : daysList) {
                        DayConditions day = new DayConditions(d);
                        alToAdd.addDay(day);
                    }
                    alarme.add(alToAdd);
                    saveData();
                    reloadRecycler();
                    dialog.dismiss();
                }
            });
        }

        dialogBuilder.setView(newAlarmView);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    //done
    public void deleteForecast(String fcName)
    {
        for(Forecast fc : forecasts)
        {
            if(fc.getName().equals(fcName))
            {
                locatii.remove(fc.getLocation());
                forecasts.remove(fc);
                saveData();
                reloadRecycler();
                return;
            }
        }
    }

    //done
    public static Forecast getForecastByName(String fcName)
    {
        for(Forecast fc : forecasts)
        {
            if(fc.getName().equals(fcName))
                return fc;
        }
        return null;
    }

    //done
    public void deleteAlarm(String alName)
    {
        for(Alarm al : alarme)
        {
            if(al.getName().equals(alName))
            {
                alarme.remove(al);
                saveData();
                reloadRecycler();
                return;
            }
        }
    }

    //done
    public static Alarm getAlarmByName(String name)
    {
        for(Alarm al : alarme)
        {
            if(al.getName().equals(name))
                return al;
        }
        return null;
    }

    //done
    public void deleteLocation(String locName)
    {
        for(Location loc : locatii)
        {
            if(loc.getName().equals(locName))
            {
                locatii.remove(loc);
                saveData();
                reloadRecycler();
                return;
            }
        }
    }

    //done
    public static Location getLocationByName(String name)
    {
        for(Location loc : locatii)
        {
            if(loc.getName().equals(name))
                return loc;
        }
        return null;
    }

}