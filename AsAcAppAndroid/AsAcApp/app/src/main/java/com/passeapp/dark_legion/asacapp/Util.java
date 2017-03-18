package com.passeapp.dark_legion.asacapp;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.http.HttpEntity;
import android.util.Log;


public class Util {
    public Util() {
        // TODO Auto-generated constructor stub
    }

    public static boolean isNumber(String value) {
        try {
            new BigInteger(value);
            return true;
        } catch (Exception e) {
            Log.i("Validating number:",e.getLocalizedMessage());
        }
        return false;
    }

    public static String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();

        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n>0) {
            byte[] b = new byte[4096];
            n =  in.read(b);

            if (n>0) out.append(new String(b, 0, n));
        }

        return out.toString();
    }

    public static String getDateFromTime(String time) {
        return time.substring(0,10);
    }

    public static String getHourFromTime(String time) {
        return time.substring(11);
    }

    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());

    }

    public static String getHour() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());

    }

    public static String convertDateHourToTime(String date, String hour) {
        String new_date = date;
        if (date.length()==8)
            new_date=date.substring(0,2)+"-"+date.substring(2,4)+"-"+date.substring(4);
        return new_date+" "+hour;
    }
}
