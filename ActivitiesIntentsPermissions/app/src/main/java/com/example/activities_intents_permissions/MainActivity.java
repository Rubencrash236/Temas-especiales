package com.example.activities_intents_permissions;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.activities_intents_permissions.Class.Permission;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Permission> permissions;
    Permission lastPermission;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, PermissionTab.class);
        permissions = new ArrayList<>();
        permissions.add(new Permission("Storage","android.permission.READ_EXTERNAL_STORAGE",
                findViewById(R.id.storageSwh), 101));
        permissions.add(new Permission("Location","android.permission.ACCESS_COARSE_LOCATION",
                findViewById(R.id.locationSwh),102));
        permissions.add(new Permission("Camera","android.permission.CAMERA",
                findViewById(R.id.cameraSwh),103));
        permissions.add(new Permission("Phone","android.permission.CALL_PHONE",
                findViewById(R.id.phoneSwh),104));
        permissions.add(new Permission("Contacts","android.permission.READ_CONTACTS",
                findViewById(R.id.contactSwh),105));

        /// Check if permission is granted and reflecting them on the switch
        for (Permission p:permissions) {
            p.getSwitchCompat().setChecked(isGranted(p));
            if(isGranted(p))
                p.getSwitchCompat().setClickable(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode >= 101 && requestCode <= 105) {
            Permission permission = getByCode(requestCode);
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, permission.getName()+" permission granted", Toast.LENGTH_SHORT).show();
            }

            if(permission.getRequestCode() == lastPermission.getRequestCode()){
                startActivity(intent);
            }
        }
    }

    private Boolean isGranted(Permission permission){
        return ContextCompat.checkSelfPermission(this, permission.getPermission()) == PackageManager.PERMISSION_GRANTED;
    }

    public void nextMessage(View view){
        ArrayList<Permission> pAdd = new ArrayList<>();
        for (Permission p:permissions) {
            if(p.getSwitchCompat().isChecked() && !isGranted(p)){
                pAdd.add(p);
            }
        }
        lastPermission = pAdd.get(pAdd.size()-1);
        for (Permission permission: pAdd) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission.getPermission())){
                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("This permission is needed for the best experience :3")
                        .setPositiveButton("ok", (dialogInterface, i) -> {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{permission.getPermission()}, permission.getRequestCode());

                        })
                        .setNegativeButton("cancel", ((dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })).create().show();
            } else{
                ActivityCompat.requestPermissions(this,new String[]{permission.getPermission()},
                        permission.getRequestCode());
            }

            /*if(permission == lastPermission){
                startActivity(intent);
            }*/
        }

        //need a way to stop this and wait for the dialogs
        //startActivity(intent);
    }

    public Permission getByCode(int rc){
        Permission aux = new Permission();
        for (Permission p:permissions) {
            if(p.getRequestCode() == rc){
                return p;
            }
        }
        return aux;
    }
}