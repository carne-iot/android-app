package ar.edu.itba.iot.iot_android.service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import ar.edu.itba.iot.iot_android.model.Device;
import okhttp3.Callback;

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
