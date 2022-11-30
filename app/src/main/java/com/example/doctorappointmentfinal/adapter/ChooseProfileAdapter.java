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
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.example.doctorappointmentfinal.appclass.Patient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class ChooseProfileAdapter extends RecyclerView.Adapter<ChooseProfileAdapter.ViewHolder> {
    private Context context;
    ArrayList<Patient> patientProfiles;
    private ItemClickListener clickListener;

    public ChooseProfileAdapter(ArrayList<Patient> patientProfiles, ItemClickListener clickListener) {
        this.patientProfiles = patientProfiles;
        this.clickListener=clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_chooseprofile, parent, false);

        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.chooseProfileFullName.setText(patientProfiles.get(position).Name);
        holder.chooseProfileGender.setText(patientProfiles.get(position).Gender==false?"Male":"Female");
        holder.chooseProfileDob.setText(FirebaseNumberAndDateTimeProcess.dateToString(patientProfiles.get(position).DateOfBrith,"E, dd/MM/yyyy"));
        String picUrl="";
        picUrl="person";
        //int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        //Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.chooseProfilePic);
        StorageReference sf= FirebaseNumberAndDateTimeProcess.storageReference.child("/PatientImage").child(picUrl+".png");
        try{
            final File localFile=File.createTempFile(patientProfiles.get(position).PicImage,"png");
            sf.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bmp= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    holder.chooseProfilePic.setImageBitmap(bmp);
                }
            });
        }catch (Exception e){}

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

                clickListener.onItemClick(patientProfiles.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return patientProfiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView chooseProfileFullName;
        TextView chooseProfileGender;
        TextView chooseProfileDob;
        ImageView chooseProfilePic;
        //CardView typeAppointmentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chooseProfileFullName =(TextView) itemView.findViewById(R.id.chooseProfileFullName);
            chooseProfileGender =(TextView)itemView.findViewById(R.id.chooseProfileGender);
            chooseProfileDob=(TextView)itemView.findViewById(R.id.chooseProfileDob);
            chooseProfilePic=(ImageView) itemView.findViewById(R.id.chooseProfilePic);
            //typeAppointmentLayout=itemView.findViewById(R.id.typeAppointmentLayout);
        }
    }

    public interface ItemClickListener
    {
        public void onItemClick(Patient patientProfile);
    }
}
