package com.example.incidenciesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TecnicActivity extends AppCompatActivity {

    EditText matriculaTecnicET;
    EditText nomTecnicET;
    EditText telefonTecnicET;
    Button btnAfegirTecnic;

    DBInterface db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tecnic_layout);

        matriculaTecnicET = (EditText) findViewById(R.id.eTmatriculaTecnicTecnicLayout);
        nomTecnicET = (EditText) findViewById(R.id.eTnomTecnicTecnicLayout);
        telefonTecnicET = (EditText) findViewById(R.id.eTnomTecnicTecnicLayout);
        btnAfegirTecnic = (Button) findViewById(R.id.btnAfegirTecnic);

        btnAfegirTecnic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tecnic tecnic = null;
                if((matriculaTecnicET != null || !matriculaTecnicET.getText().toString().isEmpty()) &&
                        ((nomTecnicET!=null || nomTecnicET.getText().toString().isEmpty()))) {

                    tecnic.setMatricula(matriculaTecnicET.getText().toString());
                    tecnic.setNomTecnic(nomTecnicET.getText().toString());
                    tecnic.setTelefonTecnic(telefonTecnicET.getText().toString());
                }
                db = new DBInterface(getApplicationContext());
                db.obre();
                db.insereixTecnic(tecnic);
                db.tanca();

                Toast.makeText(getApplicationContext(), "Tecnic Afegit correctament", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    }

