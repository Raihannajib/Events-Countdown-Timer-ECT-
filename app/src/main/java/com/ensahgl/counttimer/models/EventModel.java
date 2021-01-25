package com.ensahgl.counttimer.models;

import java.util.Date;

public class EventModel {

    private String Name;
    private String date;

    public EventModel() {
    }

    public EventModel(String name, String date) {
        Name = name;
        this.date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return  Name + '\'' +
                ", date=" + date ;
    }

}
