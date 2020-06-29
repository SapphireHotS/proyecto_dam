package com.example.sgh;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class BackgroundTaskMesas {

    public interface CustomReqFinished{
        public void onCustomReqFinished(ArrayList<String> list);
    }
    Context context;
    CustomReqFinished listener;
    ArrayList<String> arrayList=new ArrayList<>();
    String str_url="http://192.168.1.39:8083/sgh/buscar_mesas.php";
    String data="";
    public BackgroundTaskMesas(Activity activity)
    {
        this.context=activity;
        this.listener=(CustomReqFinished) activity;
    }
    public void getList()
    {
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(str_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        arrayList.add(jsonObject.getString("nummesa"));

                    } catch (Exception e) {
                        Toast.makeText(context,"Error...!!!",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                listener.onCustomReqFinished(arrayList);
            }

        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context,"Error...!!!",Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });


        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
//
    }
}