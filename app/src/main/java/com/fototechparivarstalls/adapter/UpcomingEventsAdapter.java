package com.fototechparivarstalls.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fototechparivarstalls.R;
import com.fototechparivarstalls.activity.BookStallListActivity;
import com.fototechparivarstalls.activity.ForgotPasswordActivity;
import com.fototechparivarstalls.activity.RaisaIssueActivity;
import com.fototechparivarstalls.activity.VerifyOTPActivity;
import com.fototechparivarstalls.databinding.UpcomingEventsLayoutBinding;
import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.model.UpcomingEvents.UpcomingEventsDetails;
import com.fototechparivarstalls.webservices.UserLocalStore;




import java.util.ArrayList;


public class UpcomingEventsAdapter extends  RecyclerView.Adapter<UpcomingEventsAdapter.MyViewHolder> {

    private Context context;
    ArrayList<UpcomingEventsDetails> upcomingEventsList = new ArrayList<>();
    Integer i= 0;
    String token, userId;
    String mTitle = "Location";
    UserLocalStore userLocalStore;
    UserStoreDetails user;



    public UpcomingEventsAdapter(Context context, ArrayList<UpcomingEventsDetails>  upcomingEventsList) {
        this.upcomingEventsList = upcomingEventsList;
        this.context = context;


    }

    @NonNull
    @Override
    public UpcomingEventsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        UpcomingEventsLayoutBinding binding = UpcomingEventsLayoutBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingEventsAdapter.MyViewHolder holder, int position) {
        UpcomingEventsDetails upcomingEventsModel = upcomingEventsList.get(position);

        userLocalStore = new UserLocalStore(context);
        user = userLocalStore.getLoggedInUser();

        token = user.getToken();

        holder.binding.IdEventName.setText(upcomingEventsModel.getEventName());

        holder.binding.IdLocation.setText(upcomingEventsModel.getEventAddress());

        holder.binding.IdPhone.setText(upcomingEventsModel.getEventMobile());
        holder.binding.idFirstName.setText(upcomingEventsModel.getPersonName());
        holder.binding.date.setText(upcomingEventsModel.getEventStartDate());

       // holder.binding.idFirstName.setText(upcomingEventsModel.);

        Glide.with(context)
                .load(upcomingEventsModel.getEventImage())
                .placeholder(R.drawable.ic_upcoming_events)
                .into(holder.binding.IdEventImage);

        //Dialog dialog1 = new Dialog(context);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, BookStallListActivity.class);
                //intent.putExtra("mobile", strMobile );

                context.startActivity(intent);


               /* dialog1.setContentView(R.layout.dialog_price_details_layout);

                dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog1.setCancelable(false);
                //  Window window = dialog1.getWindow();
                //WindowManager.LayoutParams wlp = window.getAttributes();
                dialog1.setCanceledOnTouchOutside(true);
                //  window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                TextView stall_dimensions = dialog1.findViewById(R.id.stall_dimensions);
                TextView stall_no = dialog1.findViewById(R.id.stall_no);
                TextView price = dialog1.findViewById(R.id.price);

                ImageView close_button = dialog1.findViewById(R.id.close_button);
               Button book_now_button = dialog1.findViewById(R.id.book_now_button);
                Button ask_price_button = dialog1.findViewById(R.id.ask_price_button);

                stall_dimensions.setText("");
                stall_no.setText("");
                price.setText("");


                close_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog1.dismiss();
                        // Toast.makeText(PostDetailsActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                    }
                });

                book_now_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog1.dismiss();
                        // Toast.makeText(PostDetailsActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                    }
                });

                ask_price_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog1.dismiss();

                    }
                });

                dialog1.show();*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return upcomingEventsList.size();
    }

    @Override
    public long getItemId(int position) {
        return  upcomingEventsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public void removeAtPosition(int position) {
        upcomingEventsList.remove(position);
        notifyItemRemoved(position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        UpcomingEventsLayoutBinding binding;

        public MyViewHolder(UpcomingEventsLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }

}
