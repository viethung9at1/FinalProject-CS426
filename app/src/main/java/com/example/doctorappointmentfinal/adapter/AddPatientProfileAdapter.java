package com.example.doctorappointmentfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.example.doctorappointmentfinal.appclass.Patient;

import java.util.ArrayList;

public class AddPatientProfileAdapter extends RecyclerView.Adapter<AddPatientProfileAdapter.ViewHolder> {
    private Context context;
    ArrayList<Patient> addPatientProfiles;
    private ItemClickListener clickListener;

    public AddPatientProfileAdapter(ArrayList<Patient> addPatientProfiles, ItemClickListener clickListener) {
        this.addPatientProfiles = addPatientProfiles;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_chooseprofile, parent, false);

        AddPatientProfileAdapter.ViewHolder viewHolder = new AddPatientProfileAdapter.ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fullname.setText(addPatientProfiles.get(position).Name);
        holder.gender.setText(addPatientProfiles.get(position).Gender==false?"Male":"Female");
        holder.dob.setText(FirebaseNumberAndDateTimeProcess.dateToString(addPatientProfiles.get(position).DateOfBrith,"E, dd/MM/yyyy"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(addPatientProfiles.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return addPatientProfiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView fullname;
        private TextView gender;
        private TextView dob;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullname = itemView.findViewById(R.id.chooseProfileFullName);
            gender = itemView.findViewById(R.id.chooseProfileGender);
            dob = itemView.findViewById(R.id.chooseProfileDob);
        }
    }

    public interface ItemClickListener
    {
        public void onItemClick(Patient addPatientProfiles);
    }
}
