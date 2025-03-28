package com.fototechparivarstalls.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fototechparivarstalls.R;
import com.fototechparivarstalls.databinding.FragmentEventsBinding;
import com.fototechparivarstalls.databinding.FragmentHomeBinding;

public class EventsFragment extends Fragment {
    FragmentEventsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEventsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        return view;
    }
}