package ar.edu.itba.iot.iot_android.service.callbacks;

import java.io.IOException;

import ar.edu.itba.iot.iot_android.controller.UserController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by julianrodrigueznicastro on 10/31/17.
 */

public class GetUserByUserNameCallback implements Callback {
    private final UserController userController;

    public GetUserByUserNameCallback(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        //TODO no anda el endpoint y no se sabe como devuelve
        userController.getUser().setId(Long.valueOf(response.header("")));
    }
}
