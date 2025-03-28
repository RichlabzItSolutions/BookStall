package com.fototechparivarstalls.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.fototechparivarstalls.R;
import com.fototechparivarstalls.model.Issue.GetIssuesDetails;

import java.util.ArrayList;
import java.util.Locale;


public class SelectCountryAdapter extends BaseAdapter {

    Context context;
    ArrayList<GetIssuesDetails> data;
    private ArrayList<GetIssuesDetails> arraylist;

    public SelectCountryAdapter(Context context, ArrayList<GetIssuesDetails> list_country) {
        this.context = context;
        data = list_country;
        this.arraylist = new ArrayList<GetIssuesDetails>();
        this.arraylist.addAll(data);

    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.list_select_contry, null);


            holder.txt_CountryName = (TextView) convertView.findViewById(R.id.txt_CountryName);


            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.txt_CountryName.setText(data.get(position).getIssueTitle());
        String provinceid =  data.get(position).getId();


        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if (charText.length() == 0) {
            data.addAll(arraylist);
        } else {
            for (GetIssuesDetails wp : arraylist) {

                if (wp.getIssueTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(wp);
                }

            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {

        TextView txt_CountryName;

    }
}
