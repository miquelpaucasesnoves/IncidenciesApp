package com.example.incidenciesapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
    public static final String CLAU_DESCRIPCIO_INCIDENCIA = "incidencia_descrip";
    public static final String CLAU_SOLUCIO_INCIDENCIA = "incidencia_solucio";
    public static final String CLAU_FOTO_INCIDENCIA = "incidencia_foto";

    public static final String TAG = "DBInterface";
    public static final String NOM_BBDD = "incidenciesDB";


    public static final int VERSIO = 1;
    public static final String CREATE_TAULA_TECNICS = "create table " + TAULA_TECNICS +
            "(" + CLAU_MATRICULA_TECNIC + " INTEGER PRIMARY KEY NOT NULL, " + CLAU_NOM_TECNIC + " TEXT NOT NULL, " +
            CLAU_TELEFON_TECNIC + " TEXT NOT NULL);";

    public static final String CREATE_TAULA_INCIDENCIES = "create table " + TAULA_INCIDENCIES +
            "(" + CLAU_ID_INCIDENCIA + " integer primary key autoincrement, " + CLAU_DESCRIPCIO_INCIDENCIA + " TEXT NOT NULL, " +
            CLAU_SOLUCIO_INCIDENCIA + " TEXT NOT NULL, "+ CLAU_SOLUCIO_INCIDENCIA +" TEXT NOT NULL, " + CLAU_FOTO_INCIDENCIA + "BLOB);";

    public static final String CREATE_TAULA_TRENS = "create table " + TAULA_TRENS +
            "(" + CLAU_MATRICULA_TREN + " INTEGER PRIMARY KEY, " + CLAU_NOM_TECNIC + " TEXT NOT NULL, " +
            CLAU_TELEFON_TECNIC + " TEXT NOT NULL);";


/*

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

    //Per inserir un Contacte
    public long insereixContacte(String nom,String email){
        ContentValues initialValues = new ContentValues();
        initialValues.put(CLAU_NOM,nom);
        initialValues.put(CLAU_EMAIL,email);
        return bd.insert(BD_TAULA,null,initialValues);
    }

    //Per esborrar un Contacte
    public boolean esborraContacte(long IDFila){
        return bd.delete(BD_TAULA,CLAU_ID + "="+IDFila,null)>0;
    }

    //Retorna un Contacte(Consulta)
    public Cursor obtenirContacte(long IDFila) throws SQLException{
        Cursor mCursor = bd.query(true,BD_TAULA,new String[]{CLAU_ID,CLAU_NOM,CLAU_EMAIL},CLAU_ID + "=" + IDFila,null,null,null,null,null);

        if(mCursor !=null) mCursor.moveToFirst();
        return mCursor;
    }

    //Retorna tots els contactes
    public Cursor obtenirTotsContactes(){
        return bd.query(BD_TAULA,new String[]{CLAU_ID,CLAU_NOM,CLAU_EMAIL},null,null,null,null,null);
    }

    //Modifica un contacte
    public boolean actualitzaContacte(long IDFila,String nom, String email){
        ContentValues args = new ContentValues();
        args.put(CLAU_NOM,nom);
        args.put(CLAU_EMAIL,email);
        return bd.update(BD_TAULA,args,CLAU_ID + "="+ IDFila,null) > 0;
    }
*/
}

