package com.example.myapplication;

import static android.graphics.Color.BLACK;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> category;
    ArrayList<String>fcstTime;
    ArrayList<String>fcstValue;
    ArrayList<String> category1;
    ArrayList<String>fcstTime1;
    ArrayList<String>fcstValue1;
    RequestQueue queue;
    com.google.android.material.bottomnavigation.BottomNavigationView bar;
    HomeFragment homeFragment;
    InfoFragment infoFragment;
    SettingFragment settingFragment;
    sleepFragment sleepFragment;
    int in=0,random_num,a=0,hot=0;
    public static Context context_main;
    Handler mhandler =new Handler(Looper.getMainLooper());
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar=(com.google.android.material.bottomnavigation.BottomNavigationView)findViewById(R.id.bottom_navigationview);
        homeFragment = new HomeFragment();
        infoFragment = new InfoFragment();
        settingFragment = new SettingFragment();
        sleepFragment =new sleepFragment();
        context_main=this;
        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();


        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
        navigationBarView.setSelectedItemId(R.id.home);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.home:
                        setFrag(0);
                        return true;
                    case R.id.setting:
                        setFrag(1);
                        return true;
                    case R.id.info:
                        setFrag(2);
                        return true;
                }
                return false;
            }
        });
        setFrag(0);
        json();



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private  void json(){

        if(queue == null) {
            queue = Volley.newRequestQueue(this);
        }
        String today = null;
        String wa="RN1";
        long mNow =System.currentTimeMillis();
        Date mReDate =new Date(mNow);
        SimpleDateFormat mFormat=new SimpleDateFormat("HH");
        SimpleDateFormat rawdate=new SimpleDateFormat("yyyyMMdd");
        String formatDate =mFormat.format(mReDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(mReDate);
        cal.add(Calendar.HOUR, -1);
        today = mFormat.format(cal.getTime());
        String time = today+"00";
        today=rawdate.format(cal.getTime());
        String date = today;
        String url = "http://ec2-52-78-121-204.ap-northeast-2.compute.amazonaws.com:8000/api/get_weather/37/126/"+date+"/"+time;
        category = new ArrayList<>();
        fcstTime = new ArrayList<>();
        fcstValue = new ArrayList<>();
        category1 = new ArrayList<>();
        fcstTime1 = new ArrayList<>();
        fcstValue1 = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                jsonRead(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textOri.setText(error.toString());
                Toast.makeText(MainActivity.this, "error: " + error.toString()
                        , Toast.LENGTH_LONG).show();
            }
        });

            queue.add(jsonArrayRequest);


    }
    private void jsonRead(JSONArray jsonArray) {
        //textOri.setText(jsonArray.toString());
        try {

            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String gory = jsonObject.getString("category");
                if(gory.equals("RN1")) {
                    String Time = jsonObject.getString("fcstTime");
                    String Value = jsonObject.getString("fcstValue");
                    category.add(gory);
                    fcstTime.add(Time);
                    fcstValue.add(Value);
                }
                else if(gory.equals("T1H")) {
                    String Time = jsonObject.getString("fcstTime");
                    String Value = jsonObject.getString("fcstValue");
                    category1.add(gory);
                    fcstTime1.add(Time);
                    fcstValue1.add(Value);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(fcstValue.get(0).equals("강수없음")){
            a=0;
        }
        else{
            a=1;
        }

        try {

            hot = Integer.parseInt(fcstValue1.get(0));

        }
        catch (NumberFormatException e){

        }
        catch (Exception e){

        }

        Toast.makeText(MainActivity.this, "fcstValue"+fcstValue.toString()+hot
                , Toast.LENGTH_LONG).show();
        //textParse.setText(category.toString() + "\n" + fcstTime.toString() + "\n" + fcstValue.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private  void setFrag(int n){

        switch (n) {
            case 0:
                onFragmentChange(in);
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, settingFragment).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, infoFragment).commit();
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void onFragmentChange(int index){
        final GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.shape);

        in=index;
        if(index==0){
            getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();

            drawable.setColor(BLACK);
            getWindow().setStatusBarColor(Color.BLACK);
            bar.setItemBackground(drawable);
            mhandler.removeCallbacksAndMessages(null);

        }
        else if(index==1){
            Bundle bundle=new Bundle();
            bundle.putString("you",Integer.toString(a));
            bundle.putString("hot",Integer.toString(hot));
            //bundle.putString("you","1");
            FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
            sleepFragment =new sleepFragment();
            sleepFragment.setArguments(bundle);

            //transaction.replace(R.id.containers,sleep).commit();
            //drawable.setColor(Color.parseColor("#A6673AB7"));
            //drawable.setColor(Color.parseColor("#3F51B5"));
            if(a==0) {
                drawable.setColor(Color.parseColor("#3F51B5"));
            }
            else if(a==1){
                drawable.setColor(Color.parseColor("#3F51B5"));
            }
            //drawable1.setColor(BLACK);
            bar.setItemBackground(drawable);
            getWindow().setStatusBarColor(Color.parseColor("#3F51B5"));
            //getSupportFragmentManager().beginTransaction().replace(R.id.containers, sleepFragment).commit();
            transaction.replace(R.id.containers,sleepFragment);
            transaction.commit();


        }
    }
public void up(){
    Random random=new Random();
    random_num=random.nextInt(4)+1;

    if(a==1){

        Intent intent = new Intent(getApplicationContext(), rain_sleep.class);
        startActivity(intent);
    }
    else if(random_num==2){

        Intent intent = new Intent(getApplicationContext(), comfortable_night.class);
        startActivity(intent);
    }
    else if(random_num==3){

        Intent intent = new Intent(getApplicationContext(), uncomfortable_night.class);
        startActivity(intent);
    }
    else if(random_num==4){

        Intent intent = new Intent(getApplicationContext(), sleep_time.class);
        startActivity(intent);
    }
    else if(random_num==1){

        Intent intent = new Intent(getApplicationContext(), goodmorning.class);
        startActivity(intent);
    }
    Alarm();
}
    public void Alarm(){
        Random random=new Random();
        random_num=random.nextInt(10)+10;
        random_num*=1000;




        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent = new Intent(getApplicationContext(), quiz.class);
                //intent1.putExtra("formatDate", formatDate);
                //intent1.putExtra("val",Integer.toString(to2));
                //intent1.putExtra("val1",Integer.toString(con));
                //startActivity(intent);
                //finish();
                clickBtn();
            }
        }, random_num);

    }

    public void clickBtn() {

        //알림(Notification)을 관리하는 관리자 객체를 운영체제(Context)로부터 소환하기
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Notification 객체를 생성해주는 건축가객체 생성(AlertDialog 와 비슷)
        NotificationCompat.Builder builder= null;

        //Oreo 버전(API26 버전)이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            String channelID="channel_01"; //알림채널 식별자
            String channelName="MyChannel01"; //알림채널의 이름(별명)

            //알림채널 객체 만들기
            NotificationChannel channel= new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);

            //알림매니저에게 채널 객체의 생성을 요청
            notificationManager.createNotificationChannel(channel);

            //알림건축가 객체 생성
            builder=new NotificationCompat.Builder(this, channelID);


        }else{
            //알림 건축가 객체 생성
            //builder= new NotificationCompat.Builder(this, null);
        }

        //건축가에게 원하는 알림의 설정작업
        builder.setSmallIcon(android.R.drawable.ic_menu_view);
        long mNow =System.currentTimeMillis();
        int mnow= Long.valueOf(mNow).intValue();
        //상태바를 드래그하여 아래로 내리면 보이는
        //알림창(확장 상태바)의 설정
        builder.setContentTitle("Title");//알림창 제목
        builder.setContentText("Messages....");//알림창 내용
        //알림창의 큰 이미지
        //Bitmap bm= BitmapFactory.decodeResource(getResources(),R.drawable.gametitle_09);
        //builder.setLargeIcon(bm);//매개변수가 Bitmap을 줘야한다.

        long Now =System.currentTimeMillis();
        Date mReDate =new Date(Now);
        SimpleDateFormat mFormat=new SimpleDateFormat("HH");
        String formatDate =mFormat.format(mReDate);
        int to=0;
        try {
            if(formatDate!=null) {
                to = Integer.parseInt(formatDate);
            }
        }
        catch (NumberFormatException e){

        }
        catch (Exception e){

        }
        if(to>=6&&to<=17) {
            Random random=new Random();
            random_num=random.nextInt(5)+2;
            if(a==0&&hot>30) {
                Intent intent = new Intent(this, weather_1.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==2) {
                Intent intent = new Intent(this, stroll.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==3) {
                Intent intent = new Intent(this, read_book.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==4) {
                Intent intent = new Intent(this, call_to_other.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==5) {
                Intent intent = new Intent(this, have_plan.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==6&&to>=12) {
                Intent intent = new Intent(this, lunch.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==6&&to<=12) {
                Intent intent = new Intent(this, lunch.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
        }
        else if(to>=18&&to<=23){
            Random random=new Random();
            random_num=random.nextInt(3)+1;
            if(random_num==1) {
                Intent intent = new Intent(this, today_ending.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==2) {
                Intent intent = new Intent(this,good_word.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==3) {
                Intent intent = new Intent(this, seekbar_today.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
        }
        //알림 요청시에 사용한 번호를 알림제거 할 수 있음.
        //notificationManager.cancel(1);

        Alarm();
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Alarm();
    }*/
}



