package com.fototechparivarstalls.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fototechparivarstalls.databinding.PaymentsHistoryLayoutBinding;
import com.fototechparivarstalls.databinding.PreviousIssuesListLayoutBinding;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.model.Issue.DeleteIssueJson;
import com.fototechparivarstalls.model.Issue.GetIssuessDetails;
import com.fototechparivarstalls.model.PaymentHistory.PaymentHistoryDetails;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PaymentsHistoryListAdapter extends RecyclerView.Adapter<PaymentsHistoryListAdapter.MyViewHolder> /*implements View.OnClickListener*/ {

    Context context;
    ArrayList<PaymentHistoryDetails> data = new ArrayList<>();


    public PaymentsHistoryListAdapter(Context context, ArrayList<PaymentHistoryDetails> getPaymentHistoryDetailArrayList ) {
        this.context = context;
        data = getPaymentHistoryDetailArrayList;
    }

    @NonNull
    @Override
    public PaymentsHistoryListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PaymentsHistoryLayoutBinding binding = PaymentsHistoryLayoutBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull PaymentsHistoryListAdapter.MyViewHolder holder, int position ) {


        PaymentHistoryDetails paymentHistoryDetails = data.get(position);

        holder.binding.idDate.setText(paymentHistoryDetails.getPaidDate());
        holder.binding.idAmount.setText(paymentHistoryDetails.getPaidAmount());

        String TotalAmount =  paymentHistoryDetails.getPaidAmount() + data.size();

        if (paymentHistoryDetails.getPaymentMode().equals("1")){

            holder.binding.idPaymentMode.setText("Cash");
        }
        if (paymentHistoryDetails.getPaymentMode().equals("2")){

            holder.binding.idPaymentMode.setText("UPI");
        }
        if (paymentHistoryDetails.getPaymentMode().equals("3")){

            holder.binding.idPaymentMode.setText("Cheque");
        }
        if (paymentHistoryDetails.getPaymentMode().equals("4")){
            holder.binding.idPaymentMode.setText("Online");

        }
        if (paymentHistoryDetails.getPaymentMode().equals("5")){

            holder.binding.idPaymentMode.setText("PO");
        }
        if (paymentHistoryDetails.getPaymentMode().equals("6")){
            holder.binding.idPaymentMode.setText("Others");

        }






    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }




    public void removeAtPosition(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }





    // Initializing the Views
    public class MyViewHolder extends RecyclerView.ViewHolder {

        PaymentsHistoryLayoutBinding binding;

        public MyViewHolder(PaymentsHistoryLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            //mobileNumberBillTxt = (TextView) view.findViewById(R.id.mobileNumberBillTxt);
        }
    }
}
