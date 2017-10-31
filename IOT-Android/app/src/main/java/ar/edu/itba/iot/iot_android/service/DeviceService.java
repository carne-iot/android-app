package ar.edu.itba.iot.iot_android.service;

import android.util.Log;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import ar.edu.itba.iot.iot_android.model.Device;
import ar.edu.itba.iot.iot_android.utils.JSONParser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DeviceService {

    private final String baseURL;
    private HTTPService httpService = new HTTPService();

    public DeviceService(String baseURL) {
        this.baseURL = baseURL;
    }

    public DeviceService() {
        baseURL = "http://server.carne-iot.itba.bellotapps.com";
    }

    public Collection<Device> getDevices(Callback callback) {
        try {
            httpService.get(baseURL + "/devices", callback);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashSet<>();
    }

    //TODO
    public boolean registerDevice(String id, String userid){
        return false;
    }
}
