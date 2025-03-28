package com.fototechparivarstalls.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fototechparivarstalls.databinding.AddOnsLayoutBinding;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.model.GetAddons.AddAddonsJson;
import com.fototechparivarstalls.model.GetAddons.AddaddonsDetails;
import com.fototechparivarstalls.model.GetAddons.GetAddonsDetails;
import com.fototechparivarstalls.model.ItemQuantityPrice;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.UserLocalStore;
import com.fototechparivarstalls.webservices.Uttils;
import com.fototechparivarstalls.webservices.WebServices;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AddOnsListAdapter extends RecyclerView.Adapter<AddOnsListAdapter.MyViewHolder>{

    Map<String, Integer> selectedAddOnsMap = new HashMap<>();
    Context context;
    WebServices webServices;
    ArrayList<GetAddonsDetails> data;
    //private ArrayList<GetAddonsDetails> getAddonsArrayList;
    private final HashMap<Integer, ItemQuantityPrice> itemQuantities = new HashMap<>();
    double  price, total;
    onQtyChange onQtyChange;
    String AddonId,bookingId;
    public UserLocalStore userLocalStore;
    TextView tv_idSubmit;
    List<AddaddonsDetails> addaddonsDetails=new ArrayList<>();


    private OnQuantityChangeListener quantityChangeListener;
    public interface OnQuantityChangeListener {
        void onQuantityChanged(int addid, int qty);
    }


    public AddOnsListAdapter(Context context, ArrayList<GetAddonsDetails> data, OnQuantityChangeListener quantityChangeListener, TextView idSubmit, String bId) {
        this.context = context;
        this.data = data;
        this.quantityChangeListener = quantityChangeListener;
        tv_idSubmit=idSubmit;
        bookingId=bId;

    }
    @NonNull
    @Override
    public AddOnsListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AddOnsLayoutBinding binding = AddOnsLayoutBinding.inflate(inflater, parent, false);

        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull AddOnsListAdapter.MyViewHolder holder, int position ) {


        userLocalStore = new UserLocalStore(context);
        GetAddonsDetails item = data.get(position);
        ItemQuantityPrice itemQuantityPrice = itemQuantities.get(item.getPrice());
        holder.binding.idItemName.setText(item.getAddon());
        holder.binding.idUnitPrice.setText(String.valueOf(item.getPrice()));

        int quantity = itemQuantityPrice != null ? itemQuantityPrice.getQuantity() : item.getQty();
        holder.binding.tvQuantity.setText(String.valueOf(quantity));


        holder.binding.btnPlus.setOnClickListener(v -> {

            item.setQty(item.getQty() + 1);
            holder.binding.tvQuantity.setText(String.valueOf(item.getQty()));

            // Update quantity and price in HashMap
            int itemId = data.get(position).getQty();
            double itemPrice = data.get(position).getPrice();
            itemQuantities.put(itemId, new ItemQuantityPrice(item.getQty(), itemPrice,item.getAddonId()));
            quantityChangeListener.onQuantityChanged(item.getAddonId(), item.getQty());


        });
        holder.binding.btnMinus.setOnClickListener(v -> {
            if (item.getQty() > 0) {
                item.setQty(item.getQty() - 1);
                holder.binding.tvQuantity.setText(String.valueOf(item.getQty()));
                int itemId = data.get(position).getAddonId();
                double itemPrice = data.get(position).getPrice();
                itemQuantities.put(itemId, new ItemQuantityPrice(data.get(position).getQty(), itemPrice,data.get(position).getAddonId()));
                quantityChangeListener.onQuantityChanged(item.getAddonId(), item.getQty());


            }

        });

        tv_idSubmit.setOnClickListener(view -> {
            if (Uttils.getInternetConnection(context)) {

                if (Uttils.getInternetConnection(context)) {
                    ArrayList<AddaddonsDetails> addonsList = new ArrayList<>();
                    Uttils.showProgressDialoug(context);

                    for (Map.Entry<Integer, ItemQuantityPrice> entry : itemQuantities.entrySet()) {
                        ItemQuantityPrice itemQuantityPrice1 = entry.getValue();
                        AddaddonsDetails itemsList = new AddaddonsDetails(itemQuantityPrice1.getAddOnId(), itemQuantityPrice1.getQuantity());
                        // ... (Set other properties of itemsList)
                        addonsList.add(itemsList); // Add itemsList to addonsList
                    }

                    ApiHandler.getApiService().getAddAddonsResponse(new AddAddonsJson(bookingId, addonsList))
                            .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableObserver<ForgotPasswordResponse>() { // Change response type
                                @Override
                                public void onNext(ForgotPasswordResponse addAddonsResponse) {
                                    Uttils.dismissDialoug();

                                    try {
                                        if (addAddonsResponse.getStatusCode().equals("200")) {
                                            Toast.makeText(context, addAddonsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(context, addAddonsResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.e("AddonsResponseList", "Exception: " + e.getMessage());
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

        });


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

    public interface onQtyChange {
        public void QtyChanged(AddOnsListAdapter.MyViewHolder holder);
    }

    // Initializing the Views
    public class MyViewHolder extends RecyclerView.ViewHolder {
        AddOnsLayoutBinding binding;
        int qty = 0;
        public MyViewHolder(AddOnsLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
