package com.dhprac.myapp2;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String NOTIFICATION_CHANNEL = "channel1";
    static final String NOTIFICATION_CHANNEL_2 = "channel2";
    static final String NOTIFICATION_NAME = "채널1 알림";
    static final String NOTIFICATION_NAME_2 = "채널2 알림";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        TextView tv = findViewById(R.id.test);
        Button btn = findViewById(R.id.notification_btn);
        Button activity3Btn = findViewById(R.id.notification_btn2);
        Button serviceBtn = findViewById(R.id.btn_service);
        Button fragmentBtn = findViewById(R.id.btn_fragment);

        tv.setOnClickListener(new FCMClickListener());
        fragmentBtn.setOnClickListener(new FragmentActivityOnClickListener());

        // Notification 채널을 등록
        addNotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_NAME);
        btn.setOnClickListener(new NotificationClickListener());

        addNotificationChannel(NOTIFICATION_CHANNEL_2, NOTIFICATION_NAME_2);
        activity3Btn.setOnClickListener(new Notification2ClickListener());

        // 서비스 액티비티로 이동
        serviceBtn.setOnClickListener(new ServiceActivityStartOnClickListener());

        boolean isRunning = isServiceRunning("com.dhprac.myapp2.MyService");

    }

    class FCMClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        String token = task.getResult();
                        Log.e(TAG, "FCM Token: " + token);
                        Toast.makeText(MainActivity.this, "FCM Token: " + token, Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }

    class NotificationClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            NotificationCompat.Builder builder = getNotificationBuilder(NOTIFICATION_CHANNEL);
            // 작은 아이콘
            builder.setSmallIcon(R.mipmap.ic_launcher);
            // 큰 아이콘
            Bitmap bitmap = BitmapFactory.decodeResource(getResources() ,R.mipmap.ic_launcher);
            builder.setLargeIcon(bitmap);

            builder.setNumber(65);

            builder.setContentTitle("이것을 제목");

            SimpleDateFormat sdf =new SimpleDateFormat("mm");
            String minute = sdf.format(new Date());

            builder.setContentText("이것은 본문" + minute + "분 실행");

            // 메시지를 터치했을때 실행할 Activity를 지정
            // android 11 에 대응 위해 PendingIntent.FLAG_IMMUTABLE 추가
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            intent.putExtra("minute", minute + "");

            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 11, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            builder.setContentIntent(pendingIntent);

            // 메시지를 클릭하게 되면 자동으로 메시지를 삭제
            builder.setAutoCancel(true);

            // 메세지 객체 생성
            Notification notification = builder.build();
            // 알림 메시지를 관리하는 객체 추출
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // 메세지 출력
            manager.notify(10, notification);
        }
    }

    class ServiceActivityStartOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, ServiceTestActivity.class);
            startActivity(intent);
        }
    }

    class FragmentActivityOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, FragmentTestActivity.class);
            startActivity(intent);
        }
    }

    class Notification2ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            NotificationCompat.Builder builder = getNotificationBuilder(NOTIFICATION_CHANNEL_2);
            builder.setContentTitle("이것은 타이틀 2");
            builder.setContentText("이것은 택스트 2");
            builder.setSmallIcon(R.drawable.ic_launcher_background);

            Intent intent = new Intent(MainActivity.this, NotificationActivity2.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 11, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);

            Notification notification = builder.build();
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(11, notification);
        }
    }

    private void addNotificationChannel(String id, String name) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = notificationManager.getNotificationChannel(id);

            // 채널 값이 없다면, 객체 생성
            if (channel == null) {
                channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);

                channel.enableVibration(true);
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private NotificationCompat.Builder getNotificationBuilder(String id) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);
        return builder;
    }

     public boolean isServiceRunning(String name) {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfos = activityManager.getRunningServices(Integer.MAX_VALUE);

        for(ActivityManager.RunningServiceInfo info : serviceInfos) {
            if (info.service.getClassName() == name) {
                return true;
            }
        }

        return false;

    }

}