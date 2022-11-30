package com.example.doctorappointmentfinal.fragmentAppointment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.appclass.Doctor;
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientDetailDoctorBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientDetailDoctorBookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientDetailDoctorBookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientDetailDoctorBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientDetailDoctorBookFragment newInstance(String param1, String param2) {
        PatientDetailDoctorBookFragment fragment = new PatientDetailDoctorBookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private String SelectedDate="";
    private int SelectedTime=-1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_patient_detail_doctor_book, container, false);


        return view;
    }
    public void onViewCreated(View v, Bundle b){
        TextView doctorName=getView().findViewById(R.id.doctorDetailBookFullName);
        TextView doctorRating=getView().findViewById(R.id.doctorDetailBookRating);
        TextView doctorExp=getView().findViewById(R.id.doctorDetailBookYearExperience);
        TextView doctorAdd=getView().findViewById(R.id.doctorDetailBookAddressClinic);
        ImageView imgView=getView().findViewById(R.id.doctorDetailBookPhoto);
        doctorName.setText(Doctor.CurrentDoctor.Name);
        doctorExp.setText(String.valueOf(Doctor.CurrentDoctor.Experience));
        doctorAdd.setText(Doctor.CurrentDoctor.Address);
        doctorRating.setText(String.format("%.1f",Doctor.CurrentDoctor.Rating));
        StorageReference sf= FirebaseNumberAndDateTimeProcess.storageReference.child("/DoctorImage").child(Doctor.CurrentDoctor.PicImage+".png");
        try{
            final File localFile=File.createTempFile(Doctor.CurrentDoctor.PicImage,"png");
            sf.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bmp= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imgView.setImageBitmap(bmp);
                }
            });
        }catch (Exception e){
        }
        MaterialCardView buttonViewCalendar=(MaterialCardView) getView().findViewById(R.id.buttonViewCalendar);
        TextView selectedDate=(TextView)getView().findViewById(R.id.selectedDate);
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        buttonViewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        selectedDate.setText("Selected date: " + materialDatePicker.getHeaderText());// MMM, DD,YYYY
                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendar.setTimeInMillis((long)selection);
                        SelectedDate=FirebaseNumberAndDateTimeProcess.dateToString(calendar.getTime(),"dd/MM/yyyy");
                        // in the above statement, getHeaderText
                        // is the selected date preview from the
                        // dialog
                    }
                });
        ToggleButton choose830to930=(ToggleButton) getView().findViewById(R.id.choose830to930);
        ToggleButton choose930to1030=(ToggleButton) getView().findViewById(R.id.choose930to1030);
        ToggleButton choose130to230=(ToggleButton) getView().findViewById(R.id.choose130to230);
        ToggleButton choose230to330=(ToggleButton) getView().findViewById(R.id.choose230to330);
        TextView selectedTime=(TextView) getView().findViewById(R.id.selectedTime);

        choose830to930.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choose930to1030.isChecked() || choose130to230.isChecked() || choose230to330.isChecked())
                {
                    choose930to1030.setChecked(false);
                    choose130to230.setChecked(false);
                    choose230to330.setChecked(false);
                    //choose730to830.setChecked(true);
                }
                SelectedTime=0;
                selectedTime.setText("Selected time: "+choose830to930.getText());
            }
        });
        choose930to1030.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choose830to930.isChecked() || choose130to230.isChecked() || choose230to330.isChecked())
                {
                    choose830to930.setChecked(false);
                    choose130to230.setChecked(false);
                    choose230to330.setChecked(false);
                    //choose830to930.setChecked(true);
                }
                SelectedTime=1;
                selectedTime.setText("Selected time: "+choose930to1030.getText());
            }
        });
        choose130to230.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choose830to930.isChecked() || choose930to1030.isChecked() || choose230to330.isChecked())
                {
                    choose830to930.setChecked(false);
                    choose930to1030.setChecked(false);
                    choose230to330.setChecked(false);
                    //choose930to1030.setChecked(true);
                }
                SelectedTime=2;
                selectedTime.setText("Selected time: "+choose130to230.getText());
            }
        });
        choose230to330.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choose830to930.isChecked() || choose930to1030.isChecked() || choose130to230.isChecked())
                {
                    choose830to930.setChecked(false);
                    choose930to1030.setChecked(false);
                    choose130to230.setChecked(false);
                    //choose1030to1130.setChecked(true);
                }
                SelectedTime=3;
                selectedTime.setText("Selected time: "+choose230to330.getText());
            }
        });


        Button doctorDetailBookButton=(Button) getView().findViewById(R.id.doctorDetailBookButton);
        doctorDetailBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDate.getText()!="Selected date: " && selectedTime.getText()!="Selected time: " || SelectedTime==-1 || SelectedDate.equals(""))
                {
                    String value=getArguments().getString("messageToDetailDoctor");
                    Bundle bundleValue=new Bundle();
                    bundleValue.putString("messageFromBookToPay", value);
                    //Bundle b=new Bundle();
                    bundleValue.putString("setDateString",SelectedDate);
                    //Bundle c=new Bundle();
                    bundleValue.putInt("setTimeInt",SelectedTime);
                    //FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    Fragment fragment=null;
                    /*if (value=="Go to clinic")
                    {
                        fragment=new PatientOrderDetailClinicFragment();
                        fragment.setArguments(bundleValue);
                        transaction.add(R.id.patientDetailDoctorBookContainer,fragment,"step");

                    }
                    else if (value=="Video call later")
                    {
                        fragment=new PatientOrderDetailCallLaterFragment();
                        fragment.setArguments(bundleValue);
                        transaction.add(R.id.patientDetailDoctorBookContainer,fragment,"step");
                    }*/
                    fragment=new PatientAppointmentPayFragment();
                    fragment.setArguments(bundleValue);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    /*transaction.add(R.id.patientDetailDoctorBookContainer,fragment, "step6PayBook");
                        transaction.add(R.id.patientOrderDetailCallLaterContainer, fragment);*/
                    /*fragment=new PatientAppointmentPayFragment();
                    fragment.setArguments(bundleValue);*/
                    transaction.add(R.id.patientDetailDoctorBookContainer,fragment, "step6PayBook");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                else Toast.makeText(getContext(), "Please select date and time first!",Toast.LENGTH_LONG).show();
            }
        });
    }
}