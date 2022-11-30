package com.example.doctorappointmentfinal.adapter;

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

import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.appclass.Doctor;
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class ChooseDoctorAdapter extends RecyclerView.Adapter<ChooseDoctorAdapter.ViewHolder> {
    private Context context;
    ArrayList<Doctor> doctors;
    private ItemClickListener clickListener;

    public ChooseDoctorAdapter(ArrayList<Doctor> doctorModels, ItemClickListener clickListener) {
        this.doctors = doctorModels;
        this.clickListener=clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_choosedoctor, parent, false);

        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.chooseDoctorFullName.setText(doctors.get(position).Name);
        holder.chooseDoctorYearExperience.setText(String.valueOf(doctors.get(position).Experience));
        float doctorNumRating=doctors.get(position).Rating;
        holder.chooseDoctorRating.setText(String.format("%.1f", doctorNumRating));
        holder.chooseDoctorAddressClinic.setText(doctors.get(position).Address);
        String picUrl=doctors.get(position).PicImage;
        //int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        //Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.chooseDoctorPic);
        StorageReference sf= FirebaseNumberAndDateTimeProcess.storageReference.child("/DoctorImage").child(picUrl+".png");
        try{
            final File localFile=File.createTempFile(doctors.get(position).PicImage,"png");
            sf.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bmp= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    holder.chooseDoctorPic.setImageBitmap(bmp);
                }
            });
        }catch (Exception e){

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Fragment fragment = null;
                fragment=new PatientChooseDoctorFragment();
                FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
                transaction.replace(R.id.patientAppointmentContainer,fragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
                newFragment.setVisibility(View.GONE);
                textViewTmp.setVisibility(View.GONE);*/

                /*FragmentManager manager=((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction Ft=manager.beginTransaction();
                Ft.replace(R.id.patientAppointmentContainer,new PatientChooseDoctorFragment());
                Ft.addToBackStack(null);
                Ft.commit();*/
                clickListener.onItemClick(doctors.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView chooseDoctorFullName;
        TextView chooseDoctorYearExperience;
        TextView chooseDoctorRating;
        TextView chooseDoctorAddressClinic;
        ImageView chooseDoctorPic;
        //CardView typeAppointmentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chooseDoctorFullName =(TextView) itemView.findViewById(R.id.chooseDoctorFullName);
            chooseDoctorYearExperience =(TextView)itemView.findViewById(R.id.chooseDoctorYearExperience);
            chooseDoctorRating=(TextView)itemView.findViewById(R.id.chooseDoctorRating);
            chooseDoctorAddressClinic=(TextView) itemView.findViewById(R.id.chooseDoctorAddressClinic);
            chooseDoctorPic=(ImageView) itemView.findViewById(R.id.chooseDoctorPic);
            //typeAppointmentLayout=itemView.findViewById(R.id.typeAppointmentLayout);
        }
    }

    public interface ItemClickListener
    {
        public void onItemClick(Doctor doctors);
    }
}
