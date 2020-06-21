package com.example.pmpcryptograph.roomdb;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class WordRequest {

    private Context context;
    private RequestQueue requestQueue;
    private JSONArray response;

    public WordRequest(Context context)
    {
        this.context=context;
        requestQueue= Volley.newRequestQueue(context);
    }


    public void getWord(String url,final VolleyCallback callback) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jresponse) {
                        Log.e("RESPONSE_OK", String.valueOf(response));
                        response = jresponse;
                        try {
                            callback.getResponse(response);
                        } catch (JSONException | ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("RESPONSE_NOK", String.valueOf(response));
                        error.printStackTrace();
                    }
                }) /*{
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Token 64bb1631545e298942c12698e0d8e05233a1fdda");
                return headers;
            }
        }*/;
        requestQueue.add(request);
    }



}
