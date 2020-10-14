package mx.unam.formapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class ContactDetail extends AppCompatActivity {

    private TextView etName, etPhone, etDate, etEmail, etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        // Get the Intent that started this activity and extract the string
        Bundle params = getIntent().getExtras();

        String name = params.getString(getResources().getString(R.string.name));
        String date = params.getString(getResources().getString(R.string.birth_date));
        String phone = params.getString(getResources().getString(R.string.phone));
        String email = params.getString(getResources().getString(R.string.email));
        String description = params.getString(getResources().getString(R.string.contact_description));

        etName = (TextView) findViewById(R.id.tvNameData);
        etDate = (TextView) findViewById(R.id.tvDateData);
        etPhone = (TextView) findViewById(R.id.tvPhoneData);
        etEmail = (TextView) findViewById(R.id.tvEmailData);
        etDescription = (TextView) findViewById(R.id.tvDescriptionData);

        etName.setText(name);
        etDate.setText(date);
        etPhone.setText(phone);
        etEmail.setText(email);
        etDescription.setText(description);

        Button send = (Button) findViewById(R.id.btnEditDetails);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData();
            }
        });
    }

    public void editData(){
        // Crear Objeto Bundle
        Bundle bunParam = new Bundle();
        bunParam.putString(getResources().getString(R.string.name), etName.getText().toString());
        bunParam.putString(getResources().getString(R.string.birth_date), etDate.getText().toString());
        bunParam.putString(getResources().getString(R.string.phone), etPhone.getText().toString());
        bunParam.putString(getResources().getString(R.string.email),etEmail.getText().toString());
        bunParam.putString(getResources().getString(R.string.contact_description), etDescription.getText().toString());
        //instanciar Intent
        Intent intent = new Intent( ContactDetail.this, MainActivity.class);
        //Pasar los parametros al intent
        intent.putExtras(bunParam);
        //iniciar la actividad
        startActivity(intent);
        //Finalizar actividad anterior
        finish();
    }

    public void callContact(View v){
        String url = etPhone.getText().toString();
        if (Build.VERSION.SDK_INT > 22) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ContactDetail.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                return;
            }
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ url)));
        } else {
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ url)));
        }
    }

    public void sendEmail(View v){
        String email = etEmail.getText().toString();
        Intent emailIntent = new Intent((Intent.ACTION_SEND));
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent,"Email "));
    }
}