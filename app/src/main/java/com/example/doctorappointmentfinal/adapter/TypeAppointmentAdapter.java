package com.example.doctorappointmentfinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.fragmentAppointment.PatientChooseDoctorFragment;
import com.example.doctorappointmentfinal.model.TypeAppointment;

import java.util.ArrayList;

public class TypeAppointmentAdapter extends RecyclerView.Adapter<TypeAppointmentAdapter.ViewHolder> {



    private Context context;
    ArrayList<TypeAppointment> typeAppointments;
    private ItemClickListener clickListener;

    public TypeAppointmentAdapter(ArrayList<TypeAppointment> typeAppointments, ItemClickListener clickListener) {
        this.typeAppointments = typeAppointments;
        this.clickListener=clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_typeappointment, parent, false);

        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textTypeAppointment.setText(typeAppointments.get(position).getTypeAppointment());
        String picUrl="";
        switch(position)
        {
            case 0:{
                picUrl="typeappointment1clinic";
                break;
            }
            case 1:{
                picUrl="typeappointment2vid";
                break;
            }
            case 2:{
                picUrl="typeappointment3later";
                break;
            }
        }
        int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.picTypeAppointment);



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

                clickListener.onItemClick(typeAppointments.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return typeAppointments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTypeAppointment;
        ImageView picTypeAppointment;
        //CardView typeAppointmentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTypeAppointment =itemView.findViewById(R.id.textTypeAppointment);
            picTypeAppointment =itemView.findViewById(R.id.picTypeAppointment);
            //typeAppointmentLayout=itemView.findViewById(R.id.typeAppointmentLayout);
        }
    }

    public interface ItemClickListener
    {
        public void onItemClick(TypeAppointment typeappointment);
    }
}

