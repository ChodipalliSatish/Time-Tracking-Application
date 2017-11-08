package com.example.computer.signinapp;

import java.util.Date;

/**
 * Created by Computer on 9/21/2017.
 */

public class Location {

    private String UUID;
    private String proximity;
    private String Eid;
    private Date date;
Location(String UUID,String proximity,String EmpID){

    this.UUID=UUID;
    this.proximity=proximity;
    this.Eid=EmpID;
    this.date= new Date();
}
    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getPriximity() {
        return proximity;
    }

    public void setPriximity(String priximity) {
        this.proximity = priximity;
    }

    public String getEid() {
        return Eid;
    }

    public void setEid(String eid) {
        Eid = eid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
