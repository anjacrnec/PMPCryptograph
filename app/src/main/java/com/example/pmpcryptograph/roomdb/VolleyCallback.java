package com.example.pmpcryptograph.roomdb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public interface VolleyCallback {
    void getResponse(JSONArray response) throws JSONException, ExecutionException, InterruptedException;
}
