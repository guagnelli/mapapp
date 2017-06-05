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

public class Producto {

    public static boolean existe(Context context, String code){
        boolean salida = false;
        ControladorDB dbHelper = new ControladorDB(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT 1 from productos where code = ? "
                , new String[]{code});
        cursor.moveToFirst();
        salida = !cursor.isAfterLast();
        cursor.close();
        database.close();
        dbHelper.close();
        return  salida;
    }

    public static List<Producto> getProductos(Context context){
        List<Producto> lista = new LinkedList<>();
        ControladorDB dbHelper = new ControladorDB(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT code, producto, descripcion from productos order by producto, code"
            ,new String[]{});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Producto p = new Producto(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2));
            lista.add(p);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        database.close();
        dbHelper.close();
        return lista;
    }

    public static Producto getProducto(Context context, String code){
        Producto p = null;
        ControladorDB dbHelper = new ControladorDB(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT code, producto, descripcion from productos where code = ?"
                ,new String[]{code});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            p = new Producto(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2));
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        database.close();
        dbHelper.close();
        return p;
    }

    private String code;
    private String nombre;
    private String descripcion;

    public Producto(String code, String nombre, String descripcion) {
        this.code = code;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre + " [" + code + "]";
    }

    @Override
    public boolean equals(Object obj) {
        boolean salida = false;
        if(obj instanceof Producto){
            Producto tmp = (Producto) obj;
            salida = tmp.code.equals(this.code);
        }
        return salida;
    }
}
