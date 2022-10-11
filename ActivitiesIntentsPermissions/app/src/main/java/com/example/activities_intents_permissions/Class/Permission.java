package com.example.activities_intents_permissions.Class;

public class Permission<T> {
    String name;
    String permission;
    T action;

    public Permission(String name, String permission, T action){
        this.name = name;
        this.permission = permission;
        this.action = action;
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

}
