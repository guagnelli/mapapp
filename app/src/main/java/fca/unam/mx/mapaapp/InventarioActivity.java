package fca.unam.mx.mapaapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fca.unam.mx.mapaapp.entidades.Inventario;
import fca.unam.mx.mapaapp.entidades.Producto;
import fca.unam.mx.mapaapp.graficos.NavigationActivity;

public class InventarioActivity extends NavigationActivity {

    ArrayList nombre = new ArrayList();
    ArrayList descripcion = new ArrayList();
    ArrayList code = new ArrayList();
    ArrayList longitud = new ArrayList();
    ArrayList latitud = new ArrayList();
    ListView lv = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        lv = (ListView) findViewById(R.id.inventario);
        this.loadList();
        //ArrayAdapter<Inventario> adapter = new ArrayAdapter<Inventario>(this, android.R.layout.simple_list_item_1, Inventario.getInventario(this));
        //lv.setAdapter(adapter);




    }

    private void loadList(){

        List<Inventario> inventarios = Inventario.getInventario(this);
        for(Inventario inv : inventarios){
            nombre.add(inv.getProduct().getNombre());
            descripcion.add(inv.getProduct().getDescripcion());
            code.add(inv.getProduct().getCode());
            latitud.add(inv.getLatitud());
            longitud.add(inv.getLongitud());
        }
        lv.setAdapter(new InventarioAdapter(getApplicationContext()));
    }

    private class InventarioAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater layoutInflater;
        TextView tvNombre, tvDescripcion, tvCode, tvLongitud, tvLatitud;

        public InventarioAdapter(Context applicationcontext){
            this.ctx = applicationcontext;
            this.layoutInflater = (LayoutInflater) this.ctx.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return nombre.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewGroup viewGroup=(ViewGroup)layoutInflater.inflate(R.layout.activita_main_item,null);

            tvNombre = (TextView)viewGroup.findViewById(R.id.amNombre);
            tvDescripcion = (TextView)viewGroup.findViewById(R.id.amDescripcion);
            tvCode = (TextView)viewGroup.findViewById(R.id.amCode);
            tvLongitud = (TextView)viewGroup.findViewById(R.id.amLongitud);
            tvLatitud = (TextView)viewGroup.findViewById(R.id.amLatitud);

            tvNombre.setText(nombre.get(position).toString());
            tvDescripcion.setText(descripcion.get(position).toString());
            tvCode.setText(code.get(position).toString());
            tvLongitud.setText(longitud.get(position).toString());
            tvLatitud.setText(latitud.get(position).toString());

            return viewGroup;
        }
    }
}
