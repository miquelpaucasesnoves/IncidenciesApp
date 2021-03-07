package com.example.incidenciesapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//Classe per connectar el layout tecnic_layout amb la App
public class TecnicActivity extends AppCompatActivity {

    //Declaració d'objectes JAVA
    EditText matriculaTecnicET;
    EditText nomTecnicET;
    EditText telefonTecnicET;
    Button btnAfegirTecnic,btnEditarTecnic,btnBorrarTecnic;

    DBInterface db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tecnic_layout);

        //Connexio entre els objectes JAVA d'aquesta classe i el layout tecnic_layout
        matriculaTecnicET = (EditText) findViewById(R.id.eTmatriculaTecnicTecnicLayout);
        nomTecnicET = (EditText) findViewById(R.id.eTnomTecnicTecnicLayout);
        telefonTecnicET = (EditText) findViewById(R.id.eTtelefonTecnicTecnicLayout);
        btnAfegirTecnic = (Button) findViewById(R.id.btnAfegirTecnic);
        btnEditarTecnic = (Button) findViewById(R.id.btnEditarTecnic);
        btnBorrarTecnic = (Button) findViewById(R.id.btnBorrarTecnic);

        db = new DBInterface(getApplicationContext());

        //Definició de les instruccions a fer al fer Click al boto btnAfegirTecnic
        btnAfegirTecnic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tecnic tecnic = new Tecnic();
                //Miram que matricula i nom tengui qualque cosa escrita
                // perque estan definits com a NOT NULL a la BBDD
                if((matriculaTecnicET != null || !matriculaTecnicET.getText().toString().isEmpty()) &&
                        ((nomTecnicET!=null || !nomTecnicET.getText().toString().isEmpty()))) {
                    //Assignam els camps del formulari al objecte pont Tecnic
                    tecnic.setMatricula(matriculaTecnicET.getText().toString());
                    tecnic.setNomTecnic(nomTecnicET.getText().toString());
                    tecnic.setTelefonTecnic(telefonTecnicET.getText().toString());
               }
                //Feim la insercció del tecnic a la BBDD
                db.obre();
                db.insereixTecnic(tecnic);
                db.tanca();

                Toast.makeText(getApplicationContext(), "Tecnic Afegit correctament", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        //Definició de les instruccions a fer al fer Click al boto btnEditarTecnic
        //Feim un update d' un tecnic
        btnEditarTecnic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!matriculaTecnicET.getText().toString().isEmpty()){
                    Tecnic tecnic = new Tecnic();
                    db.obre();
                    tecnic = db.getTecnic(matriculaTecnicET.getText().toString());

                    if (matriculaTecnicET.getText().toString() != tecnic.matriculaTecnic) {
                        Toast.makeText(getApplicationContext(), "Matricula de tecnic no valida", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (nomTecnicET.getText().toString() != tecnic.nomTecnic) {
                            tecnic.setNomTecnic(nomTecnicET.getText().toString());
                        }
                        if (telefonTecnicET.getText().toString() != tecnic.telefonTecnic) {
                            tecnic.setTelefonTecnic(telefonTecnicET.getText().toString());
                        }
                        if (!db.editaTecnic(tecnic)) {
                            Toast.makeText(getApplicationContext(), "Error al modificar tecnic", Toast.LENGTH_SHORT).show();
                            db.tanca();
                        } else {
                            Toast.makeText(getApplicationContext(), "Tecnic modificat amb exit", Toast.LENGTH_SHORT).show();
                            db.tanca();
                            finish();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Heu d'introduir matricula del tecnic",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Definició de les instruccions a fer al fer Click al boto btnBorrarTecnic
        //Feim un delete del técnic
        btnBorrarTecnic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tecnic tecnic = new Tecnic();
                db.obre();

                if (!matriculaTecnicET.getText().toString().isEmpty()) {
                    tecnic = db.getTecnic(matriculaTecnicET.getText().toString());
                    if (tecnic.matriculaTecnic != matriculaTecnicET.getText().toString()) {
                        Toast.makeText(getApplicationContext(),"Matricula introduida no valida",Toast.LENGTH_SHORT).show();
                    } else {
                        if (!db.borraTecnic(matriculaTecnicET.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "Error al borrar tecnic", Toast.LENGTH_SHORT).show();
                            db.tanca();

                        } else {
                            Toast.makeText(getApplicationContext(), "Tecnic borrat amb exit", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Introduiu una matricula",Toast.LENGTH_SHORT).show();
                }
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

