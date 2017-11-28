package ar.edu.itba.iot.iot_android.service;

import android.util.Log;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by julianrodrigueznicastro on 10/30/17.
 */

public class HTTPService implements Serializable{

    private final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public HTTPService() {

    }

    public void post(String url, String json, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public Response postSync(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response;
        }
    }

    public void post(String url, String json, String token, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void get(String url, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void get(String url, String token, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + token)
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void put(String url,String token, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .put(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void put(String url, String json, String token, Callback callback) throws IOException {

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .put(body)
                .build();
        client.newCall(request).enqueue(callback);
    }


}
