package com.example.sgh;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    private static MySingleton mInstance;
    private static Context mCtx;
    private RequestQueue requestQueue;

    private MySingleton(Context context) {
        mCtx = context;
        requestQueue = getRequestQueue();
        Log.d("request queue", "" + requestQueue.toString());
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        Log.d("mInstaces", "" + mInstance);
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        Log.d("request",""+request.toString());
        requestQueue.add(request);
        Log.d("now request queue",""+requestQueue.toString());
    }
}