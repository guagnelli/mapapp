package fca.unam.mx.mapaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;

import fca.unam.mx.mapaapp.control.ControladorDB;
import fca.unam.mx.mapaapp.entidades.Producto;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Compilador on 25/11/2016.
 */

public class ScannerFragment extends Fragment implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    private View area_qr;
    private View area_escaner;

    public ScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflate = inflater.inflate(R.layout.fragment_scanner, container, false);

        area_qr = inflate.findViewById(R.id.area_qr);
        area_escaner = inflate.findViewById(R.id.area_scanner);

        inflate.findViewById(R.id.boton_scanner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                area_qr.setVisibility(View.GONE);
                area_escaner.setVisibility(View.VISIBLE);
                mScannerView = (ZXingScannerView) inflate.findViewById(R.id.scan_qr);
                mScannerView.setResultHandler(ScannerFragment.this); // Register ourselves as a handler for scan results.<br />
                mScannerView.startCamera();
            }
        });

        return inflate;
    }

    @Override
    public void handleResult(Result result) {
     /*   AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Result");
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();*/
        double longitud = getArguments().getDouble("longitud");
        double latitud = getArguments().getDouble("latitud");
        String code =result.getText();
        mScannerView.stopCamera();
        if(Producto.existe(getContext(), code)){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Producto ya registrado");
            builder.setMessage(Producto.getProducto(getContext(), code).toString());
            AlertDialog alert1 = builder.create();
            alert1.show();
            area_qr.setVisibility(View.VISIBLE);
            area_escaner.setVisibility(View.GONE);
        }else {
            Bundle b = new Bundle();
            b.putString("code", code);
            Intent intent = new Intent(getContext(), RegistroActivity.class);
            intent.putExtras(b);
            startActivity(intent);
        }
        ControladorDB.executaQuery(getContext(), "INSERT INTO inventario(code, longitud, latitud) VALUES (?,?,?) ;", code, String.valueOf(longitud), String.valueOf(latitud));
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            mScannerView.stopCamera();
        }catch (Exception ex){

        }
    }
}
