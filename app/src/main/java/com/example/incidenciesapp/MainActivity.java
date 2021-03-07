package com.example.incidenciesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

         btnActivityTren = (Button) findViewById(R.id.btnActivityTren);
         btnActivityTecnic = (Button) findViewById(R.id.btnActivityTecnics);
         btnActivityIncidencia = (Button) findViewById(R.id.btnActivityIncidencia);
         btnActivityLlistatIncidencies = (Button) findViewById(R.id.btnActivityLlistatIncidencies);

        btnActivityTren.setOnClickListener(this);
        btnActivityIncidencia.setOnClickListener(this);
        btnActivityTecnic.setOnClickListener(this);
        btnActivityLlistatIncidencies.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){
            case R.id.btnActivityTren:
                Intent trenIntent = new Intent(this,TrenActivity.class);
                startActivity(trenIntent);
                break;

            case R.id.btnActivityTecnics:
                Intent tecnicIntent = new Intent(this, TecnicActivity.class);
                startActivity(tecnicIntent);
                break;
            case R.id.btnActivityIncidencia:
                Intent incidenciaIntent = new Intent(this,IncidenciaActivity.class);
                startActivity(incidenciaIntent);
                break;
            case R.id.btnActivityLlistatIncidencies:
                Intent llistatIncidencies = new Intent(this,IncidenciesRV.class);
                startActivity(llistatIncidencies);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }
}