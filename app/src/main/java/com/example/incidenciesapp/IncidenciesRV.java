package com.example.incidenciesapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IncidenciesRV extends AppCompatActivity {

    ArrayList<Incidencia> llista_incidencis = new ArrayList<Incidencia>();
    RecyclerView recyclerView;
    DBInterface db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_llistat_incidencies);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        db = new DBInterface(this);
        llista_incidencis = recuperaLlistatIncidencies();
        //IncidenciaAdapter.OnItemClickListener listener = null;

        IncidenciaAdapter incidenciaAdapter = new IncidenciaAdapter(getApplicationContext(),llista_incidencis);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(incidenciaAdapter);

    }

    public ArrayList<Incidencia> recuperaLlistatIncidencies(){
        ArrayList<Incidencia> llista = new ArrayList<Incidencia>();
        db.obre();
        llista = db.getAllIncidencies();
        db.tanca();

        return llista;
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
