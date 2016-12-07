package mx.uxie.app.uxie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;

public class AcountMenuActivity extends AppCompatActivity {

    Button btn_sesion;
    TextView tv_Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_acount_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_sesion = (Button) findViewById(R.id.btn_sesion);
        //tv_Register = (TextView) findViewById(R.id.tv_Register);
        btn_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcountMenuActivity.this, CategoriasActivity.class);
                intent.putExtra("categoria_Nombre", "Bebidas");
                startActivity(intent);
            }
        });
        /*
        tv_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcountMenuActivity.this, CrearCuentaActivity.class);
                intent.putExtra("categoria_Nombre", "Bebidas");
                startActivity(intent);
            }
        });
        */

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
