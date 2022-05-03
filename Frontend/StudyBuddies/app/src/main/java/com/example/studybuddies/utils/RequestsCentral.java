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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains all methods used to send requests to the server.
 * @author Andy Bruder
 */
public class RequestsCentral {

    /**
     * Tag to be inserted in the log upon response
     */
    private static String TAG = RequestsCentral.class.getSimpleName();

    /**
     * Tag to be sent in request queue with requests
     */
    private static String tag_json_obj = "jobj_req";

    /**
     * Posts a JSONObject to the given url and calls the onSuccess() method from OnSuccessfulObject
     * @param url Url for the JSONObject to be sent to
     * @param j JSONObject to be sent by the request
     * @param onSuccessfulResponse Reference to the interface OnSuccessfulObject to help handle the response
     */
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

    /**
     * Puts a JSONObject to the given url and calls the onSuccess() method from OnSuccessfulObject
     * @param url Url for the JSONObject to be sent to
     * @param j JSONObject to be sent by the request
     * @param onSuccessfulResponse Reference to the interface OnSuccessfulObject to help handle the response
     */
    public static void putJSONObject(String url, JSONObject j, OnSuccessfulObject onSuccessfulResponse) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.PUT,
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

    /**
     * Gets a JSONObject from the given url and calls the onSuccess() method from OnSuccessfulObject
     * @param url Url to get the JSONObject from
     * @param onSuccessfulResponse Reference to the interface OnSuccessfulObject to help handle the response
     */
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

    /**
     * Posts a JSONArray to the given url and calls the onSuccess() method from OnSuccessfulArray
     * @param url Url for the JSONArray to be sent to
     * @param j JSONArray to be sent by the request
     * @param onSuccessfulResponse Reference to the interface OnSuccessfulArray to help handle the response
     */
    public static void postJSONArray(String url, JSONArray j, OnSuccessfulArray onSuccessfulResponse) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.POST,
                url,
                j,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, response.toString());
                        try {
                            onSuccessfulResponse.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    /**
     * Gets a JSONArray from the given url and calls the onSuccess() method from OnSuccessfulArray
     * @param url Url to get the JSONArray from
     * @param successfulResponse Reference to the interface OnSuccessfulArray to help handle the response
     */
    public static void getJSONArray(String url, OnSuccessfulArray successfulResponse) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, response.toString());
                        try {
                            successfulResponse.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    public static void deleteJSONObject(String url, OnSuccessfulObject onSuccessfulResponse) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.DELETE,
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

}
