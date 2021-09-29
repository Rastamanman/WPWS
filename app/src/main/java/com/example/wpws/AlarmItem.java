package com.example.wpws;

public class AlarmItem {

    private String alName;
    private String alLocation;

    public AlarmItem(String alName, String alLocation)
    {
        this.alName = alName;
        this.alLocation = "@" + alLocation;
    }

    public AlarmItem(Alarm al)
    {
        alName = al.getName();
        alLocation = "@" + al.getLocation().getName() + "(" + al.getLocation().getCity_name() +
                ", " + al.getLocation().getCountry_name() + ")";
    }

    public String getAlName()
    {
        return alName;
    }

    public String getAlLocation()
    {
        return alLocation;
    }
}
