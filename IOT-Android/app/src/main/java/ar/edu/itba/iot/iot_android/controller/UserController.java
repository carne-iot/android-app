package ar.edu.itba.iot.iot_android.controller;

import android.content.SharedPreferences;

import ar.edu.itba.iot.iot_android.model.User;

/**
 * Created by julianrodrigueznicastro on 10/31/17.
 */

public class UserController {

    private final User user;
    private final SharedPreferences sp;

    public UserController(User user, SharedPreferences sp) {
        this.user = user;
        this.sp = sp;
    }

    public UserController(SharedPreferences sp) {
        this.sp = sp;
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setNewToken(String token){
        //TODO PERSIST TOKEN
        user.setToken(token);
    }
}
