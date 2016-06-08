package com.example.y.notetogether.Activity.DB;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kimwoohyeun on 2016-05-29.
 */
public class ContentDB {
    private String name;
    public ContentDB(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "ContentDB{" +
                "name='" + name + '\'' +
                '}';
    }
}
