package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class good_word extends AppCompatActivity {

    Button word;
    TextView word_content;

    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_word);

        word= findViewById(R.id.word_button);
        word_content= findViewById(R.id.word_content);

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
                tts.speak(word_content.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }, 100);

        word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //따봉?
                finish();
            }
        });
    }
}