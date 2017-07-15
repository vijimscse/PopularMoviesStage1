package com.udacity.popularmoviesstage1.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by VijayaLakshmi.IN on 7/14/2017.
 */

public class DateFormatter {

    public static String getDateFormat(String dateStr) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        try {
            Date date = dt.parse(dateStr);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd MMM yyyy", Locale.US);

            return dt1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

       return null;
    }
}
