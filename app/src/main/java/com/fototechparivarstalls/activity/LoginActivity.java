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
import com.fototechparivarstalls.databinding.ActivityLoginBinding;
import com.fototechparivarstalls.model.Login.GetLoginResponse;
import com.fototechparivarstalls.viewmodel.LoginViewModel;
import com.fototechparivarstalls.webservices.UserLocalStore;
import com.fototechparivarstalls.webservices.Uttils;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    String strMobile;
    String strPassword;

    LoginViewModel loginViewModel;

    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        userLocalStore = new UserLocalStore(LoginActivity.this);


        loginViewModel = new ViewModelProvider(LoginActivity.this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new LoginViewModel(userLocalStore, LoginActivity.this);
            }
        }).get(LoginViewModel.class);


        binding.idForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);

            }
        });

        binding.ivShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                togglePasswordVisibility();

            }
        });


        binding.idLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strMobile = binding.edtUsername.getText().toString().trim();
                strPassword = binding.edtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(strMobile)) {

                    Toast.makeText(LoginActivity.this, R.string.please_enter_your_mobile_number, Toast.LENGTH_SHORT).show();

                } else if (strMobile.length() < 10) {

                    Toast.makeText(LoginActivity.this, R.string.please_enter_your_valid_mobile_number, Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(strPassword)) {

                    Toast.makeText(LoginActivity.this, R.string.please_enter_your_password, Toast.LENGTH_SHORT).show();

                } else {

                    //login();

                    loginViewModel.login(strMobile, strPassword);
                    // startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                  /*  Intent i_Home = new Intent(LoginActivity.this, HomeActivity.class);
                    i_Home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);*/

                }

            }
        });


        loginViewModel.getLoginResponse().observe(this, new Observer<GetLoginResponse>() {
            @Override
            public void onChanged(GetLoginResponse response) {


                /*if (response.getMessage().equals("Invalid Credentials")){

                    Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                }*/


                if (response != null) {

                    if (!response.getMessage().equals("User Logged in Successfully")){

                        Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    else if (response.getMessage().equals("User Logged in Successfully")) {

                        Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                        Intent i_Home = new Intent(LoginActivity.this, HomeActivity.class);
                        i_Home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(i_Home);
                        finish();

                    } else {
                        Toast.makeText(LoginActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Exception occurred", Toast.LENGTH_SHORT).show();


                }
            }

        });

    }





    private void togglePasswordVisibility() {
        if (binding.edtPassword.getTransformationMethod() == null) {
            binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.ivShowPassword.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_password_off, null));
        } else {
            binding.edtPassword.setTransformationMethod(null);
            binding.ivShowPassword.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_password_open, null));
        }

        // Move cursor to the end of the text
        binding.edtPassword.setSelection(binding.edtPassword.getText().length());
    }

}