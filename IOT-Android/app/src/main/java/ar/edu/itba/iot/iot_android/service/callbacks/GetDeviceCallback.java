package ar.edu.itba.iot.iot_android.service.callbacks;

import java.io.IOException;

import ar.edu.itba.iot.iot_android.model.Device;
import ar.edu.itba.iot.iot_android.utils.JSONParser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by julianrodrigueznicastro on 10/31/17.
 */

public class GetDeviceCallback implements Callback {

    private final Device device;

    public GetDeviceCallback(Device device) {
        this.device = device;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        device.setCurrentTemperature(JSONParser.parseDevice(response.body().string()).getCurrentTemperature());
    }
}
