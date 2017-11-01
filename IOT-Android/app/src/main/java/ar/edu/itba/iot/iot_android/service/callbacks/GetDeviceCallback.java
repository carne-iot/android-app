package ar.edu.itba.iot.iot_android.service.callbacks;

import java.io.IOException;
import java.util.List;
import java.util.Observer;


import ar.edu.itba.iot.iot_android.model.Device;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.utils.JSONManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class GetDeviceCallback implements Callback {

    private final User user;
    private final Observer observer;

    public GetDeviceCallback(User user, Observer observer) {
        this.user = user;
        this.observer = observer;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String resp = response.body().string();
        Device device = JSONManager.parseDevice(resp);
        List<Device> devices = user.getDevices();
        if(!devices.contains(device)) {
            devices.add(device);
        }
        device.addObserver(this.observer);
        device.setTemperature(device.getTemperature());
    }
}
