package ar.edu.itba.iot.iot_android.service.callbacks;

import android.app.Activity;
import android.util.Log;

import java.io.IOException;

import ar.edu.itba.iot.iot_android.controller.UserController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by julianrodrigueznicastro on 10/31/17.
 */

public class LogInCallback implements Callback {

    private final UserController userController;

    public LogInCallback(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        userController.setNewToken(response.header("x-token"));
    }
}
