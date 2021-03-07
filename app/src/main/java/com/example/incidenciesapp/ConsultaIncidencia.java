package com.example.incidenciesapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ConsultaIncidencia extends AppCompatActivity {

    //Declaració dels objectes java per fer referencia al layout
    TextView numIncidenciaTV,trenIncidenciaTV,tecnicIncidenciaTV,dataIncidenciaTV,descripcioIncidenciaTV,
    solucioIncidenciaTV;
    ImageView imageViewIncidencia;
    Button btnTornaConsulta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta_incidencia);

        //Relacionam els objectes java amb els objtes del layout consulta_incidencia
        numIncidenciaTV = (TextView) findViewById(R.id.incidenciaIdTextViewConsulta);
        trenIncidenciaTV = (TextView) findViewById(R.id.trenIdTextViewConsulta);
        tecnicIncidenciaTV = (TextView) findViewById(R.id.tecnicIdTextViewConsulta);
        dataIncidenciaTV = (TextView) findViewById(R.id.dataIncidenciaTextViewConsulta);
        descripcioIncidenciaTV = (TextView) findViewById(R.id.descripcioIncidenciaTextViewConsulta);
        solucioIncidenciaTV = (TextView) findViewById(R.id.solucioIncidenciaTextViewConsulta);
        imageViewIncidencia = (ImageView) findViewById(R.id.imatgeIncidenciaConsulta);
        btnTornaConsulta = (Button) findViewById(R.id.btnTornaConsulta);

        //Definim l'event del boto al esser pitjat
        btnTornaConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Recuperam intercanvi de dades entre Activities
        Intent intent = getIntent();
        Incidencia incidencia = (Incidencia) intent.getSerializableExtra("incidenciaPassada");

        //Assignam les daddes recuperades als diferents camps del layout
        numIncidenciaTV.setText(String.valueOf(incidencia.incidenciaId));
        trenIncidenciaTV.setText(incidencia.matriculaTrenIncidencia);
        tecnicIncidenciaTV.setText(incidencia.matriculaTecnicIncidencia);
        dataIncidenciaTV.setText(incidencia.dataIncidencia);
        descripcioIncidenciaTV.setText(incidencia.descripcioIncidencia);
        solucioIncidenciaTV.setText(incidencia.solucioIncidencia);

        if (incidencia.fotoIncidencia != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(incidencia.fotoIncidencia, 0, incidencia.fotoIncidencia.length);
            imageViewIncidencia.setImageBitmap(Bitmap.createScaledBitmap(bmp, imageViewIncidencia.getWidth(), imageViewIncidencia.getHeight(), false));
        }
    }
}
