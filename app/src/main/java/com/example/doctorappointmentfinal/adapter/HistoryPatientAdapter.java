package com.example.doctorappointmentfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.appclass.Doctor;
import com.example.doctorappointmentfinal.appclass.Faculty;
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.example.doctorappointmentfinal.appclass.Order;
import com.example.doctorappointmentfinal.appclass.People;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HistoryPatientAdapter extends RecyclerView.Adapter<HistoryPatientAdapter.ViewHolder> {

    private Context context;
    ArrayList<Order> historyPatientAppointmentLists;
    private ItemClickListener clickListener;

    public HistoryPatientAdapter (ArrayList<Order> historyPatientAppointmentLists, HistoryPatientAdapter.ItemClickListener clickListener){
        this.historyPatientAppointmentLists = historyPatientAppointmentLists;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public HistoryPatientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_history_item, parent, false);

        HistoryPatientAdapter.ViewHolder viewHolder =
                new HistoryPatientAdapter.ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryPatientAdapter.ViewHolder holder, int position) {
        //////Phan code xu ly backend
        FirebaseNumberAndDateTimeProcess.db.collection("Doctors").whereEqualTo("ID",historyPatientAppointmentLists.get(position).DoctorID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot q: task.getResult()){
                            Doctor d=(Doctor) People.convertFromQuery(q);
                            holder.historyDoctorName.setText(d.Name);
                            FirebaseNumberAndDateTimeProcess.db.collection("Faculty").whereEqualTo("ID",d.SpecialityID).get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for(QueryDocumentSnapshot d1: task.getResult()){
                                                Faculty f=Faculty.covertFromQuery(d1);
                                                holder.historySpecialtyName.setText(f.Name);
                                                break;
                                            }
                                        }
                                    });
                            break;
                        }
                    }
                });
        if(historyPatientAppointmentLists.get(position).ID.startsWith("V")) holder.historyAppointmentType.setText("Video call order");
        else holder.historyAppointmentType.setText("Hospital order");
        holder.historyTotalFee.setText(String.format("$ %.1f",historyPatientAppointmentLists.get(position).Fee));
        holder.historyDateTime.setText(FirebaseNumberAndDateTimeProcess.dateToString(historyPatientAppointmentLists.get(position).DateAppoint,"EEE dd/MM/yyyy")+" from: "+FirebaseNumberAndDateTimeProcess.timeToString(historyPatientAppointmentLists.get(position).StartTime,"hh:mm aa")+" - "+FirebaseNumberAndDateTimeProcess.timeToString(historyPatientAppointmentLists.get(position).EndTime,"hh:mm aa"));
        ///////////////////////////
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(historyPatientAppointmentLists.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyPatientAppointmentLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView historyDoctorName;
        private TextView historySpecialtyName;
        private TextView historyAppointmentType;
        private TextView historyDateTime;
        private TextView historyTotalFee;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyDoctorName = itemView.findViewById(R.id.historyDoctorName);
            historySpecialtyName = itemView.findViewById(R.id.historySpecialtyName);
            historyAppointmentType = itemView.findViewById(R.id.historyAppointmentType);
            historyDateTime = itemView.findViewById(R.id.historyDateTime);
            historyTotalFee = itemView.findViewById(R.id.historyTotalFee);
        }
    }

    public interface ItemClickListener {
        void onItemClick(Order historyPatientAppointmentLists);
    }
}
