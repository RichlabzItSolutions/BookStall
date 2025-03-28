package com.fototechparivarstalls.adapter;



import static androidx.core.app.NotificationCompat.getColor;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fototechparivarstalls.R;
import com.fototechparivarstalls.activity.AddonsActivity;
import com.fototechparivarstalls.activity.DownloadsActivity;
import com.fototechparivarstalls.activity.PaymentsHistoryActivity;
import com.fototechparivarstalls.activity.RaisaIssueActivity;
import com.fototechparivarstalls.databinding.BookingLayoutBinding;
import com.fototechparivarstalls.databinding.UpcomingEventsLayoutBinding;
import com.fototechparivarstalls.fragments.MybookingFragment;
import com.fototechparivarstalls.model.CancelBooking.CancelBookingJson;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.model.GetBookings.GetBookingsDetails;
import com.fototechparivarstalls.model.Issue.GetIssuesDetails;
import com.fototechparivarstalls.model.Issue.GetIssuesResponse;
import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.model.UpcomingEvents.UpcomingEventsDetails;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.UserLocalStore;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class GetBookingsAdapter extends  RecyclerView.Adapter<GetBookingsAdapter.MyViewHolder> {

    private Context context;
    ArrayList<GetBookingsDetails> bookingList = new ArrayList<>();
    Integer i= 0;
    String token, userId;
    UserLocalStore userLocalStore;
    UserStoreDetails user;

    String reason;



    public GetBookingsAdapter(Context context, ArrayList<GetBookingsDetails>  bookingList) {
        this.bookingList = bookingList;
        this.context = context;


    }

    @NonNull
    @Override
    public GetBookingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BookingLayoutBinding binding = BookingLayoutBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull GetBookingsAdapter.MyViewHolder holder, int position) {
        GetBookingsDetails bookingListModel = bookingList.get(position);

        userLocalStore = new UserLocalStore(context);
        user = userLocalStore.getLoggedInUser();

        token = user.getToken();

        holder.binding.eventTitle.setText(bookingListModel.getEventName());


        holder.binding.IdBalance.setText(bookingListModel.getBalance());

        holder.binding.IdTotalSqft.setText(bookingListModel.getSqMts());

        holder.binding.IdTotalPrice.setText(bookingListModel.getTotalAmount());


        holder.binding.IdPaidAmount.setText(bookingListModel.getPaidAmount());

        holder.binding.IdLocation.setText(bookingListModel.getEventAddress());

        holder.binding.IdStallNo.setText(bookingListModel.getStallName());


        holder.binding.IdDiscount.setText(bookingListModel.getDiscount());

        if (bookingListModel.getBookingStatus().equals("0")) {

            holder.binding.BookingStatus.setText("Pending");

            holder.binding.BookingStatus.setTextColor(context.getResources().getColor(R.color.sky));


            holder.binding.Addons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, AddonsActivity.class);
                    intent.putExtra("bookingId", bookingListModel.getBookingId());
                    context.startActivity(intent);


                }
            });



            holder.binding.Download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context, "Action Can't be Performed", Toast.LENGTH_SHORT).show();

                }
            });
          /*  holder.binding.Addons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context, "Action Can't be Performed", Toast.LENGTH_SHORT).show();

                }
            });*/

            holder.binding.RaiseIssue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context, "Action Can't be Performed", Toast.LENGTH_SHORT).show();

                }
            });
            holder.binding.PaymentHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context, "Action Can't be Performed", Toast.LENGTH_SHORT).show();

                }
            });
        }


        if (bookingListModel.getBookingStatus().equals("1")){

            holder.binding.BookingStatus.setText("Confirmed");

            holder.binding.BookingStatus.setTextColor(context.getResources().getColor(R.color.green));


           // holder.binding.BookingStatus.setTextColor("#DF1E29");


            holder.binding.PaymentHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, PaymentsHistoryActivity.class);
                    intent.putExtra("bookingId", bookingListModel.getBookingId());
                    context.startActivity(intent);

                }
            });
            holder.binding.Addons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, AddonsActivity.class);
                    intent.putExtra("bookingId", bookingListModel.getBookingId());
                    context.startActivity(intent);


                }
            });

            holder.binding.Download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, DownloadsActivity.class);
                    intent.putExtra("bookingId", bookingListModel.getBookingId());
                    context.startActivity(intent);

                }
            });

            holder.binding.RaiseIssue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, RaisaIssueActivity.class);
                    intent.putExtra("bookingId", bookingListModel.getBookingId());
                    context.startActivity(intent);
                }
            });


        }
        if (bookingListModel.getBookingStatus().equals("2")){

            holder.binding.BookingStatus.setText("Rejected");


            holder.binding.BookingStatus.setTextColor(context.getResources().getColor(R.color.red));
            holder.binding.Download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context, "Action Can't be Performed", Toast.LENGTH_SHORT).show();

                }
            });
            holder.binding.Addons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context, "Action Can't be Performed", Toast.LENGTH_SHORT).show();

                }
            });

            holder.binding.RaiseIssue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context, "Action Can't be Performed", Toast.LENGTH_SHORT).show();

                }
            });
            holder.binding.PaymentHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context, "Action Can't be Performed", Toast.LENGTH_SHORT).show();

                }
            });

        }
        if (bookingListModel.getBookingStatus().equals("3")){

            holder.binding.BookingStatus.setText("Cancelled");

            holder.binding.BookingStatus.setTextColor(context.getResources().getColor(R.color.red));

            holder.binding.PaymentHistory.setVisibility(View.GONE);
            holder.binding.Cancel.setVisibility(View.GONE);
            holder.binding.Addons.setVisibility(View.GONE);
            holder.binding.Download.setVisibility(View.GONE);
            holder.binding.RaiseIssue.setVisibility(View.GONE);

        }


       /* holder.binding.PaymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, PaymentsHistoryActivity.class);
                intent.putExtra("bookingId", bookingListModel.getBookingId());
                context.startActivity(intent);

            }
        });
        holder.binding.Addons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AddonsActivity.class);
                intent.putExtra("bookingId", bookingListModel.getBookingId());
                context.startActivity(intent);


            }
        });

        holder.binding.Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DownloadsActivity.class);
                intent.putExtra("bookingId", bookingListModel.getBookingId());
                context.startActivity(intent);


            }
        });*/











        /*holder.binding.RaiseIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, RaisaIssueActivity.class);
                intent.putExtra("bookingId", bookingListModel.getBookingId());
                context.startActivity(intent);
            }
        });*/

        holder.binding.Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dialog dialog3 = new Dialog(context);

                dialog3.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog3.setCancelable(false);
                Window window = dialog3.getWindow();
                //WindowManager.LayoutParams wlp = window.getAttributes();
                dialog3.setCanceledOnTouchOutside(true);

                dialog3.setContentView(R.layout.dilogbox_booking_cancel);

                dialog3.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog3.setCancelable(false);
                //  Window window = dialog1.getWindow();
                //WindowManager.LayoutParams wlp = window.getAttributes();
                dialog3.setCanceledOnTouchOutside(true);
                //  window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

               EditText etreason = dialog3.findViewById(R.id.Reason);
               ImageView closeMaId = dialog3.findViewById(R.id.closeMaId);
               TextView idSubmit = dialog3.findViewById(R.id.idSubmit);


                closeMaId.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog3.dismiss();
                        // Toast.makeText(PostDetailsActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                    }
                });

                idSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        reason = etreason.getText().toString();


                        // Log.d("fgt", name.substring(0,1));
                        if (reason.isEmpty()) {
                            Toast.makeText(context, "Please Enter Reason", Toast.LENGTH_SHORT).show();
                        }


                        else{

                           CancelApi(bookingListModel.getBookingId());

                            dialog3.dismiss();

                        }

                    }
                });

                dialog3.show();
            }
        });


      /*  holder.binding.IdPhone.setText(bookingListModel.getEventMobile());

        holder.binding.stallNumber.setText("Stall No - " + bookingListModel.getStallName());

        holder.binding.stallSqfit.setText("Size - " + bookingListModel.getSqMts());*/




        Glide.with(context)
                .load(bookingListModel.getEventImage())
                .placeholder(R.drawable.ic_upcoming_events)
                .into(holder.binding.eventImage);



        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, RaisaIssueActivity.class);
                intent.putExtra("bookingId", bookingListModel.getBookingId());
                context.startActivity(intent);
            }
        });*/


    }



    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    @Override
    public long getItemId(int position) {
        return  bookingList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void removeAtPosition(int position) {
        bookingList.remove(position);
        notifyItemRemoved(position);
    }


    public void CancelApi(String bookingId){

        if (Uttils.getInternetConnection(context)) {

            Uttils.showProgressDialoug(context);

            ApiHandler.getApiService().getCancelBookingResponse( new CancelBookingJson(bookingId, reason ))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<ForgotPasswordResponse>() {
                        @Override
                        public void onNext(ForgotPasswordResponse bookingCancelResponse) {
                            Uttils.dismissDialoug();

                            try {

                                if (!bookingCancelResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(context, bookingCancelResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                } else if (bookingCancelResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(context, bookingCancelResponse.getMessage(), Toast.LENGTH_SHORT).show();


                                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.flFragment, new MybookingFragment());
                                    fragmentTransaction.addToBackStack(null); // Add to back stack to allow user to go back
                                    fragmentTransaction.commit();
//                                    Intent refreshCan = new Intent(context, AlbumLeadActivity.class);
//                                    context.startActivity(refreshCan);



                                    //refreshActivity();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Cancel Booking", "Exception: " + e.getMessage());
                                Toast.makeText(context, "Exception occurred", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Uttils.dismissDialoug();
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Uttils.dismissDialoug();
                        }
                    });
        }
    }









    public class MyViewHolder extends RecyclerView.ViewHolder {

        BookingLayoutBinding binding;

        public MyViewHolder(BookingLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

    }

}
