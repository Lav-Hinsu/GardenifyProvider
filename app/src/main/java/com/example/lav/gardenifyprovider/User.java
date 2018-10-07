package com.example.lav.gardenifyprovider;

public class User {
    String name;
    String password;



    void set(String name,String password)
    {
        this.name=name;
        this.password=password;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }

}
