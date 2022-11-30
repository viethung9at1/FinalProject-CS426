package com.example.doctorappointmentfinal.fragmentAppointment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.doctorappointmentfinal.MainActivityPatient;
import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.appclass.Doctor;
import com.example.doctorappointmentfinal.appclass.Faculty;
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.example.doctorappointmentfinal.appclass.HospitalOrder;
import com.example.doctorappointmentfinal.appclass.Order;
import com.example.doctorappointmentfinal.appclass.Patient;
import com.example.doctorappointmentfinal.model.TypeAppointment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.text.ParseException;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientOrderDetailClinicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientOrderDetailClinicFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientOrderDetailClinicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientOrderDetailClinicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientOrderDetailClinicFragment newInstance(String param1, String param2) {
        PatientOrderDetailClinicFragment fragment = new PatientOrderDetailClinicFragment();
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
        View view= inflater.inflate(R.layout.fragment_patient_order_detail_clinic, container, false);
        String value=getArguments().getString("messageFromPayBookToOrder");
        Bundle bundleValue=new Bundle();
        bundleValue.putString("messageFromOrderClinicToMain", value);
        MaterialCardView clinicGoToMainPage=(MaterialCardView) view.findViewById(R.id.clinicGoToMainPage);
        clinicGoToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MainActivityPatient.class);
                startActivity(intent);
            }
        });

        return view;
    }
    public void onViewCreated(View v, Bundle b){
        TextView orderNumber=getView().findViewById(R.id.orderClinicNumber);
        TextView orderClinicType=getView().findViewById(R.id.orderClinicType);
        TextView orderAddress=getView().findViewById(R.id.orderClinicAddress);
        TextView orderRoom=getView().findViewById(R.id.orderClinicRoom);
        TextView orderSpecs=getView().findViewById(R.id.orderClinicSpecialty);
        TextView orderDoctor=getView().findViewById(R.id.orderClinicDoctor);
        TextView orderDate=getView().findViewById(R.id.orderClinicDate);
        TextView orderTime=getView().findViewById(R.id.orderClinicTime);
        TextView orderFee=getView().findViewById(R.id.orderClinicTotalFee);
        TextView orderPatient=getView().findViewById(R.id.orderClinicPatientName);
        TextView orderPatientDOB=getView().findViewById(R.id.orderClinicPatientDob);
        HospitalOrder o=new HospitalOrder();
        orderClinicType.setText(TypeAppointment.CurrentType.getTypeAppointment());
        orderAddress.setText(Doctor.CurrentDoctor.Address);
        o.AddClinic=Doctor.CurrentDoctor.Address;
        String getDate=getArguments().getString("dateToOrderDetail");
        o.DateAppoint= FirebaseNumberAndDateTimeProcess.stringToDate(getDate,"dd/MM/yyyy");
        orderDate.setText(FirebaseNumberAndDateTimeProcess.dateToString(o.DateAppoint,"dd/MM/yyyy"));
        o.Room=Character.toString(FirebaseNumberAndDateTimeProcess.randomChar('A','F'))+Integer.toString(FirebaseNumberAndDateTimeProcess.randomInt(1,10));
        orderRoom.setText(o.Room);
        orderSpecs.setText(Faculty.CurrentFaculty.Name);
        orderDoctor.setText(Doctor.CurrentDoctor.Name);
        o.DoctorID= Doctor.CurrentDoctor.ID;
        int choiceOfTime=getArguments().getInt("timeToOrderDetail");
        Time sT, eT;
        String oT;
        switch (choiceOfTime){
            case 0:
                sT=new Time(8,30,0);
                eT=new Time(9,30,0);
                oT="8:30 - 9:30 AM";
                break;
            case 1:
                sT=new Time(9,30,0);
                eT=new Time(10,30,0);
                oT="9:30 - 10:30 AM";
                break;
            case 2:
                sT=new Time(13,30,0);
                eT=new Time(14,30,0);
                oT="1:30 - 2:30 PM";
                break;
            case 3:
                sT=new Time(14,30,0);
                eT=new Time(15,30,0);
                oT="2:30 - 3:30 PM";
                break;
            default:
                sT=eT=new Time(0,0,0);
                oT="";
        }
        o.StartTime=sT;
        o.EndTime=eT;
        orderTime.setText(oT);
        o.Fee=FirebaseNumberAndDateTimeProcess.randomInt(0,10)+new Random().nextFloat();
        orderFee.setText(String.format("$ %.1f",o.Fee));
        orderPatient.setText(Patient.CurrentPatient.Name);
        o.PatientID=Patient.CurrentPatient.ID;
        //orderPatientDOB.setText("ABC");
        orderPatientDOB.setText(FirebaseNumberAndDateTimeProcess.dateToString(Patient.CurrentPatient.DateOfBrith,"dd/MM/yyyy"));
        FirebaseNumberAndDateTimeProcess.db.collection("Orders").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int numOfClinicOrder=0;
                        for(QueryDocumentSnapshot q: task.getResult()){
                            try {
                                Order o=Order.convertFromQuery(q);
                                if(o.ID.startsWith("H")) numOfClinicOrder++;

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String orderNum="H"+Integer.toString(numOfClinicOrder+1);
                            o.ID=orderNum;
                            orderNumber.setText(orderNum);
                        }
                        FirebaseNumberAndDateTimeProcess.db.collection("Orders").add(Order.convertToMap(o));
                    }
                });
    }

}