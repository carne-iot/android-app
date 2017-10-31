package ar.edu.itba.iot.iot_android.service.callbacks;

import android.util.Log;

import java.io.IOException;

import ar.edu.itba.iot.iot_android.controller.UserController;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.utils.JSONParser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class GetDevicesCallback implements Callback {

    private User user;

    public GetDevicesCallback(User user){
        this.user = user;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String resp = response.body().string();
        user.setDevices(JSONParser.parseDevices(resp));
        Log.d("devices", "i got my devices");
        //System.out.println(resp);
        Log.d("list devices", "DEVICES: " + resp);
    }
}
