package com.example.jgragia.biometria;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/*
// Nombre fichero: Servicio.java
// Fecha: 16/10/2021
// Autor: Jorge Grau Giannakakis
// Descripción: Servicio en segundo plano
*/

public class Servicio extends Service {

    private NotificationManager notificationManager;
    static final String CANAL_ID = "mi_canal";
    static final int NOTIFICACION_ID = 1;
    Logica logica = new Logica();

    @Override public void onCreate() {
        Toast.makeText(this,"Servicio arrancado", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CANAL_ID, "Mis Notificaciones",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripcion del canal");
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificacion =
                new NotificationCompat.Builder(this, CANAL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Servicio en segundo plano")
                        .setContentText("El servicio esta activo");
        PendingIntent intencionPendiente = PendingIntent.getActivity(
                this, 0, new Intent(this, MainActivity.class), 0);
        notificacion.setContentIntent(intencionPendiente);
        notificationManager.notify(NOTIFICACION_ID, notificacion.build());

        ejecutarTarea();
        return START_STICKY;
    }

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private double valor;

    private final int TIEMPO = 5000;

    Handler handler = new Handler();

    public void ejecutarTarea() {
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");

        logica.iniciarLogica();

        handler.postDelayed(new Runnable() {
            public void run() {
                calendar = Calendar.getInstance();
                dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = dateFormat.format(calendar.getTime());

                // Por aqui recibiria un dato
                valor = new Random().nextInt(61);
                Medicion medicion = new Medicion(valor, MainActivity.getLatitud(), MainActivity.getLongitud(), date);
                // función a ejecutar


                logica.insertarMedicion(medicion); // función para refrescar la ubicación del conductor, creada en otra línea de código
                logica.mostrarMedicion(medicion);
                handler.postDelayed(this, TIEMPO);
            }

        }, TIEMPO);

    }

    @Override public void onDestroy() {
        Toast.makeText(this,"Servicio detenido",
                Toast.LENGTH_SHORT).show();
        handler.removeCallbacksAndMessages(null);
        notificationManager.cancel(NOTIFICACION_ID);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}