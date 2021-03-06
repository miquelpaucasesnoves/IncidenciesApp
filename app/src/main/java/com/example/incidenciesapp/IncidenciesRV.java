package com.example.incidenciesapp;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

        db = new DBInterface(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        llista_incidencis = recuperaLlistatIncidencies();

        IncidenciaAdapter.OnItemClickListener listener = null;
        IncidenciaAdapter incidenciaAdapter = new IncidenciaAdapter(getApplicationContext(),llista_incidencis,listener);
        recyclerView.setAdapter(incidenciaAdapter);

    }

    public ArrayList<Incidencia> recuperaLlistatIncidencies(){
        ArrayList<Incidencia> llista = new ArrayList<Incidencia>();
        db.obre();
        llista = db.getAllIncidencies();
        db.tanca();

        return llista;
    }
}
