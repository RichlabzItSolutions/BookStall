package com.fototechparivarstalls.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fototechparivarstalls.R;
import com.fototechparivarstalls.activity.RaisaIssueActivity;
import com.fototechparivarstalls.adapter.PastEventsAdapter;
import com.fototechparivarstalls.adapter.PreviousIssuesListAdapter;
import com.fototechparivarstalls.adapter.UpcomingEventsAdapter;
import com.fototechparivarstalls.databinding.FragmentHomeBinding;
import com.fototechparivarstalls.model.Issue.GetIssuessResponse;
import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.model.PastEvents.PastEventsDetails;
import com.fototechparivarstalls.model.PastEvents.PastEventsResponse;
import com.fototechparivarstalls.model.UpcomingEvents.UpcomingEventsDetails;
import com.fototechparivarstalls.model.UpcomingEvents.UpcomingEventsResponse;
import com.fototechparivarstalls.viewmodel.HomeFragmentViewModel;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.UserLocalStore;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;

    String token, userId, c;

    ArrayList<UpcomingEventsDetails> upcomingEventsList;
    ArrayList<PastEventsDetails> pastEventsList;

    UpcomingEventsAdapter upcomingEventsAdapter;

    PastEventsAdapter pastEventsAdapter;
    UserLocalStore userLocalStore;
    UserStoreDetails user;
    HomeFragmentViewModel homeFragmentViewModel;

    private Handler handler;
    private Runnable autoScrollRunnable;
    private int currentPosition = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        userLocalStore = new UserLocalStore(getActivity());
        user = userLocalStore.getLoggedInUser();

        token = user.getToken();

        Log.d("tok", token);

        homeFragmentViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HomeFragmentViewModel(userLocalStore, getActivity());
            }
        }).get(HomeFragmentViewModel.class);

      //  homeFragmentViewModel.UpcomingList();

        getUpcomingEventList();
        getPastEventList();

        //getUpcomingEventList();
      // homeFragmentViewModel.PastList();


        binding.idFirstName.setText("Welcome " + user.getFirstName());
        binding.IdLocation.setText(user.getUserCity());

        Glide.with(getActivity())
                .load(user.getProfileImage())
                .placeholder(R.drawable.ic_upcoming_events)
                .into(binding.userPic);


       /* homeFragmentViewModel.getUpcomingListResponse().observe(getActivity(), new Observer<UpcomingEventsResponse>() {
            @Override
            public void onChanged(UpcomingEventsResponse response) {
                if (response != null) {

                    if (response.getStatusCode().equals("No upcoming events found.")) {

                        binding.dataNotComeUpcoming.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);

                    }
                    if (response.getStatusCode().equals("200")) {
                        // Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        upcomingEventsList = new ArrayList<>();
                        upcomingEventsList.addAll(response.getData());

                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
                        upcomingEventsAdapter = new UpcomingEventsAdapter(getContext(), upcomingEventsList);
                        binding.recyclerView.setAdapter(upcomingEventsAdapter);
                    } else if (response.getMessage().equals("Data not found")){
                        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                       *//* binding.dataNotCome.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);*//*
                    }

                    else  {
                        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                      //  binding.dataNotCome.setVisibility(View.VISIBLE);

                    }

                } else {
                  //  Toast.makeText(getActivity(), "Exception occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });*/



       /* homeFragmentViewModel.getPastListResponse().observe(getActivity(), new Observer<PastEventsResponse>() {
            @Override
            public void onChanged(PastEventsResponse response) {
                if (response != null) {


                    if (response.getStatusCode().equals("No past events found.")) {

                        binding.dataNotComePast.setVisibility(View.VISIBLE);
                        binding.recyclerViewPast.setVisibility(View.GONE);

                    }

                    if (response.getStatusCode().equals("200")) {
                        // Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        pastEventsList = new ArrayList<>();
                        pastEventsList.addAll(response.getData());

                        binding.recyclerViewPast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
                        pastEventsAdapter = new PastEventsAdapter(getContext(), pastEventsList);
                        binding.recyclerViewPast.setAdapter(pastEventsAdapter);
                    } *//*else if (response.getMessage().equals("Data not found")){
                        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        binding.dataNotCome.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                    }*//*

                    else  {
                        //Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        //  binding.dataNotCome.setVisibility(View.VISIBLE);

                    }

                } else {
                    //  Toast.makeText(getActivity(), "Exception occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        return view;
    }

    private void getUpcomingEventList(){


       /* if (Uttils.getInternetConnection(getActivity())) {

            Uttils.showProgressDialoug(getActivity());*/

            ApiHandler.setAuthToken(token);

            ApiHandler.getApiService().getUpcomingEventsListResponse()
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<UpcomingEventsResponse>() {
                        @Override
                        public void onNext(UpcomingEventsResponse upcomingEventsResponse) {
                            //Uttils.dismissDialoug();

                            try {

                                if (upcomingEventsResponse.getMessage().equals("No upcoming events found.")) {

                                    binding.dataNotComeUpcoming.setVisibility(View.VISIBLE);
                                    binding.recyclerView.setVisibility(View.GONE);

                                }
                                if (!upcomingEventsResponse.getStatusCode().equals("200")) {


                                    if (getContext() != null) {
                                        Toast.makeText(getActivity(), upcomingEventsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                }
                                if (upcomingEventsResponse.getStatusCode().equals("200")) {

                                    if (getContext() != null) {
                                        Toast.makeText(getActivity(), upcomingEventsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                    upcomingEventsList = new ArrayList<>();
                                    upcomingEventsList.addAll(upcomingEventsResponse.getData());

                                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
                                    upcomingEventsAdapter = new UpcomingEventsAdapter(getContext(), upcomingEventsList);
                                    binding.recyclerView.setAdapter(upcomingEventsAdapter);

                                    binding.recyclerView.setClipToPadding(false);
                                    binding.recyclerView.setPadding(0, 0, 100, 0);

                                }

                           } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("UpcomingEventsList", "Exception: " + e.getMessage());
                              //  Toast.makeText(getActivity(), "Exception occurred", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                           //Uttils.dismissDialoug();
                          //  Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                           // Uttils.dismissDialoug();
                        }
                    });
       /* }
        else {

            Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
        }*/

    }



    private void getPastEventList(){


       /* if (Uttils.getInternetConnection(getContext())) {

            Uttils.showProgressDialoug(getContext());*/

            ApiHandler.setAuthToken(user.getToken());

            ApiHandler.getApiService().getPastEventsListResponse()
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<PastEventsResponse>() {
                        @Override
                        public void onNext(PastEventsResponse pastEventsResponse) {
                           // Uttils.dismissDialoug();

                            try {

                                if (pastEventsResponse.getMessage().equals("No past events found.")) {

                                    binding.dataNotComePast.setVisibility(View.VISIBLE);
                                    binding.recyclerViewPast.setVisibility(View.GONE);

                                }
                                if (!pastEventsResponse.getStatusCode().equals("200")) {


                                    if (getContext() != null) {
                                        Toast.makeText(getActivity(), pastEventsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                    }

                                }
                                if (pastEventsResponse.getStatusCode().equals("200")) {

                                    if (getContext() != null) {
                                        Toast.makeText(getActivity(), pastEventsResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                    pastEventsList = new ArrayList<>();
                                    pastEventsList.addAll(pastEventsResponse.getData());

                                    binding.recyclerViewPast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
                                    pastEventsAdapter = new PastEventsAdapter(getContext(), pastEventsList);
                                    binding.recyclerView.setAdapter(pastEventsAdapter);

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("PastEventsList", "Exception: " + e.getMessage());
                               // Toast.makeText(getActivity(), "Exception occurred", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                          //  Uttils.dismissDialoug();
                           // Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                          //  Uttils.dismissDialoug();
                        }
                    });
      //  }

    }

   /* private void startAutoScroll() {
        handler = new Handler();
        autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentPosition == a.getItemCount()) {
                    currentPosition = 0; // Reset to the first position
                }
                binding.recyclerView.smoothScrollToPosition(currentPosition++);
                handler.postDelayed(this, 2000); // Scroll every 2 seconds
            }
        };
        handler.post(autoScrollRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null && autoScrollRunnable != null) {
            handler.removeCallbacks(autoScrollRunnable);
        }
    }*/
}