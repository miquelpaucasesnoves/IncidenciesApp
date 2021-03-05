package com.example.incidenciesapp;

public class Tecnic {

    String matricula;
    String nomTecnic;
    String telefonTecnic;

    public Tecnic(String matricula, String nomTecnic) {
        this.matricula = matricula;
        this.nomTecnic = nomTecnic;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNomTecnic() {
        return nomTecnic;
    }

    public void setNomTecnic(String nomTecnic) {
        this.nomTecnic = nomTecnic;
    }

    public String getTelefonTecnic() {
        return telefonTecnic;
    }

    public void setTelefonTecnic(String telefonTecnic) {
        this.telefonTecnic = telefonTecnic;
    }
}
