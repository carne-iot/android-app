package ar.edu.itba.iot.iot_android.service.callbacks;

import java.io.IOException;

import ar.edu.itba.iot.iot_android.controller.Controller;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by julianrodrigueznicastro on 11/1/17.
 */

public class RegisterDeviceCallback implements Callback {

    private final Controller controller;

    public RegisterDeviceCallback(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        controller.getDevices();
    }
}
