package ar.edu.itba.iot.iot_android.service.callbacks;

import java.io.IOException;

import ar.edu.itba.iot.iot_android.activities.MainActivity;
import ar.edu.itba.iot.iot_android.controller.Controller;
import okhttp3.Call;
import okhttp3.Response;

public class LogOutCallback implements okhttp3.Callback {

    private final Controller controller;
    private final MainActivity mainActivity;

    public LogOutCallback(MainActivity mainActivity, Controller controller) {
        this.controller = controller;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onFailure(Call call, IOException e) {
    }

    @Override
    public void onResponse(Call call, Response response) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.logout();
            }
        });
    }
}
