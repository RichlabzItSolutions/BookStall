package com.fototechparivarstalls.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.model.PastEvents.PastEventsResponse;
import com.fototechparivarstalls.model.UpcomingEvents.UpcomingEventsResponse;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.UserLocalStore;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class HomeFragmentViewModel extends ViewModel {

    private MutableLiveData<UpcomingEventsResponse> upcomingEventsList;
    private MutableLiveData<PastEventsResponse> pastEventsList;
    private UserLocalStore userLocalStore;

    private Context context;


    public HomeFragmentViewModel(UserLocalStore userLocalStore, Context context) {
        this.userLocalStore = userLocalStore;
        this.context = context;
        upcomingEventsList = new MutableLiveData<>();
        pastEventsList = new MutableLiveData<>();
    }

    public LiveData<UpcomingEventsResponse> getUpcomingListResponse() {
        return upcomingEventsList;

    }

    public LiveData<PastEventsResponse> getPastListResponse() {
        return pastEventsList;

    }

    public void UpcomingList() {

        UserStoreDetails user = userLocalStore.getLoggedInUser();


        ApiHandler.setAuthToken(user.getToken());  // Set the token

        /*if (Uttils.getInternetConnection(context)) {

            Uttils.showProgressDialoug(context);*/

        ApiHandler.getApiService().getUpcomingEventsListResponse()
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UpcomingEventsResponse>() {
                    @Override
                    public void onNext(UpcomingEventsResponse response) {
                        Uttils.dismissDialoug();

                        try {

                            upcomingEventsList.setValue(response);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Upcoming Events List", "Exception: " + e.getMessage());

                        }

                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Uttils.dismissDialoug();
                        upcomingEventsList.setValue(null);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Uttils.dismissDialoug();
                    }
                });


       /* } else {

            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }*/
    }


    public void PastList() {

        UserStoreDetails user = userLocalStore.getLoggedInUser();


            ApiHandler.setAuthToken(user.getToken());  // Set the token

       /* if (Uttils.getInternetConnection(context)) {

            Uttils.showProgressDialoug(context);*/

            ApiHandler.getApiService().getPastEventsListResponse()
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<PastEventsResponse>() {
                        @Override
                        public void onNext(PastEventsResponse response) {
                            Uttils.dismissDialoug();

                            try {

                                pastEventsList.setValue(response);

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Upcoming Events List", "Exception: " + e.getMessage());

                            }

                        }

                        @Override
                        public void onError(@NotNull Throwable e) {
                            Uttils.dismissDialoug();
                            pastEventsList.setValue(null);
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            Uttils.dismissDialoug();
                        }
                    });


        /*} else {

            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        }*/
    }

}
