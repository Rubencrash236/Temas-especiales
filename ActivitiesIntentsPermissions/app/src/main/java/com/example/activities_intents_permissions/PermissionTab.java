package com.example.activities_intents_permissions;

import android.content.pm.PackageManager;
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
        permissions.add(new Permission<>("Storage","android.permission.READ_EXTERNAL_STORAGE",
                findViewById(R.id.storageBtn), 101));
        permissions.add(new Permission<>("Location","android.permission.ACCESS_COARSE_LOCATION",
                findViewById(R.id.locationBtn),102));
        permissions.add(new Permission<>("Camera","android.permission.CAMERA",
                findViewById(R.id.cameraBtn),103));
        permissions.add(new Permission<>("Phone","android.permission.CALL_PHONE",
                findViewById(R.id.phoneBtn),104));
        permissions.add(new Permission<>("Contacts","android.permission.READ_CONTACTS",
                findViewById(R.id.contactsBtn),105));

        /// Check if permission is granted and reflecting them on the switch
        for (Permission<Button> p:permissions) {
            p.getAction().setEnabled(isGranted(p));
            p.getAction().setClickable(isGranted(p));
        }

    }

    private Boolean isGranted(Permission<Button> permission){
        return ContextCompat.checkSelfPermission(this,
                permission.getPermission()) == PackageManager.PERMISSION_GRANTED;
    }
}