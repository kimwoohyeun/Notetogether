package com.example.y.notetogether.Activity;

/**
 * Created by kimwoohyeun on 2016-05-18.
 */
public class User {
    public static final int USERTYPE_BUDDY = 1;

    public String id;
    public String password;


    public User(String id, String password) {
        this.password = password;
        this.id = id;
    }
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + id + '\'' +
                ", password='" + password  +

                '}';
    }
}