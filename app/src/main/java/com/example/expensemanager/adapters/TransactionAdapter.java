package com.example.expensemanager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.databinding.RowTransactionBinding;
import com.example.expensemanager.models.Category;
import com.example.expensemanager.models.Transaction;
import com.example.expensemanager.utils.Constants;
import com.example.expensemanager.utils.Helper;
import com.example.expensemanager.views.activities.MainActivity;

import io.realm.RealmResults;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>  {

    Context context;;
    RealmResults<Transaction> transactions;

    public TransactionAdapter(Context context, RealmResults<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.row_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.binding.transactionCategory.setText(transaction.getCategory());
        holder.binding.transactionAmount.setText(String.valueOf(transaction.getAmount()));

        holder.binding.accountName.setText(transaction.getAccount());
        holder.binding.transactionDate.setText(Helper.formatDateTime(transaction.getDate()));

        Category transactionCategory = Constants.getCategory(transaction.getCategory());

        holder.binding.categoryIcon.setImageResource(transactionCategory.getCategoryImage());
        holder.binding.categoryIcon.setColorFilter(context.getResources().getColor(transactionCategory.getCategoryColor()));

        holder.binding.accountName.setBackground(context.getResources().getDrawable(Constants.getAccountColor(transaction.getAccount())));


        if(transaction.getType().equals(Constants.INCOME)){
            holder.binding.transactionAmount.setTextColor(context.getResources().getColor(R.color.green));
        }else if(transaction.getType().equals(Constants.EXPENSE)){
            holder.binding.transactionAmount.setTextColor(context.getResources().getColor(R.color.red));
        }

        holder.itemView.setOnLongClickListener(view ->{
            AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
            deleteDialog.setTitle("Delete Transaction");
            deleteDialog.setMessage("Are you sure you want to delete this transaction?");
            deleteDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", (dialogInterface, i) -> {
                ((MainActivity)context).viewModel.deleteTransaction(transaction);
            });
            deleteDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", (dialogInterface, i) -> {
                deleteDialog.dismiss();
            });
            deleteDialog.show();
            return false;
        });

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        RowTransactionBinding binding;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RowTransactionBinding.bind(itemView);
        }
    }
}
