package com.fototechparivarstalls.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fototechparivarstalls.R;
import com.fototechparivarstalls.databinding.ActivityResetPasswordBinding;
import com.fototechparivarstalls.model.ChangePassword.ChangePasswordJson;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.model.ResetPassword.ResetPasswordJson;
import com.fototechparivarstalls.viewmodel.ChangePasswordViewModel;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.UserLocalStore;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ResetPasswordActivity extends AppCompatActivity {

    ActivityResetPasswordBinding binding;

    String MobileNumber;

    String  newPassword, confirmPassword, token, c ;

    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();


        Intent intent4 = getIntent();

        MobileNumber = intent4.getStringExtra("strMobile");


        binding.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        binding.ivShowPasswordNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                togglePasswordNewVisibility();
            }
        });

        binding.ivShowPasswordConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                togglePasswordConfirmVisibility();
            }
        });

        binding.idSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newPassword = binding.edtNewPassword.getText().toString().trim();
                confirmPassword = binding.edtConfirmPassword.getText().toString().trim();

                 if (TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, R.string.please_enter_your_new_password, Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, R.string.please_enter_your_confirm_password, Toast.LENGTH_SHORT).show();
                }
                else if (!TextUtils.equals(newPassword, confirmPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, R.string.password_not_match, Toast.LENGTH_SHORT).show();
                }
                else {


                    //changePasswordViewModel.changePassword(oldPassword, newPassword, confirmPassword);
                     resetPassword();
                }

            }
        });

    }

    private void togglePasswordNewVisibility() {
        if (binding.edtNewPassword.getTransformationMethod() == null) {
            binding.edtNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.ivShowPasswordNew.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_password_off, null));
        } else {
            binding.edtNewPassword.setTransformationMethod(null);
            binding.ivShowPasswordNew.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_password_open, null));
        }
        // Move cursor to the end of the text
        binding.edtNewPassword.setSelection(binding.edtNewPassword.getText().length());
    }

    private void togglePasswordConfirmVisibility() {
        if (binding.edtConfirmPassword.getTransformationMethod() == null) {
            binding.edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.ivShowPasswordConfirm.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_password_off, null));
        } else {
            binding.edtConfirmPassword.setTransformationMethod(null);
            binding.ivShowPasswordConfirm.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_password_open, null));
        }
        // Move cursor to the end of the text
        binding.edtConfirmPassword.setSelection(binding.edtConfirmPassword.getText().length());
    }

    private void resetPassword(){
        ApiHandler.getApiService().getresetPasswordResponse( new ResetPasswordJson(MobileNumber, newPassword, confirmPassword))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ForgotPasswordResponse>() {
                    @Override
                    public void onNext(ForgotPasswordResponse response) {
                        Uttils.dismissDialoug();


                        try {

                            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);

                            startActivity(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Reset Password", "Exception: " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Uttils.dismissDialoug();

                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Uttils.dismissDialoug();
                    }
                });
    }
}