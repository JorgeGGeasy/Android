package com.example.jgragia.biometria;

import android.Manifest;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/*
// Nombre fichero: Logica.java
// Fecha: 16/10/2021
// Autor: Jorge Grau Giannakakis
// Descripción: Aqui se hacen los envios a la base de datos
*/

public class Logica {

    RequestQueue requestQueue;
    private static final String URL = "http://192.168.1.100/database_biometria/insertarMedicion.php";

    // Crea la requestQueue al arrancar el servicio
    // -> iniciarLogica() ->
    public void iniciarLogica(){
        requestQueue = Volley.newRequestQueue(MainActivity.getInstance());
    }

    // Añade la medicion
    // medicion : Medicion -> insertarMedicion() ->
    // @params Ultima medicion recogida por este dispositivo
    public void insertarMedicion(final Medicion medicion){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Test", "Conseguido");
                        Toast.makeText(MainActivity.getInstance(), "Subida correcta", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Test", "Fallado");
                        Toast.makeText(MainActivity.getInstance(), "Subida incorrecta", Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("valor", Double.toString(medicion.getValor()));
                params.put("latitud", Double.toString(medicion.getLatitud()));
                params.put("longitud", Double.toString(medicion.getLongitud()));
                params.put("fecha", medicion.getFecha());

                return params;
            }
        };

        requestQueue.add(stringRequest);
        Log.e("Test", "Enviado");
    }

    // Llama a la funcion de MainActivity
    // medicion : Medicion -> mostrarMedicion() ->
    // @params Ultima medicion recogida por este dispositivo
    public void mostrarMedicion(final Medicion medicion){
        MainActivity.getInstance().actualizarUltimaMedicion(medicion);
    }
}

