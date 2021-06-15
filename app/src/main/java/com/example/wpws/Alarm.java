package com.example.wpws;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Alarm {

    private Location location;
    private String name;
    private List<DayConditions> dayConds;

    public Alarm()
    {
        location = new Location();
        name = "";
        dayConds = new ArrayList<>();
    }

    public Alarm(Location loc, String name)
    {
        location = loc;
        this.name = name;
        dayConds = new ArrayList<>();
    }

    public void setLocation(Location locToSet) { location = locToSet; }

    public Location getLocation() { return location; }

    public void setName(String nameToSet) { name = nameToSet; }

    public String getName() { return name; }

    public List<DayConditions> getDays() { return dayConds; }

    public void setConditions(List<DayConditions> condToSet) { dayConds = condToSet; }

    public void addDay(DayConditions dayToAdd) { dayConds.add(dayToAdd); }

    public void addCondition(int dayIndex, Condition condToAdd) { dayConds.get(dayIndex).addCondition(condToAdd); }

    //public Condition getCondition(int index) { return dayConds.get(index); }

    public void notifyAlarm(List<String> daysFound)
    {
        String textToNotify = "Conditions found for " + name + " alarm at " + location.getName() +
                " for the following days: ";
        for(String day : daysFound)
        {
            textToNotify += day + ", ";
        }
        Log.println(Log.INFO,"NOTIFICATION", textToNotify);
    }

}
