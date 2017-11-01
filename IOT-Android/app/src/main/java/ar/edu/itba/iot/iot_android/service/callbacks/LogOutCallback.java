package ar.edu.itba.iot.iot_android.service.callbacks;

import java.io.IOException;

import javax.security.auth.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

public class LogOutCallback implements okhttp3.Callback {


    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) {
    }
}
