package com.fototechparivarstalls.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fototechparivarstalls.model.ChangePassword.ChangePasswordJson;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.model.Login.GetLoginResponse;
import com.fototechparivarstalls.model.Login.LoginJson;
import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.UserLocalStore;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ChangePasswordViewModel extends ViewModel {
    private MutableLiveData<ForgotPasswordResponse> changPasswordResponse;
    private UserLocalStore userLocalStore;

    public ChangePasswordViewModel(UserLocalStore userLocalStore) {
        this.userLocalStore = userLocalStore;
        changPasswordResponse = new MutableLiveData<>();
    }

  /*  public LoginViewModel() {
        loginResponse = new MutableLiveData<>();
    }*/

    public LiveData<ForgotPasswordResponse> getChangPasswordResponse() {
        return changPasswordResponse;
    }

    public void changePassword(String current_password, String new_password, String confirm_password ) {
        UserStoreDetails user = userLocalStore.getLoggedInUser();
        ApiHandler.setAuthToken(user.getToken());  // Set the token

        ApiHandler.getApiService().getChangePasswordResponse( new ChangePasswordJson(current_password, new_password, confirm_password))
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<ForgotPasswordResponse>() {
                    @Override
                    public void onNext(ForgotPasswordResponse response) {
                        Uttils.dismissDialoug();


                        try {


                        /*    String userId = response.getUserData().getUserId();
                            String userCode = response.getUserData().getUserCode();
                            String firstname = response.getUserData().getFirstName();
                            String  lastname= response.getUserData().getLastName();
                            String email = response.getUserData().getEmailId();
                            String mobile = response.getUserData().getMobileNumber();
                            String token = response.getUserData().getToken();



                            UserStoreDetails cUser = new UserStoreDetails(userId, userCode, firstname, lastname, email, mobile, token  );*/

                            //userLocalStore.storeUserData(cUser);

                            changPasswordResponse.setValue(response);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Login", "Exception: " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Uttils.dismissDialoug();
                        changPasswordResponse.setValue(null);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Uttils.dismissDialoug();
                    }
                });

    }
}
