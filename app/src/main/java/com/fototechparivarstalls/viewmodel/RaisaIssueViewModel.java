package com.fototechparivarstalls.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fototechparivarstalls.model.ChangePassword.ChangePasswordJson;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.model.Issue.AddIssueJson;
import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.UserLocalStore;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RaisaIssueViewModel extends ViewModel {
    private MutableLiveData<ForgotPasswordResponse> addRaisaIssueResponse;
    private UserLocalStore userLocalStore;

    public RaisaIssueViewModel(UserLocalStore userLocalStore) {
        this.userLocalStore = userLocalStore;
        addRaisaIssueResponse = new MutableLiveData<>();
    }

  /*  public LoginViewModel() {
        loginResponse = new MutableLiveData<>();
    }*/

    public LiveData<ForgotPasswordResponse> getAddRaisaIssueResponse() {
        return addRaisaIssueResponse;
    }

    public void addRaisaIssue(String booking_id, String subject, String message ) {
        UserStoreDetails user = userLocalStore.getLoggedInUser();
        ApiHandler.setAuthToken(user.getToken());  // Set the token

        Log.d("dfs",user.getToken());
        Log.d("booking_id",booking_id);
        Log.d("booking_id",subject);
        Log.d("booking_id",message);
        ApiHandler.getApiService().getAddIssueResponse(new AddIssueJson(booking_id , subject, message))
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

                            addRaisaIssueResponse.setValue(response);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Login", "Exception: " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Uttils.dismissDialoug();
                        addRaisaIssueResponse.setValue(null);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Uttils.dismissDialoug();
                    }
                });

    }

}
