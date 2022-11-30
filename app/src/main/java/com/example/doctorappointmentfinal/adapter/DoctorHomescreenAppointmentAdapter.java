package com.example.doctorappointmentfinal.adapter;

import static com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess.db;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorappointmentfinal.Interface.ItemClickListener;
import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.appclass.Doctor;
import com.example.doctorappointmentfinal.appclass.Faculty;
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.example.doctorappointmentfinal.appclass.Order;
import com.example.doctorappointmentfinal.appclass.Patient;
import com.example.doctorappointmentfinal.appclass.People;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class DoctorHomescreenAppointmentAdapter extends
        RecyclerView.Adapter<DoctorHomescreenAppointmentAdapter.ViewHolder> {

    private Context context;
    ArrayList<Order> doctorAppointmentLists;
    private ItemClickListener clickListener;

    public DoctorHomescreenAppointmentAdapter(ArrayList<Order> doctorAppointmentLists, ItemClickListener clickListener){
        this.doctorAppointmentLists = doctorAppointmentLists;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public DoctorHomescreenAppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(com.example.doctorappointmentfinal.
                R.layout.activity_appointment_item, parent, false);

        DoctorHomescreenAppointmentAdapter.ViewHolder viewHolder =
                new DoctorHomescreenAppointmentAdapter.ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorHomescreenAppointmentAdapter.ViewHolder holder, int position) {
        int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier("person", "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.patientAvatar);
        /* Phan code xu ly backend*/
        db.collection("Patients").whereEqualTo("ID",doctorAppointmentLists.get(position).PatientID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot q: task.getResult()){
                            Patient p=(Patient) People.convertFromQuery(q);
                            holder.doctorName.setText(p.Name);
                            holder.faculty.setText(p.Gender==false?"Male":"Female");
                            holder.patientName.setText(FirebaseNumberAndDateTimeProcess.dateToString(p.DateOfBrith,"EEE, dd/MM/yyyy"));
                            StorageReference sf= FirebaseNumberAndDateTimeProcess.storageReference.child("/DoctorImage").child(p.PicImage+".png");
                            try{
                                final File localFile=File.createTempFile(p.PicImage,"png");
                                sf.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bmp= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                        holder.patientAvatar.setImageBitmap(bmp);
                                    }
                                });
                            }catch (Exception e){

                            }
                        }
                    }
                });
        holder.dateAndTime.setText(FirebaseNumberAndDateTimeProcess.dateToString(doctorAppointmentLists.get(position).DateAppoint,"dd/MM/yyyy")+":"+FirebaseNumberAndDateTimeProcess.timeToString(doctorAppointmentLists.get(position).StartTime,"hh:mm aa")+"-"+FirebaseNumberAndDateTimeProcess.timeToString(doctorAppointmentLists.get(position).EndTime,"hh:mm aa"));
        //////////////////////////////////////////////////////////////
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(doctorAppointmentLists.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorAppointmentLists.size();
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

        /*
        Luu y, trong file activity_appointment_item, khi su dung lai thi mot so thanh phan se thay
        doi nhu sau
        * doctorName --> patientName
        * Faculty --> gender
        * Patient Name --> date of birth (dob)
        * Con lai van giu nguyen nhu cu
        * */

        /*File này sẽ liên kết với DoctorHomeFragment.java, fragment_doctor_home.xml va
        activity_appointment_item.xml*/
    }

    public interface ItemClickListener
    {
        public void onItemClick(Order doctorAppointmentList);
    }
}
