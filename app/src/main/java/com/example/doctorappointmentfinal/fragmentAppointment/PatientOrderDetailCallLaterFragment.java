package com.example.doctorappointmentfinal.fragmentAppointment;

import static com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess.db;

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
import com.example.doctorappointmentfinal.appclass.Order;
import com.example.doctorappointmentfinal.appclass.Patient;
import com.example.doctorappointmentfinal.appclass.VideoCallOrder;
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
 * Use the {@link PatientOrderDetailCallLaterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientOrderDetailCallLaterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientOrderDetailCallLaterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientOrderDetailCallLaterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientOrderDetailCallLaterFragment newInstance(String param1, String param2) {
        PatientOrderDetailCallLaterFragment fragment = new PatientOrderDetailCallLaterFragment();
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
        View view = inflater.inflate(R.layout.fragment_patient_order_detail_call_later, container, false);

        String value=getArguments().getString("messageFromPayBookToOrder");
        Bundle bundleValue=new Bundle();
        bundleValue.putString("messageFromOrderCallLaterToMain", value);

        MaterialCardView callLaterGoToMainPage=(MaterialCardView) view.findViewById(R.id.callLaterGoToMainPage);
        callLaterGoToMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MainActivityPatient.class);
                startActivity(intent);
            }
        });

        return view;
    }
    public void onViewCreated(View v, Bundle b){
        TextView orderNumber=getView().findViewById(R.id.orderCallLaterNumber);
        TextView orderSpecs=getView().findViewById(R.id.orderCallLaterSpecialty);
        TextView orderDoctor=getView().findViewById(R.id.orderCallLaterDoctor);
        TextView orderDate=getView().findViewById(R.id.orderCallLaterDate);
        TextView orderTime=getView().findViewById(R.id.orderCallLaterTime);
        TextView orderFee=getView().findViewById(R.id.orderCallLaterTotalFee);
        TextView orderPatientName=getView().findViewById(R.id.orderCallLaterPatientName);
        TextView orderPatientDob=getView().findViewById(R.id.orderCallLaterPatientDob);
        VideoCallOrder o=new VideoCallOrder();
        db.collection("Orders").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int numberOfOrder=0;
                        for(QueryDocumentSnapshot d: task.getResult()){
                            try {
                                Order o=Order.convertFromQuery(d);
                                if(o.ID.startsWith("V")) numberOfOrder++;
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        String orderNum="V"+numberOfOrder+1;
                        o.ID=orderNum;
                        orderNumber.setText(orderNum);
                    }
                });
        orderSpecs.setText(Faculty.CurrentFaculty.Name);
        orderDoctor.setText(Doctor.CurrentDoctor.Name);
        o.DoctorID=Doctor.CurrentDoctor.ID;
        orderPatientName.setText(Patient.CurrentPatient.Name);
        o.PatientID=Patient.CurrentPatient.ID;
        orderPatientDob.setText(FirebaseNumberAndDateTimeProcess.dateToString(Patient.CurrentPatient.DateOfBrith, "dd/MM/yyyy"));
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
        String getDate=getArguments().getString("dateToOrderDetail");
        o.DateAppoint= FirebaseNumberAndDateTimeProcess.stringToDate(getDate,"dd/MM/yyyy");
        orderDate.setText(FirebaseNumberAndDateTimeProcess.dateToString(o.DateAppoint,"dd/MM/yyyy"));
    }
}