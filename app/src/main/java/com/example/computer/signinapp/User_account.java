package com.example.computer.signinapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Computer on 9/19/2017.
 */

public class User_account {

    private String ename;

    private String email;
    private String password;
    private String dob;
    private String role;
    private String phone;
    private String gender;




    User_account(String ename,String email,String pwd,String dob,String phone,String role,String gender){


        this.email=email;

         this.phone=phone;
        this.ename=ename;
        this.password=pwd;
        this.dob=dob;
        this.role=role;
        this.gender=gender;
    }
User_account(){}
    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
