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
import ar.edu.itba.iot.iot_android.service.callbacks.RegisterDeviceCallback;

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

    public void updateDevices(Observer observer){
        for(Device d : user.getDevices()){
            userService.getDevice(user.getId(), d.getId(), user.getToken(), new GetDeviceCallback(this.user, observer));
        }
    }

    public void getDevices(){
        //while((user.getToken() == null));
        userService.getDevices(user.getId(), user.getToken(), new GetDevicesCallback(this.user, deviceChange));
    }

    public void getFullUserData(){
        userService.getUserByUserName(user.getUserName(), user.getToken(), new GetUserByUserNameCallback(this));
    }

    public void registerDevice(String deviceId, Observer observer){
        userService.registerDevice(user.getId(), deviceId, user.getToken(), new RegisterDeviceCallback(this.user, this, deviceId, observer));
    }

    public void getDevice(String deviceId, Observer observer){
        userService.getDevice(user.getId(), deviceId, user.getToken(), new GetDeviceCallback(this.user, observer));
    }
}

