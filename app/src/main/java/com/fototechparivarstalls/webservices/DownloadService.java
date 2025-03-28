package com.fototechparivarstalls.webservices;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends Service {

    private static final String CHANNEL_ID = "DownloadChannel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String fileUrl = intent.getStringExtra("FILE_URL");

        if (fileUrl != null) {
            Notification notification = getNotification(0, "Downloading...").build(); // Call .build() to get a Notification
            startForeground(NOTIFICATION_ID, notification);
            startDownload(fileUrl);
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startDownload(String fileUrl) {
        new Thread(() -> {
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                int fileLength = connection.getContentLength();
                InputStream inputStream = connection.getInputStream();
                File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "downloaded_file.pdf");
                FileOutputStream outputStream = new FileOutputStream(outputFile);

                byte[] buffer = new byte[1024];
                int bytesRead;
                long total = 0;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    total += bytesRead;
                    int progress = (int) (total * 100 / fileLength);
                    updateNotification(progress, "Downloading...");
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();
                stopForeground(true);
                updateNotification(100, "Download Complete!");
                stopSelf();

            } catch (Exception e) {
                Log.e("DownloadService", "Download failed", e);
                stopForeground(true);
                updateNotification(0, "Download Failed");
                stopSelf();
            }
        }).start();
    }

    private NotificationCompat.Builder getNotificationBuilder(int progress, String contentText) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("File Download")
                .setContentText(contentText)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setProgress(100, progress, false)
                .setOngoing(progress < 100);
    }

    private void updateNotification(int progress, String contentText) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, getNotificationBuilder(progress, contentText).build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Download Service", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private NotificationCompat.Builder getNotification(int progress, String contentText) {
        return getNotificationBuilder(progress, contentText);
    }
}
