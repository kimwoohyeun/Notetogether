package com.example.y.notetogether.Activity.Service;

/**
 * Created by kimwoohyeun on 2016-05-18.
 */
public class User {
    public static final int USERTYPE_BUDDY = 1;

    public String email;
    public String password;


    public User(String email, String password) {
        this.password = password;
        this.email = email;
    }
    @Override
    public String toString() {
        return "User{" +
                "firstName='" + email + '\'' +
                ", password='" + password  +

                '}';
    }
}