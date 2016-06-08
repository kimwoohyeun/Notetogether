package com.example.y.notetogether.Activity.DB;

/**
 * Created by kimwoohyeun on 2016-06-08.
 */
public class ContentMyroomDB {
    private String name;
    private int userid;
    public ContentMyroomDB(int userid,String name){
        this.name = name;
        this.userid = userid;
    }
    public String getName() {
        return name;
    }
    public int getUserid(){return userid;}

    @Override
    public String toString() {
        return "ContentMyroomDB{" +
                "name='" + name + '\'' +
                ", userid=" + userid +
                '}';
    }
}
