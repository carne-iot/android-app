package ar.edu.itba.iot.iot_android.service;

import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.Observer;

import ar.edu.itba.iot.iot_android.model.Device;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.service.callbacks.GetUserByUserNameCallback;
import okhttp3.Callback;

public class UserService  implements Serializable {

    private final String baseURL;
    private final HTTPService httpService = new HTTPService();

    public UserService(String baseURL) {
        this.baseURL = baseURL;
    }

    public UserService() {
        baseURL = "http://server.carne-iot.itba.bellotapps.com";
    }

    public void logIn(String username, String password, Callback callback) {
        try {
            //TODO user JSON lib
            httpService.post(baseURL + "/auth/login", "{\n" +
                    " \"username\":\"julian\",\n" +
                    " \"password\":\"julian\"\n" +
                    "}", callback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDevice(Long userId, String deviceId, String token, Callback callback){
        try {
            httpService.get(baseURL + "/users/" + userId + "/devices/" + deviceId, token, callback);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("fail:", "no anda");
        }
    }

    public void getDevices(Long userId, String token, Callback callback){
        try {
            httpService.get(baseURL + "/users/" + userId + "/devices/" , token, callback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getUserByUserName(String userName, String token, GetUserByUserNameCallback getUserByUserNameCallback) {
        try {
            httpService.get(baseURL + "/users/username/" + userName, token, getUserByUserNameCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerDevice(Long userId, String deviceId, String token, Callback callback) {
        try {
            httpService.put(baseURL + "/users/" + String.valueOf(userId) + "/devices/" + deviceId, token, callback);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Fail:", "Could not register device");
        }
    }

    public void logOut(User user, Callback callback){

        try {
            httpService.post(user.getLogoutURL(),"", user.getToken(), callback);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Fail:", "Could not log out");
        }

    }
}
