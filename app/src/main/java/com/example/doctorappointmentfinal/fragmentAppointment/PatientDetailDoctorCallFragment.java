package com.example.doctorappointmentfinal.fragmentAppointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.appclass.Doctor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientDetailDoctorCallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientDetailDoctorCallFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientDetailDoctorCallFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientDetailDoctorCallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientDetailDoctorCallFragment newInstance(String param1, String param2) {
        PatientDetailDoctorCallFragment fragment = new PatientDetailDoctorCallFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_detail_doctor_call, container, false);


                    /*if (value=="Go to clinic")
                    {
                        fragment=new PatientOrderDetailClinicFragment();
                        fragment.setArguments(bundleValue);
                    }
                    else if (value=="Video call later")
                    {
                        fragment=new PatientOrderDetailCallLaterFragment();
                        fragment.setArguments(bundleValue);
                    }*/
        Button doctorDetailCallButton=(Button) view.findViewById(R.id.doctorDetailCallButton);
        doctorDetailCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value=getArguments().getString("messageToDetailDoctor");
                Bundle bundleValue=new Bundle();
                bundleValue.putString("messageFromCallToPay", value);
                Fragment fragment=new PatientAppointmentPayFragment();
                fragment.setArguments(bundleValue);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.patientDetailDoctorCallContainer,fragment, "step6PayCall");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
    public void onViewCreated(View v, Bundle b){
        TextView doctorName=getView().findViewById(R.id.doctorDetailCallFullName);
        TextView doctorRating=getView().findViewById(R.id.doctorDetailCallRating);
        TextView doctorExp=getView().findViewById(R.id.doctorDetailCallYearExperience);
        TextView doctorAdd=getView().findViewById(R.id.doctorDetailCallAddressClinic);
        doctorName.setText(Doctor.CurrentDoctor.Name);
        doctorRating.setText(String.format("%.1f",Doctor.CurrentDoctor.Rating));
        doctorExp.setText(String.valueOf(Doctor.CurrentDoctor.Experience));
        doctorAdd.setText(Doctor.CurrentDoctor.Address);
    }
}