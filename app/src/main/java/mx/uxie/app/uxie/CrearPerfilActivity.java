package mx.uxie.app.uxie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CrearPerfilActivity extends AppCompatActivity {

    Button btn_continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_continuar = (Button) findViewById(R.id.btn_continuar);

        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrearPerfilActivity.this, CategoriasActivity.class);
                intent.putExtra("categoria_Nombre", "Bebidas");
                startActivity(intent);
            }
        });

    }

}
