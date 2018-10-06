package com.example.lav.gardenifyprovider;

public class User {
    String name;
    String password;

    void set(String name,String password)
    {
        this.name=name;
        this.password=password;
    }
    String getName(){
        return name;
    }
    String getPass(){
        return password;
    }

}
