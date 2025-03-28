package com.fototechparivarstalls.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fototechparivarstalls.R;
import com.fototechparivarstalls.databinding.ActivityVerifyOtpactivityBinding;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.model.ForgotPaasword.VerifiyOTPJson;
import com.fototechparivarstalls.model.ForgotPaasword.VerifiyOTPResponse;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.Uttils;
import com.mukesh.OnOtpCompletionListener;

import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class VerifyOTPActivity extends AppCompatActivity {
    ActivityVerifyOtpactivityBinding binding;

    String strMobile, strOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        Intent intent3 = getIntent();

        strMobile = intent3.getStringExtra("mobile");
        strOTP = intent3.getStringExtra("otp");

        Log.d("vty",strMobile);
        Log.d("votp",strOTP);

       // binding.priViewMobileID.setText(R.string.we_sent_a_verification_code_to + strMobile );


        binding.otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {

                Log.d("onOtpCompleted=>", otp);

                binding.idVerifyOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        strOTP = binding.otpView.getText().toString().trim();

                        if (TextUtils.isEmpty(strOTP)) {
                            Toast.makeText(VerifyOTPActivity.this, R.string.please_enter_your_otp, Toast.LENGTH_SHORT).show();
                        } else if (!TextUtils.equals(strOTP, otp)) {
                            Toast.makeText(VerifyOTPActivity.this, R.string.wrong_otp, Toast.LENGTH_SHORT).show();
                        } else {
                            OtpVerify();
                        }

                    }
                });

            }
        });

    }

    private void OtpVerify() {

        if (Uttils.getInternetConnection(VerifyOTPActivity.this)) {

            Uttils.showProgressDialoug(VerifyOTPActivity.this);

            ApiHandler.getApiService().getVerifyOtp(new VerifiyOTPJson(strMobile, strOTP))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<VerifiyOTPResponse>() {
                        @Override
                        public void onNext(VerifiyOTPResponse verifyOtpResponse) {
                            Uttils.dismissDialoug();


                            try {

                                if (!verifyOtpResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(VerifyOTPActivity.this, verifyOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                } else if (verifyOtpResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(VerifyOTPActivity.this, verifyOtpResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(VerifyOTPActivity.this, ResetPasswordActivity.class);

                                    intent.putExtra("strMobile", strMobile);

                                    startActivity(intent);

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("verifyOtpResponse", "Exception: " + e.getMessage());
                                Toast.makeText(VerifyOTPActivity.this, "Exception occurred", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Uttils.dismissDialoug();
                            Toast.makeText(VerifyOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Uttils.dismissDialoug();
                        }
                    });

        }

    }
}