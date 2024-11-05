package com.itp.pos.view.tm;

import java.util.Date;

public class ActivityTm {
    private String name;
    private Date date;
    private String message;

    public ActivityTm() {
    }

    public ActivityTm(String name, Date date, String message) {
        this.name = name;
        this.date = date;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
