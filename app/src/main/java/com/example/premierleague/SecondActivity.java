package com.example.premierleague;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class SecondActivity extends AppCompatActivity {
    Services2 a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String nomeEquipa = getIntent().getStringExtra("ITEM");
        //RecyclerView recyclerView = findViewById(R.id.recicleAdapter);
        RecyclerView recyclerView2 = findViewById(R.id.recicleAdapter2);
        View aux = new View(this);
         a= new Services2(aux,recyclerView2,nomeEquipa);
        //a.teams();
        a.matches();
    }

    public void teste(View view){
        a.aocontrario();
    }


}
