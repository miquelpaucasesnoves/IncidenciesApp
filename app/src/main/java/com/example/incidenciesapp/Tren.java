package com.example.incidenciesapp;

//Implementació de la classe Tren POJO a la BBDD
public class Tren {

    String matriculaTren;

    public Tren(){
        super();
    }

    public String getTrenId() {
        return matriculaTren;
    }

    public void setTrenId(String trenId) {
        this.matriculaTren = trenId;
    }

    public Tren(String matricula){
        this.matriculaTren = matricula;
    }
}
