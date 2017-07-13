package com.udacity.popularmoviesstage1.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Accolite on 7/13/2017.
 *
 * Class used for checking the internet connection
 */
public class NetworkUtility {

    public static boolean isInternetConnected(Context context) {
        boolean isInternetConnected = false;
        final ConnectivityManager connectivityManager = ((ConnectivityManager)
                                    context.getSystemService(Context.CONNECTIVITY_SERVICE));

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            isInternetConnected = networkInfo.isConnected();
        }

        return isInternetConnected;
    }
}
