package fca.unam.mx.mapaapp.graficos;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import fca.unam.mx.mapaapp.InventarioActivity;
import fca.unam.mx.mapaapp.MainActivity;
import fca.unam.mx.mapaapp.R;

/**
 * Created by Compilador on 24/11/2016.
 */

public class NavigationActivity extends MapaApp {

    protected NavigationView navView;
    protected DrawerLayout drawerLayout;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.plantilla_layout);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View childLayout = inflater.inflate(layoutResID,
                (ViewGroup) findViewById(R.id.areaTrabajo));
        getSupportActionBar().setCustomView(R.layout.actionbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        navView = (NavigationView) findViewById(R.id.navview);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.item_inicio:
                                startActivity(new Intent(NavigationActivity.this, MainActivity.class));
                                break;
                            case R.id.item_inventario:
                                startActivity(new Intent(NavigationActivity.this, InventarioActivity.class));
                                break;
                            default:
                                break;
                        }
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean salida = super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                salida = true;
            default:
                salida =  true;
        }
        return salida;
    }
}
