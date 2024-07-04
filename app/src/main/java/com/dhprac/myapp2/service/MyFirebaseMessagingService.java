package com.dhprac.myapp2.service;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.dhprac.myapp2.MainActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class  MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "MyNotificationChannel";

    @Override
    public void onNewToken(@NonNull String token) {
        //token을 서버로 전송
        Log.e("tag", "Refreshed token: " + token);

        Task<String> instanceToken = FirebaseMessaging.getInstance().getToken();
        final String[] result = new String[1];

        instanceToken.addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String token) {
                result[0] = instanceToken.getResult();

            }
        });

        super.onNewToken(token);
        Toast myToast = Toast.makeText(this.getApplicationContext(), result[0], Toast.LENGTH_SHORT);
        myToast.show();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.e("message Received", "sriodng0woirgnwoerigworig");
        super.onMessageReceived(remoteMessage);

        showNotification("title", "body");

    }

    private void showNotification(String title, String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this, "asd") != PackageManager.PERMISSION_GRANTED) {
            Log.e("Notification", "POST_NOTIFICATIONS permission not granted");
            return;
        }
        notificationManager.notify(0, notificationBuilder.build());
    }




}
