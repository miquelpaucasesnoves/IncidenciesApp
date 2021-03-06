package com.example.incidenciesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnActivityTren;
    Button btnActivityTecnic;
    Button btnActivityIncidencia;
    Button btnActivityLlistatIncidencies;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActivityTren.setOnClickListener(this);
        btnActivityIncidencia.setOnClickListener(this);
        btnActivityTecnic.setOnClickListener(this);
        btnActivityLlistatIncidencies.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnAfegirTren:
                Intent trenIntent = new Intent(this,Tren.class);
                startActivity(trenIntent);
                break;

            case R.id.btnAfegirTecnic:
                Intent tecnicIntent = new Intent(this, Tecnic.class);
                startActivity(tecnicIntent);
                break;
            case R.id.btnAfegirIncidencia:
                Intent incidenciaIntent = new Intent(this,Incidencia.class);
                startActivity(incidenciaIntent);
                break;
            case R.id.btnActivityLlistatIncidencies:
                Intent llistatIncidencies = new Intent(this,)

        }

    }
}