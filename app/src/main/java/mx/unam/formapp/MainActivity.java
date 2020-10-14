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
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView dateView;
    private int year, month, day;
    private TextInputEditText txEdiNombre, txEdiTelefono, txEdiMail, txEdiDescripcion;
    private TextView txEdiFecha;
    private String stNombre, stTelefono, stEmail, stFecha, stDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateView = (TextView) findViewById(R.id.textView3);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        this.showDate(year, month+1, day);

        //Inicalizar los campos
        incializarEditTexts();
        //Asiganr evento al boton
        Button send = (Button) findViewById(R.id.btnMiBoton);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatos();
            }
        });

        //Validar si fue llamado con parametros
        Bundle bunConf = getIntent().getExtras();
        //Si recibio parametros
        if( bunConf != null ) {
            stNombre = bunConf.getString(getResources().getString(R.string.name));
            stFecha = bunConf.getString(getResources().getString(R.string.date_selected));
            stTelefono = bunConf.getString(getResources().getString(R.string.phone));
            stEmail = bunConf.getString(getResources().getString(R.string.email));
            stDescripcion = bunConf.getString(getResources().getString(R.string.contact_description));
            //
            incializarEditTexts();
            setDataFromForm();
        }

        this.setOnFocusChangeListener(txEdiNombre);
        this.setOnFocusChangeListener(txEdiTelefono);
        this.setOnFocusChangeListener(txEdiMail);
        this.setOnFocusChangeListener(txEdiDescripcion);
        this.setOnFocusChangeListener(txEdiFecha);

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
            bunParam.putString(getResources().getString(R.string.name),stNombre);
            bunParam.putString(getResources().getString(R.string.date_selected),stFecha);
            bunParam.putString(getResources().getString(R.string.phone),stTelefono);
            bunParam.putString(getResources().getString(R.string.email),stEmail);
            bunParam.putString(getResources().getString(R.string.contact_description),stDescripcion);
            //instanciar Intent
            Intent intent = new Intent(MainActivity.this, DataDetail.class);
            //Pasar los parametros al intent
            intent.putExtras(bunParam);
            //iniciar la actividad
            startActivity(intent);
            //Finalizar actividad anterior
            finish();
        }
    }
    public void incializarEditTexts(){
        txEdiNombre   = (TextInputEditText) findViewById(R.id.tienombre);
        txEdiFecha    = (TextView) findViewById(R.id.textView3);
        txEdiTelefono = (TextInputEditText) findViewById(R.id.tietelefono);
        txEdiMail     = (TextInputEditText) findViewById(R.id.tiemail);
        txEdiDescripcion = (TextInputEditText) findViewById(R.id.tiedescripcion);
    }
    public void getDataFromForm(){
        stNombre = txEdiNombre.getText().toString();
        stTelefono = txEdiTelefono.getText().toString();
        stEmail = txEdiMail.getText().toString();
        stFecha = txEdiFecha.getText().toString();
        stDescripcion = txEdiDescripcion.getText().toString();
    }

    public void setDataFromForm(){
        txEdiNombre.setText(stNombre);
        txEdiTelefono.setText(stTelefono);
        txEdiMail.setText(stEmail);
        txEdiFecha.setText(stFecha);
        txEdiDescripcion.setText(stDescripcion);
    }

    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.select_date), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
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
}