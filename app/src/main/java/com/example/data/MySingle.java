package com.example.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingle {
    private static MySingle mySingle;
    private RequestQueue requestQueue;
    private static Context context;

    private MySingle(Context ctx)
    {
        context=ctx;
        requestQueue=getRequestQueue();
    }

    private RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingle getInstance(Context ctx)
    {
        if(mySingle==null)
        {
            mySingle=new MySingle(ctx);
        }
        return mySingle;
    }

    public <T> void addToRequest(Request<T> request)
    {
        getRequestQueue().add(request);
    }
}
