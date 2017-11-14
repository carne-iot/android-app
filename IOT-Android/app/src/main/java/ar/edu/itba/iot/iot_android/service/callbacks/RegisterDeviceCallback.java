package ar.edu.itba.iot.iot_android.service.callbacks;

import android.util.Log;

import java.io.IOException;
import java.util.Observer;

import ar.edu.itba.iot.iot_android.controller.UserController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by julianrodrigueznicastro on 11/1/17.
 */

public class RegisterDeviceCallback implements Callback {

    private final UserController userController;

    public RegisterDeviceCallback(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        userController.getDevices();
    }
}
