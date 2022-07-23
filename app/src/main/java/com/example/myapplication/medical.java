package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class medical extends AppCompatActivity {

    TextView physical;
    Button head;
    Button body;
    Button leg;
    Button arm;


    TextToSpeech tts;
    communication communication = new communication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);

        physical = (TextView) findViewById(R.id.physical);
        head= findViewById(R.id.head);
        body= findViewById(R.id.body);
        leg= findViewById(R.id.leg);
        arm= findViewById(R.id.arm);


        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){    //알림 tts 부분
            @Override
            public void onInit(int status){
                if(status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run(){
                tts.speak(physical.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }, 100);

        head.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                communication.up_A();
                communication.get_E("91");

                //머리가 아프다는걸 웹사이트로 전송
                finish();
            }
        });

        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "서비스 예정 중 입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "서비스 예정 중 입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        leg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "서비스 예정 중 입니다.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}