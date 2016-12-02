package mx.uxie.app.uxie;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CategoriasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    RelativeLayout rl_category_a1,rl_category_a2,rl_category_b1,rl_category_b2,rl_category_c1,rl_category_c2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rl_category_a1 = (RelativeLayout) findViewById(R.id.rl_category_a1);
        rl_category_a2 = (RelativeLayout) findViewById(R.id.rl_category_a2);
        rl_category_b1 = (RelativeLayout) findViewById(R.id.rl_category_b1);
        rl_category_b2 = (RelativeLayout) findViewById(R.id.rl_category_b2);
        rl_category_c1 = (RelativeLayout) findViewById(R.id.rl_category_c1);
        rl_category_c2 = (RelativeLayout) findViewById(R.id.rl_category_c2);

        rl_category_a1.setOnClickListener(this);
        rl_category_a2.setOnClickListener(this);
        rl_category_b1.setOnClickListener(this);
        rl_category_b2.setOnClickListener(this);
        rl_category_c1.setOnClickListener(this);
        rl_category_c2.setOnClickListener(this);

        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("mx.uxie.app.uxie", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, CategoriaActivity.class);

        switch (v.getId()){
            case R.id.rl_category_a1:
                intent.putExtra("categoria_Nombre","Bebidas");
                startActivity(intent);
                break;
            case R.id.rl_category_a2:
                intent.putExtra("categoria_Nombre","Comidas");
                startActivity(intent);
                break;
            case R.id.rl_category_b1:
                intent.putExtra("categoria_Nombre","Salones");
                startActivity(intent);
                break;
            case R.id.rl_category_b2:
                intent.putExtra("categoria_Nombre","Talleres");
                startActivity(intent);
                break;
            case R.id.rl_category_c1:
                intent.putExtra("categoria_Nombre","Nocturno");
                startActivity(intent);
                break;
            case R.id.rl_category_c2:
                intent.putExtra("categoria_Nombre","Consultorios");
                startActivity(intent);
                break;

        }
    }
}
