package com.example.y.notetogether.Activity.Service;

/**
 * Created by kimwoohyeun on 2016-06-06.
 */
public class MyRoom {
    public int id;
    public String name;


    public MyRoom(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

