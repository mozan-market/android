package com.example.mozan;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by User on 09.12.2014.
 */
public class ApiHelper {

    public static final String TAG = "[API]";
    public static final String API_URL = "http://mozan.market";
    public static final String TOKEN_URL = "http://mozan.market/api/auth/token/";
    public static final String HASH = "c25b902fiop74utyec591cd14d2104bf3";
    public static final String REGISTER_URL = "/api/register.php";

    private static final int HTTP_STATUS_OK = 200;
    private static byte[] sBuffer = new byte[512];
    private static List<Cookie> cookies;

    public static JSONObject getCode(String phone) throws ApiException,
            JSONException {
       // String url = API_URL + TOKEN_URL + "?phone=" + phone
       //         + "&hash=" + HASH;

        //testing
        String url = TOKEN_URL;
        //
        Log.i(TAG, "Sending request to: " + url);

        String response = getUrlContent(url, false);
        Log.i(TAG, "Response: " + response);

        return new JSONObject(response);
    }

    public static JSONObject auth(String phone, String password)
            throws ApiException, JSONException {
        String url = TOKEN_URL;
        Log.i("[API]", url);

        String response = getUrlContent(url, true);
        Log.i("[API]", "response: " + response);

        return (JSONObject) new JSONTokener(response).nextValue();
    }

    public static class ApiException extends Exception {

        public ApiException(String detailMessage) {
            super(detailMessage);
        }
    }

    public static synchronized String getUrlContent(String url,
                                                    boolean saveCookies) throws ApiException {
        if (Global.userAgent == "") {
            throw new ApiException("User-Agent string must be prepared");
        }

        // Create client and set our specific user-agent string
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", Global.userAgent);

        try {
            Log.i("[API]", "Begin to make query");
            HttpResponse response = client.execute(request);
            Log.i("[API]", "End to make query");

            Log.i("[API]", "Begin to check status");
            // Check if server response is valid
            StatusLine status = response.getStatusLine();
            if (status.getStatusCode() != HTTP_STATUS_OK) {
                throw new ApiException("Invalid response from server: "
                        + status.toString());
            }
            Log.i("[API]", "End to check status");

            // Pull content stream from response
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();

            if (saveCookies == true) {
                ApiHelper.cookies = client.getCookieStore().getCookies();
            }

            ByteArrayOutputStream content = new ByteArrayOutputStream();

            // Read response into a buffered stream
            int readBytes = 0;
            while ((readBytes = inputStream.read(sBuffer)) != -1) {
                content.write(sBuffer, 0, readBytes);
            }

            // Return result from buffered stream
            return new String(content.toByteArray());
        } catch (IOException e) {
            throw new ApiException(e.getMessage());
        }
    }

}
