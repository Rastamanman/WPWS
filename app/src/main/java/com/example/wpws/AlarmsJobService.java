package com.example.wpws;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlarmsJobService extends JobService {

    private static final String TAG = "AlarmsJobService";
    private boolean jobCanceled = false;
    private List<Alarm> alarms = new ArrayList<>();
    private List<Forecast> forecasts = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();
    private List<NotificationID> notificationsID = new ArrayList<>();

    @Override
    public boolean onStartJob(JobParameters params) {
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.println(Log.INFO, "JOB", "Job started");
                //load data:
                loadData();

                //update forecasts:
                for (Forecast forecast : forecasts) {
                    forecast.updateForecast();
                }

                //cancel old notifications
                cancelOldNotifications();

                //check and notify alarms:
                checkAlarms();
                Log.println(Log.INFO, "JOB", "Alarms checked");
                //save data:
                if(jobCanceled == true)
                    return;
                saveData();
                Log.println(Log.INFO, "JOB", "Job finished");
                jobFinished(params, false);
            }}).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobCanceled = true;
        Log.println(Log.INFO, "JOB", "Job canceled");
        return true;
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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("MyNotif","AlarmNotif",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

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
    private Forecast getForecastByLocation(Location location)
    {
        for(Forecast forecast : forecasts)
        {
            if(forecast.getLocation().getName().equals(location.getName()))
                return forecast;
        }
        return null;
    }
}

