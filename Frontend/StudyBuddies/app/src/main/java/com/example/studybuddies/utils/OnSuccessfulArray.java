package com.example.studybuddies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Interface to help handle JSONArray responses
 * @author Andy Bruder
 */
public interface OnSuccessfulArray {

    /**
     * OnSuccess method to be called when a JSONArrayRequest receives a response from the server
     * @param response Response passed by the onResponse method of the JSONArrayRequest
     * @throws JSONException
     */
    void onSuccess(JSONArray response) throws JSONException;

}
