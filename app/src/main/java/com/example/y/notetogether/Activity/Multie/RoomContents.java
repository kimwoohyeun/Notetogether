package com.example.y.notetogether.Activity.Multie;

/**
 * Created by kimwoohyeun on 2016-05-27.
 */
public class RoomContents {
    private int id;
    private String title;
    private String time;
    private int count;
    public RoomContents(String title){
        this.title = title;
    }
    public RoomContents( String title, int count) {
        this.count = count;
        this.title = title;
    }
    public RoomContents( int id, String title) {
        this.id = id;
        this.title = title;
    }
    public int getCount() {return count;}
    public int getId(){return id;}
    public String getTitle(){
        return title;
    }
    public String getTime()
    {
        return time;
    }
    public void setCount(int count) {this.count =count;};
}
