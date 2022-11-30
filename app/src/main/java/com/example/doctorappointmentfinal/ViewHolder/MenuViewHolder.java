/*
package com.example.doctorappointmentfinal.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorappointmentfinal.Interface.ItemClickListener;
import com.example.doctorappointmentfinal.R;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtTypeAppointment;
    public ImageView imageView;
    private ItemClickListener itemClickListener;

    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);
        txtTypeAppointment=(TextView) itemView.findViewById(R.id.textTypeAppointment);
        imageView=(ImageView) itemView.findViewById(R.id.picTypeAppointment);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAbsoluteAdapterPosition(), false);
    }
}
*/
