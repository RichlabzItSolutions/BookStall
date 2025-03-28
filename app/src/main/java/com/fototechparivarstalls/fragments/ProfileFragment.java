package com.fototechparivarstalls.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fototechparivarstalls.R;
import com.fototechparivarstalls.activity.ChangePasswordActivity;
import com.fototechparivarstalls.activity.LoginActivity;
import com.fototechparivarstalls.databinding.FragmentProfileBinding;
import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.webservices.UserLocalStore;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    UserLocalStore userLocalStore;
    UserStoreDetails user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile, container, false);
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        userLocalStore = new UserLocalStore(getActivity());
        user = userLocalStore.getLoggedInUser();

        binding.idFirstName.setText(user.getFirstName());
        binding.idPhone.setText(user.getEmailId());


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        Glide.with(getActivity())
                .load(user.getProfileImage())
                .placeholder(R.drawable.ic_upcoming_events)
                .into(binding.logoId);

        binding.IdMyBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flFragment, new MybookingFragment());
                fragmentTransaction.addToBackStack(null); // Add this transaction to the back stack
                fragmentTransaction.commit();
            }
        });


        binding.IdChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ChangePasswordActivity.class));
            }
        });

        binding.IdLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Logout successfully", Toast.LENGTH_SHORT).show();
                final UserLocalStore userLocalStore = new UserLocalStore(getActivity());
                userLocalStore.clearUserData();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                ((Activity)getActivity()).finish();

            }
        });


        return view;
    }
}