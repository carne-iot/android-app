package ar.edu.itba.iot.iot_android.controller;

import android.content.SharedPreferences;

import ar.edu.itba.iot.iot_android.model.Device;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.service.UserService;
import ar.edu.itba.iot.iot_android.service.callbacks.GetDeviceCallback;
import ar.edu.itba.iot.iot_android.service.callbacks.GetUserByUserNameCallback;
import ar.edu.itba.iot.iot_android.service.callbacks.LoginCallback;

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
        userService.logIn(user.getUserName(), user.getPassword(), new LoginCallback(this));
    }

    public void updateDevices(){
        for(Device d : user.getDevices()){
            userService.getDevice(user.getId(), d.getId(), user.getToken(), new GetDeviceCallback(d));
        };
    }

    public void getFullUserData(){
        userService.getUserByUserName(user.getUserName(),user.getToken(), new GetUserByUserNameCallback(this));
    }
}
