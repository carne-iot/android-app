package ar.edu.itba.iot.iot_android.service.callbacks;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.Collection;
import java.util.Observer;

import ar.edu.itba.iot.iot_android.activities.MainActivity;
import ar.edu.itba.iot.iot_android.model.Device;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.utils.JSONManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class GetDevicesCallback implements Callback {

    private final User user;
    private final MainActivity mainActivity;

    public GetDevicesCallback(MainActivity mainActivity, User user){
        this.mainActivity = mainActivity;
        this.user = user;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Collection<Device> newDevices = JSONManager.parseDevices(response.body().string());
        user.setDevices(newDevices);
        Log.d("devices", "I got my devices");

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.populateAdapter();
            }
        });
    }
}
