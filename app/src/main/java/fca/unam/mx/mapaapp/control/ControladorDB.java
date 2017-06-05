package fca.unam.mx.mapaapp.control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Compilador on 12/08/2016.
 */
public class ControladorDB extends SQLiteOpenHelper {

    public static final String NAME = "mapa_app.db";
    public static final int VERSION = 1;

    public static final String PRODUCTOS = "CREATE TABLE productos(code varchar, producto varchar, descripcion varchar, primary key(code))";
    public static final String INVENTARIO = "CREATE TABLE inventario(code varchar, longitud double, latitud double, foreign key(code) references productos(code))";

    private Context context;

    public ControladorDB(Context context) {
        super(context, NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("LOG_TAG", "creando base de datos");
        db.execSQL(PRODUCTOS);
        db.execSQL(INVENTARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


    }

    public static void limpiarDB(Context context) {
        ControladorDB dbHelper = new ControladorDB(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM productos;");
        db.execSQL("DELETE FROM inventario;");
    }

    public static void executaQuery(Context context1, String query, String... args){
        ControladorDB dbHelper = new ControladorDB(context1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(query, args);
        db.close();
    }
}
