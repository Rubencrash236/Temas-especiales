package com.example.activities_intents_permissions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.example.activities_intents_permissions.Class.Permission;

import java.util.ArrayList;

public class PermissionTab extends AppCompatActivity {

    ArrayList<Permission<Button>> permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_tab);

        permissions = new ArrayList<>();
        permissions.add(new Permission<>("Storage", Manifest.permission.READ_EXTERNAL_STORAGE,
                findViewById(R.id.storageBtn)));
        permissions.add(new Permission<>("Location",Manifest.permission.ACCESS_COARSE_LOCATION,
                findViewById(R.id.locationBtn)));
        permissions.add(new Permission<>("Camera",Manifest.permission.CAMERA,
                findViewById(R.id.cameraBtn)));
        permissions.add(new Permission<>("Phone",Manifest.permission.CALL_PHONE,
                findViewById(R.id.phoneBtn)));
        permissions.add(new Permission<>("Contacts",Manifest.permission.READ_CONTACTS,
                findViewById(R.id.contactsBtn)));

        /// Check if permission is granted and reflecting them on the switch
        for (Permission<Button> p:permissions) {
            if(isGranted(p)){
                p.getAction().setEnabled(true);
            }else {
                p.getAction().setEnabled(false);
                p.getAction().setBackgroundColor(Color.parseColor("#FF22333B"));
                p.getAction().setTextColor(Color.parseColor("#FF5E503F"));
            }
        }

        permissions.get(0).getAction().setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivity(intent);
        });

        permissions.get(1).getAction().setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        });

        permissions.get(2).getAction().setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
        });

        permissions.get(3).getAction().setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"8092234299"));
            startActivity(intent);
        });

        permissions.get(4).getAction().setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivity(intent);
        });
    }

    private Boolean isGranted(Permission<Button> permission){
        return ContextCompat.checkSelfPermission(this,
                permission.getPermission()) == PackageManager.PERMISSION_GRANTED;
    }
}