package com.example.doubtnutprojectapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.example.doubtnutprojectapp.NewsApplication;

public class Utils {
    /**
     * Method to detect network connection on the device
     */
    public static boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) NewsApplication.getMyTimesApplicationInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }


    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}