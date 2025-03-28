package com.fototechparivarstalls.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.fototechparivarstalls.R;
import com.fototechparivarstalls.adapter.PreviousIssuesListAdapter;
import com.fototechparivarstalls.adapter.SelectCountryAdapter;
import com.fototechparivarstalls.databinding.ActivityRaisaIssueBinding;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.model.Issue.GetIssuesDetails;
import com.fototechparivarstalls.model.Issue.GetIssuesResponse;
import com.fototechparivarstalls.model.Issue.GetIssuessDetails;
import com.fototechparivarstalls.model.Issue.GetIssuessResponse;
import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.model.PaymentHistory.PaymentHistoryJson;
import com.fototechparivarstalls.viewmodel.ChangePasswordViewModel;
import com.fototechparivarstalls.viewmodel.RaisaIssueViewModel;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.UserLocalStore;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RaisaIssueActivity extends AppCompatActivity {
    ActivityRaisaIssueBinding binding;
    String bookingId, subject, meassge, token;

    RaisaIssueViewModel raisaIssueViewModel;

    UserLocalStore userLocalStore;

    UserStoreDetails user;

    String selectedUserId;
    Dialog dialog_GetCountryList;
    ArrayList<GetIssuesDetails> provinceList;

    PreviousIssuesListAdapter previousIssuesListAdapter;
    ArrayList<GetIssuessDetails> previousIssuesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRaisaIssueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();


        userLocalStore = new UserLocalStore(RaisaIssueActivity.this);
        user = userLocalStore.getLoggedInUser();

        token = user.getToken();


        Intent intent = getIntent();
        // receive the value by getStringExtra() method and
        // key must be same which is send by first activity
        bookingId = intent.getStringExtra("bookingId");

        Log.d("vbnm",bookingId);
        raisaIssueViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new RaisaIssueViewModel(userLocalStore);
            }
        }).get(RaisaIssueViewModel.class);


        getPreviousIssuesList();


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        binding.etIssueType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProvinceList();
            }
        });

        binding.idSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                subject = binding.etIssueType.getText().toString().trim();
                meassge = binding.message.getText().toString().trim();

                if (TextUtils.isEmpty(subject)) {

                    Toast.makeText(RaisaIssueActivity.this, "Please Enter Subject", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(meassge)) {

                    Toast.makeText(RaisaIssueActivity.this, "Please Enter Message", Toast.LENGTH_SHORT).show();

                } else {

                    //login();

                    raisaIssueViewModel.addRaisaIssue(bookingId, selectedUserId, meassge );

                }

            }
        });


        raisaIssueViewModel.getAddRaisaIssueResponse().observe(this, new Observer<ForgotPasswordResponse>() {
            @Override
            public void onChanged(ForgotPasswordResponse response) {

                if (response.getStatusCode().equals("200")) {

                    Toast.makeText(RaisaIssueActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                    onBackPressed();

                } else {
                    Toast.makeText(RaisaIssueActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void getPreviousIssuesList(){

        if (Uttils.getInternetConnection(RaisaIssueActivity.this)) {

            Uttils.showProgressDialoug(RaisaIssueActivity.this);

            ApiHandler.getApiService().getIssuessResponse(new PaymentHistoryJson(bookingId))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<GetIssuessResponse>() {
                        @Override
                        public void onNext(GetIssuessResponse getIssuessResponse) {
                            Uttils.dismissDialoug();

                            try {

                                if (getIssuessResponse.getMessage().equals("Issues not found.")) {

                                    binding.dataNotCome.setVisibility(View.VISIBLE);
                                    binding.PreviousIssuesText.setVisibility(View.GONE);
                                    binding.recyclerView.setVisibility(View.GONE);

                                }
                                if (!getIssuessResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(RaisaIssueActivity.this, getIssuessResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                } else if (getIssuessResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(RaisaIssueActivity.this, getIssuessResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                    previousIssuesArrayList = new ArrayList<>();

                                    previousIssuesArrayList.addAll(getIssuessResponse.getData());

                                    LinearLayoutManager layoutManager = new LinearLayoutManager(RaisaIssueActivity.this);
                                    binding.recyclerView.setLayoutManager(layoutManager);

                                    previousIssuesListAdapter = new PreviousIssuesListAdapter(RaisaIssueActivity.this, previousIssuesArrayList);
                                    binding.recyclerView.setAdapter(previousIssuesListAdapter);

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("PreviousIssuesList", "Exception: " + e.getMessage());
                                Toast.makeText(RaisaIssueActivity.this, "Exception occurred", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Uttils.dismissDialoug();
                            Toast.makeText(RaisaIssueActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Uttils.dismissDialoug();
                        }
                    });
        }

    }


    private void getProvinceList() {

        if (Uttils.getInternetConnection(RaisaIssueActivity.this)) {

            Uttils.showProgressDialoug(RaisaIssueActivity.this);


            ApiHandler.getApiService().getIssuesResponse()
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<GetIssuesResponse>() {
                        @Override
                        public void onNext(GetIssuesResponse getProvinceResponse) {
                            Uttils.dismissDialoug();

                            try {

                                if (!getProvinceResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(RaisaIssueActivity.this, getProvinceResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                } else if (getProvinceResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(RaisaIssueActivity.this, getProvinceResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                    //Log.d("tgh", String.valueOf(getProvinceResponse));
                                    provinceList = getProvinceResponse.getData();

                                    openCountryDialog("All User");

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Login", "Exception: " + e.getMessage());
                                Toast.makeText(RaisaIssueActivity.this, "Exception occurred", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Uttils.dismissDialoug();
                            Toast.makeText(RaisaIssueActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Uttils.dismissDialoug();
                        }
                    });

        }
    }

    private void openCountryDialog(String from) {

        dialog_GetCountryList = new Dialog(RaisaIssueActivity.this);
        dialog_GetCountryList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_GetCountryList.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_GetCountryList.getWindow().setGravity(Gravity.CENTER);

        dialog_GetCountryList.setCanceledOnTouchOutside(true);

        //setting custom layout to dialog_car_variant
        dialog_GetCountryList.setContentView(R.layout.diloug_select_country);

        ListView lv_Country = dialog_GetCountryList.findViewById(R.id.lv_Country);
        EditText edt_SearchCountry = dialog_GetCountryList.findViewById(R.id.edt_Search);
        ImageView cancel_button = dialog_GetCountryList.findViewById(R.id.cancel_button);

        SelectCountryAdapter adapterSelectCountry;

        adapterSelectCountry = new SelectCountryAdapter(RaisaIssueActivity.this, provinceList);
        lv_Country.setAdapter(adapterSelectCountry);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_GetCountryList.dismiss();

            }
        });

        edt_SearchCountry.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapterSelectCountry.filter(cs.toString());

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {


            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        lv_Country.setOnItemClickListener((parent, view, position, id) -> {

            String str_IssueName = provinceList.get(position).getIssueTitle();
            // String str_Mobile = provinceList.get(position).getMobileNumber();
            //et_user_name.setText(str_CountryName +" - "+ str_Mobile);
            binding.etIssueType.setText(str_IssueName);

            selectedUserId = provinceList.get(position).getId();

            dialog_GetCountryList.dismiss();
        });


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels / 2;
        int width = displaymetrics.widthPixels - 100;

        dialog_GetCountryList.getWindow().setLayout(width, height);

        dialog_GetCountryList.show();

    }
}