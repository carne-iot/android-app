package ar.edu.itba.iot.iot_android.service.callbacks;

import java.io.IOException;

import ar.edu.itba.iot.iot_android.activities.MainActivity;
import ar.edu.itba.iot.iot_android.controller.UserController;
import okhttp3.Call;
import okhttp3.Response;

public class LogOutCallback implements okhttp3.Callback {

    private final UserController userController;
    private final MainActivity mainActivity;

    public LogOutCallback(MainActivity mainActivity, UserController userController) {
        this.userController = userController;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onFailure(Call call, IOException e) {
    }

    @Override
    public void onResponse(Call call, Response response) {
        userController.setLoggedIn(false);
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.updateDrawer();
            }
        });
    }
}
