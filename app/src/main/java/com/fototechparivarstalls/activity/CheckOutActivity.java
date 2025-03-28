package com.fototechparivarstalls.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fototechparivarstalls.R;
import com.fototechparivarstalls.databinding.ActivityChangePasswordBinding;
import com.fototechparivarstalls.databinding.ActivityCheckOutBinding;

public class CheckOutActivity extends AppCompatActivity {

    ActivityCheckOutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
    }
}