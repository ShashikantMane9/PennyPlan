package com.example.expensemanager.utils;

import com.example.expensemanager.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public static String formatDateTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM, yyyy");
        return sdf.format(date);
    }

    public static String formatDateByMonth(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM, yyyy");
        return sdf.format(date);
    }


}
