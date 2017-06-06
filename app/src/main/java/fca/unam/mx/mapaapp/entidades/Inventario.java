package fca.unam.mx.mapaapp.entidades;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import fca.unam.mx.mapaapp.control.ControladorDB;

/**
 * Created by Compilador on 04/06/2017.
 */

public class Inventario {

    public static List<Inventario> getInventario(Context context){
        LinkedList<Inventario> lista = new LinkedList<>();
        ControladorDB dbHelper = new ControladorDB(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT A.code, producto, descripcion, latitud, longitud from productos A inner join inventario B on B.code"
                ,new String[]{});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Producto p = new Producto(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2));
            Inventario tmp = new Inventario(p, cursor.getString(3), cursor.getString(4));
            lista.add(tmp);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        database.close();
        dbHelper.close();
        return lista;
    }

    private Producto producto;
    private String latitud;
    private String longitud;

    public Inventario(Producto producto, String latitud, String longitud) {
        this.producto = producto;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "producto=" + producto +
                ", latitud='" + latitud + '\'' +
                ", longitud='" + longitud + '\'' +
                '}';
    }

    public Producto getProduct(){
        return this.producto;
    }

    public String getLatitud(){
        return this.latitud;
    }

    public String getLongitud(){
        return this.longitud;
    }
}
