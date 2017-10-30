package ar.edu.itba.iot.iot_android.services;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DeviceService {

    private final OkHttpClient client = new OkHttpClient();
    private final String baseURL;

    public DeviceService(String baseURL) {
        this.baseURL = baseURL;
    }

    public DeviceService() {
        baseURL = "http://server.carne-iot.itba.bellotapps.com";
    }

    public void getDevices() {

        Request request = new Request.Builder()
                .url(baseURL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                // you code to handle response
                Log.d("http", response.body().string());
            }
        });

    }
}
