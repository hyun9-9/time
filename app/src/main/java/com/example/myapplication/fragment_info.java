package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class fragment_info extends AppCompatActivity {

    Button emergency_call;
    Button emergency_message;

    communication communication = new communication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_info);

        emergency_call = findViewById(R.id.emergency_call);
        emergency_message = findViewById(R.id.emergency_message);

        emergency_call.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                communication.up_A();
                communication.get_up("911");
                finish();
            }
        });

        emergency_message.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "서비스 예정 중 입니다.", Toast.LENGTH_SHORT).show();
                /*communication.up_A();
                communication.get_up("5");*/
            }
        });
    }
}
