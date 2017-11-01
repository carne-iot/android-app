package ar.edu.itba.iot.iot_android.service.callbacks;

import android.util.Log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;

import ar.edu.itba.iot.iot_android.model.Device;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.utils.JSONParser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class GetDevicesCallback implements Callback {

    private final User user;
    private final Observer observer;

    public GetDevicesCallback(User user, Observer observer){
        this.user = user;
        this.observer = observer;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.d("list devices", "DEVICES: " + response.body().string());
        String resp = response.body().string();
        user.setDevices(JSONParser.parseDevices(resp));

        for (Device device: user.getDevices()) {
            device.addObserver(observer);
        }

        Log.d("devices", "i got my devices");
        //System.out.println(resp);
        //Log.d("list devices", "DEVICES: " + resp);
    }
}
