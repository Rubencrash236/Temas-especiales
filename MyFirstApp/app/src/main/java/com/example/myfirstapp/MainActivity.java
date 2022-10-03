package com.example.myfirstapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.my_first_app.MESSAGE";
    EditText date,nameText, surnText;
    Spinner sex;
    DatePickerDialog datePickerDialog;
    Button sendBtn, cleanBtn;
    ArrayList<CheckBox> languages;
    RadioGroup likeCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = (EditText) findViewById(R.id.date);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        cleanBtn = (Button) findViewById(R.id.cleanBtn);
        nameText = (EditText) findViewById(R.id.name);
        surnText = (EditText) findViewById(R.id.surname);
        sex = (Spinner) findViewById(R.id.sex);
        languages = new ArrayList<>();
        getCbxs(languages);
        likeCode = (RadioGroup) findViewById(R.id.radioGroup2);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(view);
            }
        });

        cleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanForm(view);
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    final Calendar calendar = Calendar.getInstance();
                    int mYear = calendar.get(Calendar.YEAR);
                    int mMonth = calendar.get(Calendar.MONTH);
                    int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                    datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            date.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                        }
                    },mYear,mMonth,mDay);
                    datePickerDialog.show();
                }
            }
        });
    }

    private void getCbxs(ArrayList<CheckBox> languages) {
        languages.add((CheckBox) findViewById(R.id.cbxJava));
        languages.add((CheckBox) findViewById(R.id.cbxC));
        languages.add((CheckBox) findViewById(R.id.cbxCS));
        languages.add((CheckBox) findViewById(R.id.cbxGo));
        languages.add((CheckBox) findViewById(R.id.cbxJs));
        languages.add((CheckBox) findViewById(R.id.cbxPy));
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        StringBuilder message = null;

        if(!nameText.getText().toString().equalsIgnoreCase("") && !surnText.getText().toString().equalsIgnoreCase("") && !sex.getSelectedItem().toString().equalsIgnoreCase("") && !date.getText().toString().equalsIgnoreCase("")){
            message = new StringBuilder("¡Hola!, mi nombre es: " + nameText.getText().toString() + " " + surnText.getText().toString() + ".\n\n"
                    + "Soy " + sex.getSelectedItem().toString() + ", y nací en la fecha " + date.getText().toString() + ".\n\n");
            int answerLikeCode = likeCode.getCheckedRadioButtonId();
            RadioButton rdB = (RadioButton) findViewById(answerLikeCode);
            String selection = rdB.getText().toString();
            Boolean isPoblated = checkOne(languages);
            if(selection.equalsIgnoreCase("si") && isPoblated){

                message.append("Me gusta programar. Mis lenguajes favoritos son: ");

                for (CheckBox language: languages) {
                    if(language.isChecked()){
                        message.append(language.getText().toString()).append(", ");
                    }
                }

                intent.putExtra(EXTRA_MESSAGE, message.toString());
                startActivity(intent);
            } else if(selection.equalsIgnoreCase("si") && !isPoblated){
                Toast.makeText(getApplicationContext(),"Seleccione al menos 1 lenguaje",Toast.LENGTH_SHORT).show();
            } else if(selection.equalsIgnoreCase("no") && isPoblated){
                Toast.makeText(getApplicationContext(),"No puede seleccionar lenguajes",Toast.LENGTH_SHORT).show();
            } else {
                message.append("No me gusta programar.");
                intent.putExtra(EXTRA_MESSAGE, message.toString());
                startActivity(intent);
            }

        } else {
            Toast.makeText(getApplicationContext(),"Los campos Nombre, Apellido, Género y Nacimiento son obligatorios.",Toast.LENGTH_LONG).show();
        }

        //intent.putExtra(EXTRA_MESSAGE, message);
    }

    public void cleanForm(View view){
        nameText.setText("");
        surnText.setText("");
        sex.setSelection(0);
        date.setText("");
        RadioButton rdb = (RadioButton) likeCode.getChildAt(0);
        rdb.setChecked(true);
        rdb = (RadioButton) likeCode.getChildAt(1);
        rdb.setChecked(false);
        for (CheckBox c: languages) {
            c.setChecked(false);
        }
    }

    private boolean checkOne(ArrayList<CheckBox> checkBoxes){
        for (CheckBox cbx: checkBoxes) {
            if(cbx.isChecked()){
                return true;
            }
        }
        return false;
    }
}