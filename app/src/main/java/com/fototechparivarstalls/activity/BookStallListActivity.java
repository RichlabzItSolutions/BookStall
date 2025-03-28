package com.fototechparivarstalls.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.fototechparivarstalls.R;
import com.fototechparivarstalls.adapter.BookStallListAdapter;
import com.fototechparivarstalls.adapter.DownloadsAdapter;
import com.fototechparivarstalls.databinding.ActivityBookStallListBinding;
import com.fototechparivarstalls.databinding.ActivityChangePasswordBinding;
import com.fototechparivarstalls.model.BookStallListModel;
import com.fototechparivarstalls.model.DownloadsModel;

import java.util.ArrayList;
import java.util.List;

public class BookStallListActivity extends AppCompatActivity {

    ActivityBookStallListBinding binding;

    private BookStallListAdapter bookStallListAdapter;
    private List<BookStallListModel> bookStallListModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookStallListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();


        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
      /*bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));
        bookStallListModelList.add(new BookStallListModel("10x12"));*/



        GridLayoutManager layoutManager = new GridLayoutManager(BookStallListActivity.this, 4);
        binding.recyclerView.setLayoutManager(layoutManager);
        bookStallListAdapter = new BookStallListAdapter(BookStallListActivity.this, bookStallListModelList);

        binding.recyclerView.setAdapter(bookStallListAdapter);

    }
}