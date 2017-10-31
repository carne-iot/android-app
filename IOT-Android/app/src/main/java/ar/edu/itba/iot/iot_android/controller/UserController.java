package ar.edu.itba.iot.iot_android.controller;

import android.content.SharedPreferences;

import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.service.UserService;
import ar.edu.itba.iot.iot_android.service.callbacks.LogInCallback;
import okhttp3.Callback;

/**
 * Created by julianrodrigueznicastro on 10/31/17.
 */

public class UserController {

    private UserService userService = new UserService();
    private final User user;
    private final SharedPreferences sp;

    public UserController(User user, SharedPreferences sp) {
        this.user = user;
        this.sp = sp;
    }

    public User getUser() {
        return user;
    }

    public void login(){
        userService.logIn(user.getUserName(), user.getPassword(), new LogInCallback(this));
    }

    public void setNewToken(String token){
        //TODO PERSIST TOKEN
        user.setToken(token);
    }
}
