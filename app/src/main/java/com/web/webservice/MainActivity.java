package com.web.webservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ServiceHelper serviceHelper;
    String url = "http://api.androidhive.info/contacts/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceHelper = new ServiceHelper();
        serviceHelper.jsonSendHttpRequest(url, "GET", "");

    }
}
