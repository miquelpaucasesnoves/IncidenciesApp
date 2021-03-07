package com.example.incidenciesapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Classe JAVA que conté el recyclerView
public class IncidenciesRV extends AppCompatActivity {

    //Declaració de les variables necessaries a aquesta classe
    ArrayList<Incidencia> llista_incidencis = new ArrayList<Incidencia>();
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    DBInterface db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_llistat_incidencies);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        db = new DBInterface(this);
        //recuperam el llistat dels objectes Incidencia
        llista_incidencis = recuperaLlistatIncidencies();
        //Definim la direcció del recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Construccio de l'adapter Incidencia basat en la llista d'incidencies
        mAdapter = new IncidenciaAdapter(llista_incidencis,this);
        //Assignam l'adapter al recyclerView perque aquest es dissenyi
        recyclerView.setAdapter(mAdapter);

    }
    //recuperam el llistat dels objectes Incidencia
     public ArrayList<Incidencia> recuperaLlistatIncidencies(){
        ArrayList<Incidencia> llista = new ArrayList<Incidencia>();
        db.obre();
        llista = db.getAllIncidencies();
        db.tanca();

        return llista;
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
