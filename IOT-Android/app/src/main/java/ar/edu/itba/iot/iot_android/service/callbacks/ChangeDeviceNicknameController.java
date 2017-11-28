package ar.edu.itba.iot.iot_android.service.callbacks;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;


public class ChangeDeviceNicknameController implements okhttp3.Callback{
    @Override
    public void onFailure(Call call, IOException e) {
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
    }
}
