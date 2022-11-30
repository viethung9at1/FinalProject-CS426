package com.example.doctorappointmentfinal.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.appclass.Doctor;
import com.example.doctorappointmentfinal.appclass.Faculty;
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.example.doctorappointmentfinal.appclass.Order;
import com.example.doctorappointmentfinal.appclass.Patient;
import com.example.doctorappointmentfinal.appclass.People;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class OrderPatientHomeAdapter extends RecyclerView.Adapter<OrderPatientHomeAdapter.ViewHolder>{

    private Context context;
    ArrayList<Order> patientAppointmentLists;
    private ItemClickListener clickListener;

    public OrderPatientHomeAdapter(ArrayList<Order> patientAppointmentLists, ItemClickListener clickListener) {
        this.patientAppointmentLists = patientAppointmentLists;
        this.clickListener=clickListener;
    }


    @NonNull
    @Override
    public OrderPatientHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(com.example.doctorappointmentfinal.R.layout.activity_appointment_item, parent, false);

        OrderPatientHomeAdapter.ViewHolder viewHolder = new OrderPatientHomeAdapter.ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderPatientHomeAdapter.ViewHolder holder, int position) {
        /*holder.dateAndTime.setText(patientAppointmentLists.get(position).getApp_time());
        holder.faculty.setText(patientAppointmentLists.get(position).getFaculty());
        holder.patientName.setText(patientAppointmentLists.get(position).getPatientName());
       holder.doctorName.setText(patientAppointmentLists.get(position).getDoctorName());*/
        int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier("person", "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.patientAvatar);
        Date dtStart;
        Time sT=patientAppointmentLists.get(position).StartTime;
        Time eT=patientAppointmentLists.get(position).EndTime;
        dtStart=patientAppointmentLists.get(position).DateAppoint;
        String DateTimeStartEnd= FirebaseNumberAndDateTimeProcess.dateToString(dtStart,"EEE, dd/MM/yyyy")+": At "+ FirebaseNumberAndDateTimeProcess.timeToString(sT,"hh:mm aa")+"-"+ FirebaseNumberAndDateTimeProcess.timeToString(eT,"hh:mm aa");
        holder.dateAndTime.setText(DateTimeStartEnd);
        FirebaseNumberAndDateTimeProcess.db.collection("Doctors").whereEqualTo("ID",patientAppointmentLists.get(position).DoctorID).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    Doctor d=(Doctor) People.convertFromQuery(documentSnapshot);
                                    holder.doctorName.setText(d.Name);
                                    FirebaseNumberAndDateTimeProcess.db.collection("Faculty").whereEqualTo("ID",d.SpecialityID).get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    for(QueryDocumentSnapshot d:task.getResult())  {
                                                        Faculty f=Faculty.covertFromQuery(d);
                                                        holder.faculty.setText(f.Name);
                                                        break;
                                                    }
                                                }
                                            });
                                    break;
                                }
                            }
                        });
        FirebaseNumberAndDateTimeProcess.db.collection("Patients").whereEqualTo("ID",patientAppointmentLists.get(position).PatientID).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for(QueryDocumentSnapshot d: task.getResult()){
                                    Patient p=(Patient) People.convertFromQuery(d);
                                    holder.patientName.setText(p.Name);
                                }
                            }
                        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(patientAppointmentLists.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientAppointmentLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dateAndTime;
        private ImageView patientAvatar;
        private TextView doctorName;
        private TextView faculty;
        private TextView patientName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateAndTime = itemView.findViewById(R.id.textView_date_time);
            patientAvatar = itemView.findViewById(R.id.imageView_activity_appointment_item);
            doctorName = itemView.findViewById(R.id.textview_doctor_name);
            faculty = itemView.findViewById(R.id.textview_faculty);
            patientName = itemView.findViewById(R.id.textview_patient_name);
        }
    }

    public interface ItemClickListener
    {
        public void onItemClick(Order patientAppointmentList);
    }
}
