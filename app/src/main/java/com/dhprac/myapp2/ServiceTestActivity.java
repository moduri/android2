package com.dhprac.myapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dhprac.myapp2.service.MyService;

public class ServiceTestActivity extends AppCompatActivity {

    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);

        Button startServiceBtn = findViewById(R.id.btn_service_start);
        Button stopServiceBtn = findViewById(R.id.btn_service_stop);

        startServiceBtn.setOnClickListener(new ServiceStart());
        stopServiceBtn.setOnClickListener(new ServiceStop());

    }

    class ServiceStart implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 서비스 가동
            serviceIntent = new Intent(ServiceTestActivity.this, MyService.class);
            startService(serviceIntent);
        }
    }

    class ServiceStop implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 서비스 중지
            stopService(serviceIntent);
        }
    }
}