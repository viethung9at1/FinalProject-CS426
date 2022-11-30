package com.example.doctorappointmentfinal.appclass;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Doctor extends People {
    public String SpecialityID;
    public int Experience;
    public static Doctor CurrentDoctor;
    public boolean IsFree;
    public float Rating;
    public Doctor(String id, String name, boolean g, Date dob, String phoneNumber, String spec, int exp, String address, String picImg){
        super(id,name,g,dob,phoneNumber, address, picImg);
        SpecialityID=spec;
        Experience=exp;
        IsFree= new Random().nextInt(3)>1?false:true;
        int max=5;
        int min=3;
        Rating=new Random().nextInt(max-min)+min+new Random().nextFloat();
    }
    public Doctor(){
        IsFree= new Random().nextInt(3)>1?false:true;
        int max=5;
        int min=3;
        Rating=new Random().nextInt(max-min)+min+new Random().nextFloat();
    }
}
