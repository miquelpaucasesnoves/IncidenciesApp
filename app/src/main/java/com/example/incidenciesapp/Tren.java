package com.example.incidenciesapp;

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
