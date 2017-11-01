package ar.edu.itba.iot.iot_android.service.callbacks;

import java.io.IOException;

import ar.edu.itba.iot.iot_android.controller.UserController;
import okhttp3.Call;
import okhttp3.Response;

public class LogOutCallback implements okhttp3.Callback {

    private final UserController userController;

    public LogOutCallback(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void onFailure(Call call, IOException e) {
    }

    @Override
    public void onResponse(Call call, Response response) {
        userController.setLoggedIn(false);
    }
}
