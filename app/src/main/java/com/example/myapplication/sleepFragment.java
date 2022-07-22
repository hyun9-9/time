package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sleepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sleepFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageButton button;
    ImageView imageView;
    FrameLayout ss;
    TextView textView,textView2;
    public sleepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sleepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static sleepFragment newInstance(String param1, String param2) {
        sleepFragment fragment = new sleepFragment();
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
        View v =inflater.inflate(R.layout.fragment_sleep, container, false);
        /*setHasOptionsMenu(true);
        Bundle extra =getArguments();*/
        //String message=this.getArguments().getString("message");
        ss=(FrameLayout)v.findViewById(R.id.ss);
        //imageView=(ImageView)v.findViewById(R.id.imageButton);
        button=(ImageButton) v.findViewById(R.id.sleep);
        textView=(TextView)v.findViewById(R.id.textView2);
        textView2=(TextView) v.findViewById(R.id.time);
        /*if(wer.equals("0")){
            imageView.setImageResource(R.drawable.sun);
        }
        else {
            imageView.setImageResource(R.drawable.rain);
        }*/
        if(getArguments() != null) {
            String re=getArguments().getString("you");
            String hot=getArguments().getString("hot");
            if(re.equals("0")) {
                //imageView.setImageResource(R.drawable.sun2);
                textView.setTextColor(Color.parseColor("#000000"));
                textView2.setTextColor(Color.parseColor("#000000"));
                textView.setText("오늘은 화창해요!\n     섭씨 : "+hot+"C");
                //ss.setBackgroundResource(R.drawable.sun2);
                //ss.setBackgroundColor(Color.parseColor("#00aee7"));

            }
            else if(re.equals("1")){
                //imageView.setImageResource(R.drawable.rain);
                //ss.setBackgroundResource(R.drawable.rain3);
                textView.setTextColor(Color.parseColor("#000000"));
                textView2.setTextColor(Color.parseColor("#ffffff"));
                textView.setText("오늘은 비가오네요 우산을 챙겨요\n                    섭씨 : "+hot+"C");
                //ss.setBackgroundColor(Color.parseColor("#CECCD5"));
            }
        }

        //textView.setText(message);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                long mNow =System.currentTimeMillis();
                String l=Long.toString(mNow);

                Date mReDate =new Date(mNow);
                SimpleDateFormat mFormat=new SimpleDateFormat("HH:mm:ss");
                String formatDate =mFormat.format(mReDate);
                sendEventListener.sendMessage2(l,formatDate);

                mainActivity.onFragmentChange(0);
                mainActivity.down();
            }
        });



        return v;
    }
}