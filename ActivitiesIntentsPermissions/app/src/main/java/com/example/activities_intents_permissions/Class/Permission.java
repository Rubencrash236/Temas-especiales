package com.example.activities_intents_permissions.Class;

public class Permission<T> {
    String name;
    String permission;
    T action;
    int requestCode;

    public Permission(String name, String permission, T action, int requestCode){
        this.name = name;
        this.permission = permission;
        this.action = action;
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

    public T getAction() {
        return action;
    }

    public int getRequestCode() {
        return requestCode;
    }
}
