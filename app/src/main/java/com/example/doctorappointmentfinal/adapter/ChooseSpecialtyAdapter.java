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

import com.bumptech.glide.Glide;
import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.appclass.Faculty;
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;

public class ChooseSpecialtyAdapter extends RecyclerView.Adapter<ChooseSpecialtyAdapter.ViewHolder> {



    private Context context;
    ArrayList<Faculty> specialtyModels;
    private ItemClickListener clickListener;

    public ChooseSpecialtyAdapter(ArrayList<Faculty> specialtyModels, ItemClickListener clickListener) {
        this.specialtyModels = specialtyModels;
        this.clickListener=clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_choosespecialty, parent, false);

        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textSpecialty.setText(specialtyModels.get(position).Name);
        String picUrl=specialtyModels.get(position).PicName;
        int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.picSpecialty);
        StorageReference sf= FirebaseNumberAndDateTimeProcess.storageReference.child("/FacultyImage").child(specialtyModels.get(position).PicName+".png");
        try{
            final File localFile=File.createTempFile(specialtyModels.get(position).PicName,"png");
            sf.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bmp= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    holder.picSpecialty.setImageBitmap(bmp);
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

                clickListener.onItemClick(specialtyModels.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return specialtyModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textSpecialty;
        ImageView picSpecialty;
        //CardView typeAppointmentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textSpecialty =itemView.findViewById(R.id.textSpecialty);
            picSpecialty =itemView.findViewById(R.id.picSpecialty);
            //typeAppointmentLayout=itemView.findViewById(R.id.typeAppointmentLayout);
        }
    }

    public interface ItemClickListener
    {
        public void onItemClick(Faculty specialtyModel);
    }
}

