package fca.unam.mx.mapaapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ProgressBar;

import fca.unam.mx.mapaapp.graficos.MapaApp;

public class SplashActivity extends MapaApp {

    private static final int PERMISO_CAMARA = 100;

    private ProgressBar barra;
    private boolean permiso_camara;
    private boolean permiso_gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        permiso_camara = false;
        permiso_gps = false;

        barra = (ProgressBar) findViewById(R.id.progressBar);

        verifyPermission();
        Runnable hilo = new Runnable() {
            @Override
            public void run() {
                iniciaAplicacion();
            }
        };
        new Thread(hilo).start();
    }

    private void iniciaAplicacion(){
        int time = 0;

        while(time<3) {
            time++;
            try {
                Thread.sleep(1000);
                barra.setProgress(time * 33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(permisos_validos()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private boolean permisos_validos(){
        return permiso_camara   == true && permiso_gps == true;
    }

    private void verifyPermission()
    {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        } else {
            permiso_camara = true;
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);

        }else{
            permiso_gps = true;
        }
    }
}
