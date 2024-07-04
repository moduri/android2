package com.dhprac.myapp2.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.dhprac.myapp2.R;

public class MyService extends Service {


    static final String NOTIFICATION_CHANNEL_ID = "service";
    static final String NOTIFICATION_CHANNEL_NAME = "service";
    static final int MESSAGE_ID = 11;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("test", "서비스가 가동되었습니다.");

        // 안드로이드 8.0 이상부터는 포그라운드 서비스로 해야한다.
        addNotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME);

        NotificationCompat.Builder builder = getNotificationBuilder(NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle("서비스 가동");
        builder.setContentText("서비스 가동중입니다.");

        Notification build = builder.build();
        // 알림 메시지를 포그라운드 서비스를 위해 표시
        startForeground(MESSAGE_ID, build);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("test", "서비스가 종료되었습니다.");
        super.onDestroy();
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
}