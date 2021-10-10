package com.k.week02;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientHelper {
    public static CloseableHttpClient httpclient = HttpClients.createDefault();

    public static String getAsString(String url) throws IOException {
        try {
            ResponseHandler<String> responseHandler = rsp -> {
                System.out.println("statusLine: " + rsp.getStatusLine());

                int status = rsp.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = rsp.getEntity();
                    return entity != null ? EntityUtils.toString(entity, "UTF-8") : null;
                } else {
                    throw new ClientProtocolException("Unexpected rsp status: " + status);
                }
            };

            HttpGet httpGet = new HttpGet(url);
            return httpclient.execute(httpGet, responseHandler);
        } finally {
            if (null != httpclient) {
                httpclient.close();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String url = "http://localhost:8803/";
        String text = HttpClientHelper.getAsString(url);
        System.out.println("url: " + url + " ; response: \n" + text);
    }
}
