package com.example.myapplication.Data;

public class User {
    private String Name;
    private String Email;
    private String Pass;
    private String Phone;

    public User(String name, String email, String pass, String phone) {
        Name = name;
        Email = email;
        Pass = pass;
        Phone = phone;
    }

    public User() {

    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}

