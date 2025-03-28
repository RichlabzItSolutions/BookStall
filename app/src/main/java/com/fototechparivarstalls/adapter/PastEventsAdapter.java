package com.fototechparivarstalls.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fototechparivarstalls.R;
import com.fototechparivarstalls.databinding.PastEventLayoutBinding;
import com.fototechparivarstalls.databinding.UpcomingEventsLayoutBinding;
import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.model.PastEvents.PastEventsDetails;
import com.fototechparivarstalls.model.UpcomingEvents.UpcomingEventsDetails;
import com.fototechparivarstalls.webservices.UserLocalStore;

import java.util.ArrayList;

public class PastEventsAdapter extends  RecyclerView.Adapter<PastEventsAdapter.MyViewHolder> {

    private Context context;
    ArrayList<PastEventsDetails> pastEventsList = new ArrayList<>();

    String token, userId;

    UserLocalStore userLocalStore;
    UserStoreDetails user;


    public PastEventsAdapter(Context context, ArrayList<PastEventsDetails>  pastEventsList) {
        this.pastEventsList = pastEventsList;
        this.context = context;


    }

    @NonNull
    @Override
    public PastEventsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PastEventLayoutBinding binding = PastEventLayoutBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull PastEventsAdapter.MyViewHolder holder, int position) {
        PastEventsDetails pastEventsModel = pastEventsList.get(position);

        userLocalStore = new UserLocalStore(context);
        user = userLocalStore.getLoggedInUser();

        token = user.getToken();


        Glide.with(context)
                .load(pastEventsModel.getEventImage())
                .placeholder(R.drawable.ic_upcoming_events)
                .into(holder.binding.IdEventImage);

    }

    @Override
    public int getItemCount() {
        return pastEventsList.size();
    }

    @Override
    public long getItemId(int position) {
        return  pastEventsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void removeAtPosition(int position) {
        pastEventsList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        PastEventLayoutBinding binding;

        public MyViewHolder(PastEventLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }

}
