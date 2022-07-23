package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class bedding extends AppCompatActivity {

    TextView bedding_text;
    Button bedding_passed;
    Button bedding_passing;
    TextToSpeech tts;

    communication communication =new communication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bedding);

         bedding_text = (TextView) findViewById(R.id.bedding_text);
         bedding_passed= findViewById(R.id.bedding_passed);
         bedding_passing= findViewById(R.id.bedding_passing);

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
                tts.speak(bedding_text.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }, 100);

        bedding_passed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), bedding_old.class);
                startActivity(intent);
                //침구류가 2주 이상되었으므로 bedding_old로 전환
                finish();

            }
        });

        bedding_passing.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                communication.up_A();
                communication.get_up("5");
                //환경 점수up(아직 빨래할때 안됐음)
                finish();
            }
        });
    }
}
