package com.example.lav.gardenifyprovider;

public class User {
    String name;
    String password;
    String address1;
    String city;
    String mobileno;

    void set(String name,String password,String address1,String city,String mobileno)
    {
        this.name=name;
        this.password=password;
        this.address1=address1;
        this.city=city;
        this.mobileno=mobileno;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public String getAddress1(){
        return address1;
    }
    public String getMobileno(){
        return mobileno;
    }
    public String getCity(){
        return city;
    }

}
