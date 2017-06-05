package fca.unam.mx.mapaapp;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.zxing.Result;

import fca.unam.mx.mapaapp.graficos.NavigationActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends NavigationActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ZXingScannerView.ResultHandler {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private boolean mapaActivado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapaActivado = false;
        activaAPI();

        getSupportActionBar().getCustomView().findViewById(R.id.menu_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        getSupportActionBar().getCustomView().findViewById(R.id.barra_encabezado).setVisibility(View.GONE);

        ((ImageButton) findViewById(R.id.boton_inicio)).setImageResource(R.drawable.home2);

        //findViewById(R.id.areabuscador).setVisibility(View.VISIBLE);

        ProductoFragment productoFragment = new ProductoFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, productoFragment)
                .commit();


        findViewById(R.id.boton_inicio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        findViewById(R.id.boton_qr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarScanner();
            }
        });
    }

    protected void activaAPI() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    private void iniciarScanner() {
        initBotones();
        ((ImageButton) findViewById(R.id.boton_qr)).setImageResource(R.drawable.escanear2);
        ScannerFragment pm = new ScannerFragment();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Bundle bundle = new Bundle();
        double latitud = 19.3244795;
        double longitud = -99.1847611;
        try{
            longitud = mLastLocation.getLongitude();
            latitud = mLastLocation.getLatitude();
        }catch (Exception ex){

        }
        bundle.putDouble("longitud", longitud);
        bundle.putDouble("latitud", latitud);
        pm.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, pm)
                .commit();
        ((TextView)getSupportActionBar().getCustomView().findViewById(R.id.barra_texto)).setText("QR");
    }

    private void initBotones(){
        getSupportActionBar().getCustomView().findViewById(R.id.barra_encabezado).setVisibility(View.VISIBLE);
        ((ImageButton)findViewById(R.id.boton_inicio)).setImageResource(R.drawable.home);
        ((ImageButton)findViewById(R.id.boton_qr)).setImageResource(R.drawable.escanear);
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mapaActivado = true;
    //    renderMapa();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result");
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

}
