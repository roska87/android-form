package mx.unam.formapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.app.DatePickerDialog;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName, etPhone, etDate, etEmail, etDescription;
    private String stName, stPhone, stDate, stEmail, stDescription;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Validar si fue llamado con parametros
        Bundle bunConf = getIntent().getExtras();
        //Si recibio parametros
        if( bunConf != null ) {
            stName = bunConf.getString(getResources().getString(R.string.name));
            stDate = bunConf.getString(getResources().getString(R.string.birth_date));
            stPhone = bunConf.getString(getResources().getString(R.string.phone));
            stEmail = bunConf.getString(getResources().getString(R.string.email));
            stDescription = bunConf.getString(getResources().getString(R.string.contact_description));
        }

        incializarEditTexts();
        setDataFromForm();
    }

    public void setDataFromForm(){
        etName.setText(stName);
        etPhone.setText(stPhone);
        etEmail.setText(stEmail);
        etDate.setText(stDate);
        etDescription.setText(stDescription);
    }

    public void incializarEditTexts(){
        etName = (EditText) findViewById(R.id.etName);
        etDate = (EditText) findViewById(R.id.etDate);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etDescription = (EditText) findViewById(R.id.etDescription);
        btnNext = (Button) findViewById(R.id.btnNext);

        btnNext.setOnClickListener(this);
        etDate.setOnClickListener(this);

        this.setOnFocusChangeListener(etName);
        this.setOnFocusChangeListener(etPhone);
        this.setOnFocusChangeListener(etDate);
        this.setOnFocusChangeListener(etEmail);
        this.setOnFocusChangeListener(etDescription);
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
            case R.id.etDate:
                showDatePickerDialog();
                break;
            case R.id.btnNext:
                sendData();
                break;
        }
    }

    public void getDataFromForm(){
        stName = etName.getText().toString();
        stPhone = etPhone.getText().toString();
        stEmail = etEmail.getText().toString();
        stDate = etDate.getText().toString();
        stDescription = etDescription.getText().toString();
    }

    public void sendData(){
        //Tomar datos del formulario
        this.getDataFromForm();
        if( stName.length() == 0 || stPhone.length() == 0 ||
                stEmail.length() == 0 || stDate.length() == 0 ||
                stDescription.length() == 0){
            //Mostrar mensaje por formulario incompleto
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.complete_form), Toast.LENGTH_SHORT).show();
        }else{
            // Crear Objeto Bundle
            Bundle bunParam = new Bundle();
            bunParam.putString(getResources().getString(R.string.name), stName);
            bunParam.putString(getResources().getString(R.string.birth_date), stDate);
            bunParam.putString(getResources().getString(R.string.phone), stPhone);
            bunParam.putString(getResources().getString(R.string.email),stEmail);
            bunParam.putString(getResources().getString(R.string.contact_description), stDescription);
            //instanciar Intent
            Intent intent = new Intent(MainActivity.this, ContactDetail.class);
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
                etDate.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}