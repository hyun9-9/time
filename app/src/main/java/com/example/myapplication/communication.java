package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.util.LruCache;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class communication extends AppCompatActivity {
    String Alarm_A="1";

    public void up_A(){

        get_A();
    }





    Person person;
    static String strJson = "";

    public static String POST(String url, Person person){
        InputStream is = null;
        String result = "";
        try {
            URL urlCon = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection();

            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("data", person.getAlarm_A());

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
    public void get_A(){

        communication.HttpAsyncTask httpTask = new HttpAsyncTask(communication.this);
        httpTask.execute("http://ec2-52-78-121-204.ap-northeast-2.compute.amazonaws.com:8000/api/alarm_a_counter_up/Sunny8973");

    }
    //a= 서버로직정하기에있음
    public void get_up(String a){

        communication.HttpAsyncTask httpTask = new HttpAsyncTask(communication.this);
        httpTask.execute("http://ec2-52-78-121-204.ap-northeast-2.compute.amazonaws.com:8000/api/score/Sunny8973/"+a);

    }
    
    public void get_E(String a){

        communication.HttpAsyncTask httpTask = new HttpAsyncTask(communication.this);
        httpTask.execute("http://ec2-52-78-121-204.ap-northeast-2.compute.amazonaws.com:8000/api/event_alert/Sunny8973/"+a);

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        //private   MainActivity mainAct;

        private  communication comm;
        HttpAsyncTask(communication communication) {
            this.comm = communication;
        }
        @Override
        protected String doInBackground(String... urls) {

            person = new Person();
            //person.setAlarm_A(urls[1]);


            /*person.setTwitter(urls[4]);*/

            return POST(urls[0],person);

        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            strJson = result;

            comm.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Toast.makeText(mainAct, "Received!응애뚜뚜  ", Toast.LENGTH_LONG).show();
                    try {
                        JSONArray json = new JSONArray(strJson);
                       // mainAct.time.setText(json.toString(1));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
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
