package com.example.pmpcryptograph.roomdb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface VolleyCallback {
    void getResponse(JSONArray response) throws JSONException;
}
