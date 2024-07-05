package com.dhprac.myapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dhprac.myapp2.service.MyService;

public class ServiceTestActivity extends AppCompatActivity {

    Intent serviceIntent;
    MyService ipcService;
    // 서비스 관리 객체
    ServiceConnectionClass connection;

    int serviceValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);

        Button startServiceBtn = findViewById(R.id.btn_service_start);
        Button stopServiceBtn = findViewById(R.id.btn_service_stop);

        startServiceBtn.setOnClickListener(new ServiceStart());
        stopServiceBtn.setOnClickListener(new ServiceStop());

        // 서비스에 접속
        connection = new ServiceConnectionClass();

        // serviceIntent 가 null 이라서 NullPointException 애러 발생
        // 원래는 클릭시, 서비스가 실행되도록 설정 되어 있었음.
        serviceIntent = new Intent(ServiceTestActivity.this, MyService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }

        bindService(serviceIntent, connection, BIND_AUTO_CREATE);

    }

    class ServiceStart implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 서비스 가동
            serviceIntent = new Intent(ServiceTestActivity.this, MyService.class);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // 안드로이드 8.0 이상은 포그라운드로 운영
                startForegroundService(serviceIntent);
            } else  {
                // 서비스 가동
                startService(serviceIntent);
            }

        }
    }

    class ServiceStop implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // 서비스 중지
            stopService(serviceIntent);
        }
    }

    // Activity의 서비스 접속을 관리하는 클래스
    public class ServiceConnectionClass implements ServiceConnection {
        // 서비스에 접속이 성공했을 때
        // 두번째 매개변수 : onBind 메서드가 반환하는 바인드 객체
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {

            Log.e("testtest", "여기까지는 실행");

            MyService.LocalBinder binder = (MyService.LocalBinder) iBinder;
            ipcService = binder.getService();

            serviceValue = ipcService.getNumber();

            Log.e("serviceTest", serviceValue + "");
        }
        // 서비스에 접속이 해제 됐을 때
        @Override
        public void onServiceDisconnected(ComponentName name) {
            ipcService = null;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}