package ar.edu.itba.iot.iot_android.controller;

import android.content.SharedPreferences;

import java.util.Observer;

import ar.edu.itba.iot.iot_android.model.Device;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.service.UserService;
import ar.edu.itba.iot.iot_android.service.callbacks.GetDeviceCallback;
import ar.edu.itba.iot.iot_android.service.callbacks.GetDevicesCallback;
import ar.edu.itba.iot.iot_android.service.callbacks.GetUserByUserNameCallback;
import ar.edu.itba.iot.iot_android.service.callbacks.LogInCallback;


public class UserController {

    private UserService userService = new UserService();
    private final User user;
    private final SharedPreferences sp;
    private final Observer deviceChange;

    public UserController(User user, SharedPreferences sp, Observer deviceChange) {
        this.user = user;
        this.sp = sp;
        this.deviceChange = deviceChange;
    }

    public User getUser() {
        return user;
    }

    public void login(){
        userService.logIn(user.getUserName(), user.getPassword(), new LogInCallback(this));
    }

    public void updateDevices(){
        for(Device d : user.getDevices()){
            userService.getDevice(user.getId(), d.getId(), user.getToken(), new GetDeviceCallback(d));
        }
    }

    public void getDevices(){
        //while((user.getToken() == null));
        userService.getDevices(user.getId(), user.getToken(), new GetDevicesCallback(this.user, deviceChange));
    }

    public void getFullUserData(){
        userService.getUserByUserName(user.getUserName(),user.getToken(), new GetUserByUserNameCallback(this));
    }
}

