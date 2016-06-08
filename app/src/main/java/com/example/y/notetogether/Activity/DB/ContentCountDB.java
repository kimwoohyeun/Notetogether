package com.example.y.notetogether.Activity.DB;

/**
 * Created by kimwoohyeun on 2016-06-05.
 */
public class ContentCountDB {
    private int groupid;
    private int count;
    public ContentCountDB(int groupid, int count){

        this.groupid = groupid;
        this.count = count;
    }
    public int getGroupid() {return groupid;}
    public int getCount() {return count;}

    @Override
    public String toString() {
        return "ContentCountDB{" +
                "groupid=" + groupid +
                ", count=" + count +
                '}';
    }
}
