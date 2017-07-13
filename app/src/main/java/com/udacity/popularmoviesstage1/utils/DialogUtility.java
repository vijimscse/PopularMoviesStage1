package com.udacity.popularmoviesstage1.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Accolite on 7/13/2017.
 */

public class DialogUtility {

    public static void showToast(Context context, String message) {
        if (context != null && !TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
