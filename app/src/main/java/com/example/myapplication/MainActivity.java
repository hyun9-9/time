package com.example.myapplication;

import static android.graphics.Color.BLACK;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SendEventListener{

    androidx.constraintlayout.widget.ConstraintLayout layout;
    ArrayList<String> category;
    ArrayList<String>fcstTime;
    ArrayList<String>fcstValue;
    ArrayList<String> category1;
    ArrayList<String>fcstTime1;
    ArrayList<String>fcstValue1;
    String UsrCode;
    String Name;
    String SleepTime;
    String WakeUpTime;
    String Alarm_Q;
    String Alarm_A;
    RequestQueue queue;
    com.google.android.material.bottomnavigation.BottomNavigationView bar;
    HomeFragment homeFragment;
    InfoFragment infoFragment;
    SettingFragment settingFragment;
    sleepFragment sleepFragment;
    String wp,wp2,st,st2;
    int in=0,random_num,a=0,hot=0,aq=0;
    public static Context context_main;
    Handler mhandler =new Handler(Looper.getMainLooper());
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bar=(com.google.android.material.bottomnavigation.BottomNavigationView)findViewById(R.id.bottom_navigationview);
        layout=(androidx.constraintlayout.widget.ConstraintLayout) findViewById(R.id.main);
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
        json(0);
        json(1);



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private  void json(int num){

        if(queue == null) {
            queue = Volley.newRequestQueue(this);
        }
        String today = null;
        String wa="RN1";

        long mNow =System.currentTimeMillis();
        Date mReDate =new Date(mNow);
        SimpleDateFormat mFormat=new SimpleDateFormat("HH");
        SimpleDateFormat rawdate=new SimpleDateFormat("yyyyMMdd");

        Calendar cal = Calendar.getInstance();
        cal.setTime(mReDate);
        cal.add(Calendar.HOUR, -1);
        today = mFormat.format(cal.getTime());
        String time = today+"00";
        today=rawdate.format(cal.getTime());
        String date = today;
        String url="";

        if(num==0) {
            url = "http://ec2-52-78-121-204.ap-northeast-2.compute.amazonaws.com:8000/api/get_weather/37/126/" + date + "/" + time;
        }
        else if(num==1) {
            url = "http://ec2-52-78-121-204.ap-northeast-2.compute.amazonaws.com:8000/api/get_UsrSleepData/Sunny8973";
        }
        category = new ArrayList<>();
        fcstTime = new ArrayList<>();
        fcstValue = new ArrayList<>();
        category1 = new ArrayList<>();
        fcstTime1 = new ArrayList<>();
        fcstValue1 = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                jsonRead(response,num);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textOri.setText(error.toString());
                /*Toast.makeText(MainActivity.this, "error: " + error.toString()
                        , Toast.LENGTH_LONG).show();*/
            }
        });

            queue.add(jsonArrayRequest);


    }
    private void jsonRead(JSONArray jsonArray,int num) {
        //textOri.setText(jsonArray.toString());
        try {

            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if(num==0) {
                    String gory = jsonObject.getString("category");
                    if (gory.equals("RN1")) {
                        String Time = jsonObject.getString("fcstTime");
                        String Value = jsonObject.getString("fcstValue");
                        category.add(gory);
                        fcstTime.add(Time);
                        fcstValue.add(Value);
                    } else if (gory.equals("T1H")) {
                        String Time = jsonObject.getString("fcstTime");
                        String Value = jsonObject.getString("fcstValue");
                        category1.add(gory);
                        fcstTime1.add(Time);
                        fcstValue1.add(Value);
                    }

                }
                else if (num == 1) {
                    UsrCode= jsonObject.getString("UsrCode");
                    Name= jsonObject.getString("Name");
                    SleepTime= jsonObject.getString("SleepTime");
                    WakeUpTime= jsonObject.getString("WakeUpTime");
                    Alarm_Q= jsonObject.getString("Alarm_Q");
                    Alarm_A= jsonObject.getString("Alarm_A");




                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(num==0) {
            if (fcstValue.get(0).equals("강수없음")) {
                a = 0;
            } else {
                a = 1;
            }

            try {

                hot = Integer.parseInt(fcstValue1.get(0));

            } catch (NumberFormatException e) {

            } catch (Exception e) {

            }

            /*Toast.makeText(MainActivity.this, "fcstValue" + fcstValue.toString() + hot
                    , Toast.LENGTH_LONG).show();*/
        }
        else if(num==1) {
            int s;

            /*try {

                s = Integer.parseInt(SleepTime);

            } catch (NumberFormatException e) {

            } catch (Exception e) {

            }*/

            /*Toast.makeText(MainActivity.this, "UsrCode" + UsrCode.toString() + Name + SleepTime + WakeUpTime + Alarm_Q + Alarm_A
                    , Toast.LENGTH_LONG).show();*/

        }
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
                layout.setBackgroundResource(R.drawable.watercolor2);
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void onFragmentChange(int index){
        final GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(this, R.drawable.shape);

        in=index;
        if(index==0){

            Bundle bundle2=new Bundle();
            bundle2.putString("color", "0");
            FragmentTransaction transaction2 =getSupportFragmentManager().beginTransaction();
            settingFragment =new SettingFragment();
            settingFragment.setArguments(bundle2);
            transaction2.replace(R.id.containers,settingFragment);
            transaction2.commit();

            Bundle bundle=new Bundle();
            bundle.putString("sleep",st2);
            bundle.putString("wp",wp2);
            FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
            homeFragment =new HomeFragment();
            homeFragment.setArguments(bundle);
            //getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
            transaction.replace(R.id.containers,homeFragment);
            transaction.commit();
            drawable.setColor(Color.TRANSPARENT);
            layout.setBackgroundResource(R.drawable.night);
            //drawable.setColor(BLACK);
            getWindow().setStatusBarColor(Color.BLACK);
            bar.setItemBackground(drawable);
            bar.setItemIconTintList(getResources().getColorStateList(R.color.nav_color));
            bar.setItemTextColor(getResources().getColorStateList(R.color.nav_color));
            mhandler.removeCallbacksAndMessages(null);



        }
        else if(index==1){

            Bundle bundle2=new Bundle();
            bundle2.putString("color", "1");
            FragmentTransaction transaction2 =getSupportFragmentManager().beginTransaction();
            settingFragment =new SettingFragment();
            settingFragment.setArguments(bundle2);
            transaction2.replace(R.id.containers,settingFragment);
            transaction2.commit();

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
           /* if(a==0) {
                //drawable.setColor(Color.parseColor("#000000"));
                drawable.setColor(Color.TRANSPARENT);
            }
            else if(a==1){
                drawable.setColor(Color.parseColor("#000000"));
            }*/
            //drawable1.setColor(BLACK);


            if(a==0) {
                layout.setBackgroundResource(R.drawable.sun3);
                bar.setItemIconTintList(getResources().getColorStateList(R.color.nav_color2));
                bar.setItemTextColor(getResources().getColorStateList(R.color.nav_color2));
            }
            else if(a==1){
                layout.setBackgroundResource(R.drawable.rain4);
                bar.setItemIconTintList(getResources().getColorStateList(R.color.nav_color));
                bar.setItemTextColor(getResources().getColorStateList(R.color.nav_color));
            }
            //bar.setItemBackground(drawable);
            //getWindow().setStatusBarColor(Color.parseColor("#000000"));
            //getSupportFragmentManager().beginTransaction().replace(R.id.containers, sleepFragment).commit();
            transaction.replace(R.id.containers,sleepFragment);
            transaction.commit();
        }
    }

    public void down(){
        get();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void up(){
    Random random=new Random();
    random_num=random.nextInt(3)+1;
        //random_num=1;
        json(1);
        get_q();
        SimpleDateFormat wp1=new SimpleDateFormat("HH:mm:ss");
        Long lastdiff= Long.valueOf(6);
        if(SleepTime!=null&&st2!=null) {
            try {
                Date d1 = wp1.parse(SleepTime);
                Date d2 = wp1.parse(wp2);
                Long diff = d2.getTime() - d1.getTime();
                lastdiff = diff / 1000;


            } catch (ParseException e) {
                e.printStackTrace();
            }
            /*Toast.makeText(MainActivity.this, "lastdiff  " + lastdiff +"  "+SleepTime +"  "+wp2
                    , Toast.LENGTH_LONG).show();*/
        }
    if(a==1){

        Intent intent = new Intent(getApplicationContext(), rain_sleep.class);
        startActivity(intent);
    }
    else if(lastdiff<=5){

        Intent intent = new Intent(getApplicationContext(), sleep_time.class);
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

    else if(random_num==1){

        Intent intent = new Intent(getApplicationContext(), goodmorning.class);
        startActivity(intent);
    }
    Alarm();
}
    public void Alarm(){
        Random random=new Random();
        random_num=random.nextInt(20)+10;
        random_num*=1000;




        mhandler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                //Intent intent = new Intent(getApplicationContext(), quiz.class);
                //intent1.putExtra("formatDate", formatDate);
                //intent1.putExtra("val",Integer.toString(to2));
                //intent1.putExtra("val1",Integer.toString(con));
                //startActivity(intent);
                //finish();
                json(1);

                /*Alarm_Q="1";
                aq++;*/


                get_q();
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
        builder.setContentTitle("새로운 알림");//알림창 제목
        builder.setContentText("클릭해주세요");//알림창 내용
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
        if(to>=6&&to<=11) {
            Random random=new Random();
            random_num=random.nextInt(4)+1;
            if(a==0&&hot>30) {
                Intent intent = new Intent(this, weather_1.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==1&&hot>23) {
                Intent intent = new Intent(this, stroll.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==2) {
                Intent intent = new Intent(this, read_book.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==3) {
                Intent intent = new Intent(this, have_plan.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==4) {
                Intent intent = new Intent(this, bedding.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
        }
        else if(to>=12&&to<=17){ //오후
            Random random=new Random();
            random_num=random.nextInt(9)+2;

            if(random_num==1) {
                Intent intent = new Intent(this,ingredient.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==2) {
                Intent intent = new Intent(this, lunch.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==3) {
                Intent intent = new Intent(this, house.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==4) {
                Intent intent = new Intent(this, read_book.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==5&&hot>23) {
                Intent intent = new Intent(this, stroll.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==6) {
                Intent intent = new Intent(this, hbp.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==7) {
                Intent intent = new Intent(this, diabetes.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==8) {
                Intent intent = new Intent(this, nail.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==9) {
                Intent intent = new Intent(this, bedding.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
        }
        else if(to>=18&&to<=23){   //저녁
            Random random=new Random();
            random_num=random.nextInt(4)+1;
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
                Intent intent = new Intent(this, hbp.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, mnow, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                notificationManager.notify(mnow, notification);
            }
            else if(random_num==4) {
                Intent intent = new Intent(this, diabetes.class);
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
    Person person;
    static    String strJson = "";
    TextView time;

    public static String POST(String url, Person person){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("UsrCode", person.getUsrCode());
            jsonObject.accumulate("name", person.getName());
            jsonObject.accumulate("SleepTime", person.getSleepTime());
            jsonObject.accumulate("WakeUpTime", person.getWakeUpTime());
            jsonObject.accumulate("Alarm_Q", person.getAlarm_Q());
            jsonObject.accumulate("Alarm_A", person.getAlarm_A());

            /*jsonObject.accumulate("twitter", person.getTwitter());*/

            // convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // Set some headers to inform server about the type of the content
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            OutputStream os = httpCon.getOutputStream();
            os.write(json.getBytes("euc-kr"));
            os.flush();
            // receive response as inputStream
            try {
                is = httpCon.getInputStream();
                // convert inputstream to string
                if(is != null)
                    result = convertInputStreamToString(is);
                else
                    result = "Did not work!";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                httpCon.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void get(){
               /* if(!validate())
                    Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                else {*/
                    // call AsynTask to perform network operation on separate thread

        HttpAsyncTask httpTask = new HttpAsyncTask(MainActivity.this);
        //httpTask.execute("http://ec2-52-78-121-204.ap-northeast-2.compute.amazonaws.com:8000/Update-UsrStatus/Information",UsrCode,Name,SleepTime,WakeUpTime,Alarm_Q,Alarm_A);
        httpTask.execute("http://ec2-52-78-121-204.ap-northeast-2.compute.amazonaws.com:8000/api/update_sleep_data_table",UsrCode,Name,SleepTime,WakeUpTime,Alarm_Q,Alarm_A);
               // }

    }
    public void get_q(){
        HttpAsyncTask httpTask = new HttpAsyncTask(MainActivity.this);
        httpTask.execute("http://ec2-52-78-121-204.ap-northeast-2.compute.amazonaws.com:8000/api/alarm_q_counter_up/Sunny8973",UsrCode,Name,SleepTime,WakeUpTime,Alarm_Q,Alarm_A);
        // }

    }

    @Override
    public void sendMessage(String message,String message2) {

        WakeUpTime=message;
        wp2=message2;

        //Toast.makeText(this, "message:" + message, Toast.LENGTH_SHORT).show();
        get();
    }

    @Override
    public void sendMessage2(String message,String message2) {
        SleepTime=message2;
        st2=message2;

        //Toast.makeText(this, "message:" + message, Toast.LENGTH_SHORT).show();
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        private   MainActivity mainAct;

        HttpAsyncTask(MainActivity mainActivity) {
            this.mainAct = mainActivity;
        }
        @Override
        protected String doInBackground(String... urls) {

            person = new Person();
            person.setUsrCode(urls[1]);
            person.setName(urls[2]);
            person.setSleepTime(urls[3]);
            person.setWakeUpTime(urls[4]);
            person.setAlarm_Q(urls[5]);
            person.setAlarm_A(urls[6]);


            /*person.setTwitter(urls[4]);*/

            return POST(urls[0],person);

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;
            time=(TextView)findViewById(R.id.time);
            mainAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    /*Toast.makeText(mainAct, "Received!응애  "+aq, Toast.LENGTH_LONG).show();*/
                    try {
                        JSONArray json = new JSONArray(strJson);
                        mainAct.time.setText(json.toString(1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

   /* private boolean validate(){
        if(etName.getText().toString().trim().equals(""))
            return false;
        else if(etCountry.getText().toString().trim().equals(""))
            return false;
        else if(etTwitter.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }*/
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }





}



