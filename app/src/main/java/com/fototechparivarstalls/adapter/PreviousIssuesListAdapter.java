package com.fototechparivarstalls.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.recyclerview.widget.RecyclerView;


import com.fototechparivarstalls.R;
import com.fototechparivarstalls.activity.DownloadsActivity;
import com.fototechparivarstalls.activity.RaisaIssueActivity;
import com.fototechparivarstalls.databinding.PreviousIssuesListLayoutBinding;
import com.fototechparivarstalls.model.ForgotPaasword.ForgotPasswordResponse;
import com.fototechparivarstalls.model.Issue.AddIssueJson;
import com.fototechparivarstalls.model.Issue.DeleteIssueJson;
import com.fototechparivarstalls.model.Issue.EditIssueJson;
import com.fototechparivarstalls.model.Issue.GetIssuesDetails;
import com.fototechparivarstalls.model.Issue.GetIssuesResponse;
import com.fototechparivarstalls.model.Issue.GetIssuessDetails;
import com.fototechparivarstalls.model.Login.UserStoreDetails;
import com.fototechparivarstalls.webservices.ApiHandler;
import com.fototechparivarstalls.webservices.UserLocalStore;
import com.fototechparivarstalls.webservices.Uttils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class PreviousIssuesListAdapter extends RecyclerView.Adapter<PreviousIssuesListAdapter.MyViewHolder> /*implements View.OnClickListener*/ {

    Context context;
    ArrayList<GetIssuessDetails> data = new ArrayList<>();

    String Message, issueId;

    UserLocalStore userLocalStore;
    UserStoreDetails user;

    String selectedUserId;
    Dialog dialog_GetCountryList;
    ArrayList<GetIssuesDetails> provinceList;

    TextView idSubmit, message, et_issueType;
    ImageView closeMaId;

    public PreviousIssuesListAdapter(Context context, ArrayList<GetIssuessDetails> getIIssuessListDetailsArrayList ) {
        this.context = context;
        data = getIIssuessListDetailsArrayList;
    }

    @NonNull
    @Override
    public PreviousIssuesListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        PreviousIssuesListLayoutBinding binding = PreviousIssuesListLayoutBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull PreviousIssuesListAdapter.MyViewHolder holder, int position ) {

        GetIssuessDetails getIssuessDetails = data.get(position);

        holder.binding.tvIssue.setText(getIssuessDetails.getIssue());
        holder.binding.tvMessage.setText(getIssuessDetails.getMessage());


        userLocalStore = new UserLocalStore(context);
        user = userLocalStore.getLoggedInUser();

       // token = user.getToken();

        if (getIssuessDetails.getStatus().equals("1")){

          holder.binding.tvResoved.setText("New Issue");
            holder.binding.tvResoved.setTextColor(context.getResources().getColor(R.color.sky));

        }
        if (getIssuessDetails.getStatus().equals("2")){

            holder.binding.tvResoved.setText("On hold");
            holder.binding.tvResoved.setTextColor(context.getResources().getColor(R.color.red));
        }
        if (getIssuessDetails.getStatus().equals("3")){

            holder.binding.tvResoved.setText("Sloved");
            holder.binding.tvResoved.setTextColor(context.getResources().getColor(R.color.green));
        }


        holder.binding.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog3 = new Dialog(context);

                dialog3.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog3.setCancelable(false);
                Window window = dialog3.getWindow();
                //WindowManager.LayoutParams wlp = window.getAttributes();
                dialog3.setCanceledOnTouchOutside(true);

                dialog3.setContentView(R.layout.dilogbox_layout_edit_issue);

                dialog3.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog3.setCancelable(false);
                //  Window window = dialog1.getWindow();
                //WindowManager.LayoutParams wlp = window.getAttributes();
                dialog3.setCanceledOnTouchOutside(true);
                //  window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



                closeMaId = dialog3.findViewById(R.id.closeMaId);
                et_issueType = dialog3.findViewById(R.id.et_issueType);
                message = dialog3.findViewById(R.id.message);
                idSubmit = dialog3.findViewById(R.id.idSubmit);


                selectedUserId = getIssuessDetails.getIssueId();

                message.setText(getIssuessDetails.getMessage());

                et_issueType.setText(getIssuessDetails.getIssue());

                et_issueType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        getProvinceList();
                    }
                });

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

                    Message  =  getIssuessDetails.getMessage();

                    issueId = getIssuessDetails.getIssueId();


                        if (Message.isEmpty()) {
                            Toast.makeText(context, "Please Enter Message", Toast.LENGTH_SHORT).show();
                        }
                        else if (issueId.isEmpty()) {

                            Toast.makeText(context, "Please Enter Subject", Toast.LENGTH_SHORT).show();
                        }

                        else{


                            ApiHandler.setAuthToken(user.getToken());

                            ApiHandler.getApiService().getEditIssueResponse(new EditIssueJson(getIssuessDetails.getId(),selectedUserId, Message))
                                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DisposableObserver<ForgotPasswordResponse>() {
                                        @Override
                                        public void onNext(ForgotPasswordResponse response) {
                                            Uttils.dismissDialoug();


                                            try {


                                                if (response.getStatusCode().equals("200")) {

                                                    Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Log.e("Login", "Exception: " + e.getMessage());

                                            }
                                        }

                                        @Override
                                        public void onError(@NotNull Throwable e) {
                                            Uttils.dismissDialoug();
                                            e.printStackTrace();
                                        }

                                        @Override
                                        public void onComplete() {
                                            Uttils.dismissDialoug();
                                        }
                                    });

                        }


                            dialog3.dismiss();



                        }


                });

                dialog3.show();

            }
        });

        holder.binding.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // DeleteApi(getIssuessDetails.getId());

                if (Uttils.getInternetConnection(context)) {

                    Uttils.showProgressDialoug(context);

                    ApiHandler.getApiService().getDeleteIssueResponse( new DeleteIssueJson(getIssuessDetails.getId()))
                            .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DisposableObserver<ForgotPasswordResponse>() {
                                @Override
                                public void onNext(ForgotPasswordResponse deleteIssueResponse) {
                                    Uttils.dismissDialoug();

                                    try {

                                        if (!deleteIssueResponse.getStatusCode().equals("200")) {

                                            Toast.makeText(context, deleteIssueResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                        } else if (deleteIssueResponse.getStatusCode().equals("200")) {

                                            Toast.makeText(context, deleteIssueResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                            removeAtPosition(position);
//                                    Intent refreshCan = new Intent(context, AlbumLeadActivity.class);
//                                    context.startActivity(refreshCan);

                                            //refreshActivity();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.e("deleteIssueResponse", "Exception: " + e.getMessage());
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



    private void getProvinceList() {

        if (Uttils.getInternetConnection(context)) {

            Uttils.showProgressDialoug(context);


            ApiHandler.getApiService().getIssuesResponse()
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<GetIssuesResponse>() {
                        @Override
                        public void onNext(GetIssuesResponse getProvinceResponse) {
                            Uttils.dismissDialoug();

                            try {

                                if (!getProvinceResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(context, getProvinceResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                } else if (getProvinceResponse.getStatusCode().equals("200")) {

                                    Toast.makeText(context, getProvinceResponse.getMessage(), Toast.LENGTH_SHORT).show();

                                    //Log.d("tgh", String.valueOf(getProvinceResponse));
                                    provinceList = getProvinceResponse.getData();

                                    openCountryDialog("All User");

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("Edit Issue", "Exception: " + e.getMessage());
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


    private void openCountryDialog(String from) {

        dialog_GetCountryList = new Dialog(context);
        dialog_GetCountryList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_GetCountryList.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_GetCountryList.getWindow().setGravity(Gravity.CENTER);

        dialog_GetCountryList.setCanceledOnTouchOutside(true);

        //setting custom layout to dialog_car_variant
        dialog_GetCountryList.setContentView(R.layout.diloug_select_country);

        ListView lv_Country = dialog_GetCountryList.findViewById(R.id.lv_Country);
        EditText edt_SearchCountry = dialog_GetCountryList.findViewById(R.id.edt_Search);
        ImageView cancel_button = dialog_GetCountryList.findViewById(R.id.cancel_button);

        SelectCountryAdapter adapterSelectCountry;

        adapterSelectCountry = new SelectCountryAdapter(context, provinceList);
        lv_Country.setAdapter(adapterSelectCountry);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_GetCountryList.dismiss();

            }
        });

        edt_SearchCountry.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapterSelectCountry.filter(cs.toString());

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {


            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });

        lv_Country.setOnItemClickListener((parent, view, position, id) -> {

            String str_IssueName = provinceList.get(position).getIssueTitle();
            // String str_Mobile = provinceList.get(position).getMobileNumber();
            //et_user_name.setText(str_CountryName +" - "+ str_Mobile);
            et_issueType.setText(str_IssueName);

            selectedUserId = provinceList.get(position).getId();

            Log.d("secteIssueid",selectedUserId);

            dialog_GetCountryList.dismiss();
        });


       /* DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager(context).getDefaultDisplay().getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels / 2;
        int width = displaymetrics.widthPixels - 100;

        dialog_GetCountryList.getWindow().setLayout(width, height);*/

        dialog_GetCountryList.show();

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

        PreviousIssuesListLayoutBinding binding;




        public MyViewHolder(PreviousIssuesListLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


           // mobileNumberBillTxt = (TextView) view.findViewById(R.id.idSubmit);
            //mobileNumberBillTxt = (TextView) view.findViewById(R.id.mobileNumberBillTxt);
        }
    }
}
