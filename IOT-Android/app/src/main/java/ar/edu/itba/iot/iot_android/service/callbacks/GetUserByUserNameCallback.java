package ar.edu.itba.iot.iot_android.service.callbacks;

import java.io.IOException;

import ar.edu.itba.iot.iot_android.activities.MainActivity;
import ar.edu.itba.iot.iot_android.controller.UserController;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.utils.JSONManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GetUserByUserNameCallback implements Callback {

    private final UserController userController;
    private final MainActivity mainActivity;

    public GetUserByUserNameCallback(MainActivity mainActivity, UserController userController) {
        this.mainActivity = mainActivity;
        this.userController = userController;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        //TODO no anda el endpoint y no se sabe como devuelve

        User newUser =  JSONManager.parseUser(response.body().string());
        userController.getUser().setId(newUser.getId());
        userController.getUser().setFullName(newUser.getFullName());
        userController.getUser().setEmail(newUser.getEmail());
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //TODO fix this (double drawer)
//                mainActivity.setupDrawer();
            }
        });

    }
}
