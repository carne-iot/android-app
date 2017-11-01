package ar.edu.itba.iot.iot_android.service;

import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import ar.edu.itba.iot.iot_android.model.Device;
import ar.edu.itba.iot.iot_android.service.callbacks.GetUserByUserNameCallback;
import ar.edu.itba.iot.iot_android.utils.JSONParser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class UserService {

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
            httpService.post(baseURL + "/auth/login", "{\"username\":\"julian\",\"password\":\"julian\"}", callback);
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
}
