package com.fototechparivarstalls.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fototechparivarstalls.activity.LoginActivity;
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

public class LoginViewModel  extends ViewModel{
    private MutableLiveData<GetLoginResponse> loginResponse;
    private UserLocalStore userLocalStore;

    private Context context;

    public LoginViewModel(UserLocalStore userLocalStore, Context context) {
        this.userLocalStore = userLocalStore;
        this.context = context;
        loginResponse = new MutableLiveData<>();
    }

    public LiveData<GetLoginResponse> getLoginResponse() {
        return loginResponse;
    }

    public void login(String mobileNumber, String password ) {

        if (Uttils.getInternetConnection(context)) {

            Uttils.showProgressDialoug(context);

            ApiHandler.getApiService().getLoginResponse( new LoginJson(mobileNumber, password))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<GetLoginResponse>() {
                        @Override
                        public void onNext(GetLoginResponse response) {
                            Uttils.dismissDialoug();

                            try {

                                //Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();

                                if (!response.getMessage().equals("User Logged in Successfully")){

                                    Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();

                                }

                                String userId = response.getUserData().getUserId();
                                String userCode = response.getUserData().getUserCode();
                                String firstname = response.getUserData().getFirstName();
                                String  lastname= response.getUserData().getLastName();
                                String email = response.getUserData().getEmailId();
                                String mobile = response.getUserData().getMobileNumber();
                                String token = response.getUserData().getToken();
                                String userCity = response.getUserData().getUserCity();
                                String profileImage = response.getUserData().getProfileImage();

                                UserStoreDetails cUser = new UserStoreDetails(userId, userCode, firstname, lastname, email, mobile, userCity, profileImage, token  );

                                userLocalStore.storeUserData(cUser);

                                loginResponse.setValue(response);

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Login", "Exception: " + e.getMessage());
                            }
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            Uttils.dismissDialoug();
                            loginResponse.setValue(null);
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            Uttils.dismissDialoug();
                        }
                    });


        } else {

            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }
    }

}
