package com.example.studybuddies.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Interface to handle JSONObject responses
 * @author Andy Bruder
 */
public interface OnSuccessfulObject {

    /**
     * OnSuccess method to be called when a JSONObjectRequest receives a response from the server
     * @param response Response passed by the onResponse method of the JSONObjectRequest
     */
    public void onSuccess(JSONObject response) throws JSONException;
}
