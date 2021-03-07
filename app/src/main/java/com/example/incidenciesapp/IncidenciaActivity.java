package com.example.incidenciesapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//Classe vinculada a la insercció i modificació d'incidencies
public class IncidenciaActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //Declaració de les constants i variables usades en aquesta classe
    private int APP_PERMISSION_READ_STORAGE = 1;

    private static final int IMAGE_PICK_CODE = 1000;

    private ArrayList<Tren> llista_trens;
    private ArrayList<Tecnic> llista_tecnics;

    EditText descripcioIncidenciaET,solucioIncidenciaET;

    ImageView fotoIncidencia;
    byte[] bitmapmap;

    Spinner spinnerTren,spinnerTecnic;
    Button btnAfegirIncidencia,btnAfegeixFoto,btnTorna;

    private Tren tren = null;
    private Tecnic tecnic = null;
    private Incidencia incidencia = null;

    DBInterface bd;

    Integer activityAnterior;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incidencia_layout);


        //Referencia dels objectes que utilitzarem en aquesta classe als layout incidencia_layout
        descripcioIncidenciaET = findViewById(R.id.descripcioIncidenciaTextViewConsulta);
        solucioIncidenciaET = findViewById(R.id.solucioIncidenciaTextViewConsulta);
        btnAfegirIncidencia = (Button) findViewById(R.id.btnAfegirIncidencia);
        btnAfegeixFoto = (Button) findViewById(R.id.btnAfegirFoto);
        btnTorna = (Button) findViewById(R.id.btnTornaMenuIncidencia);
        fotoIncidencia = (ImageView) findViewById(R.id.imatgeIncidenciaConsulta);
        spinnerTren = (Spinner) findViewById(R.id.spinnerTrenIncidencia);
        spinnerTecnic = (Spinner) findViewById(R.id.spinnerTecnicIncidencia);


        //Definicio dels events tant de botons com d'spinners
        spinnerTren.setOnItemSelectedListener(this);
        spinnerTecnic.setOnItemSelectedListener(this);
        btnAfegeixFoto.setOnClickListener(this);
        btnAfegirIncidencia.setOnClickListener(this);
        btnTorna.setOnClickListener(this);

        bd = new DBInterface(this);

        //Carrega els dos spinners
        ferSpinnerTrens();
        ferSpinnerTecnics();

        //Recupera dades que volem transferir entre Activities
        //En aquest cas un objecte incidencia
        // i un integer per saber de quina Activity veniem per modificar el layout i la funcionalitat
        Intent intent = getIntent();
        Incidencia incidencia = (Incidencia) intent.getSerializableExtra("incidenciaPassada");
        activityAnterior = intent.getIntExtra("Activity Anterior",1);

        //Si venim des de l'Activity de IncidenciesRV
        //Hem de carregar els camps que tenim de la incidencia als diferents camps del formulari
        // segons la seva naturalesa
        if (activityAnterior == 000) {
            spinnerTren.setSelection(((ArrayAdapter<String>)spinnerTren.getAdapter()).getPosition(incidencia.matriculaTrenIncidencia));
            bd.obre();
            Tecnic tecnic = bd.getTecnic(incidencia.matriculaTecnicIncidencia);
            spinnerTecnic.setSelection(((ArrayAdapter<String>)spinnerTecnic.getAdapter()).getPosition(tecnic.nomTecnic));

            descripcioIncidenciaET.setText(incidencia.descripcioIncidencia);
            solucioIncidenciaET.setText(incidencia.solucioIncidencia);

            if (incidencia.fotoIncidencia !=null){
                Bitmap bmp = BitmapFactory.decodeByteArray(incidencia.fotoIncidencia, 0, incidencia.fotoIncidencia.length);
                //   ImageView image = (ImageView) findViewById(R.id.imageView1);
                fotoIncidencia.setImageBitmap(Bitmap.createScaledBitmap(bmp, fotoIncidencia.getWidth(), fotoIncidencia.getHeight(), false));
            }
            btnAfegirIncidencia.setText("Edita canvis");
        }

    }
    //Implementació de les accions dels botons del layout
    @Override
    public void onClick(View v) {
        if (v == btnAfegirIncidencia) {
            if (activityAnterior == 000) { //Venim de l'Activity IncidenciaRV
                //Per tant hem de fer un update i els camps Id i data no les hem de modificar
                Incidencia incidencia = new Incidencia();
                //Tenc aquest Log perque em dona error en aquest punt
                // diu que es null el valor seleccionat
Log.d("spinner tren esta",spinnerTren.getSelectedItem().toString());
                incidencia.setTrenIdIncidencia(spinnerTren.getSelectedItem().toString());
                incidencia.setMatriculaTecnicIncidencia(spinnerTecnic.getSelectedItem().toString());
                incidencia.setDescripcioIncidencia(descripcioIncidenciaET.getText().toString());
                incidencia.setSolucioIncidencia(solucioIncidenciaET.getText().toString());

                if (fotoIncidencia.getDrawable() != null) {
                    Bitmap bitmap = ((BitmapDrawable) fotoIncidencia.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageInByte = baos.toByteArray();

                    incidencia.setFotoIncidencia(bitmapmap);

                    //Un cop tenim l'objecte passariem a realitzar el update
                    bd.obre();
                    if (bd.editaIncidencia(incidencia)){
                        Toast.makeText(getApplicationContext(),"Incidencia actualitzada amb exit",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Error al actualitzar incidencia",Toast.LENGTH_SHORT).show();
                    }

                }
            } else {
                //En aquesta condició significa que venim de la ActivityMain
                //Per tant hem de fer un Insert amb els camps del formulari
                Incidencia incidencia = generaIncidencia();

                bd.obre();
                if (incidencia == null) {
                    Toast.makeText(getApplicationContext(), "Camps obligatoris per crear una incidencia son: Matricula Tren,Tecnic i descripcio", Toast.LENGTH_SHORT).show();
                } else if (bd.insereixIncidencia(incidencia) != -1) {
                    Toast.makeText(this, "Incidencia Afegida correctament", Toast.LENGTH_SHORT).show();
                    bd.tanca();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(this, "Error a l’afegir BBDD", Toast.LENGTH_SHORT).show();
                }
            }
        }
         else if (v == btnAfegeixFoto) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, APP_PERMISSION_READ_STORAGE);
                }
                recullDeGaleria();
            } else if (v == btnTorna) {
                finish();
            }
        }

    //Crea un objecte Incidencia amb els valors del formulari i retorna aquesta objecte
    public Incidencia generaIncidencia(){
        if(tren != null && tecnic != null && !descripcioIncidenciaET.getText().toString().isEmpty()){
            incidencia = new Incidencia();
            incidencia.setTrenIdIncidencia(spinnerTren.getSelectedItem().toString());

            Tecnic t = llista_tecnics.get(spinnerTecnic.getSelectedItemPosition()-1);
            incidencia.setMatriculaTecnicIncidencia(t.matriculaTecnic);

            //Per falta de temps simplific i agaf la data en que es realitza
            // l'insercció de la incidencia a la BBDD
            Date avui = Calendar.getInstance().getTime();
            incidencia.setDataIncidencia(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(avui));

            incidencia.setDescripcioIncidencia(descripcioIncidenciaET.getText().toString());
            incidencia.setSolucioIncidencia(solucioIncidenciaET.getText().toString());
            //Si hem posat foto, la passam a byte[] per poder introduir-la a la BBDD
            if (fotoIncidencia.getDrawable() != null) {
                Bitmap bitmap = ((BitmapDrawable) fotoIncidencia.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();

                incidencia.setFotoIncidencia(bitmapmap);
            }

        }
        return incidencia;
    }

    //Mètode per selecciona una imatge de la galeria de fotos del nostre movil
    private void recullDeGaleria(){
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //Avaluació del resultat de cercar la foto a la galeria de fotos
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            //set image to image view
            fotoIncidencia.setImageURI(data.getData());
        }
    }

    //Mètode per crear el spinner Tren
    private void ferSpinnerTrens(){

        bd.obre();
        llista_trens = bd.getAllTrens();
        bd.tanca();
        // Spinner Drop down elements
        List<String> string_trens = new ArrayList<String>();
        string_trens.add("Selecciona tren..");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            for (int i = 0; i<llista_trens.size();i++){
                string_trens.add(llista_trens.get(i).getTrenId());
            }
        }

        // Cream l'spinner a partir de un Adapter de strings amb el layout text_spinner.xml(Carpeta RES)
        ArrayAdapter<String> dataAdapterTrens = new ArrayAdapter<>(this, R.layout.text_spinner, string_trens);

        //Passam les dades al spinner i el posicionam a la posicio 0
        spinnerTren.setAdapter(dataAdapterTrens);
        spinnerTren.setSelection(0);
    }

    //Mateix proces per Tecnics
    private void ferSpinnerTecnics(){
        bd.obre();
        llista_tecnics = bd.getAllTecnics();
        bd.tanca();
        List<String> string_tecnics = new ArrayList<String>();
        string_tecnics.add("Selecciona tecnic..");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            for (int i = 0; i<llista_tecnics.size();i++){
                string_tecnics.add(llista_tecnics.get(i).getNomTecnic());
            }
        }
        ArrayAdapter<String> dataAdapterTecnics = new ArrayAdapter<>(this, R.layout.text_spinner, string_tecnics);

        spinnerTecnic.setAdapter(dataAdapterTecnics);
        spinnerTecnic.setSelection(0);
    }


    //Assignació dels elements seleccionats a cada spinner
    // a Objecte segons la naturalesa del spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position != 0){
            if(parent.getId() == R.id.spinnerTrenIncidencia) {
                tren = llista_trens.get(position-1);
            } else if(parent.getId() == R.id.spinnerTecnicIncidencia) {
                tecnic = llista_tecnics.get(position-1);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
