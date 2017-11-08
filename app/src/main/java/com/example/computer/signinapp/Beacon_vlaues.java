package com.example.computer.signinapp;

/**
 * Created by Miracle on 10/10/2017.
 */

public class Beacon_vlaues {



    private String Beacon_Desc;
    private String Beacon_Major_Number;
    private String Beacon_Minor_Number;
    private String Beacon_Name;
    private String Beacon_UUID;

    Beacon_vlaues(String Beacon_Desc, String Beacon_Major_Number,String Beacon_Minor_Number,String Beacon_Name,String Beacon_UUID){



        this.Beacon_Desc=Beacon_Desc;
        this.Beacon_Major_Number=Beacon_Major_Number;
        this.Beacon_Minor_Number=Beacon_Minor_Number;
        this.Beacon_Name=Beacon_Name;
        this.Beacon_UUID=Beacon_UUID;
            }

    Beacon_vlaues(){}

    public String getBeacon_Desc() {
        return Beacon_Desc;
    }



    public void setBeacon_Desc(String Beacon_Desc) {
        this.Beacon_Desc = Beacon_Desc;
    }


    public String getBeacon_UUID() {
        return Beacon_UUID;
    }

    public void setBeacon_UUID(String Beacon_UUID) {
        this.Beacon_UUID = Beacon_UUID;
    }

    public String getBeacon_Major_Number(){
        return Beacon_Major_Number;
    }
    public void setBeacon_Major_Number(String Beacon_Major_Number ){
        this.Beacon_Major_Number=Beacon_Major_Number;
    }
    public String getBeacon_Minor_Number(){
        return Beacon_Minor_Number;
    }
    public void setBeacon_Minor_Number(String Beacon_minor_number ){
        this.Beacon_Minor_Number=Beacon_minor_number;
    }
    public String getBeacon_Name(){
        return Beacon_Name;
    }
    public void setBeacon_Name(String Beacon_Name ){
        this.Beacon_Name=Beacon_Name;
    }
}
