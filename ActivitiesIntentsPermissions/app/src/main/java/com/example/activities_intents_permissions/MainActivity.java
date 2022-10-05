package com.example.activities_intents_permissions;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nextMessage(View view){
        Intent intent = new Intent(this, PermissionTab.class);
        intent.putExtra("hello","world");
        startActivity(intent);
    }
}