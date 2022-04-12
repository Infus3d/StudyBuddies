package com.example.studybuddies.utils;

import org.json.JSONObject;

public interface OnSuccessfulObject {

    /**
     * OnSuccess method to be called when a JSONObjectRequest receives a response from the server
     * @param response Response passed by the onResponse method of the JSONObjectRequest
     */
    public void onSuccess(JSONObject response);
}
