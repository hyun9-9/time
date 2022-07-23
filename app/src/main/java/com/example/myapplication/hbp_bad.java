package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class hbp_bad extends AppCompatActivity {

    Button hbp_bad_button  ;
    TextView hbp_bad_content;

    TextToSpeech tts;
    communication communication = new communication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hbp_bad);

        hbp_bad_button= findViewById(R.id.hbp_bad_button);
        hbp_bad_content= findViewById(R.id.hbp_bad_content);


        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run(){
                tts.speak(hbp_bad_content.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }, 100);

        hbp_bad_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                communication.up_A();
                communication.get_up("2");
                communication.get_E("95");
                //결과가 안좋아서 그냥 ...?
                finish();
            }
        });
    }
}