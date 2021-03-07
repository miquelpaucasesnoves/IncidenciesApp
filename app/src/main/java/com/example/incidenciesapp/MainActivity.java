package com.example.incidenciesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int INCIDENCIA_CODE = 666;

    //Declaració dels objectes JAVA que haurem d'utilitzar en aquesta Classe
    Button btnActivityTren;
    Button btnActivityTecnic;
    Button btnActivityIncidencia;
    Button btnActivityLlistatIncidencies;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connexio entre els objectes JAVA d' aquesta classe amb els del layout activity_main
         btnActivityTren = (Button) findViewById(R.id.btnActivityTren);
         btnActivityTecnic = (Button) findViewById(R.id.btnActivityTecnics);
         btnActivityIncidencia = (Button) findViewById(R.id.btnActivityIncidencia);
         btnActivityLlistatIncidencies = (Button) findViewById(R.id.btnActivityLlistatIncidencies);

         //Dessignació de l'interface de fer click a cada boto
        btnActivityTren.setOnClickListener(this);
        btnActivityIncidencia.setOnClickListener(this);
        btnActivityTecnic.setOnClickListener(this);
        btnActivityLlistatIncidencies.setOnClickListener(this);
    }

    //Definició de les instruccions per a cada Boto
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
                //Per saber que venim del Activity Main
                incidenciaIntent.putExtra("Activity Anterior",INCIDENCIA_CODE);
                startActivity(incidenciaIntent);
                break;
            case R.id.btnActivityLlistatIncidencies:
                Intent llistatIncidencies = new Intent(this,IncidenciesRV.class);
                startActivity(llistatIncidencies);
        }

    }
    //Intent d'implementació del sharedPreferences per guardar la darrera Activity
    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }
}