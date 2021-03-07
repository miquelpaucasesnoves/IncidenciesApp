package com.example.incidenciesapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Declaració de l'adatpador d'Incidencia al recyclerView
public class IncidenciaAdapter extends RecyclerView.Adapter<IncidenciaAdapter.ViewHolder> {

    static ArrayList<Incidencia> llista_incidencies;
    AdapterView.AdapterContextMenuInfo info;
    static Context context;

    //Es crea aquesta Adapter que hereda del Adapter ViewHolder de més abaix
    // perque he volgut implementar un menu Popup per a cada Item
    //on he implementat l'edició o consulta al detall per cada item
    public IncidenciaAdapter(ArrayList<Incidencia> llista_incidencies,Context context) {
        this.llista_incidencies = llista_incidencies;
        this.context = context;

    }

    IncidenciaAdapter(){
    }

    //implementació de l'adaptador del RecyclerView amb l'implementació en aquest dels events
    //OncreateContextMenuListener(menu que surt fent una pitjada llarga damunt un item i ClickListener
    // per trobar la posició on es clica i recuperar l'objecte Incidencia del item clicat
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,View.OnClickListener {

        //Declaració d'objectes Java que ens conectaran amb el layout
        TextView numIncidencia;
        TextView matriculaTrenIncidencia;
        TextView nomTecnicIncidencia;
        TextView dataIncidencia;
        TextView descripcioIncidencia;
        ArrayList<Incidencia> llista_incidencies;
        Integer posicioIncidencia;


        public ViewHolder(View v,ArrayList<Incidencia> llista_incidencies) {
            super(v);
            //Connexió entre els objectes en Java amb els del layout
            this.llista_incidencies = llista_incidencies;
            numIncidencia = (TextView) itemView.findViewById(R.id.num_incidencia_rv);
            matriculaTrenIncidencia = (TextView) itemView.findViewById(R.id.matricula_tren_rv);
            nomTecnicIncidencia = (TextView) itemView.findViewById(R.id.nom_tecnic_rv);
            dataIncidencia = (TextView) itemView.findViewById(R.id.dataIncidenciaRV);
            descripcioIncidencia = (TextView) itemView.findViewById(R.id.descripcio_incidencia_rv);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
        }

        //Definició del mètode quan s'executi l'event d'aparició del menu contextual(PopUp)
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onClick(itemView);

                Incidencia incidencia = llista_incidencies.get(posicioIncidencia-1);

                switch (item.getItemId()) {
                    //Opcio 1 : Editar Incidencia
                    case 1:
                        Intent editIntent = new Intent(context,IncidenciaActivity.class);
                        //passam aquesta valor per saber que venim de IncidenciesRV
                        editIntent.putExtra("Activity Anterior", 000);
                        //passam l'objecte incidencia fent la classe Incidencia serializable
                        editIntent.putExtra("incidenciaPassada",incidencia);
                        context.startActivity(editIntent);
                        break;
                    //Opcio 2 : Consultar Incidencia
                    case 2:
                        //Feim un intent de la classe ConsultaIncidencia
                        //que conté una vista distinta dels camps de Incidencia
                        Intent consultaIntent = new Intent(context,ConsultaIncidencia.class);
                        //Passam l'objecte incidencia per Intent
                        consultaIntent.putExtra("incidenciaPassada",incidencia);
                        context.startActivity(consultaIntent);
                        break;
                }
                return true;
            }
        };

        //Definició dels camps del menu Contextual
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            new IncidenciaAdapter().info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Editar");
            MenuItem Consulta = menu.add(Menu.NONE, 2, 2, "Consultar");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Consulta.setOnMenuItemClickListener(onEditMenu);
        }

        //Per poder fer referencia al objecte on hem pitjat pel menu contextual i poder passar
        //l'objecte en si
        @Override
        public void onClick(View v) {
            posicioIncidencia = Integer.parseInt(numIncidencia.getText().toString());
        }
    }


    public ArrayList<Incidencia> getData() {
        return llista_incidencies;
    }


    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }

    //Assignació del layout per "dibuixar" cada item
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_incidenciarv, parent, false);
        ViewHolder vh = new ViewHolder(view,llista_incidencies);
        return vh;
    }

    //Assignació dels objectes Java als que feim referencia al layout amb
    //les dades del objecte incidencia per cada un de la llista_incidencies
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

       Incidencia incidencia = llista_incidencies.get(position);

        holder.numIncidencia.setText(String.valueOf(incidencia.incidenciaId));
        holder.matriculaTrenIncidencia.setText(incidencia.matriculaTrenIncidencia);
        holder.nomTecnicIncidencia.setText(incidencia.matriculaTecnicIncidencia);
        holder.dataIncidencia.setText(incidencia.dataIncidencia);
        holder.descripcioIncidencia.setText(incidencia.descripcioIncidencia);
    }

    //retorna el tamany de la llista llista_incidencies
    @Override
    public int getItemCount() {
        return llista_incidencies.size();
    }

    private int position;

    public int getPosition() {

        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}