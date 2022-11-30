package com.example.doctorappointmentfinal.appclass;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Map;

public class Faculty {
    public String ID;
    public String Name;
    public String PicName;
    public static Faculty CurrentFaculty;
    public Faculty(String id, String name, String picName){
        this.ID=id;
        this.Name=name;
        this.PicName=picName;
    }
    public Faculty(){};
    public static Faculty covertFromQuery(QueryDocumentSnapshot documentSnapshot){
        Map<String,Object> m=documentSnapshot.getData();
        Faculty f=new Faculty();
        f.ID=m.get("ID").toString();
        f.PicName=m.get("PicName").toString();
        f.Name=m.get("Name").toString();
        return f;
    }
}
