package com.fototechparivarstalls.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.fototechparivarstalls.R;
import com.fototechparivarstalls.databinding.ActivityMainBinding;
import com.fototechparivarstalls.fragments.EventsFragment;
import com.fototechparivarstalls.fragments.HomeFragment;
import com.fototechparivarstalls.fragments.MybookingFragment;
import com.fototechparivarstalls.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements  BottomNavigationView
        .OnNavigationItemSelectedListener {
    ActivityMainBinding binding;

    Boolean doubleBackToExitPressedOnce = false;
     public static int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        count=0;

        loadFrag(new HomeFragment());

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
        binding.bottomNavigationView.setSelectedItemId(R.id.Home);

    }


    HomeFragment firstFragment = new HomeFragment();
    EventsFragment secondFragment = new EventsFragment();
    MybookingFragment thirdFragment = new MybookingFragment();
    ProfileFragment forthFragment = new ProfileFragment();

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.Home:
                count=0;
               /* getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, firstFragment)
                        .commit();*/


                  loadFrag(new HomeFragment());
                return true;

           /* case R.id.MyPosts:
                *//*getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, secondFragment)
                        .commit();*//*
                loadFrag(new EventsFragment());
                return true;*/

            case R.id.Requests:
                /*getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, thirdFragment)
                        .commit();*/
                loadFrag(new MybookingFragment());
                return true;

            case R.id.Profile:

                /*getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, forthFragment)
                        .commit();*/

                loadFrag(new ProfileFragment());
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1500);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void loadFrag(Fragment frag){

        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(count>0){
            ft.addToBackStack(null);
        }
        ft.setCustomAnimations(R.anim.slide_in,R.anim.fade_out,R.anim.fade_in,R.anim.slide_out);
        ft.replace(R.id.flFragment,frag);
        count=count+1;
        ft.commit();

    }
}