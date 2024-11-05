package com.itp.pos.model;

import java.util.Date;

public class Activity {
    private String id;
    private String activity;
    private Date date;
    private String user;

    public Activity() {
    }

    public Activity(String id, String activity, Date date, String user) {
        this.id = id;
        this.activity = activity;
        this.date = date;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
