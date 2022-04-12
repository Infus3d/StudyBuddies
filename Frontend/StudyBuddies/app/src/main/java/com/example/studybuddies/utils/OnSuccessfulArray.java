package com.example.studybuddies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface OnSuccessfulArray {

    /**
     * OnSuccess method to be called when a JSONArrayRequest receives a response from the server
     * @param response Response passed by the onResponse method of the JSONArrayRequest
     * @throws JSONException
     */
    public void onSuccess(JSONArray response) throws JSONException;

}
