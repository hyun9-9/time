package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class clean_manual extends AppCompatActivity {

    Button clean;
    TextView clean_content;
    TextToSpeech tts;

    communication communication = new communication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_manual);

        clean = findViewById(R.id.clean_button);
        clean_content = (TextView)findViewById(R.id.clean_content);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tts.speak(clean_content.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }, 100);

        clean.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                communication.up_A();
                communication.get_up("5");
                //청소했으니 환경 점수 up
                finish();
            }
        });
    }
}