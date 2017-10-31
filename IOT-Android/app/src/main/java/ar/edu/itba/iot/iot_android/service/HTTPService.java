package ar.edu.itba.iot.iot_android.service;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by julianrodrigueznicastro on 10/30/17.
 */

public class HTTPService {

    private final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public HTTPService() {

    }

    Response post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    Response post(String url, String json, String token) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-token", token)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    Response get(String url, String token) throws IOException {
        Request request = new Request.Builder()
                .addHeader("x-token", token)
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

}
