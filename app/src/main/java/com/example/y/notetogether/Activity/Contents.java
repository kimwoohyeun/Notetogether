package com.example.y.notetogether.Activity;

/**
 * Created by y on 2016-03-22.
 */
public class Contents {
    private int id;
    private String title;
    private String time;
    private String content;
    private int color;
    public Contents( String title,  String time, String content, int color) {
        this.color = color;
        this.title = title;
        this.content = content;
        this.time = time;
    }
    public Contents( int id, String title,  String time, String content, int color) {
        this.id = id;
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
