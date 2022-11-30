package com.example.doctorappointmentfinal.appclass;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HospitalOrder extends Order{
    public String AddClinic;
    public String Room;
    public HospitalOrder(){}
    public HospitalOrder(String ID, String dID, Time sTime, Time eTime, String pID, float fee, Date dayAppoint, String addClinic, String room){
        super(ID, dID, sTime, eTime, pID, fee, dayAppoint);
        AddClinic=addClinic;
        Room=room;
    }
    public static Map<String, Object> convertToMap(HospitalOrder o){
        Map<String, Object> m=new HashMap<>();
        Map<String, Object> order=new HashMap<>();
        order.put("ID",o.ID);
        order.put("DoctorID",o.DoctorID);
        order.put("StartTime", FirebaseNumberAndDateTimeProcess.timeToString(o.StartTime,"hh:mm:ss"));
        order.put("EndTime", FirebaseNumberAndDateTimeProcess.timeToString(o.EndTime,"hh:mm:ss"));
        order.put("PatientID",o.PatientID);
        order.put("Fee",o.Fee);
        order.put("AppointmentDate", FirebaseNumberAndDateTimeProcess.dateToString(o.DateAppoint,"E, dd/MM/yyyy"));
        order.put("AddClinic",o.AddClinic);
        order.put("Room", o.Room);
        return m;
    }
}
