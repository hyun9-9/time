package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    TextView time;
    ImageButton button;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    MainActivity mainActivity;
    SendEventListener sendEventListener;
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mainActivity=(MainActivity) getActivity();

        try {
            sendEventListener = (SendEventListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SendEventListener");
        }
    }
    @Override
    public void onDetach(){
        super.onDetach();
        mainActivity=null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        //View v =inflater.inflate(R.layout.fragment_home, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        time =(TextView) rootView.findViewById(R.id.time);
        button=(ImageButton) rootView.findViewById(R.id.button);

        if(getArguments() != null){
            String sp=getArguments().getString("sleep");
            time.setText(" 잠든 시간 : "+sp);
        }





        button.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                long mNow =System.currentTimeMillis();
                String l=Long.toString(mNow);

                Date mReDate =new Date(mNow);
                SimpleDateFormat mFormat=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
                String formatDate =mFormat.format(mReDate);
                sendEventListener.sendMessage(l,formatDate);



                mainActivity.onFragmentChange(1);
                mainActivity.up();
            }
        });


        return rootView;
    }
}