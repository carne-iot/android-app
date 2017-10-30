package ar.edu.itba.iot.iot_android.service;

import android.util.Log;

import java.io.IOException;
import java.util.Collection;

import ar.edu.itba.iot.iot_android.model.Device;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by julianrodrigueznicastro on 10/29/17.
 */

public class DeviceService {

    private final OkHttpClient client = new OkHttpClient();

    private final String baseURL;

    public DeviceService(String baseURL) {
        this.baseURL = baseURL;
    }

    public DeviceService() {
        baseURL = "https://7a79837b-ef74-4e99-97cf-6d2fbf4f013a.mock.pstmn.io";
    }

    //TODO
    public Collection<Device> getDevices() throws IOException{
        Request request = new Request.Builder()
                .url(baseURL + "/devices")
                .build();

        Response response = client.newCall(request).execute();
        Log.d("HTTP", String.valueOf(response.code()));
        Log.d("HTTP",response.body().string());
        return null;
    }

    public Device getDevice(){
        return null;
    }
}
