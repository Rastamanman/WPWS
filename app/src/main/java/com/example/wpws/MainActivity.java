package com.example.wpws;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
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

    private static final String OPEN_WEATHER_MAP_URL ="https://api.weatherbit.io/v2.0/forecast/daily?lat=%s&lon=%s&key=%s";
    private static final String OPEN_WEATHER_MAP_API ="6c5da0a9e9694b94aa82f50bfd5e39d5";

    private List<Forecast> forecasts = new ArrayList<>();
    private List<Location> locatii = new ArrayList<>();
    private List<Alarm> alarme = new ArrayList<>();

    private RecyclerView fcRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ForecastItem> fcItems = new ArrayList<>();
    private ForecastAdapter fcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy((policy));
        requestPermissions();

        //load app data
        loadData();

        /*Location craiova = new Location((float)44.32, (float)23.81, "Craiova", "Romania","Locatia mea");
        Forecast fcCraiova = new Forecast(craiova);
        Alarm aCraiova = new Alarm(craiova, "Alarma mea");
        DayConditions cautZiua = new DayConditions();
        cautZiua.addCondition(new Condition(CondType.RH, CondMode.EQUAL, 73));
        cautZiua.addCondition(new Condition(CondType.CLOUDS, CondMode.EQUAL, 43));
        aCraiova.addDay(cautZiua);

        locatii.add(craiova);
        forecasts.add(fcCraiova);
        alarme.add(aCraiova);*/

        //get forecast for all  aka UPDATE
        getAllForecasts();

        //create forecasts items
        makeFcItems();

        //build forecasts recycler view
        buildRecycler();

        //load views
        loadForecastView();

        //check alarms for every location
        checkAlarms();

        saveData();

        doNothing();
    }

    //done
    private void makeFcItems()
    {
        fcItems.clear();
        for(Forecast fc : forecasts)
        {
            fcItems.add(new ForecastItem(fc));
        }
    }

    //done
    private void buildRecycler()
    {
        fcRecycler = findViewById(R.id.forecasts_recycler);
        fcRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        fcAdapter = new ForecastAdapter(fcItems);

        fcRecycler.setLayoutManager(layoutManager);
        fcRecycler.setAdapter(fcAdapter);
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
            JSONObject jsonWeather = null;
            try{
                jsonWeather = getWeatherJSON("" + fc.getLatitude(), "" + fc.getLongitude());
            } catch (Exception e)
            {
                Log.d("Error", "Cannot process JSON results", e);
            }
            try{
                if(jsonWeather != null)
                {
                    fc.clearForecast();
                    for(int i = 0; i < jsonWeather.getJSONArray("data").length(); i++)
                    {
                        fc.addDay(jsonWeather.getJSONArray("data").getJSONObject(i));
                    }
                    fc.setCityName(jsonWeather.getString("city_name"));
                    fc.setCountryName(jsonWeather.getString("country_code"));
                    fc.setLatitude(Float.parseFloat(jsonWeather.getString("lat")));
                    fc.setLongitude(Float.parseFloat(jsonWeather.getString("lon")));
                }
            } catch (Exception e){
                Log.d("Error", "Something went wrong", e);
            }
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
                                alarm.notifyAlarm(daysFound);
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
    private static JSONObject getWeatherJSON(String lat, String lon)
    {
        try{
            URL url = new URL(String.format(OPEN_WEATHER_MAP_URL, lat, lon, OPEN_WEATHER_MAP_API));
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

    private void loadForecastView()
    {

    }

    private void doNothing()
    {
        String thisIsUseless = "";
    }
}