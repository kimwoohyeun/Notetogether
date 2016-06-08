package com.example.y.notetogether.Activity.DB;

/**
 * Created by y on 2016-03-22.
 */
public class Contents {
    private int id;
    private String title;
    private String time;
    private String content;
    private int color;
    private String sessionkey;
    public Contents( String title,  String time, String content, int color) {
        this.color = color;
        this.title = title;
        this.content = content;
        this.time = time;
    }
    public Contents( int id, String content) {
        this.id = id;
        this.content = content;

    }
    public Contents( int id, String title,  String time, String content, int color) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.time = time;
        this.color = color;
    }
    public Contents( String sessionkey, String title,  String time, String content, int color) {
        this.sessionkey = sessionkey;
        this.title = title;
        this.content = content;
        this.time = time;
        this.color = color;
    }
    public int getColor() {return color;}
    public int getId(){return id;}
    public String getTitle(){
        return title;
    }
    public String getTime()
    {
        return time;
    }
    public String getContent(){return content;}
}
