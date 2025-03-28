package com.fototechparivarstalls.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fototechparivarstalls.R;
import com.fototechparivarstalls.adapter.PaymentsHistoryListAdapter;
import com.fototechparivarstalls.adapter.PreviousIssuesListAdapter;
import com.fototechparivarstalls.databinding.ActivityPaymentsHistoryBinding;
import com.fototechparivarstalls.databinding.ActivityVerifyOtpactivityBinding;
import com.fototechparivarstalls.model.Issue.GetIssuessDetails;
import com.fototechparivarstalls.model.Issue.GetIssuessResponse;
import com.fototechparivarstalls.model.PaymentHistory.PaymentHistoryDetails;
import com.fototechparivarstalls.model.PaymentHistory.PaymentHistoryJson;
import com.fototechparivarstalls.model.PaymentHistory.PaymentHistoryResponse;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PaymentsHistoryActivity extends AppCompatActivity {

    ActivityPaymentsHistoryBinding binding;

    String bookingId;

    PaymentsHistoryListAdapter paymentsHistoryListAdapter;

    ArrayList<PaymentHistoryDetails> paymentHistoryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPaymentsHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().hide();

        Intent intent = getIntent();
        // receive the value by getStringExtra() method and
        // key must be same which is send by first activity
        bookingId = intent.getStringExtra("bookingId");


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getPaymentHistoryList();




    }


    private void getPaymentHistoryList(){

        if (Uttils.getInternetConnection(PaymentsHistoryActivity.this)) {

            Uttils.showProgressDialoug(PaymentsHistoryActivity.this);

            ApiHandler.getApiService().getPaymentHistoryResponse(new PaymentHistoryJson(bookingId))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<PaymentHistoryResponse>() {
                        @Override
                        public void onNext(PaymentHistoryResponse paymentHistoryResponse) {
                            Uttils.dismissDialoug();

                            try {

                               /* if (paymentHistoryResponse.getStatusCode().equals("200")) {

                                    binding.dataNotCome.setVisibility(View.VISIBLE);

                                }*/
                                if (!paymentHistoryResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(PaymentsHistoryActivity.this, paymentHistoryResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                } else if (paymentHistoryResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(PaymentsHistoryActivity.this, paymentHistoryResponse.getMessage(), Toast.LENGTH_SHORT).show();


                                    if (paymentHistoryResponse.getTotalAmount().equals("0")){
                                        binding.IdTotalLiner.setVisibility(View.GONE);
                                        binding.IdReferNumber.setVisibility(View.GONE);
                                        binding.PaymentHistoryLinear.setVisibility(View.GONE);
                                        binding.dataNotCome.setVisibility(View.VISIBLE);
                                        Log.d("ghjk","Nikun");
                                    }
                                    else{



                                    }

                                    paymentHistoryArrayList = new ArrayList<>();

                                    paymentHistoryArrayList.addAll(paymentHistoryResponse.getData());

                                    String EventName = paymentHistoryResponse.getData().get(0).getEventName();
                                    String RefNumber = paymentHistoryResponse.getData().get(0).getRefNumber();
                                    String EventDate = paymentHistoryResponse.getData().get(0).getPaidDate();
                                    String PaidAmount = paymentHistoryResponse.getData().get(0).getPaidAmount();
                                    String TotalAmount = paymentHistoryResponse.getTotalAmount();

                                    binding.eventTitle.setText(EventName);
                                    binding.eventDate.setText(EventDate);
                                    binding.Total.setText(TotalAmount + "/-");
                                    binding.IdReferNumber.setText(RefNumber);

                                   // Log.d("ghjk",PaidAmount);



                                    LinearLayoutManager layoutManager = new LinearLayoutManager(PaymentsHistoryActivity.this);
                                    binding.recyclerView.setLayoutManager(layoutManager);

                                    paymentsHistoryListAdapter = new PaymentsHistoryListAdapter(PaymentsHistoryActivity.this, paymentHistoryArrayList);
                                    binding.recyclerView.setAdapter(paymentsHistoryListAdapter);

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("PaymentHistoryList", "Exception: " + e.getMessage());
                               // Toast.makeText(PaymentsHistoryActivity.this, "Exception occurred", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Uttils.dismissDialoug();
                            Toast.makeText(PaymentsHistoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

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