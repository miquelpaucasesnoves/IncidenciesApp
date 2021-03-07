package com.example.incidenciesapp;

import java.io.Serializable;
import java.util.Date;

//Classe POJO per definir un objecte Incidencia
public class Incidencia implements Serializable {

    long incidenciaId;
    String matriculaTrenIncidencia;
    String matriculaTecnicIncidencia;
    String dataIncidencia;
    String descripcioIncidencia;
    String solucioIncidencia;
    byte[] fotoIncidencia;

    public Incidencia(){
        super();
    }

    public void setIncidenciaId(long incidenciaId) {
        this.incidenciaId = incidenciaId;
    }

    public long getIncidenciaId() {
        return incidenciaId;
    }

    public String getTrenIdIncidencia() {
        return matriculaTrenIncidencia;
    }

    public void setTrenIdIncidencia(String trenIdIncidencia) {
        this.matriculaTrenIncidencia = trenIdIncidencia;
    }

    public String getMatriculaTecnicIncidencia() {
        return matriculaTecnicIncidencia;
    }

    public void setMatriculaTecnicIncidencia(String matriculaTecnicIncidencia) {
        this.matriculaTecnicIncidencia = matriculaTecnicIncidencia;
    }

    public String getDataIncidencia() {return dataIncidencia;  }

    public void setDataIncidencia(String dataIncidencia) {
        this.dataIncidencia = dataIncidencia;
    }

    public String getDescripcioIncidencia() {
        return descripcioIncidencia;
    }

    public void setDescripcioIncidencia(String descripcioIncidencia) {
        this.descripcioIncidencia = descripcioIncidencia;
    }

    public String getSolucioIncidencia() {
        return solucioIncidencia;
    }

    public void setSolucioIncidencia(String solucioIncidencia) {
        this.solucioIncidencia = solucioIncidencia;
    }

    public byte[] getFotoIncidencia() {
        return fotoIncidencia;
    }

    public void setFotoIncidencia(byte[] fotoIncidencia) {
        this.fotoIncidencia = fotoIncidencia;
    }
}
