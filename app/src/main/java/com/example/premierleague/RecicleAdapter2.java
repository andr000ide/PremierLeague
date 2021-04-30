package com.example.premierleague;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.premierleague.R;
import com.example.premierleague.RecicleAdapter;
import com.example.premierleague.SecondActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecicleAdapter2 extends RecyclerView.Adapter<RecicleAdapter2.ViewHolder>
{

    protected List<Matches> mList;
    Context context;
    JSONObject jsonObj2;
    String nomeEquipa;
    JSONArray arr;
    int aux;

    public RecicleAdapter2(Context context,JSONObject conteudo,String nomeEquipa){
        this.context=context;
        this.jsonObj2=conteudo;
        this.nomeEquipa=nomeEquipa;
        mList = new ArrayList<>();
        decifrar();
    }
    public void decifrar(){

        try {
            jsonObj2.getString("name");
            arr=jsonObj2.getJSONArray("rounds");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i=0;i<arr.length();i++){
            try {
                JSONObject aux;
                aux=arr.getJSONObject(i);
                String aux1=aux.getString("name");
                System.out.println(aux1);
                JSONArray aux2=aux.getJSONArray("matches");
                System.out.println(aux2.toString());

                for(int y=0;y<aux2.length();y++){
                    JSONObject teste;
                    teste = aux2.getJSONObject(y);
                    String data,score1,score2;
                    JSONObject equipas;
                    data=teste.getString("date").replaceAll("-","");
                    System.out.println("data ->"+Integer.parseInt(data));
                    equipas=teste.getJSONObject("team1");
                    String equipaa1 = equipas.getString("name");
                    //equipa1=new Team(equipas.getString("key"),equipas.getString("code"),equipas.getString("name"));
                    equipas=teste.getJSONObject("team2");
                    String equipaa2 = equipas.getString("name");
                    //equipa2=new Team(equipas.getString("key"),equipas.getString("code"),equipas.getString("name"));
                    score1 =(teste.getString("score1"));
                    score2 = (teste.getString("score2"));
                    if(equipaa1.equals(nomeEquipa) || equipaa2.equals(nomeEquipa)){
                        Matches match= new Matches(equipaa1,equipaa2,score1,score2,data);
                        mList.add(match);
                    }
                    System.out.println("Score -> "+equipaa1 + " " + score1 + " x "+equipaa2 + " "+ score2);
                }
                //System.out.println(aux.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        aocontrario();
    }
    public void aocontrario(){
        Collections.sort(mList, new Comparator<Matches>() {
            @Override
            public int compare(Matches o1, Matches o2) {
                return Integer.compare(Integer.parseInt(o1.data),Integer.parseInt( o2.data));
            }
        });
        if(aux==0){
            aux=1;
            Collections.reverse(mList);
        }
        else{
            aux=0;
        }

        //for(int i=0;i<mList.size();i++){
        //    System.out.println(mList.get(i).data);
        //}

    }

    @NonNull
    @Override
    //inflate the row layout from xml when needed
    public RecicleAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.second_recicler_row, viewGroup,false));
    }

    //binds data to the textview in each row
    @Override
    public void onBindViewHolder(@NonNull RecicleAdapter2.ViewHolder viewHolder, int i) {
        Matches item = mList.get(i);
        String dataaux = mList.get(i).data;
        dataaux= dataaux.substring(0, 4) + "-" + dataaux.substring(4,6) + "-"+ dataaux.substring(6, dataaux.length());
        String textojogo = (""+dataaux + " - "+mList.get(i).nomeEquipa1 + " " + mList.get(i).score1 + " x "+mList.get(i).nomeEquipa2 + " "+ mList.get(i).score2);
        //String textojogo = (""+mList.get(i).data + " - "+mList.get(i).nomeEquipa1 + " x "+mList.get(i).nomeEquipa2);
        viewHolder.myTextView.setText(textojogo);
        //TextView textView = viewHolder.itemView.findViewById(R.id.itemText);
        //textView.setText(item +i);
        if(i%2==0){
            viewHolder.celula.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }
        else{
            viewHolder.celula.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() { return mList.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView myTextView;
        private LinearLayout celula;
        ViewHolder(View itemview) {
            super(itemview);
            myTextView = itemView.findViewById(R.id.jogo);
            celula = itemView.findViewById(R.id.celula2);
        }
    }
}
