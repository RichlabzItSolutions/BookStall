package com.fototechparivarstalls.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.fototechparivarstalls.R;
import com.fototechparivarstalls.databinding.ActivityChangePasswordBinding;
import com.fototechparivarstalls.databinding.ActivityLoginBinding;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.viewmodel.ChangePasswordViewModel;
import com.fototechparivarstalls.viewmodel.ForgotPasswordViewModel;
import com.fototechparivarstalls.viewmodel.LoginViewModel;
import com.fototechparivarstalls.webservices.UserLocalStore;

public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding binding;

    ChangePasswordViewModel changePasswordViewModel;

    String oldPassword, newPassword, confirmPassword, token, c ;

    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();


        changePasswordViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ChangePasswordViewModel(userLocalStore);
            }
        }).get(ChangePasswordViewModel.class);


        binding.backId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.ivShowPasswordOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                togglePasswordOldVisibility();

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

                oldPassword = binding.edtOldPassword.getText().toString().trim();
                newPassword = binding.edtNewPassword.getText().toString().trim();
                confirmPassword = binding.edtConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(oldPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, R.string.please_enter_your_old_password, Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, R.string.please_enter_your_new_password, Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, R.string.please_enter_your_confirm_password, Toast.LENGTH_SHORT).show();
                }
                else if (!TextUtils.equals(newPassword, confirmPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, R.string.password_not_match, Toast.LENGTH_SHORT).show();
                }
                else {


                    changePasswordViewModel.changePassword(oldPassword, newPassword, confirmPassword);
                   // changePassword();
                }

            }
        });



        changePasswordViewModel.getChangPasswordResponse().observe(this, new Observer<ForgotPasswordResponse>() {
            @Override
            public void onChanged(ForgotPasswordResponse response) {

                if (response.getStatusCode().equals("200")) {

                    Toast.makeText(ChangePasswordActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);

                    startActivity(intent);
                } else {
                    Toast.makeText(ChangePasswordActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void togglePasswordOldVisibility() {
        if (binding.edtOldPassword.getTransformationMethod() == null) {
            binding.edtOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.ivShowPasswordOld.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_password_off, null));
        } else {
            binding.edtOldPassword.setTransformationMethod(null);
            binding.ivShowPasswordOld.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_password_open, null));
        }
        // Move cursor to the end of the text
        binding.edtOldPassword.setSelection(binding.edtOldPassword.getText().length());
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
}