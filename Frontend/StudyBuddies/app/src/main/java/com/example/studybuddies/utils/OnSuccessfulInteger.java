package com.example.studybuddies.utils;

/**
 * Interface to help pass integer values out of methods and responses
 */
public interface OnSuccessfulInteger {

    /**
     * Method to be called on success of an operation to pass the value
     * @param i
     */
    void onSuccess(int i);

}
