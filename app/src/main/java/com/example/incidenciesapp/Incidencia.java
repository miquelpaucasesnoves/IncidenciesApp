package com.example.incidenciesapp;

import java.util.Date;

public class Incidencia {

    String trenIdIncidencia;
    String matriculaTecnicIncidencia;
    Date dataIncidencia;
    String descripcioIncidencia;
    String solucioIncidencia;
    byte[] fotoIncidencia;

    public Incidencia(String tren,String tecnic,Date data,String descripcio){
        super();
    }

    public String getTrenIdIncidencia() {
        return trenIdIncidencia;
    }

    public void setTrenIdIncidencia(String trenIdIncidencia) {
        this.trenIdIncidencia = trenIdIncidencia;
    }

    public String getMatriculaTecnicIncidencia() {
        return matriculaTecnicIncidencia;
    }

    public void setMatriculaTecnicIncidencia(String matriculaTecnicIncidencia) {
        this.matriculaTecnicIncidencia = matriculaTecnicIncidencia;
    }

    public Date getDataIncidencia() {
        return dataIncidencia;
    }

    public void setDataIncidencia(Date dataIncidencia) {
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
