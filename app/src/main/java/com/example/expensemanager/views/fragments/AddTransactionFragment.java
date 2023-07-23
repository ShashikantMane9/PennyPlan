package com.example.expensemanager.views.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.expensemanager.R;
import com.example.expensemanager.adapters.AccountAdapter;
import com.example.expensemanager.adapters.CategoryAdapter;
import com.example.expensemanager.databinding.FragmentAddTransactionBinding;
import com.example.expensemanager.databinding.ListDialogBinding;
import com.example.expensemanager.models.Account;
import com.example.expensemanager.models.Category;
import com.example.expensemanager.models.Transaction;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;
import com.example.expensemanager.views.activities.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AddTransactionFragment extends BottomSheetDialogFragment {


    public AddTransactionFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

    FragmentAddTransactionBinding binding;
    Transaction transaction;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddTransactionBinding.inflate(inflater);

        transaction = new Transaction();

        //first income button
        binding.incomeBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackgroundResource(R.drawable.income_selector);
            binding.expenseBtn.setBackgroundResource(R.drawable.default_selector);
            binding.incomeBtn.setTextColor(getResources().getColor(R.color.lightgreen));
            binding.expenseBtn.setTextColor(getResources().getColor(R.color.grey));

            transaction.setType(Constants.INCOME);
        });
        //second expense button
        binding.expenseBtn.setOnClickListener(view -> {
            binding.incomeBtn.setBackgroundResource(R.drawable.default_selector);
            binding.expenseBtn.setBackgroundResource(R.drawable.expense_selector);
            binding.expenseBtn.setTextColor(getResources().getColor(R.color.red));
            binding.incomeBtn.setTextColor(getResources().getColor(R.color.grey));

            transaction.setType(Constants.EXPENSE);

        });

        //third date Picker
        binding.dateTIB.setOnClickListener(view ->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
            datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.DAY_OF_MONTH,datePicker.getDayOfMonth());
                calendar.set(calendar.MONTH,datePicker.getMonth());
                calendar.set(calendar.YEAR,datePicker.getYear());

                String date = Helper.formatDateTime(calendar.getTime());

                binding.dateTIB.setText(date);

                transaction.setDate(calendar.getTime()); //set date to transaction
                transaction.setId(calendar.getTime().getTime());
            });
            datePickerDialog.show();
        });

        //fourth category selector
        binding.categoryTIB.setOnClickListener(view -> {
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog categoryDialog = new AlertDialog.Builder(getContext()).create();
            categoryDialog.setView(dialogBinding.getRoot());

            CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), Constants.categories, category -> {
                binding.categoryTIB.setText(category.getCategoryName());
                transaction.setCategory(category.getCategoryName());
                categoryDialog.dismiss();
            });

            dialogBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
            dialogBinding.recyclerView.setAdapter(categoryAdapter);

            categoryDialog.show();
        });

        binding.accountTIB.setOnClickListener(c ->{
            ListDialogBinding dialogBinding = ListDialogBinding.inflate(inflater);
            AlertDialog accountDialog = new AlertDialog.Builder(getContext()).create();
            accountDialog.setView(dialogBinding.getRoot());


            AccountAdapter accountAdapter = new AccountAdapter(getContext(),Constants.accounts,account -> {
                binding.accountTIB.setText(account.getAccountName());
                accountDialog.dismiss();
            });

            dialogBinding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            dialogBinding.recyclerView.setAdapter(accountAdapter);
            dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            transaction.setAccount(Constants.accounts.get(0).getAccountName());
            accountDialog.show();
        });

        binding.saveTransactionBtn.setOnClickListener(view -> {
           double amount = Double.parseDouble(binding.amountTIB.getText().toString());
           String note = binding.noteTIB.getText().toString();

           if(transaction.getType().equals(Constants.EXPENSE)) {
               transaction.setAmount(amount*-1);
           }else{
                  transaction.setAmount(amount);
           }
           transaction.setNote(note);

            ((MainActivity)getActivity()).viewModel.addTransactions(transaction);
            ((MainActivity)getActivity()).getTransactions();
            dismiss();
        });


        return binding.getRoot();

    }
}