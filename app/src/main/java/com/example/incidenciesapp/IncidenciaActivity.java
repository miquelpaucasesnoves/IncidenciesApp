package com.example.incidenciesapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IncidenciaActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private int GALLERY_REQUEST_CODE = 1;
    private int APP_PERMISSION_READ_STORAGE = 1;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private ArrayList<Tren> llista_trens;
    private ArrayList<Tecnic> llista_tecnics;

    EditText descripcioIncidenciaET,solucioIncidenciaET;

    EditText editData;

    ImageView fotoIncidencia;
    Bitmap imatge_bitmap;
    byte[] bitmapmap;

    Spinner spinnerTren,spinnerTecnic;
    Button btnAfegirIncidencia,btnAfegeixFoto,btnTorna;

    private Tren tren = null;
    private Tecnic tecnic = null;
    private Incidencia incidencia = null;

    DBInterface bd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incidencia_layout);


        descripcioIncidenciaET = findViewById(R.id.eTdescripcioIncidencia);
        solucioIncidenciaET = findViewById(R.id.etSolucioIncidencia);
        btnAfegirIncidencia = (Button) findViewById(R.id.btnAfegirIncidencia);
        btnAfegeixFoto = (Button) findViewById(R.id.btnAfegirFoto);
        btnTorna = (Button) findViewById(R.id.btnTornaMenuIncidencia);
        fotoIncidencia = (ImageView) findViewById(R.id.imatgeIncidencia);
        spinnerTren = (Spinner) findViewById(R.id.spinnerTrenIncidencia);
        spinnerTecnic = (Spinner) findViewById(R.id.spinnerTecnicIncidencia);

        spinnerTren.setOnItemSelectedListener(this);
        spinnerTecnic.setOnItemSelectedListener(this);
     //   editData.setOnClickListener(this);
        btnAfegeixFoto.setOnClickListener(this);
        btnAfegirIncidencia.setOnClickListener(this);
        btnTorna.setOnClickListener(this);

        bd = new DBInterface(this);

        ferSpinnerTrens();
        ferSpinnerTecnics();


    }

    @Override
    public void onClick(View v) {
        if (v == btnAfegirIncidencia) {
            Incidencia incidencia = generaIncidencia();

            bd.obre();
            bd.insereixIncidencia(incidencia);
            if (incidencia == null) {
                Toast.makeText(getApplicationContext(), "Camps obligatoris per crear una incidencia son: Matricula Tren,Tecnic i descripcio", Toast.LENGTH_SHORT).show();
            } else if (bd.insereixIncidencia(incidencia).getIncidenciaId() != -1) {
                Toast.makeText(this, "Incidencia Afegida correctament", Toast.LENGTH_SHORT).show();
                bd.tanca();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                //    } else {
                Toast.makeText(this, "Error a l’afegir BBDD", Toast.LENGTH_SHORT).show();
                //    }
            } else if (v == btnAfegeixFoto) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, APP_PERMISSION_READ_STORAGE);
                }
                recullDeGaleria();
//        }else if(v == editData){
//            mostraData();
            } else if (v == btnTorna) {
                finish();
            }
        }
    }
    public Incidencia generaIncidencia(){
        if(tren != null && tecnic != null && !descripcioIncidenciaET.getText().toString().isEmpty()){
            incidencia = new Incidencia();
            incidencia.setTrenIdIncidencia(spinnerTren.getSelectedItem().toString());

            Tecnic t = llista_tecnics.get(spinnerTecnic.getSelectedItemPosition()-1);
            incidencia.setMatriculaTecnicIncidencia(t.matriculaTecnic);

            Date avui = Calendar.getInstance().getTime();
            incidencia.setDataIncidencia(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(avui));

            incidencia.setDescripcioIncidencia(descripcioIncidenciaET.getText().toString());
            incidencia.setSolucioIncidencia(solucioIncidenciaET.getText().toString());

            Toast.makeText(getApplicationContext(),incidencia.matriculaTrenIncidencia,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),incidencia.matriculaTecnicIncidencia,Toast.LENGTH_SHORT).show();



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

    private void recullDeGaleria(){
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            //set image to image view
            fotoIncidencia.setImageURI(data.getData());
        }
    }

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

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterTrens = new ArrayAdapter<>(this, R.layout.text_spinner, string_trens);

        // attaching data adapter to spinner
        spinnerTren.setAdapter(dataAdapterTrens);
        spinnerTren.setSelection(0);
    }

    private void ferSpinnerTecnics(){
        bd.obre();
        llista_tecnics = bd.getAllTecnics();
        bd.tanca();
        // Spinner Drop down elements
        List<String> string_tecnics = new ArrayList<String>();
        string_tecnics.add("Selecciona tecnic..");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            for (int i = 0; i<llista_tecnics.size();i++){
                string_tecnics.add(llista_tecnics.get(i).getNomTecnic());
            }
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterTecnics = new ArrayAdapter<>(this, R.layout.text_spinner, string_tecnics);

        // attaching data adapter to spinner
        spinnerTecnic.setAdapter(dataAdapterTecnics);
        spinnerTecnic.setSelection(0);
    }



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

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }
}
