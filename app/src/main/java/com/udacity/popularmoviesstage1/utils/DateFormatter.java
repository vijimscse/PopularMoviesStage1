package com.udacity.popularmoviesstage1.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by VijayaLakshmi.IN on 7/14/2017.
 */

public class DateFormatter {

    public static String getDateFormat(String dateStr) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = dt.parse(dateStr);
            SimpleDateFormat dt1 = new SimpleDateFormat("dd MMM yyyy");

            return dt1.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

       return null;
    }
}
