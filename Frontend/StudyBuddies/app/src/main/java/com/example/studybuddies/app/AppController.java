package com.example.studybuddies.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * This class is used to handle request queues
 */
public class AppController extends Application {

	/**
	 * Tag for this class
	 */
	public static final String TAG = AppController.class.getSimpleName();

	private RequestQueue mRequestQueue;

	private static AppController mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}


	/**
	 * returns the instance of the AppController
	 * @return
	 */
	public static synchronized AppController getInstance() {
		return mInstance;
	}

	/**
	 * Creates a new request queue if null, else return the request queue
	 * @return
	 */
	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	/*
	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}
	 */

	/**
	 * Adds a new Volley Request to the queue
	 * @param req the request to be added to the queue
	 * @param tag the tag to go along with the request
	 * @param <T>
	 */
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	/**
	 * Adds a new Volley Request to the queue
	 * @param req the request to be added to the queue
	 * @param <T>
	 */
	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests with the given tag
	 * @param tag tag to indicate which requests to cancel
	 */
	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}
