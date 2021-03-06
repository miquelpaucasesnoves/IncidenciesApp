package com.example.incidenciesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IncidenciaAdapter extends RecyclerView.Adapter<IncidenciaAdapter.IncidenciaViewHolder> {

    private ArrayList<Incidencia> llista_incidencies;
    private Context context;
    private OnItemClickListener listener;

    public IncidenciaAdapter(Context context, ArrayList<Incidencia> llista_incidencies, OnItemClickListener listener) {
        this.llista_incidencies = llista_incidencies;
        this.context = context;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Incidencia incidencia);
    }

    @NonNull
    @Override
    public IncidenciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detall_incidencia_rv, parent, false);
        IncidenciaViewHolder incidenciaVH = new IncidenciaViewHolder(view);
        return incidenciaVH;

    }

    @Override
    public void onBindViewHolder(IncidenciaViewHolder holder, int position) {
        holder.bindIncidencia(llista_incidencies.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return llista_incidencies.size();
    }

    public void eliminaPeli(int position) {
        llista_incidencies.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, llista_incidencies.size());
    }

    public void retornaPeli(Incidencia peli, int position) {
        llista_incidencies.add(position, peli);
        // notify item added by position
        notifyItemInserted(position);
    }


    public static class IncidenciaViewHolder extends RecyclerView.ViewHolder {

        private Context Context;
        TextView numIncidencia;
        TextView matriculaTrenIncidencia;
        TextView nomTecnicIncidencia;
        TextView dataIncidencia;
        TextView descripcioIncidencia;


        public IncidenciaViewHolder(@NonNull View itemView) {

            super(itemView);

           numIncidencia = (TextView) itemView.findViewById(R.id.num_incidencia_rv);
           matriculaTrenIncidencia = (TextView) itemView.findViewById(R.id.matricula_tren_rv);
           nomTecnicIncidencia = (TextView) itemView.findViewById(R.id.nom_tecnic_rv);
           dataIncidencia = (TextView) itemView.findViewById(R.id.data_incidencia_rv);
           descripcioIncidencia = (TextView) itemView.findViewById(R.id.descripcio_incidencia_rv);
        }


        public void bindIncidencia(Incidencia incidencia, OnItemClickListener listener) {
            numIncidencia.setText(incidencia.getIncidenciaId());
            matriculaTrenIncidencia.setText(incidencia.getTrenIdIncidencia());
            nomTecnicIncidencia.setText(incidencia.getMatriculaTecnicIncidencia());
            dataIncidencia.setText(incidencia.getDataIncidencia());
            descripcioIncidencia.setText(incidencia.getDescripcioIncidencia());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
             //       listener.onItemClick(incidencia);
                }
            });

        }

    }
}
