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
    ArrayList<String> pendingorder;
    ArrayList<String> confirmorder;
    ArrayList<String> declineorder;
    User()
    {
        declineorder=new ArrayList<>();
        confirmorder=new ArrayList<>();
        pendingorder=new ArrayList<>();
        skills=new ArrayList<>();
    }

    void set(String name,String password,String address1,String city,String mobileno)
    {
        this.name=name;
        this.password=password;
        this.address1=address1;
        this.city=city;
        this.mobileno=mobileno;
        skills = new ArrayList<>();
       // skills.add("\tCurrent Skills:-");
        pendingorder = new ArrayList<>();
       // pendingorder.add("\tPending Orders:-");
        confirmorder = new ArrayList<>();
       // confirmorder.add("\tConfirmed Orders:-");
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

    public ArrayList<String> getConfirmorder() {
        return confirmorder;
    }

    public ArrayList<String> getDeclineorder() {
        return declineorder;
    }

    public ArrayList<String> getPendingorder() {
        return pendingorder;
    }
    void addpendingorder(String pendingorder)
    {
        this.pendingorder.add(pendingorder);
    }
    void addconfirmorder(String confirmorder)
    {
        this.confirmorder.add(confirmorder);
    }
    public void removependingorder(String order){
        pendingorder.remove(order);
    }
    public void adddeclineorder(String order)
    {
        declineorder.add(order);
    }
    void setName(String name){
        this.name=name;

    }
    void setAddress1(String address){
        this.address1=address;
    }
    void setMobileno(String phoneno){
        this.mobileno=phoneno;
    }


}

