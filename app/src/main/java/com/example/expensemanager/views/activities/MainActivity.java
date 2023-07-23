package com.example.expensemanager.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.expensemanager.R;
import com.example.expensemanager.adapters.TransactionAdapter;
import com.example.expensemanager.databinding.ActivityMainBinding;
import com.example.expensemanager.models.Transaction;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;
import com.example.expensemanager.viewmodels.MainViewModel;
import com.example.expensemanager.views.fragments.AddTransactionFragment;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Calendar calender;

    //0 -daily, 1 - monthly, 2 - calender 4-Summary 5-Notes


    public MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot() );


        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Constants.setAccounts();
        Constants.setCategories();

        setSupportActionBar(binding.materialToolbar);
        getSupportActionBar().setTitle("Transactions");

        calender = Calendar.getInstance();
        updateDate();

        binding.previousButton.setOnClickListener(c->{
            if(Constants.SELECTED_TAB == Constants.DAILY){
                calender.add(Calendar.DATE, -1);
            }else  if (Constants.SELECTED_TAB == Constants.MONTHLY){
                calender.add(Calendar.MONTH, -1);
            }
            updateDate();
        });

        binding.nextButton.setOnClickListener(c->{
            if (Constants.SELECTED_TAB == Constants.DAILY)
                calender.add(Calendar.DATE, 1);
            else if (Constants.SELECTED_TAB == Constants.MONTHLY)
                calender.add(Calendar.MONTH, 1);

            updateDate();
        });

        binding.floatingActionButton.setOnClickListener(c->{
            new AddTransactionFragment().show(getSupportFragmentManager(), "Add Transaction");
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
               if(tab.getText().equals("Monthly")){
                   Constants.SELECTED_TAB = 1;
                    updateDate();
               }else if (tab.getText().equals("Daily")){
                   Constants.SELECTED_TAB = 0;
                     updateDate();
               }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.transactionList.setLayoutManager(new LinearLayoutManager(this));

        viewModel.transactions.observe(this, transactions -> {
            TransactionAdapter adapter = new TransactionAdapter(MainActivity.this, transactions);
            binding.transactionList.setAdapter(adapter);

            if(transactions.size() >0){
                binding.emptyState.setVisibility(View.GONE);
            }else{
                binding.emptyState.setVisibility(View.VISIBLE);
            }

        });

        viewModel.totalIncome.observe(this, aDouble -> {
            binding.incomeTV.setText(String.valueOf(aDouble));
        });

        viewModel.totalExpense.observe(this, aDouble -> {
            binding.expenseTV.setText(String.valueOf(aDouble));
        });

        viewModel.totalAmount.observe(this, aDouble -> {
            binding.totalTV.setText(String.valueOf(aDouble));
        });

        viewModel.getTransactions(calender);

    }

    public void getTransactions(){
        viewModel.getTransactions(calender);
    }

    void updateDate(){
        if(Constants.SELECTED_TAB == Constants.DAILY){
            binding.currentDate.setText(Helper.formatDateTime(calender.getTime()));
        }else if(Constants.SELECTED_TAB == Constants.MONTHLY){
            binding.currentDate.setText(Helper.formatDateByMonth(calender.getTime()));
        }
        viewModel.getTransactions(calender);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}