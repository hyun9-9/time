package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import java.util.Locale;

public class bedding_dry extends AppCompatActivity {

    Button dry;
    TextView dry_content;

    TextToSpeech tts;
    communication communication =new communication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bedding_dry);

        dry= findViewById(R.id.dry);
        dry_content= findViewById(R.id.dry_content);

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
                tts.speak(dry_content.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }, 100);

         dry.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.N)
             @Override
            public void onClick(View v) {
                //건조 했으니 환경점수 up?
                 communication.up_A();
                 communication.get_up("5");
                finish();
            }
        });
    }
}