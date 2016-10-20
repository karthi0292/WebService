package com.web.webservice;

import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by CIPL0233 on 10/19/2016.
 */

public class ServiceHelper {
    public static String VALUE_AUTHENTICATION = "kbh:b1zw1zMob1leApps";
    public static String VALUE_ENCODING_AUTHENTICATION = getEncodingAuthenticationKey();

    public ServiceHelper() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public JSONObject jsonSendHttpRequest(String requestUrl, String requestType, String requestData) {
        JSONObject jsonObject = null;
        HttpURLConnection connection = null;
        try {
            URL mUrl = new URL(requestUrl);
            connection = (HttpURLConnection) mUrl.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod(requestType);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", VALUE_ENCODING_AUTHENTICATION);
            if (requestType.equals("POST")) {
                connection.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
                outputStreamWriter.write(requestData);
                outputStreamWriter.close();
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();
                jsonObject = new JSONObject(stringBuilder.toString());
                Log.d("Url", requestUrl);
                Log.d("PostData", requestData);
                Log.d("ResponseData", "" + stringBuilder.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return jsonObject;
    }

    private static String getEncodingAuthenticationKey() {
        return "Basic "
                + Base64.encodeToString(VALUE_AUTHENTICATION.getBytes(),
                Base64.NO_WRAP);
    }
}
