package mx.unam.formapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class DatosContacto extends AppCompatActivity {

    private TextView etNombre, etTelefono, etFecha, etEmail, etDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_contacto);

        // Get the Intent that started this activity and extract the string
        Bundle parametros = getIntent().getExtras();

        String nombre  = parametros.getString(getResources().getString(R.string.nombre));
        String nacimiento   = parametros.getString(getResources().getString(R.string.fecha_nacimiento));
        String telefono= parametros.getString(getResources().getString(R.string.telefono));
        String mail   = parametros.getString(getResources().getString(R.string.email));
        String descripcion   = parametros.getString(getResources().getString(R.string.descripcion_contacto));

        etNombre = (TextView) findViewById(R.id.tvNombreContacto2);
        etFecha = (TextView) findViewById(R.id.tvNacimiento2);
        etTelefono = (TextView) findViewById(R.id.tvTelefono2);
        etEmail = (TextView) findViewById(R.id.tvEmail2);
        etDescripcion = (TextView) findViewById(R.id.tvDescripcion2);

        etNombre.setText(nombre);
        etFecha.setText(nacimiento);
        etTelefono.setText(telefono);
        etEmail.setText(mail);
        etDescripcion.setText(descripcion);

        Button send = (Button) findViewById(R.id.btnEditarDatos);
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
        bunParam.putString(getResources().getString(R.string.nombre),etNombre.getText().toString());
        bunParam.putString(getResources().getString(R.string.fecha_nacimiento),etFecha.getText().toString());
        bunParam.putString(getResources().getString(R.string.telefono),etTelefono.getText().toString());
        bunParam.putString(getResources().getString(R.string.email),etEmail.getText().toString());
        bunParam.putString(getResources().getString(R.string.descripcion_contacto),etDescripcion.getText().toString());
        //instanciar Intent
        Intent intent = new Intent( DatosContacto.this, MainActivity.class);
        //Pasar los parametros al intent
        intent.putExtras(bunParam);
        //iniciar la actividad
        startActivity(intent);
        //Finalizar actividad anterior
        finish();
    }

    public void llamarContacto(View v){
        String url = etTelefono.getText().toString();
        if (Build.VERSION.SDK_INT > 22) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DatosContacto.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                return;
            }
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ url)));
        } else {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ url)));
        }
    }

    public void enviarMail(View v){
        String email = etEmail.getText().toString();
        Intent emailIntent = new Intent((Intent.ACTION_SEND));
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent,"Email "));
    }
}