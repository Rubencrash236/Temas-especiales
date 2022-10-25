package com.example.examen1_ruben_osmani;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterProduct extends AppCompatActivity {

    Button registerBtn, clearBtn;
    EditText pName, pDesc, pPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product);
        registerBtn = findViewById(R.id.registerBtn);
        clearBtn = findViewById(R.id.clearBtn);

        pName = findViewById(R.id.pNametxt);
        pDesc = findViewById(R.id.pDescriptionTxt);
        pPrice = findViewById(R.id.pPriceTxt);

        clearBtn.setOnClickListener(this::clearForm);
        registerBtn.setOnClickListener(this::register);
    }

    public void register(View view){
        MainActivity.products.add(new Product(pName.getText().toString(),
                pDesc.getText().toString(),Double.parseDouble(pPrice.getText().toString())));
        clearForm(view);
    }

    public void clearForm(View view){
        pName.setText("");
        pDesc.setText("");
        pPrice.setText("");
    }
}