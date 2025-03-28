package com.fototechparivarstalls.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fototechparivarstalls.adapter.GetBookingsAdapter;
import com.fototechparivarstalls.adapter.PastEventsAdapter;
import com.fototechparivarstalls.adapter.UpcomingEventsAdapter;
import com.fototechparivarstalls.databinding.FragmentMybookingBinding;
import com.fototechparivarstalls.model.GetBookings.GetBookingsDetails;
import com.fototechparivarstalls.model.GetBookings.GetBookingsResponse;
import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.model.PastEvents.PastEventsDetails;
import com.fototechparivarstalls.model.UpcomingEvents.UpcomingEventsDetails;
import com.fototechparivarstalls.model.UpcomingEvents.UpcomingEventsResponse;
import com.fototechparivarstalls.viewmodel.HomeFragmentViewModel;
import com.fototechparivarstalls.viewmodel.MybookingFragmentViewModel;
import com.fototechparivarstalls.webservices.UserLocalStore;

import java.util.ArrayList;


public class MybookingFragment extends Fragment {

    FragmentMybookingBinding binding;


    String token, userId, c;

    ArrayList<GetBookingsDetails> bookingList;

    GetBookingsAdapter getBookingsAdapter;

    UserLocalStore userLocalStore;
    UserStoreDetails user;
    MybookingFragmentViewModel mybookingFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMybookingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        userLocalStore = new UserLocalStore(getActivity());
        user = userLocalStore.getLoggedInUser();

        token = user.getToken();

        mybookingFragmentViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MybookingFragmentViewModel(userLocalStore);
            }
        }).get(MybookingFragmentViewModel.class);

        mybookingFragmentViewModel.BookingList();


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        mybookingFragmentViewModel.getBookingListResponse().observe(getActivity(), new Observer<GetBookingsResponse>() {
            @Override
            public void onChanged(GetBookingsResponse response) {
                if (response != null) {


                    if (response.getMessage().equals("Bookings not found.")) {

                        binding.dataNotComePast.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);

                    }

                    if (response.getStatusCode().equals("200")) {
                        // Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        binding.dataNotComePast.setVisibility(View.GONE);
                        bookingList = new ArrayList<>();
                        bookingList.addAll(response.getData());

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        binding.recyclerView.setLayoutManager(layoutManager);
                        getBookingsAdapter = new GetBookingsAdapter(getContext(), bookingList);
                        binding.recyclerView.setAdapter(getBookingsAdapter);
                    } /*else if (response.getMessage().equals("Data not found")){
                        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        binding.dataNotCome.setVisibility(View.VISIBLE);
                        binding.recyclerView.setVisibility(View.GONE);
                    }*/

                    else  {
                        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        //  binding.dataNotCome.setVisibility(View.VISIBLE);

                    }

                } else {
                    //  Toast.makeText(getActivity(), "Exception occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}