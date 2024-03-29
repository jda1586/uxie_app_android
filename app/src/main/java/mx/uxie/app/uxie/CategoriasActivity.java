package mx.uxie.app.uxie;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import mx.uxie.app.uxie.R;


public class CategoriasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    RelativeLayout rl_category_a1,rl_category_a2,rl_category_b1,rl_category_b2,rl_category_c1,rl_category_c2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StatusBar(getResources().getColor(R.color.status));

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

    public void StatusBar(int Color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color);
        }
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
            Intent intent = new Intent(this, AcountMenuActivity.class);
            intent.putExtra("categoria_Nombre", "Bebidas");
            startActivity(intent);
            // Handle the camera action
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
                intent.putExtra("categoria_Nombre","Automotriz");
                startActivity(intent);
                break;
            case R.id.rl_category_a2:
                intent.putExtra("categoria_Nombre","Salud");
                startActivity(intent);
                break;
            case R.id.rl_category_b1:
                intent.putExtra("categoria_Nombre","Belleza");
                startActivity(intent);
                break;
            case R.id.rl_category_b2:
                intent.putExtra("categoria_Nombre","Comida");
                startActivity(intent);
                break;
            case R.id.rl_category_c1:
                intent.putExtra("categoria_Nombre","Entretenimiento");
                startActivity(intent);
                break;
            case R.id.rl_category_c2:
                intent.putExtra("categoria_Nombre","Varios");
                startActivity(intent);
                break;

        }
    }
}
