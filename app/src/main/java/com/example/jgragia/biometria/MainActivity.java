package com.example.jgragia.biometria;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
// Nombre fichero: MainActivity.java
// Fecha: 16/10/2021
// Autor: Jorge Grau Giannakakis
// Descripción: Es la actividad principal de la aplicación
*/

public class MainActivity extends AppCompatActivity implements LocationListener {

    private static MainActivity instancia;
    private TextView ultimaMedidaTexto;
    private Button arrancar;
    private Button detener;


    protected LocationManager locationManager;

    private static double latitud;
    private static double longitud;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ultimaMedidaTexto = findViewById(R.id.ultimaMedicion);

        detener = findViewById(R.id.detener);
        detener.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                detenerServicio();
            }
        });
        detener.setBackgroundColor(0xFFE60505);

        arrancar = findViewById(R.id.arrancar);
        arrancar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                arrancarServicio();
            }
        });
        arrancar.setClickable(false);
        arrancar.setBackgroundColor(0xFF1F7004);

        startService(new Intent(MainActivity.this, Servicio.class));
        instancia = this;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    public static MainActivity getInstance() {
        return instancia;
    }
    public static double getLatitud() {
        return latitud;
    }
    public static double getLongitud() {
        return longitud;
    }

    public void actualizarUltimaMedicion(Medicion medicion){
        ultimaMedidaTexto.setText(Double.toString(medicion.getValor()));
    }

    public void detenerServicio(){
        stopService(new Intent(MainActivity.this, Servicio.class));
        arrancar.setClickable(true);
        detener.setClickable(false);
        arrancar.setBackgroundColor(0xFF42ED09);
        detener.setBackgroundColor(0xFF700404);
    }

    public void arrancarServicio(){
        startService(new Intent(MainActivity.this, Servicio.class));
        arrancar.setClickable(false);
        detener.setClickable(true);
        arrancar.setBackgroundColor(0xFF1F7004);
        detener.setBackgroundColor(0xFFE60505);
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.e("Test","Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        latitud = location.getLatitude();
        longitud = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

}