package com.fototechparivarstalls.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import android.Manifest;
import com.fototechparivarstalls.R;
import com.fototechparivarstalls.adapter.DownloadsAdapter;
import com.fototechparivarstalls.adapter.PaymentsHistoryListAdapter;
import com.fototechparivarstalls.databinding.ActivityDownloadsBinding;
import com.fototechparivarstalls.databinding.ActivityVerifyOtpactivityBinding;
import com.fototechparivarstalls.model.Documents.DocumentsResponse;
import com.fototechparivarstalls.model.DownloadsModel;
import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.model.PaymentHistory.PaymentHistoryJson;
import com.fototechparivarstalls.model.PaymentHistory.PaymentHistoryResponse;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.UserLocalStore;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DownloadsActivity extends AppCompatActivity {

    ActivityDownloadsBinding binding;
    private DownloadsAdapter leadsAdpter;
    private List<DownloadsModel> leadsModelList = new ArrayList<>();
    String bookingId, token, ConfirmationLetter, TransportLetter, GatePass;
    UserLocalStore userLocalStore;
    UserStoreDetails user;
    private File downloadedFile;
    String meassge;
    private ProgressDialog progressDialog;
    private static final int PERMISSION_REQUEST_CODE = 100;
   // private static final String ConfirmationLetter = ""; // Replace with your URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDownloadsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        bookingId = intent.getStringExtra("bookingId");

        Log.d("bookingId", bookingId);

        userLocalStore = new UserLocalStore(DownloadsActivity.this);
        user = userLocalStore.getLoggedInUser();

        token = user.getToken();

        progressDialog = new ProgressDialog(DownloadsActivity.this);
        progressDialog.setTitle("Downloading PDF");
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSupportActionBar().hide();

        getDocumentList();

        leadsModelList.add(new DownloadsModel("Confirmation Letter", R.drawable.conformation_lettors));
        leadsModelList.add(new DownloadsModel("Transport Letter", R.drawable.ic_tranformation));
        leadsModelList.add(new DownloadsModel("Gate Pass", R.drawable.ic_getpass));

        GridLayoutManager layoutManager = new GridLayoutManager(DownloadsActivity.this, 2);
        binding.recyclerView.setLayoutManager(layoutManager);
        leadsAdpter = new DownloadsAdapter(this, leadsModelList, downloadsModel -> {
            handleItemClick(downloadsModel);
        });
        binding.recyclerView.setAdapter(leadsAdpter);

    }

    private void handleItemClick(DownloadsModel downloadsModel) {
        // Handle item click based on the item's name
        switch (downloadsModel.getLeadsName()) {
            case "Confirmation Letter":

                if (ConfirmationLetter != null){

                    if (checkPermissions()) {
                        downloadConfirmationLetterPDF(ConfirmationLetter);
                    } else {
                        requestPermissions();
                    }
                }

                else {
                    Toast.makeText(DownloadsActivity.this, "payment is pending contact to organizer", Toast.LENGTH_SHORT).show();
                }

                break;

            case "Transport Letter":

                if (TransportLetter != null){

                    if (checkPermissions()) {
                        downloadTransportLetterPDF(TransportLetter);
                    } else {
                        requestPermissions();
                    }

                }

                else {
                    Toast.makeText(DownloadsActivity.this, "payment is pending contact to organizer", Toast.LENGTH_SHORT).show();
                }

                break;

            case "Gate Pass":

                if (GatePass != null){

                    if (checkPermissions()) {
                        downloadGetPassPDF(GatePass);
                    } else {
                        requestPermissions();
                    }

                }

                else {
                    Toast.makeText(DownloadsActivity.this, "payment is pending contact to organizer", Toast.LENGTH_SHORT).show();
                }




                break;

            default:
                Toast.makeText(this, "Unknown document type", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDocumentList(){

        if (Uttils.getInternetConnection(DownloadsActivity.this)) {

            Uttils.showProgressDialoug(DownloadsActivity.this);

            ApiHandler.setAuthToken(user.getToken());

            ApiHandler.getApiService().getDocumentsResponse(new PaymentHistoryJson(bookingId))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<DocumentsResponse>() {
                        @Override
                        public void onNext(DocumentsResponse documentsResponse) {
                            Uttils.dismissDialoug();

                            meassge = documentsResponse.getMessage();

                            Log.d("meassge", meassge);
                           /* if (documentsResponse.getMessage().equals("Booking not found.")){

                                Toast.makeText(DownloadsActivity.this, documentsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                            if (documentsResponse.getMessage().equals("Documents not found.")){

                                Toast.makeText(DownloadsActivity.this, documentsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }*/
                                // if (documentsResponse != null){

                                /*if (documentsResponse.getMessage().equals("Documents not found.")) {

                                    meassge = documentsResponse.getMessage();

                                    Log.d("meassge", meassge);

                                    Toast.makeText(DownloadsActivity.this, documentsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                } */

                            if (documentsResponse.getStatusCode().equals("404")) {

                                Toast.makeText(DownloadsActivity.this, documentsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            if (documentsResponse.getStatusCode().equals("200")) {

                              /*  GridLayoutManager layoutManager = new GridLayoutManager(DownloadsActivity.this, 2);
                                binding.recyclerView.setLayoutManager(layoutManager);
                                leadsAdpter = new DownloadsAdapter(DownloadsActivity.this, leadsModelList, downloadsModel -> {
                                    handleItemClick(downloadsModel);
                                });
                                binding.recyclerView.setAdapter(leadsAdpter);*/

                                    Toast.makeText(DownloadsActivity.this, documentsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                    ConfirmationLetter = documentsResponse.getDocuments().getConfirmationLetter();
                                    TransportLetter = documentsResponse.getDocuments().getTransportLetter();
                                    GatePass = documentsResponse.getDocuments().getGatePass();



                                }
                            //}

                               // if(documentsResponse.body()!=null)
                           /* try {

                               *//* if (documentsResponse.getStatusCode().equals("404")) {

                                    Toast.makeText(DownloadsActivity.this, documentsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                    meassge = documentsResponse.getMessage();

                                }*//*

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Documents List", "Exception: " + e.getMessage());
                                // Toast.makeText(PaymentsHistoryActivity.this, "Exception occurred", Toast.LENGTH_SHORT).show();
                            }*/
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Uttils.dismissDialoug();
                           // Toast.makeText(DownloadsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            Uttils.dismissDialoug();
                        }
                    });
        }

    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // No need for WRITE_EXTERNAL_STORAGE on Android 10+
            return true;
        }
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            // Permissions not required for scoped storage on Android 10+
            Toast.makeText(this, "No additional permissions needed", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void downloadGetPassPDF(String urlString) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        // Parse the URL
        Uri uri = Uri.parse(urlString);

        // Create the DownloadManager request
        DownloadManager.Request request = new DownloadManager.Request(uri);

        // Set the title and description for the download notification
        request.setTitle("Gate Pass");
        request.setDescription("Downloading Gate Pass PDF");

        // Set the destination for the downloaded file
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "GatePass.pdf");

        // Allow visibility in downloads UI
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Enqueue the download and get the download ID
        long downloadId = downloadManager.enqueue(request);

        // Show a progress dialog
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.show();

        // Register a BroadcastReceiver to listen for download completion
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id == downloadId) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Download complete!", Toast.LENGTH_SHORT).show();
                    unregisterReceiver(this);
                }
            }
        };
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }


    private void downloadTransportLetterPDF(String urlString) {

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(urlString);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        // Set the title and description for the download notification
        request.setTitle("Transport Letter");
        request.setDescription("Downloading Transport Letter PDF");

        // Set the destination for the downloaded file
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "TransportLetter.pdf");

        // Allow visibility in downloads UI
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);


        // Enqueue the download and get the download ID
        long downloadId = downloadManager.enqueue(request);


        // Show a progress dialog
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.show();

        // Register a BroadcastReceiver to listen for download completion
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id == downloadId) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Download complete!", Toast.LENGTH_SHORT).show();
                    unregisterReceiver(this);
                }
            }
        };
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


       // Toast.makeText(this, "Downloading Transport Letter", Toast.LENGTH_SHORT).show();
    }


 /*   private void downloadConfirmationLetterPDF(String urlString) {

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        Uri uri = Uri.parse(urlString);
        DownloadManager.Request request = new DownloadManager.Request(uri);


        // Set the title and description for the download notification
        request.setTitle("Confirmation Letter");
        request.setDescription("Downloading Confirmation Letter PDF");


        // Set the destination for the downloaded file
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "ConfirmationLetter.pdf");

        // Allow visibility in downloads UI
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Enqueue the download
        downloadManager.enqueue(request);

        Toast.makeText(this, "Downloading Confirmation Letter", Toast.LENGTH_SHORT).show();
    }*/



    public void downloadConfirmationLetterPDF(String urlString) {
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        // Parse the URL
        Uri uri = Uri.parse(urlString);

        // Create the DownloadManager request
        DownloadManager.Request request = new DownloadManager.Request(uri);

        // Set the title and description for the download notification
        request.setTitle("Confirmation Letter");
        request.setDescription("Downloading Confirmation Letter PDF");

        // Set the destination for the downloaded file
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "ConfirmationLetter.pdf");

        // Allow visibility in downloads UI
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Enqueue the download and get the download ID
        long downloadId = downloadManager.enqueue(request);

        // Show a progress dialog
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.show();

        // Register a BroadcastReceiver to listen for download completion
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id == downloadId) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Download complete!", Toast.LENGTH_SHORT).show();
                    unregisterReceiver(this);
                }
            }
        };
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

      /*  // Monitor the download progress
        new Thread(() -> {
            boolean downloading = true;
            while (downloading) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);

                Cursor cursor = downloadManager.query(query);
                if (cursor != null && cursor.moveToFirst()) {
                    int bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytesTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    if (bytesTotal > 0) {
                        int progress = (int) ((bytesDownloaded * 100L) / bytesTotal);
                        progressDialog.setProgress(progress);
                    }

                    int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if (status == DownloadManager.STATUS_SUCCESSFUL || status == DownloadManager.STATUS_FAILED) {
                        downloading = false;
                    }
                    cursor.close();
                }
            }
        }).start();*/
    }
}