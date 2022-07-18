package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.os.Handler;    //tts 기능 구현을 위한 import
import android.widget.TextView;
import android.speech.tts.TextToSpeech;


import java.util.Random;

public class quiz extends AppCompatActivity{
    Button happy;
    Button sad;
    TextView textview;
    int to,con;

    TextToSpeech tts; /*tts사용을 위한 전역변수 선언*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        happy= findViewById(R.id.happy);
        sad= findViewById(R.id.sad);

        /*Intent intent =getIntent();
        String formatDate = intent.getStringExtra("formatDate");
        String val = intent.getStringExtra("val");

        String val1 = intent.getStringExtra("val1");
*/


      /*  try {

            if(val!=null){
                to= Integer.parseInt(val);
            }
            if(val1!=null){
                con= Integer.parseInt(val1);
            }
        }
        catch (NumberFormatException e){

        }
        catch (Exception e){

        }

*/



        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status){
                if(status != TextToSpeech.ERROR){    //tts 기능 정의 및 원할한 작동을 위해
                    tts.setLanguage(Locale.KOREAN);  //에러발생시 한글로 수정하는 코드
                }
            }
        });
        textview = (TextView) findViewById(R.id.textView); //xml의 textview부분 java파일의
        //textview 변수에 연결

        Handler mHandler = new Handler();                 //Handler를 이용 액티비티 실행 후
        mHandler.postDelayed(new Runnable()  {            //즉시(0.1초 후) tts실행
            public void run(){
                tts.speak(textview.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        }, 100); // 0.1초후
        /*Handler mhandler =new Handler(Looper.getMainLooper());
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                con++;
                Intent intent=new Intent(getApplicationContext(),sleep.class);
                intent.putExtra("formatDate",formatDate);
                intent.putExtra("val",Integer.toString(to));
                intent.putExtra("val1",Integer.toString(con));
                startActivity(intent);
                finish();
            }
        },10000);*/

        happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*con++;
                to++;
                Intent intent=new Intent(getApplicationContext(),sleep.class);
                intent.putExtra("formatDate",formatDate);
                intent.putExtra("emotion","75");
                intent.putExtra("val",Integer.toString(to));
                intent.putExtra("val1",Integer.toString(con));
                mhandler.removeCallbacksAndMessages(null);
                startActivity(intent);*/
                //((MainActivity)MainActivity.context_main).Alarm();
                finish();
            }
        });

        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*con++;
                to++;
                Intent intent=new Intent(getApplicationContext(),sleep.class);
                intent.putExtra("formatDate",formatDate);
                intent.putExtra("emotion","25");
                intent.putExtra("val",Integer.toString(to));
                intent.putExtra("val1",Integer.toString(con));
                mhandler.removeCallbacksAndMessages(null);
                startActivity(intent);*/
                //((MainActivity)MainActivity.context_main).Alarm();
                finish();
            }
        });


    }

  /* @Override
    protected void onDestroy() {
        super.onDestroy();

        MainActivity mainActivity =new MainActivity();
        mainActivity.Alarm();


    }*/
}