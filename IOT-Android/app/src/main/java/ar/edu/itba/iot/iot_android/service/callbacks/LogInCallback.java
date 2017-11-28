package ar.edu.itba.iot.iot_android.service.callbacks;

import android.util.Log;

import java.io.IOException;

import ar.edu.itba.iot.iot_android.controller.Controller;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class LogInCallback implements Callback {

    private final Controller controller;

    public LogInCallback(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        controller.getUser().setToken(response.header("x-token"));
        Log.d("token", response.header("x-token"));
        controller.getUser().setLogoutURL(response.header("x-logout-url"));
    }
}
