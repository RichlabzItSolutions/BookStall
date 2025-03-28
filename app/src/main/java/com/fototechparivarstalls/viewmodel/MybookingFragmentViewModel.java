package com.fototechparivarstalls.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fototechparivarstalls.model.GetBookings.GetBookingsDetails;
import com.fototechparivarstalls.model.GetBookings.GetBookingsResponse;
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

public class MybookingFragmentViewModel extends ViewModel {

    private MutableLiveData<GetBookingsResponse> getBookingList;

    private UserLocalStore userLocalStore;

    public MybookingFragmentViewModel(UserLocalStore userLocalStore) {
        this.userLocalStore = userLocalStore;
        getBookingList = new MutableLiveData<>();

    }

    public LiveData<GetBookingsResponse> getBookingListResponse() {
        return getBookingList;

    }



    public void BookingList() {
        UserStoreDetails user = userLocalStore.getLoggedInUser();
        ApiHandler.setAuthToken(user.getToken());  // Set the token

        ApiHandler.getApiService().getBookingListResponse()
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<GetBookingsResponse>() {
                    @Override
                    public void onNext(GetBookingsResponse response) {
                        Uttils.dismissDialoug();

                        try {

                            getBookingList.setValue(response);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Upcoming Events List", "Exception: " + e.getMessage());

                        }

                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        Uttils.dismissDialoug();
                        getBookingList.setValue(null);
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Uttils.dismissDialoug();
                    }
                });
    }


}
