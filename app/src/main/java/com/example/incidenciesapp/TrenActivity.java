package com.example.incidenciesapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TrenActivity extends AppCompatActivity {

    EditText matriculaET;
    Button btnAfegirTren;
    DBInterface db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tren_layout);

        matriculaET = (EditText) findViewById(R.id.matrTrenEtTrenLayout);
        btnAfegirTren = (Button) findViewById(R.id.btnAfegirTren);

        btnAfegirTren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tren tren = null;
                    if(matriculaET != null || matriculaET.getText().toString().isEmpty()) {
                        tren = new Tren();
                        tren.setTrenId(matriculaET.getText().toString());
                    }
                db = new DBInterface(getApplicationContext());
                    db.obre();
                    db.insereixTren(tren);
                    db.tanca();

                    Toast.makeText(getApplicationContext(), "Tren Afegit correctament", Toast.LENGTH_SHORT).show();
                    finish();
            }
        });
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
