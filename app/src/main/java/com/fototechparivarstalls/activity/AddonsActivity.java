package com.fototechparivarstalls.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fototechparivarstalls.adapter.AddOnsListAdapter;
import com.fototechparivarstalls.databinding.ActivityAddonsBinding;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.model.GetAddons.AddAddonsJson;
import com.fototechparivarstalls.model.GetAddons.AddaddonsDetails;
import com.fototechparivarstalls.model.GetAddons.GetAddonsDetails;
import com.fototechparivarstalls.model.GetAddons.GetAddonsResponse;
import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.model.PaymentHistory.PaymentHistoryJson;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.UserLocalStore;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AddonsActivity extends AppCompatActivity implements AddOnsListAdapter.OnQuantityChangeListener {
    ActivityAddonsBinding binding;
    String bookingId;
    AddOnsListAdapter addOnsListAdapter;
    ArrayList<GetAddonsDetails> getAddonsArrayList;

    ArrayList<AddaddonsDetails> addonsList = new ArrayList<>();

    int id;
    int qty;

    UserLocalStore userLocalStore;

    AddaddonsDetails user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddonsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        userLocalStore = new UserLocalStore(AddonsActivity.this);

        Intent intent = getIntent();
        // receive the value by getStringExtra() method and
        // key must be same which is send by first activity
        bookingId = intent.getStringExtra("bookingId");

        Log.d("bookingId", bookingId);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        getAddonsList();

    }


    private void getAddonsList(){

        if (Uttils.getInternetConnection(AddonsActivity.this)) {

            Uttils.showProgressDialoug(AddonsActivity.this);

            ApiHandler.getApiService().getAddonsResponse(new PaymentHistoryJson(bookingId))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<GetAddonsResponse>() {
                        @Override
                        public void onNext(GetAddonsResponse getAddonsResponse) {
                            Uttils.dismissDialoug();

                            try {

                                if (!getAddonsResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(AddonsActivity.this, getAddonsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                } else if (getAddonsResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(AddonsActivity.this, getAddonsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                    getAddonsArrayList = new ArrayList<>();
                                    getAddonsArrayList.addAll(getAddonsResponse.getData());
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(AddonsActivity.this);
                                    binding.recyclerView.setLayoutManager(layoutManager);
                                    addOnsListAdapter = new AddOnsListAdapter(AddonsActivity.this, getAddonsArrayList, AddonsActivity.this,
                                            binding.idSubmit, bookingId);
                                    binding.recyclerView.setAdapter(addOnsListAdapter);

                                    binding.Total.setText("00");
                                    updateTotalPayment();

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("AddonsResponseList", "Exception: " + e.getMessage());
                                Toast.makeText(AddonsActivity.this, "Exception occurred", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Uttils.dismissDialoug();
                            Toast.makeText(AddonsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Uttils.dismissDialoug();
                        }
                    });
        }

    }

    @Override
    public void onQuantityChanged(int AddonId, int Qty) {
        updateTotalPayment();
    }

    private void updateTotalPayment() {
        int totalPayment = 0;
        binding.Total.setText("00");
        // Use a Set to store unique IDs
        Set<Integer> uniqueIds = new HashSet<>();
        for (GetAddonsDetails item : getAddonsArrayList) {
            totalPayment += item.getPrice() * item.getQty();

            AddaddonsDetails cQty = new AddaddonsDetails(item.getAddonId(), item.getQty());

            if (userLocalStore != null) {
                userLocalStore.storeQtyData(cQty);
            } else {
                Log.e("AddonsActivity", "userLocalStore is null!");
            }
        }

        binding.Total.setText("₹ " + totalPayment);

    }

}