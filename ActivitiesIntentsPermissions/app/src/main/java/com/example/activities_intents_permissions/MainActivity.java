package com.example.activities_intents_permissions;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.activities_intents_permissions.Class.Permission;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Permission<SwitchCompat>> permissions;
    Permission<SwitchCompat> lastPermission;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, PermissionTab.class);
        permissions = new ArrayList<>();
        permissions.add(new Permission<>("Storage","android.permission.READ_EXTERNAL_STORAGE",
                findViewById(R.id.storageSwh), 101));
        permissions.add(new Permission<>("Location","android.permission.ACCESS_COARSE_LOCATION",
                findViewById(R.id.locationSwh),102));
        permissions.add(new Permission<>("Camera","android.permission.CAMERA",
                findViewById(R.id.cameraSwh),103));
        permissions.add(new Permission<>("Phone","android.permission.CALL_PHONE",
                findViewById(R.id.phoneSwh),104));
        permissions.add(new Permission<>("Contacts","android.permission.READ_CONTACTS",
                findViewById(R.id.contactSwh),105));

        /// Check if permission is granted and reflecting them on the switch
        for (Permission<SwitchCompat> p:permissions) {
            p.getAction().setChecked(isGranted(p));
            if(isGranted(p))
                p.getAction().setClickable(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 101) {
            for (String string: permissions) {
                Permission<SwitchCompat> permission = getByCode(string);
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, permission.getName()+" permission granted", Toast.LENGTH_SHORT).show();
                }
            }
            startActivity(intent);
        }
    }

    private Boolean isGranted(Permission<SwitchCompat> permission){
        return ContextCompat.checkSelfPermission(this,
                permission.getPermission()) == PackageManager.PERMISSION_GRANTED;
    }

    public void nextMessage(View view){
        ArrayList<Permission<SwitchCompat>> pAdd = new ArrayList<>();
        ArrayList<String> pName = new ArrayList<>();
        for (Permission<SwitchCompat> p:permissions) {
            if(p.getAction().isChecked() && !isGranted(p)){
                pAdd.add(p);
                pName.add(p.getPermission());
            }
        }
        if(pAdd.size() > 0){
            lastPermission = pAdd.get(pAdd.size()-1);
            ActivityCompat.requestPermissions(this,pName.toArray(new String[pName.size()]),101);
        } else {
            startActivity(intent);
        }
    }

    public Permission<SwitchCompat> getByCode(String permission){
        Permission<SwitchCompat> aux = new Permission<>();
        for (Permission<SwitchCompat> p:permissions) {
            if(p.getPermission().equalsIgnoreCase(permission)){
                return p;
            }
        }
        return aux;
    }
}