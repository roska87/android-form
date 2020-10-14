package mx.unam.formapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DataDetail extends AppCompatActivity {

    private TextView txEdiNombre;
    private TextView txEdiFecha;
    private TextView txEdiTelefono;
    private TextView txEdiEmail;
    private TextView txEdiDescripcion;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        //Recuperar parametros
        Bundle parametros = getIntent().getExtras();
        //
        String nombre  = parametros.getString(getResources().getString(R.string.name));
        String fecha   = parametros.getString(getResources().getString(R.string.date_selected));
        String telefono= parametros.getString(getResources().getString(R.string.phone));
        String email   = parametros.getString(getResources().getString(R.string.email));
        String descripcion   = parametros.getString(getResources().getString(R.string.contact_description));
        //Tomar referencia de los objetos
        txEdiNombre       = (TextView) findViewById(R.id.tieNombre2);
        txEdiFecha        = (TextView) findViewById(R.id.tieFecha);
        txEdiTelefono     = (TextView) findViewById(R.id.tieTelefono);
        txEdiEmail        = (TextView) findViewById(R.id.tieEmail);
        txEdiDescripcion  = (TextView) findViewById(R.id.tieDescripcion);
        //Asignar valores
        txEdiNombre.setText(nombre);
        txEdiFecha.setText(fecha);
        txEdiTelefono.setText(telefono);
        txEdiEmail.setText(email);
        txEdiDescripcion.setText(descripcion);
        //
        Button send = (Button) findViewById(R.id.btnEditar);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarDatos();
            }
        });
    }

    public void editarDatos(){
        // Crear Objeto Bundle
        Bundle bunParam = new Bundle();
        bunParam.putString(getResources().getString(R.string.name),txEdiNombre.getText().toString());
        bunParam.putString(getResources().getString(R.string.date_selected),txEdiFecha.getText().toString());
        bunParam.putString(getResources().getString(R.string.phone),txEdiTelefono.getText().toString());
        bunParam.putString(getResources().getString(R.string.email),txEdiEmail.getText().toString());
        bunParam.putString(getResources().getString(R.string.contact_description),txEdiDescripcion.getText().toString());
        //instanciar Intent
        Intent intent = new Intent( DataDetail.this, MainActivity.class);
        //Pasar los parametros al intent
        intent.putExtras(bunParam);
        //iniciar la actividad
        startActivity(intent);
        //Finalizar actividad anterior
        finish();
    }

}
