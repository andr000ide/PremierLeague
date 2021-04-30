package com.example.premierleague;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.premierleague.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class RecicleAdapter extends RecyclerView.Adapter<RecicleAdapter.MyViewHolder>
{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    protected List<Team> mList;
    public String title;
    Context context;
    JSONObject conteudo;
    JSONArray arr;

    public RecicleAdapter(Context context, JSONObject conteudo){
        this.context=context;
        this.conteudo=conteudo;
        mList= new ArrayList<>();
        decifrar();
    }
    public void decifrar(){
        try {
            title=conteudo.getString("name");
            arr=conteudo.getJSONArray("clubs");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i=0;i<arr.length();i++){
            try {
                JSONObject aux;
                aux=arr.getJSONObject(i);
                Team teamaux = new Team(aux.getString("key"),aux.getString("code"),aux.getString("name"));

                mList.add(teamaux);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @NonNull
    @Override
    //inflate the row layout from xml when needed
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         if(i == TYPE_HEADER) {
            //inflate your layout and pass it to view holder
             //System.out.println("Header + ="+i);
             View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header, viewGroup,false);
            return new MyViewHolder(itemView,TYPE_HEADER);
        }
        else {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recicler_row, viewGroup,false);
             //System.out.println("Item + ="+i);
            return new MyViewHolder(itemView,i);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

            //Log.i("view type",holder.view_type+"");
            if(holder.view_type == TYPE_HEADER) {
                holder.titulo.setText(title);

            }
            else{
                if(i%2==0){
                    //holder.celula.setBackgroundColor(Color.parseColor("#AAAAAA"));
                }
                else{
                    //holder.celula.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                String codigo = mList.get(i-1).code;
                String nome = mList.get(i-1).name;
                holder.nomeEquipa.setText(nome);
                holder.codigoEquipa.setText(codigo);
            }
    }


    @Override
    public int getItemCount() {
       return mList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        private LinearLayout celula;
        private TextView titulo;
        private TextView codigoEquipa;
        private TextView nomeEquipa;
        int view_type;

        public MyViewHolder(View itemView, int viewType){
            super(itemView);


            if(viewType==TYPE_HEADER){
                view_type=0;
                this.titulo=  itemView.findViewById(R.id.titulo);
            }
            else{
                itemView.setOnClickListener(this);
                this.view_type=viewType;
                this.codigoEquipa  = itemView.findViewById(R.id.codigoEquipa);
                this.nomeEquipa = itemView.findViewById(R.id.nomeEquipa);
                this.celula = itemView.findViewById(R.id.celula);

            }

        }
        @Override
        public void onClick(View view){
            Toast.makeText(view.getContext(),nomeEquipa.getText(),Toast.LENGTH_SHORT).show();
            Intent randomIntent = new Intent(context, SecondActivity.class);

            randomIntent.putExtra("ITEM", nomeEquipa.getText());
            //celula.setBackgroundColor(Color.parseColor("#FF0000"));
           context.startActivity(randomIntent);

        }

    }
    @Override
    public int getItemViewType(int position) {

        if(position==0){
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

}

