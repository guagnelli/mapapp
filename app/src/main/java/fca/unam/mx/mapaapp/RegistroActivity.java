package fca.unam.mx.mapaapp;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import fca.unam.mx.mapaapp.control.ControladorDB;
import fca.unam.mx.mapaapp.entidades.Producto;
import fca.unam.mx.mapaapp.graficos.NavigationActivity;

public class RegistroActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Bundle extras = getIntent().getExtras();
        final String code = extras.getString("code");
        TextView tv = (TextView) findViewById(R.id.text_code);
        tv.setText(code);

        findViewById(R.id.boton_guardar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save(code);
            }
        });
    }

    private void save(String code){
        String nombre = ((EditText)(findViewById(R.id.nombre_producto))).getText().toString();
        String descripcion = ((EditText)(findViewById(R.id.descripcion_producto))).getText().toString();
        ControladorDB.executaQuery(this, "INSERT INTO productos(code, producto, descripcion) VALUES (?,?,?) ;", code, nombre, descripcion);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Almacenado con éxito");
        builder.setMessage("Registro de " + Producto.getProducto(this, code) + " finalizado");
        AlertDialog alert1 = builder.create();
        alert1.show();
    }
}
