package com.example.wpws;

public class AlarmItem {

    private String alarmName;
    private String alarmLocation;

    public AlarmItem(String alarmName, String alLocation)
    {
        this.alarmName = alarmName;
        this.alarmLocation = "@" + alLocation;
    }

    public AlarmItem(Alarm alarm)
    {
        alarmName = alarm.getName();
        alarmLocation = "@" + alarm.getLocation().getName() + "(" + alarm.getLocation().getCityName() +
                ", " + alarm.getLocation().getCountryName() + ")";
    }

    public String getAlarmName()
    {
        return alarmName;
    }

    public String getAlarmLocation()
    {
        return alarmLocation;
    }
}
