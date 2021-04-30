package com.example.premierleague;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class Services {
    private static final String ENDPOINT = "https://raw.githubusercontent.com/openfootball/football.json/master/2016-17/en.1.clubs.json";
    private static final String ENDPOINT2 ="https://raw.githubusercontent.com/openfootball/football.json/master/2017-18/en.1.json";
    View view;
    RecicleAdapter adapter;
    JSONObject jsonObj;
    JSONObject jsonObj2;
    RecyclerView recyclerView;

    public Services(View view,RecyclerView recyclerView ){
        this.view=view;
        this.recyclerView=recyclerView;
    }


    public void teams(){
        StringRequest request = new StringRequest(ENDPOINT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CODE", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                //JsonElement element = gson.fromJson (response, JsonElement.class);
                try {
                    jsonObj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //RecyclerView recyclerView = view.findViewById(R.id.recicleAdapter);

                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(dividerItemDecoration);
                adapter = new RecicleAdapter(view.getContext(),jsonObj);
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(),"Algo correu mal no service",Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        queue.add(request);
    }
}
