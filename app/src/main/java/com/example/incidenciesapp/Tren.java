package com.example.incidenciesapp;

public class Tren {

    String trenId;

    public Tren(){
        super();
    }

    public String getTrenId() {
        return trenId;
    }

    public void setTrenId(String trenId) {
        this.trenId = trenId;
    }

    public Tren(String matricula){
        this.trenId = matricula;
    }
}
