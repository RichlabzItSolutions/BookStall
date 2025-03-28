package com.fototechparivarstalls.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fototechparivarstalls.R;
import com.fototechparivarstalls.databinding.DownloadLayoutBinding;
import com.fototechparivarstalls.databinding.ItemStallLayoutBinding;
import com.fototechparivarstalls.model.BookStallListModel;
import com.fototechparivarstalls.model.DownloadsModel;

import java.util.List;

public class BookStallListAdapter extends  RecyclerView.Adapter<BookStallListAdapter.ImageViewHolder>/*ArrayAdapter<InspiredModel>*/ {

    private List<BookStallListModel> leadsModelList;
    private Context context;

    private OnItemClickListener onItemClickListener;

    public BookStallListAdapter(Context context, List<BookStallListModel> leadsModelList) {

        this.leadsModelList = leadsModelList;
        this.context = context;

    }

    @NonNull
    @Override
    public BookStallListAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemStallLayoutBinding binding = ItemStallLayoutBinding.inflate(inflater, parent, false);
        return new ImageViewHolder(binding);

        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leads_layout, parent, false);
        return new LeadsAdpter.ImageViewHolder(view);*/
    }

    @Override
    public void onBindViewHolder(@NonNull BookStallListAdapter.ImageViewHolder holder, int position) {
        BookStallListModel leadsModel = leadsModelList.get(position);

        holder.binding.size.setText(leadsModel.getSize());

        Dialog dialog1 = new Dialog(context);

        holder.binding.size.setOnClickListener(v -> {
           /* if (onItemClickListener != null) {
                onItemClickListener.onItemClick(leadsModel);
            }*/
        });

        holder.binding.LinerId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog1.setContentView(R.layout.dialog_price_details_layout);

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

                dialog1.show();

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
       ItemStallLayoutBinding binding;

        public ImageViewHolder(ItemStallLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
          }
    }

    public interface OnItemClickListener {
        void onItemClick(DownloadsModel downloadsModel);
    }
}
