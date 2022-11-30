package com.example.doctorappointmentfinal.appclass;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order  {
    public String ID;
    public String DoctorID;
    public Time StartTime, EndTime;
    public Date DateAppoint;
    public String PatientID;
    public float Fee;
    public Order(){}
    public Order(String ID, String dID, Time sTime, Time eTime, String pID, float fee, Date dayAppoint){
        this.ID=ID;
        DoctorID=dID;
        StartTime=sTime;
        EndTime=eTime;
        PatientID=pID;
        Fee=fee;
        DateAppoint=dayAppoint;
    }
    public static Order convertFromQuery(QueryDocumentSnapshot documentSnapshot) throws ParseException {
        Map<String, Object> m=documentSnapshot.getData();
        if(m.get("ID").toString().startsWith("V")){
            VideoCallOrder videoCallOrder=new VideoCallOrder();
            videoCallOrder.ID=m.get("ID").toString();
            videoCallOrder.StartTime= FirebaseNumberAndDateTimeProcess.stringToTime(m.get("StartTime").toString());
            videoCallOrder.EndTime= FirebaseNumberAndDateTimeProcess.stringToTime(m.get("EndTime").toString());
            videoCallOrder.DateAppoint= FirebaseNumberAndDateTimeProcess.stringToDate(m.get("DateAppoint").toString(),"dd/MM/yyyy");
            videoCallOrder.Fee=Float.parseFloat(m.get("Fee").toString());
            videoCallOrder.DoctorID=m.get("DoctorID").toString();
            videoCallOrder.PatientID=m.get("PatientID").toString();
            return videoCallOrder;
        }
        else if(m.get("ID").toString().startsWith("H")){
            HospitalOrder hospitalOrder=new HospitalOrder();
            hospitalOrder.ID=m.get("ID").toString();
            hospitalOrder.StartTime= FirebaseNumberAndDateTimeProcess.stringToTime(m.get("StartTime").toString());
            hospitalOrder.EndTime= FirebaseNumberAndDateTimeProcess.stringToTime(m.get("EndTime").toString());
            hospitalOrder.DateAppoint= FirebaseNumberAndDateTimeProcess.stringToDate(m.get("AppointmentDate").toString(),"dd/MM/yyyy");
            hospitalOrder.Fee=Float.parseFloat(m.get("Fee").toString());
            hospitalOrder.DoctorID=m.get("DoctorID").toString();
            hospitalOrder.PatientID=m.get("PatientID").toString();
            hospitalOrder.AddClinic=m.get("AddClinic").toString();
            hospitalOrder.Room=m.get("Room").toString();
            return hospitalOrder;
        }
        /*else if(m.get("ID").toString().startsWith("N")){
            VideoNowOrder videoNowOrder=new VideoNowOrder();
            videoNowOrder.ID=m.get("ID").toString();
            videoNowOrder.StartTime= FirebaseNumberAndDateTimeProcess.stringToTime(m.get("StartTime").toString());
            videoNowOrder.EndTime= FirebaseNumberAndDateTimeProcess.stringToTime(m.get("EndTime").toString());
            videoNowOrder.DateAppoint= FirebaseNumberAndDateTimeProcess.stringToDate(m.get("DateAppoint").toString(),"dd/MM/yyyy");
            videoNowOrder.Fee=Float.parseFloat(m.get("Fee").toString());
            videoNowOrder.DoctorID=m.get("DoctorID").toString();
            videoNowOrder.PatientID=m.get("PatientID").toString();
            return videoNowOrder;
        }*/
        return null;
    }
    public static Map<String, Object> convertToMap(Order o){
        Map<String, Object> order=new HashMap<>();
        if(o.ID.startsWith("H")){
            order.put("ID",o.ID);
            order.put("DoctorID",o.DoctorID);
            order.put("StartTime", FirebaseNumberAndDateTimeProcess.timeToString(o.StartTime,"hh:mm:ss"));
            order.put("EndTime", FirebaseNumberAndDateTimeProcess.timeToString(o.EndTime,"hh:mm:ss"));
            order.put("PatientID",o.PatientID);
            order.put("Fee",o.Fee);
            order.put("AppointmentDate", FirebaseNumberAndDateTimeProcess.dateToString(o.DateAppoint,"dd/MM/yyyy"));
            order.put("AddClinic",((HospitalOrder)o).AddClinic);
            order.put("Room", ((HospitalOrder)o).Room);
        }
        else if(o.ID.startsWith("V")){
            order.put("ID",o.ID);
            order.put("DoctorID",o.DoctorID);
            order.put("StartTime", FirebaseNumberAndDateTimeProcess.timeToString(o.StartTime,"hh:mm:ss"));
            order.put("EndTime", FirebaseNumberAndDateTimeProcess.timeToString(o.EndTime,"hh:mm:ss"));
            order.put("PatientID",o.PatientID);
            order.put("Fee",o.Fee);
            order.put("DateAppoint", FirebaseNumberAndDateTimeProcess.dateToString(o.DateAppoint,"dd/MM/yyyy"));
        }
        return order;
    }
}