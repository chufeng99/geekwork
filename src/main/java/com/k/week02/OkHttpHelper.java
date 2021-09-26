package com.k.week02;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpHelper {
    final static OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) throws Exception {
        String url = "http://localhost:8801/";
        Request request = new Request.Builder().url(url).build();
        Response resp = client.newCall(request).execute();
        System.out.println("url: " + url + " ; response: \n" + resp.body().toString());

    }
}
