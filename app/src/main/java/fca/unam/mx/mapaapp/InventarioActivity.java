package fca.unam.mx.mapaapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import fca.unam.mx.mapaapp.entidades.Inventario;
import fca.unam.mx.mapaapp.graficos.NavigationActivity;

public class InventarioActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        ListView lv = (ListView) findViewById(R.id.inventario);
        ArrayAdapter<Inventario> adapter = new ArrayAdapter<Inventario>(this, android.R.layout.simple_list_item_1, Inventario.getInventario(this));
        lv.setAdapter(adapter);
    }
}
