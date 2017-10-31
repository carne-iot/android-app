package ar.edu.itba.iot.iot_android.service;

import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import ar.edu.itba.iot.iot_android.model.Device;
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

    public String logIn(String username, String password) {
        try {
            //TODO user JSON lib
            Response response = httpService.post(baseURL + "/auth/login", "{\"username\":\"julian\",\"password\":\"julian\"}");
            return response.header("x-token");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Device getDevice(Long userId, String deviceId, String token){
        try {
            Response response = httpService.get(baseURL + "/users/" + userId + "/devices/" + deviceId);
            return JSONParser.parseDevice(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
