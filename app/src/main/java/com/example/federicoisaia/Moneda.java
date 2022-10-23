package com.example.federicoisaia;

public class Moneda {
    private String nombre;
    private Double valorenPesos;

    public Moneda(String nom,Double val){
        super();
        nombre=nom;
        valorenPesos=val;
        ;
    }
    public String toString(){
        return nombre;
    }
}
