package ar.edu.itba.iot.iot_android.controller;

import ar.edu.itba.iot.iot_android.activities.MainActivity;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.service.UserService;
import ar.edu.itba.iot.iot_android.service.callbacks.ChangeDeviceNicknameController;
import ar.edu.itba.iot.iot_android.service.callbacks.ChangeTargetTempCallback;
import ar.edu.itba.iot.iot_android.service.callbacks.GetDeviceCallback;
import ar.edu.itba.iot.iot_android.service.callbacks.GetDevicesCallback;
import ar.edu.itba.iot.iot_android.service.callbacks.GetUserByUserNameCallback;
import ar.edu.itba.iot.iot_android.service.callbacks.LogInCallback;
import ar.edu.itba.iot.iot_android.service.callbacks.RegisterDeviceCallback;
import ar.edu.itba.iot.iot_android.service.callbacks.LogOutCallback;
import okhttp3.Response;

public class Controller {

    private UserService userService = new UserService();
    private final User user;
    private final MainActivity mainActivity;

    public Controller(MainActivity mainActivity, User user) {
        this.user = user;
        this.mainActivity = mainActivity;
    }

    public User getUser() {
        return user;
    }

    public void login(){
        userService.logIn(user.getUsername(), user.getPassword(), new LogInCallback(this));
    }

    public Response loginSync(){
        return userService.logInSync(user.getUsername(), user.getPassword());
    }

    public void getDevices(){
        userService.getDevices(user.getId(), user.getToken(), new GetDevicesCallback(mainActivity, this.user));
    }

    public void getFullUserData(){
        userService.getUserByUserName(user.getUsername(), user.getToken(), new GetUserByUserNameCallback(mainActivity, this));
    }

    public void registerDevice(String deviceId){
       userService.registerDevice(user.getId(), deviceId, user.getToken(), new RegisterDeviceCallback(this));
    }

    public void getDevice(String deviceId){
        userService.getDevice(user.getId(), deviceId, user.getToken(), new GetDeviceCallback(mainActivity, this.user));
    }

    public void signOut(User user){
        userService.logOut(user, new LogOutCallback(mainActivity, this));
    }

    public void changeTargetTemperature(User user, String deviceId, double temperature){
        userService.changeTargetTemperature(deviceId, user.getToken(), new ChangeTargetTempCallback(), temperature);
    }

    public void changeDeviceNickname(User user, String deviceId, String nickname){
        userService.changeDeviceName(user.getId(), deviceId, user.getToken(), new ChangeDeviceNicknameController(), nickname);
    }
}

