package com.example.admin.customdb;

/**
 * Created by admin on 2017-12-26.
 */

public class Info {
    private int _id;
    private String title;
    private String subInfo; //time + genre
    private String period;
    private String link;
    private String day;

    public int get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubInfo() {
        return subInfo;
    }

    public String getPeriod() {
        return period;
    }

    public String getLink() {
        return link;
    }

    public String getDay() {
        return day;
    }

    public Info(){}

    public Info(int _id, String title, String subInfo, String period, String link, String day) {
        this._id = _id;
        this.title = title;
        this.subInfo = subInfo;
        this.period = period;
        this.link = link;
        this.day = day;
    }
}
