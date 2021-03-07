package com.example.incidenciesapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//Classe d'interrelació entre la classe Tren,el layout tren_layout i la BBDD
public class TrenActivity extends AppCompatActivity {

    //Declaració dels objectes Java que haurem d'utilitzar en aquesta classe
    EditText matriculaET;
    Button btnAfegirTren;
    DBInterface db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tren_layout);

        //Assignació entre els objectes Java i els objectes del layout tren_layout
        matriculaET = (EditText) findViewById(R.id.matrTrenEtTrenLayout);
        btnAfegirTren = (Button) findViewById(R.id.btnAfegirTren);

        //Definició de les instruccions que volem que fassi al clicar damunt btnAfegirTren
        //Un insert d' un tren
        btnAfegirTren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tren tren = null;
                //El camp matricula ET no pot ser ni null ni estar en blanc
                //la columna TrenId es clau primaria i no pot ser Nula
                    if(matriculaET != null || matriculaET.getText().toString().isEmpty()) {
                        tren = new Tren();
                        tren.setTrenId(matriculaET.getText().toString());
                    }
                //Feim la insercció del tren a la BBDD
                db = new DBInterface(getApplicationContext());
                    db.obre();
                    db.insereixTren(tren);
                    db.tanca();

                    Toast.makeText(getApplicationContext(), "Tren Afegit correctament", Toast.LENGTH_SHORT).show();
                    finish();
            }
        });
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
