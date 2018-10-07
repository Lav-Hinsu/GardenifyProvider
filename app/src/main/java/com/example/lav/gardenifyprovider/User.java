package com.example.lav.gardenifyprovider;

import java.util.ArrayList;
import java.util.List;

public class User {
    String name;
    String password;
    String address1;
    String city;
    String mobileno;
    ArrayList<String> skills;

    void set(String name,String password,String address1,String city,String mobileno)
    {
        this.name=name;
        this.password=password;
        this.address1=address1;
        this.city=city;
        this.mobileno=mobileno;
        skills = new ArrayList<>();
        skills.add("MySkills");
        //skills.add("test1");
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
    void addskills(String skill){
        skills.add(skill);
    }

    public ArrayList<String> getSkills() {
        return skills;
    }
}
