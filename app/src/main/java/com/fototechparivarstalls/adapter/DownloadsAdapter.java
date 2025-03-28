package com.fototechparivarstalls.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fototechparivarstalls.activity.PaymentsHistoryActivity;
import com.fototechparivarstalls.databinding.DownloadLayoutBinding;
import com.fototechparivarstalls.model.DownloadsModel;
import com.fototechparivarstalls.model.PaymentHistory.PaymentHistoryJson;
import com.fototechparivarstalls.model.PaymentHistory.PaymentHistoryResponse;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DownloadsAdapter extends  RecyclerView.Adapter<DownloadsAdapter.ImageViewHolder>/*ArrayAdapter<InspiredModel>*/ {

    private List<DownloadsModel> leadsModelList;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public DownloadsAdapter(Context context, List<DownloadsModel> leadsModelList, OnItemClickListener onItemClickListener) {

        this.leadsModelList = leadsModelList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public DownloadsAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
       DownloadLayoutBinding binding = DownloadLayoutBinding.inflate(inflater, parent, false);
        return new ImageViewHolder(binding);

        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leads_layout, parent, false);
        return new LeadsAdpter.ImageViewHolder(view);*/
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadsAdapter.ImageViewHolder holder, int position) {
        DownloadsModel leadsModel = leadsModelList.get(position);

        holder.binding.IdLeadImage.setImageResource(leadsModel.getImgId());
        holder.binding.IdLeadName.setText(leadsModel.getLeadsName());


        holder.binding.IdLeadLi.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(leadsModel);
            }
        });

        /*holder.binding.IdLeadLi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (leadsModel.getLeadsName().equals("Confirmation Letter")){


                }

                if (leadsModel.getLeadsName().equals("Transport Letter")){


                }

                if (leadsModel.getLeadsName().equals("Gate Pass")){

                }

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return leadsModelList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        DownloadLayoutBinding binding;

        public ImageViewHolder(DownloadLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
          }
    }

    public interface OnItemClickListener {
        void onItemClick(DownloadsModel downloadsModel);
    }
}
