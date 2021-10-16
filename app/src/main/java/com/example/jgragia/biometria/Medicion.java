package com.example.jgragia.biometria;

/*
// Nombre fichero: Medicion.java
// Fecha: 16/10/2021
// Autor: Jorge Grau Giannakakis
// Descripción: Objeto medicion
*/

public class Medicion {

    private double valor;
    private double latitud;
    private double longitud;
    private String fecha;

    public double getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(int latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Medicion(double valor, double latitud, double longitud, String fecha) {
        this.valor = valor;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fecha = fecha;
    }
}