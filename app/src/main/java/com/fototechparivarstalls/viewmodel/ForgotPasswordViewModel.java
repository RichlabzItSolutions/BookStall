package com.fototechparivarstalls.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordJson;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordViewModel extends ViewModel {

    private MutableLiveData<ForgotPasswordResponse> forgotResponse;

    public ForgotPasswordViewModel() {
        forgotResponse = new MutableLiveData<>();
    }

    public LiveData<ForgotPasswordResponse> getForgotResponse() {
        return forgotResponse;
    }


    public void forgot(String mobileNumber ) {

        ApiHandler.getApiService().getForgotPasswordResponse( new ForgotPasswordJson(mobileNumber))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ForgotPasswordResponse>() {
                    @Override
                    public void onNext(ForgotPasswordResponse response) {
                        Uttils.dismissDialoug();

                        try {
                            forgotResponse.setValue(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Forgot Password", "Exception: " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Uttils.dismissDialoug();
                        forgotResponse.setValue(null);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Uttils.dismissDialoug();
                    }
                });

    }

}
