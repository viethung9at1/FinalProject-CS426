package com.example.doctorappointmentfinal.fragmentAppointment;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.doctorappointmentfinal.MerchantZalopayDemo.AppInfo;
import com.example.doctorappointmentfinal.MerchantZalopayDemo.CreateOrder;
import com.example.doctorappointmentfinal.R;
import com.google.android.material.card.MaterialCardView;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientAppointmentPayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientAppointmentPayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientAppointmentPayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientAppointmentPayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientAppointmentPayFragment newInstance(String param1, String param2) {
        PatientAppointmentPayFragment fragment = new PatientAppointmentPayFragment();
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
        View view = inflater.inflate(R.layout.fragment_patient_appointment_pay, container, false);


        //Toggle button
        ToggleButton choosePay1=(ToggleButton) view.findViewById(R.id.choosePay1);
        ToggleButton choosePay2=(ToggleButton) view.findViewById(R.id.choosePay2);
        ToggleButton choosePay3=(ToggleButton) view.findViewById(R.id.choosePay3);
        ToggleButton choosePay4=(ToggleButton) view.findViewById(R.id.choosePay4);
        ToggleButton choosePay5=(ToggleButton) view.findViewById(R.id.choosePay5);

        choosePay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choosePay2.isChecked() || choosePay3.isChecked() || choosePay4.isChecked() || choosePay5.isChecked())
                {
                    choosePay2.setChecked(false);
                    choosePay3.setChecked(false);
                    choosePay4.setChecked(false);
                    choosePay5.setChecked(false);
                }
            }
        });

        choosePay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choosePay1.isChecked() || choosePay3.isChecked() || choosePay4.isChecked() || choosePay5.isChecked())
                {
                    choosePay1.setChecked(false);
                    choosePay3.setChecked(false);
                    choosePay4.setChecked(false);
                    choosePay5.setChecked(false);
                }
            }
        });

        choosePay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choosePay1.isChecked() || choosePay2.isChecked() || choosePay4.isChecked() || choosePay5.isChecked())
                {
                    choosePay1.setChecked(false);
                    choosePay2.setChecked(false);
                    choosePay4.setChecked(false);
                    choosePay5.setChecked(false);
                }
            }
        });

        choosePay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choosePay1.isChecked() || choosePay2.isChecked() || choosePay3.isChecked() || choosePay5.isChecked())
                {
                    choosePay1.setChecked(false);
                    choosePay2.setChecked(false);
                    choosePay3.setChecked(false);
                    choosePay5.setChecked(false);
                }
            }
        });

        choosePay5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choosePay1.isChecked() || choosePay2.isChecked() || choosePay3.isChecked() || choosePay4.isChecked())
                {
                    choosePay1.setChecked(false);
                    choosePay2.setChecked(false);
                    choosePay3.setChecked(false);
                    choosePay4.setChecked(false);
                }
            }
        });


        //pay button
        MaterialCardView buttonPay = (MaterialCardView) view.findViewById(R.id.buttonPay);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choosePay3.isChecked())
                {
                    //Add Zalo API here
                    StrictMode.ThreadPolicy policy = new
                            StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);

                    CreateOrder orderApi = new CreateOrder();
                    try {
                        JSONObject data = orderApi.createOrder("280000");
                        String code = data.getString("returncode");

                        if (code.equals("1")) {

                            String token = data.getString("zptranstoken");

                            ZaloPaySDK.getInstance().payOrder(getActivity(), token, "demozpdk://app", new PayOrderListener() {
                                @Override
                                public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                                    Toast.makeText(getActivity(), "Successful payment", Toast.LENGTH_SHORT);
                                }

                                @Override
                                public void onPaymentCanceled(String zpTransToken, String appTransID) {
                                    Toast.makeText(getActivity(), "Payment is canceled", Toast.LENGTH_SHORT);
                                }

                                @Override
                                public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                                    Toast.makeText(getActivity(), "Failed to pay", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //@Override
                    
                    //End Zalo API here


                    // These following lines are for fragment transaction, do not change
                   /* Fragment fragment=new PatientOrderDetailCallLaterFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.patientAppointmentPayContainer,fragment, "step7YourOrder");
                    transaction.addToBackStack(null);
                    transaction.commit();*/

                    if (getArguments().getString("messageFromCallToPay")!=null)
                       {
                        String valueCall=getArguments().getString("messageFromCallToPay");
                        Bundle bundle2=new Bundle();
                        bundle2.putString("messageFromPayCallToVideoCall", valueCall);
                        try {
                            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                                    .setServerURL(new URL("https://meet.jit.si"))
                                    .setRoom("test123")
                                    .setAudioMuted(false)
                                    .setVideoMuted(false)
                                    .setAudioOnly(false)
                                    .setConfigOverride("requireDisplayName", true)
                                    .build();
                            JitsiMeetActivity.launch(getContext(),options);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                       }
                    String valueBook=getArguments().getString("messageFromBookToPay");
                    Bundle bundleValue=new Bundle();
                    bundleValue.putString("messageFromPayBookToOrder", valueBook);

                    String valueDate=getArguments().getString("setDateString");
                    //Bundle bundleValueDate=new Bundle();
                    bundleValue.putString("dateToOrderDetail", valueDate);

                    Integer valueTime=getArguments().getInt("setTimeInt");
                    //Bundle bundleValueTime=new Bundle();
                    bundleValue.putInt("timeToOrderDetail", valueTime);

                    Fragment fragment=null;
                    if (valueBook=="Go to clinic")
                    {
                        fragment=new PatientOrderDetailClinicFragment();
                        fragment.setArguments(bundleValue);
                    }
                    else if (valueBook=="Video call later")
                    {
                        fragment=new PatientOrderDetailCallLaterFragment();
                        fragment.setArguments(bundleValue);
                    }
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.patientAppointmentPayContainer,fragment, "step7YourOrder");
                    transaction.addToBackStack(null);
                    transaction.commit();

                }


            }
        });

        return view;
    }


}