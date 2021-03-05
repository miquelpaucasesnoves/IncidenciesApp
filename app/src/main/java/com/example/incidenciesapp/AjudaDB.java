package com.example.incidenciesapp;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static com.example.incidenciesapp.DBInterface.CREATE_TAULA_INCIDENCIES;
import static com.example.incidenciesapp.DBInterface.CREATE_TAULA_TECNICS;
import static com.example.incidenciesapp.DBInterface.CREATE_TAULA_TRENS;
import static com.example.incidenciesapp.DBInterface.TAULA_INCIDENCIES;
import static com.example.incidenciesapp.DBInterface.TAULA_TECNICS;
import static com.example.incidenciesapp.DBInterface.TAULA_TRENS;
import static com.example.incidenciesapp.DBInterface.NOM_BBDD;
import static com.example.incidenciesapp.DBInterface.VERSIO;

public class AjudaDB extends SQLiteOpenHelper {

    AjudaDB(Context context){
        super(context, NOM_BBDD, null, VERSIO);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
                db.execSQL(CREATE_TAULA_TRENS);
                db.execSQL(CREATE_TAULA_TECNICS);
            db.execSQL(CREATE_TAULA_INCIDENCIES);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TAULA_TRENS);
        db.execSQL("DROP TABLE IF EXISTS "+ TAULA_TECNICS);
        db.execSQL("DROP TABLE IF EXISTS "+ TAULA_INCIDENCIES);
        onCreate(db);
    Log.w(TAG,"Actualitzant Base de Dades de la versió" + oldVersion + " a " + newVersion + ".Destruirà totes les dades");
        onCreate(db);
    }
}
