package com.example.doctorappointmentfinal.appclass;

import static com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess.stringToDate;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Date;
import java.util.Map;

public class People {
    public String ID;
    public String Name;
    public boolean Gender;
    public Date DateOfBrith;
    public String PhoneNumber;
    public String Address;
    public String PicImage;
    public People(){}
    public People(String id, String name, boolean g, Date dob, String phoneNumber, String address, String PicImage){
        ID=id;
        Name=name;
        Gender=g;
        DateOfBrith=dob;
        PhoneNumber=phoneNumber;
        Address=address;
        this.PicImage=PicImage;
    }
    public static People convertFromQuery(QueryDocumentSnapshot documentSnapshot){
        Map<String, Object> m=documentSnapshot.getData();
        if(m.get("ID").toString().startsWith("P")){
            Patient p=new Patient();
            Map<String,Object> u=documentSnapshot.getData();
            p.ID=u.get("ID").toString();
            p.Name=u.get("Name").toString();
            p.Gender=Boolean.parseBoolean(u.get("Gender").toString());
            p.PhoneNumber=u.get("PhoneNumber").toString();
            p.Email=u.get("Email").toString();
            p.Address=u.get("Address").toString();
            p.DateOfBrith= FirebaseNumberAndDateTimeProcess.stringToDate(u.get("DateOfBirth").toString(),"dd/MM/yyyy");
            p.BelongToID=u.get("BelongToID").toString();
            p.PicImage=u.get("PicImage").toString();
            return p;
        }
        else if(m.get("ID").toString().startsWith("D")){
            Doctor d=new Doctor();
            Map<String,Object> u=documentSnapshot.getData();
            d.ID=u.get("ID").toString();
            d.Name=u.get("Name").toString();
            d.Gender=Boolean.parseBoolean(u.get("Gender").toString());
            d.PhoneNumber=u.get("PhoneNumber").toString();
            d.Address=u.get("Address").toString();
            d.SpecialityID=u.get("Specialize").toString();
            d.Experience=Integer.parseInt(u.get("Experience").toString());
            d.DateOfBrith=stringToDate(u.get("DateOfBirth").toString(),"dd/MM/yyyy");
            d.PicImage=u.get("PicImage").toString();
            return d;
        }
        return null;
    }
}
