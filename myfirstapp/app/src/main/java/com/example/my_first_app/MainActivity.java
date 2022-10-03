package com.example.my_first_app;

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
    EditText date;
    DatePickerDialog datePickerDialog;
    Button sendBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = (EditText) findViewById(R.id.date);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(view);
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

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText nameText = (EditText) findViewById(R.id.name);
        EditText surnText = (EditText) findViewById(R.id.surname);
        Spinner sex = (Spinner) findViewById(R.id.sex);
        String message = "¡Hola!, mi nombre es: "+nameText.getText().toString() + " "+surnText.getText().toString()+".\n\n"
                +"Soy "+sex.getSelectedItem().toString()+", y nací en la fecha "+date.getText().toString()+".\n\n";
        RadioGroup likeCode = (RadioGroup) findViewById(R.id.radioGroup2);
        int answerLikeCode = likeCode.getCheckedRadioButtonId();
        RadioButton rdB = (RadioButton) findViewById(answerLikeCode);
        String selection = rdB.getText().toString();

        if(selection.equalsIgnoreCase("si")){

            message += "Me gusta programar. Mis lenguajes favoritos son: ";
            ArrayList<CheckBox> languages = new ArrayList<>();
            languages.add((CheckBox) findViewById(R.id.cbxJava));
            languages.add((CheckBox) findViewById(R.id.cbxC));
            languages.add((CheckBox) findViewById(R.id.cbxCS));
            languages.add((CheckBox) findViewById(R.id.cbxGo));
            languages.add((CheckBox) findViewById(R.id.cbxJs));
            languages.add((CheckBox) findViewById(R.id.cbxPy));

            for (CheckBox language: languages) {
                if(language.isChecked()){
                    message += language.getText().toString()+", ";
                }
            }

        } else {
            message += "No me gusta programar";

        }

        //intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}