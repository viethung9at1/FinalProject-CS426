package com.example.doctorappointmentfinal.appclass;

import com.google.android.material.timepicker.TimeFormat;

import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VideoCallOrder extends Order{
    public VideoCallOrder(){}
    public VideoCallOrder(String ID, String dID, Time sTime, Time eTime, String pID, float fee, Date dateAppoint){
        super(ID, dID, sTime, eTime, pID, fee,dateAppoint);
    }
    public static Map<String, Object> convertToMap(Order o){
        Map<String, Object> order=new HashMap<>();
        order.put("ID",o.ID);
        order.put("DoctorID",o.DoctorID);
        order.put("StartTime", FirebaseNumberAndDateTimeProcess.timeToString(o.StartTime,"hh:mm:ss"));
        order.put("EndTime", FirebaseNumberAndDateTimeProcess.timeToString(o.EndTime,"hh:mm:ss"));
        order.put("PatientID",o.PatientID);
        order.put("Fee",o.Fee);
        order.put("DateAppoint", FirebaseNumberAndDateTimeProcess.dateToString(o.DateAppoint,"E, dd/MM/yyyy"));
        return order;
    }
}
