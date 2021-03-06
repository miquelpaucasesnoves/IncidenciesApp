package com.example.incidenciesapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBInterface {

    //Declaracio de constants
    public static final String TAULA_TRENS = "trens";
    public static final String CLAU_MATRICULA_TREN = "tren_id";

    public static final String TAULA_TECNICS = "tecnics";
    public static final String CLAU_MATRICULA_TECNIC = "tecnic_id";
    public static final String CLAU_NOM_TECNIC = "tecnic_nom";
    public static final String CLAU_TELEFON_TECNIC = "tecnic_telefon";

    public static final String TAULA_INCIDENCIES = "incidencies";
    public static final String CLAU_ID_INCIDENCIA = "incidencia_id";
    public static final String CLAU_TREN_INCIDENCIA = "incidencia_tren_id";
    public static final String CLAU_TECNIC_INCIDENCIA = "incidencia_tecnic_id";
    public static final String CLAU_DATA_INCIDENCIA = "incidencia_data";
    public static final String CLAU_DESCRIPCIO_INCIDENCIA = "incidencia_descrip";
    public static final String CLAU_SOLUCIO_INCIDENCIA = "incidencia_solucio";
    public static final String CLAU_FOTO_INCIDENCIA = "incidencia_foto";

    public static final String[] COLUMNES_INCIDENCIES = new String[]{CLAU_ID_INCIDENCIA,
            CLAU_TREN_INCIDENCIA, CLAU_TECNIC_INCIDENCIA,
            CLAU_DESCRIPCIO_INCIDENCIA, CLAU_SOLUCIO_INCIDENCIA, CLAU_FOTO_INCIDENCIA};

    public static final String TAG = "DBInterface";
    public static final String NOM_BBDD = "incidenciesDB";


    public static final int VERSIO = 1;
    public static final String CREATE_TAULA_TECNICS = "create table " + TAULA_TECNICS +
            "(" + CLAU_MATRICULA_TECNIC + " TEXT PRIMARY KEY NOT NULL, " + CLAU_NOM_TECNIC + " TEXT NOT NULL, " +
            CLAU_TELEFON_TECNIC + " TEXT);";

    public static final String CREATE_TAULA_INCIDENCIES = "create table " + TAULA_INCIDENCIES +
            "(" + CLAU_ID_INCIDENCIA + " integer primary key autoincrement, "+ CLAU_TREN_INCIDENCIA  + " TEXT NOT NULL, "
            + CLAU_TECNIC_INCIDENCIA + " TEXT NOT NULL, " + CLAU_DATA_INCIDENCIA + " TEXT NOT NULL, "
            + CLAU_DESCRIPCIO_INCIDENCIA + " TEXT NOT NULL, "
            + CLAU_SOLUCIO_INCIDENCIA + " TEXT, "
            + CLAU_FOTO_INCIDENCIA + "BLOB);";

    public static final String CREATE_TAULA_TRENS = "create table " + TAULA_TRENS +
            "(" + CLAU_MATRICULA_TREN + " TEXT PRIMARY KEY);";



    private  final Context context;
    private AjudaDB ajuda;
    private SQLiteDatabase bd;

    //Constructor, crea un nou objecte AjudaDB
    // que ens servira per donar suport a la BBDD
    public DBInterface(Context con){
        this.context = con;
        ajuda = new AjudaDB(context);
    }

    //Obri la BBDD
    public DBInterface obre() throws SQLException {
        bd = ajuda.getReadableDatabase();
        return this;
    }

    //Tanca la BBDD
    public void tanca(){
        ajuda.close();
    }

    //Per insertar un tren
    public long insereixTren(Tren tren){
        ContentValues initialValues = new ContentValues();
        initialValues.put(CLAU_MATRICULA_TREN,tren.matriculaTren);
        return bd.insert(TAULA_TRENS,null,initialValues);
    }

    //Per insertar un tecnic
    public long insereixTecnic(Tecnic tecnic){
        ContentValues initialValues = new ContentValues();
        initialValues.put(CLAU_MATRICULA_TECNIC,tecnic.matriculaTecnic);
        initialValues.put(CLAU_NOM_TECNIC,tecnic.nomTecnic);
        initialValues.put(CLAU_TELEFON_TECNIC,tecnic.telefonTecnic);
        return bd.insert(TAULA_TECNICS,null,initialValues);
    }

    //Per insertar una incidencia
    public Incidencia insereixIncidencia(Incidencia incidencia){
        ContentValues initialValues = new ContentValues();
        initialValues.put(CLAU_MATRICULA_TECNIC,incidencia.matriculaTecnicIncidencia);
        initialValues.put(CLAU_TREN_INCIDENCIA,incidencia.matriculaTrenIncidencia);
        initialValues.put(CLAU_DATA_INCIDENCIA, String.valueOf(incidencia.getDataIncidencia()));
        initialValues.put(CLAU_DESCRIPCIO_INCIDENCIA,incidencia.descripcioIncidencia);
        long insertId = bd.insert(TAULA_INCIDENCIES, null, initialValues);
        incidencia.setIncidenciaId(insertId);
        bd.insert(TAULA_INCIDENCIES,null,initialValues);
        return incidencia;
    }

    //Per retornar tots els trens
    public ArrayList<Tren> getAllTrens() {
        ArrayList<Tren> trens = new ArrayList<Tren>();
        Cursor cursor = bd.query(TAULA_TRENS, new String[]{CLAU_MATRICULA_TREN}, null, null, null, null, CLAU_MATRICULA_TREN + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Tren tren = cursorToTren(cursor);
            trens.add(tren);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return trens;
    }

    private Tren cursorToTren(Cursor cursor) {
        Tren tren = new Tren();
        tren.setTrenId(cursor.getString(0));

        return tren;
    }

    //Per retornar tots els tecnics
    //En principi nomes vull que ens retorni el nom del tecnic;
    // ja que fare una petita implementacio on l'escenari principal es la taula incidencies
    public ArrayList<Tecnic> getAllTecnics() {
        ArrayList<Tecnic> tecnics = new ArrayList<Tecnic>();
        Cursor cursor = bd.query(TAULA_TECNICS, new String[]{CLAU_NOM_TECNIC}, null, null, null, null, CLAU_MATRICULA_TECNIC + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Tecnic tecnic = cursorToTecnic(cursor);
            tecnics.add(tecnic);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return tecnics;
    }

    private Tecnic cursorToTecnic(Cursor cursor) {
        Tecnic tecnic = new Tecnic();
        tecnic.setMatricula(cursor.getString(0));
        tecnic.setNomTecnic(cursor.getString(1));
        tecnic.setTelefonTecnic(cursor.getString(2));

        return tecnic;
    }

    public ArrayList<Incidencia> getAllIncidencies() {
        ArrayList<Incidencia> incidencies = new ArrayList<Incidencia>();
        Cursor cursor = bd.query(TAULA_INCIDENCIES,COLUMNES_INCIDENCIES , null, null, null, null, CLAU_ID_INCIDENCIA + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Incidencia incidicencia = cursorToIncidencia(cursor);
            incidencies.add(incidicencia);
            cursor.moveToNext();
        }
        // Make sure to close the cursor
        cursor.close();
        return incidencies;
    }

    private Incidencia cursorToIncidencia(Cursor cursor) {
        Incidencia incidencia = new Incidencia();
        incidencia.setTrenIdIncidencia(cursor.getString(0));
        incidencia.setMatriculaTecnicIncidencia(cursor.getString(1));
        incidencia.setDataIncidencia(cursor.getString(2));
        incidencia.setDescripcioIncidencia(cursor.getString(3));
        incidencia.setSolucioIncidencia(cursor.getString(4));
        incidencia.setFotoIncidencia(cursor.getBlob(5));

        return incidencia;
    }
}

