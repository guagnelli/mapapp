package fca.unam.mx.mapaapp;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fca.unam.mx.mapaapp.entidades.Inventario;
import fca.unam.mx.mapaapp.entidades.Producto;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Compilador on 04/06/2017.
 */

public class ProductoFragment extends Fragment {

    ListView lv = null;
    ArrayList nombre = new ArrayList();
    ArrayList descripcion = new ArrayList();
    ArrayList code = new ArrayList();
    ArrayList longitud = new ArrayList();
    ArrayList latitud = new ArrayList();

    public ProductoFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflate = inflater.inflate(R.layout.fragment_product, container, false);
        lv = (ListView) inflate.findViewById(R.id.lista_productos);
        this.loadList();
        //ArrayAdapter<Producto> adapter = new ArrayAdapter<Producto>(getContext(), android.R.layout.simple_list_item_1, Producto.getProductos(getContext()));
        //lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                TextView textNombre = (TextView) view.findViewById(R.id.amNombre);
                //TextView textDesc = (TextView) view.findViewById(R.id.amDescripcion);
                //TextView textCode = (TextView) view.findViewById(R.id.amCode);
                TextView textLongitud = (TextView) view.findViewById(R.id.amLongitud);
                TextView textLatitud = (TextView) view.findViewById(R.id.amLatitud);

                String lgtd = (String) textLongitud.getText();
                String lat = (String) textLatitud.getText();

                String uriBegin = "geo:" + lat + "," + lgtd;
                String query = lat + "," + lgtd + "(" + textNombre.getText() + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        return inflate;
        //https://maps.google.com/?q=45.424807,-75.699234


    }

    private void loadList(){

        List<Inventario> inventarios = Inventario.getInventario(getActivity());
        for(Inventario inv : inventarios){
            nombre.add(inv.getProduct().getNombre());
            descripcion.add(inv.getProduct().getDescripcion());
            code.add(inv.getProduct().getCode());
            latitud.add(inv.getLatitud());
            longitud.add(inv.getLongitud());
        }
        lv.setAdapter(new PFAdapter(getActivity()));
    }

    private class PFAdapter extends BaseAdapter {
        Context ctx;
        LayoutInflater layoutInflater;
        TextView tvNombre, tvDescripcion, tvCode, tvLongitud, tvLatitud;

        public PFAdapter(Context applicationcontext){
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

            tvNombre.setText("Nombre: "+nombre.get(position).toString());
            tvDescripcion.setText("Descripción: "+descripcion.get(position).toString());
            tvCode.setText("Código: "+code.get(position).toString());
            tvLongitud.setText(longitud.get(position).toString());
            tvLatitud.setText(latitud.get(position).toString());

            return viewGroup;
        }
    }
}
