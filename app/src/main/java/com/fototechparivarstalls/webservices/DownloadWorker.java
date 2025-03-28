package com.fototechparivarstalls.webservices;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadWorker extends Worker {

    public DownloadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String pdfUrl = getInputData().getString("PDF_URL");

        if (pdfUrl == null || pdfUrl.isEmpty()) {
            return Result.failure();
        }

        try {
            URL url = new URL(pdfUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return Result.failure();
            }

            InputStream inputStream = connection.getInputStream();
            File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "downloaded_with_workmanager.pdf");
            FileOutputStream outputStream = new FileOutputStream(pdfFile);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            // Return the file path as output data
            Data outputData = new Data.Builder()
                    .putString("FILE_PATH", pdfFile.getAbsolutePath())
                    .build();

            return Result.success(outputData);
        } catch (Exception e) {
            Log.e("DownloadWorker", "Error downloading PDF", e);
            return Result.failure();
        }
    }
}

