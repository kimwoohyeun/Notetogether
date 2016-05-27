package com.example.y.notetogether.Activity.Login;

/**
 * Created by kimwoohyeun on 2016-05-24.
 */
public class LoginResponse {
    String status;
    String sessionKey;

    public LoginResponse(String status, String sessionKey) {
        this.status = status;
        this.sessionKey = sessionKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}