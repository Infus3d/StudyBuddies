package com.example.studybuddies.utils;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.studybuddies.app.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestsCentral {

    private static String TAG = RequestsCentral.class.getSimpleName();
    private static String tag_json_obj = "jobj_req";

    public static void postJSONObject(String url, JSONObject j, OnSuccessfulObject onSuccessfulResponse) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST,
                url,
                j,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        onSuccessfulResponse.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                String errorMsg = "";
                if (response != null && response.data != null) {
                    String errorString = new String(response.data);
                    Log.d("log error", errorString);
                }
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    public static void getJSONObject(String url, OnSuccessfulObject onSuccessfulResponse) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        onSuccessfulResponse.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                String errorMsg = "";
                if (response != null && response.data != null) {
                    String errorString = new String(response.data);
                    Log.d("log error", errorString);
                }
            }
        }) {

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    public static  void postJSONArray(String url, JSONArray j, OnSuccessfulArray onSuccessfulResponse) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                url,
                j,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, response.toString());
                        onSuccessfulResponse.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                String errorMsg = "";
                if(response != null && response.data != null){
                    String errorString = new String(response.data);
                    Log.d("log error", errorString);
                }
            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstName", "first");
                params.put("lastName", "last");

                return params;
            }

        };

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonArrayRequest, tag_json_obj);

    }

    public static void getJSONArray(String url, OnSuccessfulArray successfulResponse) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, response.toString());
                        successfulResponse.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                String errorMsg = "";
                if(response != null && response.data != null){
                    String errorString = new String(response.data);
                    Log.d("log error", errorString);
                }
            }
        }){

            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstName", "first");
                params.put("lastName", "last");

                return params;
            }

        };

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonArrayRequest, tag_json_obj);

    }
}