package com.fototechparivarstalls.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fototechparivarstalls.R;
import com.fototechparivarstalls.databinding.ActivityForgotPasswordBinding;
import com.fototechparivarstalls.databinding.ActivityLoginBinding;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.viewmodel.ForgotPasswordViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {

    ActivityForgotPasswordBinding binding;

    String strMobile, otp;

    ForgotPasswordViewModel forgotPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        forgotPasswordViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ForgotPasswordViewModel();
            }
        }).get(ForgotPasswordViewModel.class);


        binding.idSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strMobile = binding.edtMobileNumber.getText().toString().trim();

                char first = strMobile.charAt(0);

                Integer marks = Integer.parseInt(String.valueOf(first));

                if (TextUtils.isEmpty(strMobile)) {
                    Toast.makeText(ForgotPasswordActivity.this, R.string.please_enter_your_mobile_number, Toast.LENGTH_SHORT).show();
                } else if (strMobile.length() < 10) {
                    Toast.makeText(ForgotPasswordActivity.this, R.string.please_enter_your_valid_mobile_number, Toast.LENGTH_SHORT).show();
                }
                else if (!(marks > 5)){
                    Toast.makeText(ForgotPasswordActivity.this, R.string.not_valid_mobile_number, Toast.LENGTH_SHORT).show();
                }
                else {

                    //forGoutPassword();

                    forgotPasswordViewModel.forgot(strMobile);
                }

            }
        });

        forgotPasswordViewModel.getForgotResponse().observe(this, new Observer<ForgotPasswordResponse>() {
            @Override
            public void onChanged(ForgotPasswordResponse response) {

                    if (response.getStatusCode().equals("200")) {

                        Toast.makeText(ForgotPasswordActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        otp =  response.getMessage();

                        String modifiedString = otp.substring(22);

                        Log.d("otps", modifiedString);
                        Log.d("ty",strMobile);

                        Intent intent = new Intent(ForgotPasswordActivity.this, VerifyOTPActivity.class);
                        intent.putExtra("mobile", strMobile );
                        intent.putExtra("otp", modifiedString );


                        startActivity(intent);
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }
}