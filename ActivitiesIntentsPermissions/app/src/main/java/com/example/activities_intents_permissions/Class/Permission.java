package com.example.activities_intents_permissions.Class;

import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class Permission {
    String name;
    String permission;
    SwitchCompat switchCompat;
    int requestCode;

    public Permission(String name,String permission,SwitchCompat switchCompat, int requestCode){
        this.name = name;
        this.permission = permission;
        this.switchCompat = switchCompat;
        this.requestCode = requestCode;
    }
    public Permission(){

    }


    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public SwitchCompat getSwitchCompat() {
        return switchCompat;
    }

    public int getRequestCode() {
        return requestCode;
    }
}
