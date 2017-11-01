package ar.edu.itba.iot.iot_android.service.callbacks;

import android.app.Activity;
import android.util.Log;

import java.io.IOException;

import ar.edu.itba.iot.iot_android.controller.UserController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


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
        userController.getUser().setToken(response.header("x-token"));
        Log.d("token", response.header("x-token"));
        userController.getUser().setLogoutURL(response.header("x-logout-url"));
    }
}
