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

public class weather_1 extends AppCompatActivity {

    TextView weather_text;
    Button will;
    Button wont;
    TextToSpeech tts;

    communication communication = new communication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather1);

        weather_text = (TextView) findViewById(R.id.weather_text);
        will = findViewById(R.id.will);
        wont = findViewById(R.id.wont);

        tts= new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
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
                tts.speak(weather_text.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }, 100);

        will.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), clean_manual.class);
                startActivity(intent);
                //청소 정보로 전환
                finish();
            }
        });

        wont.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                communication.up_A();
                communication.get_up("3");
                //형은 쉬러간디
                finish();
            }
        });
    }
}