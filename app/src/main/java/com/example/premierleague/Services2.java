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

public class Services2 {
    private static final String ENDPOINT = "https://raw.githubusercontent.com/openfootball/football.json/master/2017-18/en.1.clubs.json";
    private static final String ENDPOINT2 ="https://raw.githubusercontent.com/openfootball/football.json/master/2016-17/en.1.json";
    View view;
    RecicleAdapter2 adapter;
    JSONObject jsonObj;

    JSONObject jsonObj2;
    RecyclerView recyclerView;
    JSONArray arr;
    String data,score1,score2;
    String nomeEquipa;
    public Services2(View view,RecyclerView recyclerView,String nomeEquipa){
        this.view=view;
        this.recyclerView=recyclerView;
        this.nomeEquipa=nomeEquipa;
    }
    public void aocontrario(){
        adapter.aocontrario();
        adapter.notifyDataSetChanged();
    }
    public void matches(){
        StringRequest request = new StringRequest(ENDPOINT2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CODE", response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                //JsonElement element = gson.fromJson (response, JsonElement.class);
                try {
                    jsonObj2 = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(dividerItemDecoration);
                adapter = new RecicleAdapter2(view.getContext(),jsonObj2,nomeEquipa);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(),"Algo correu mal",Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(view.getContext());
        queue.add(request);
    }
}
