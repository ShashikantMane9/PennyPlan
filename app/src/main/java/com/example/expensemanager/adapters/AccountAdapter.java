package com.example.expensemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.databinding.RowAccountBinding;
import com.example.expensemanager.models.Account;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder>{

    Context context;
    ArrayList<Account> accounts;

    public  interface AccountClickedListener{
        void onAccountClicked(Account account);
    }

    AccountClickedListener listener;

    public AccountAdapter(Context context, ArrayList<Account> accounts,AccountClickedListener listener) {
        this.context = context;
        this.accounts = accounts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AccountViewHolder(LayoutInflater.from(context).inflate(R.layout.row_account,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accounts.get(position);
        holder.binding.accountName.setText(account.getAccountName());

        holder.itemView.setOnClickListener(view -> {
            listener.onAccountClicked(account);
        });
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }


    public class AccountViewHolder extends RecyclerView.ViewHolder{

        RowAccountBinding binding;
        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowAccountBinding.bind(itemView);
        }
    }
}
