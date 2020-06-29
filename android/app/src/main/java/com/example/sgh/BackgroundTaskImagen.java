package com.example.sgh;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class BackgroundTaskImagen {

    public interface CustomReqFinished{
        public void onCustomReqFinished(Bitmap bm);
    }
    Context context;
    CustomReqFinished listener;
    ArrayList<String> arrayList=new ArrayList<>();
    String str_url;
    String data="";
    public BackgroundTaskImagen(Activity activity, String nombre)
    {
        this.context=activity;
        this.listener=(CustomReqFinished) activity;
        str_url="http://192.168.1.39:8083/sgh/buscar_imagenes.php?nombre="+nombre;
    }
    public void getBitmap(final Producto producto)
    {
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, str_url,
                new Response.Listener<String>()  {
                    @Override
                    public void onResponse(String response) {
                        byte [] encodeByte = Base64.decode(response,Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        producto.setImagen(bitmap);
                        listener.onCustomReqFinished(bitmap);
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }
        );


        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
//
    }
}

