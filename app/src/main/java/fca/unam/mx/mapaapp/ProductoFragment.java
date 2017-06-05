package fca.unam.mx.mapaapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import fca.unam.mx.mapaapp.entidades.Producto;

/**
 * Created by Compilador on 04/06/2017.
 */

public class ProductoFragment extends Fragment {

    public ProductoFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflate = inflater.inflate(R.layout.fragment_product, container, false);
        ListView lv = (ListView) inflate.findViewById(R.id.lista_productos);
        ArrayAdapter<Producto> adapter = new ArrayAdapter<Producto>(getContext(), android.R.layout.simple_list_item_1, Producto.getProductos(getContext()));
        lv.setAdapter(adapter);
        return inflate;
    }
}
