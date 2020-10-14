package mx.unam.formapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNombre, etTelefono, etFecha, etEmail, etDescripcion;
    private String stNombre, stTelefono, stFecha, stEmail, stDescripcion;
    private Button btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Validar si fue llamado con parametros
        Bundle bunConf = getIntent().getExtras();
        //Si recibio parametros
        if( bunConf != null ) {
            stNombre = bunConf.getString(getResources().getString(R.string.nombre));
            stFecha = bunConf.getString(getResources().getString(R.string.fecha_nacimiento));
            stTelefono = bunConf.getString(getResources().getString(R.string.telefono));
            stEmail = bunConf.getString(getResources().getString(R.string.email));
            stDescripcion = bunConf.getString(getResources().getString(R.string.descripcion_contacto));
        }else{
            etNombre = (EditText) findViewById(R.id.etNombre);
            etFecha = (EditText) findViewById(R.id.etFecha);
            etTelefono = (EditText) findViewById(R.id.etTelefono);
            etEmail = (EditText) findViewById(R.id.etEmail);
            etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        }

        incializarEditTexts();
        setDataFromForm();
    }

    public void setDataFromForm(){
        etNombre.setText(stNombre);
        etTelefono.setText(stTelefono);
        etEmail.setText(stEmail);
        etFecha.setText(stFecha);
        etDescripcion.setText(stDescripcion);
    }

    public void incializarEditTexts(){
        etNombre   = (EditText) findViewById(R.id.etNombre);
        etFecha    = (EditText) findViewById(R.id.etFecha);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
        etEmail     = (EditText) findViewById(R.id.etEmail);
        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);

        btnSiguiente.setOnClickListener(this);
        etFecha.setOnClickListener(this);

        this.setOnFocusChangeListener(etNombre);
        this.setOnFocusChangeListener(etTelefono);
        this.setOnFocusChangeListener(etFecha);
        this.setOnFocusChangeListener(etEmail);
        this.setOnFocusChangeListener(etDescripcion);
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setOnFocusChangeListener(TextView textInputEditText){
        textInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        textInputEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    hideKeyboard(v);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etFecha:
                showDatePickerDialog();
                break;
            case R.id.btnSiguiente:
                enviarDatos();
                break;
        }
    }

    public void getDataFromForm(){
        stNombre = etNombre.getText().toString();
        stTelefono = etTelefono.getText().toString();
        stEmail = etEmail.getText().toString();
        stFecha = etFecha.getText().toString();
        stDescripcion = etDescripcion.getText().toString();
    }

    public void enviarDatos(){
        //Tomar datos del formulario
        this.getDataFromForm();
        if( stNombre.length() == 0 || stTelefono .length() == 0 ||
                stEmail.length() == 0 || stFecha.length() == 0 ||
                stDescripcion.length() == 0){
            //Mostrar mensaje por formulario incompleto
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.complete_form), Toast.LENGTH_SHORT).show();
        }else{
            // Crear Objeto Bundle
            Bundle bunParam = new Bundle();
            bunParam.putString(getResources().getString(R.string.nombre),stNombre);
            bunParam.putString(getResources().getString(R.string.fecha_nacimiento),stFecha);
            bunParam.putString(getResources().getString(R.string.telefono),stTelefono);
            bunParam.putString(getResources().getString(R.string.email),stEmail);
            bunParam.putString(getResources().getString(R.string.descripcion_contacto),stDescripcion);
            //instanciar Intent
            Intent intent = new Intent(MainActivity.this, DatosContacto.class);
            //Pasar los parametros al intent
            intent.putExtras(bunParam);
            //iniciar la actividad
            startActivity(intent);
            //Finalizar actividad anterior
            finish();
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                etFecha.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}