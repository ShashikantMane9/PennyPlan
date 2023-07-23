package com.example.expensemanager.utils;

import com.example.expensemanager.R;
import com.example.expensemanager.models.Account;
import com.example.expensemanager.models.Category;

import java.util.ArrayList;

public class Constants {

    public static String INCOME = "Income";
    public static String EXPENSE = "Expense";

    public static int DAILY = 0;
    public static int MONTHLY = 1;
    public static int CALENDER = 2;
    public static int SUMMARY = 3;
    public static int NOTES = 4;

    public static int SELECTED_TAB = 0;


    public static ArrayList<Category> categories;
    public static void setCategories(){
        categories = new ArrayList<>();
        categories.add(new Category("Bill", R.drawable.bill,R.color.category1));
        categories.add(new Category("Cloth",R.drawable.cloth,R.color.category2));
        categories.add(new Category("Deposit",R.drawable.deposit,R.color.category3));
        categories.add(new Category("Salary",R.drawable.salary,R.color.category4));
        categories.add(new Category("Car",R.drawable.car,R.color.category5));
        categories.add(new Category("Entertainment",R.drawable.entertainment,R.color.category6));
        categories.add(new Category("Food",R.drawable.food, R.color.category7));
        categories.add(new Category("Gift",R.drawable.gift,R.color.category8));
        categories.add(new Category("Home",R.drawable.home,R.color.category9));
        categories.add(new Category("Health",R.drawable.health,R.color.category10));
        categories.add(new Category("Phone",R.drawable.phone,R.color.category11));
        categories.add(new Category("Saving",R.drawable.saving,R.color.category12));
        categories.add(new Category("Sports",R.drawable.sports,R.color.category13));
        categories.add(new Category("Train",R.drawable.train,R.color.category14));
    }


    public static ArrayList<Account> accounts;

    public static void setAccounts(){
        accounts = new ArrayList<>();
        accounts.add(new Account(0,"Cash"));
        accounts.add(new Account(0,"Credit Card"));
        accounts.add(new Account(0,"Debit Card"));
        accounts.add(new Account(0,"Bank"));
        accounts.add(new Account(0,"Other"));
    }

    public static Category getCategory(String name){
        for(Category category: categories){
            if(category.getCategoryName().equals(name)){
                return category;
            }
        }
        return null;
    }


    public static int getAccountColor(String accountName){
        int color = 0;
        switch (accountName){
            case "Cash":
                return R.color.cash_color;
            case "Credit Card":
                return R.color.credit_color;
            case "Debit Card":
                return R.color.debit_color;
            case "Bank":
                return R.color.bank_color;
            case "Other":
                return R.color.default_color;
            default:
                return R.color.default_color;
        }
    }
}
