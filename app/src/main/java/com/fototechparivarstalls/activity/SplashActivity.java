package com.fototechparivarstalls.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.fototechparivarstalls.R;
import com.fototechparivarstalls.databinding.ActivityLoginBinding;
import com.fototechparivarstalls.databinding.ActivitySplashBinding;
import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.webservices.UserLocalStore;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;
    SplashActivity objSplash;
    private static final int DELAY_FOR_OPENING_LANDING_ACTIVITY = 2000;
    UserLocalStore userLocalStore;
    UserStoreDetails user;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        objSplash = this;

        Window window = objSplash.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

        // objLogin = this;
       getSupportActionBar().hide();

        userLocalStore = new UserLocalStore(this);
        user = userLocalStore.getLoggedInUser();

        Log.d("Token",user.getToken());

     /*   userLocalStore = new UserLocalStore(this);
        user = userLocalStore.getLoggedInUser();


        Log.d("userId",user.getUserId());

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        token = sh.getString("token", "");
        Log.d("zxc", token);*/

        ActivityResultLauncher<String> permisiionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                // suscrribeNotification();
                startMainScreen();

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permisiionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
        else {
            //suscrribeNotification();
            startMainScreen();

        }

    }

    private void startMainScreen() {

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

               /* Intent i = new Intent(objSplash, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();*/

                if (user.getToken() == null || user.getToken().isEmpty()) {

                    Intent i = new Intent(objSplash, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                } else {

                    Intent i = new Intent(objSplash, HomeActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                }

            }
        }, DELAY_FOR_OPENING_LANDING_ACTIVITY);

    }

}