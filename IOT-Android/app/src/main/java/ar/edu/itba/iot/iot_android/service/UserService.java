package ar.edu.itba.iot.iot_android.service;

import android.util.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.Observer;

import ar.edu.itba.iot.iot_android.model.Device;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.service.callbacks.GetUserByUserNameCallback;
import okhttp3.Callback;
import okhttp3.Response;

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
            httpService.post(baseURL + "/auth/login", "{\n" +
                    " \"username\":\"julian\",\n" +
                    " \"password\":\"julian\"\n" +
                    "}", callback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response logInSync(String username, String password) {
        try {
            return httpService.postSync(baseURL + "/auth/login", "{\n" +
                    " \"username\":\"" + username + "\",\n" +
                    " \"password\":\"" + password + "\"\n" +
                    "}");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Response signUpSync(String fullName, String email, String birthDate,
                               String password, String userName) {
        try {
            Response response = httpService.postSync(baseURL + "/users", "{\n" +
                    " \"username\":\"" + userName + "\",\n" +
                    " \"password\":\"" + password + "\"\n," +
                    " \"birthDate\":\"" + birthDate + "\"\n," +
                    " \"email\":\"" + email + "\"\n," +
                    " \"fullName\":\"" + fullName + "\"\n" +
                    "}");
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    public void changeTargetTemperature(String deviceId, String token, Callback callback, double temp){

        try {
            String json = "{\"value\":"+temp+"}";
            httpService.put(baseURL + "/devices/" + deviceId + "/target-temperature", json, token, callback);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Fail:", "Could not set target temperature");
        }
    }

    public void changeDeviceName(Long userId, String deviceId, String token, Callback callback, String nickname){

        try {
            String url = baseURL + "/users/" + userId + "/devices/" + deviceId + "/nickname";
            String json = "{\"value\":\"" + nickname + "\"}";
            httpService.put(url, json, token, callback);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
