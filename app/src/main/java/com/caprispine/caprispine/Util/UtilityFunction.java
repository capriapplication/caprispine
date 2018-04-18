package com.caprispine.caprispine.Util;

import android.content.Context;
import android.widget.EditText;

import com.caprispine.caprispine.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sunil on 01-03-2018.
 */

public class UtilityFunction {
    public static ArrayList<NameValuePair> getNameValuePairs(Context context) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("device_token", Pref.GetDeviceToken(context)));
        nameValuePairs.add(new BasicNameValuePair("device_type", "android"));

        return nameValuePairs;
    }

    public static boolean isDataValid(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().length() == 0) {
                return false;
            }
        }
        return true;
    }

    public static String getToneSpinnerData(Context context, int position) {
        if (position == 0) {
            return "";
        } else {
            String arr[] = context.getResources().getStringArray(R.array.head_tone_of_the_motor);
            return arr[position];
        }
    }

    public static String getPowerSpinnerData(Context context, int position) {
        if (position == 0) {
            return "";
        } else {
            String arr[] = context.getResources().getStringArray(R.array.head_power_of_the_motor);
            return arr[position];
        }
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        return sdf.format(d);
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d = new Date();
        return sdf.format(d);
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date d = new Date();
        return sdf.format(d);
    }

    public static String getParsedDate(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date d = simpleDateFormat.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getddMMYYYY() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    public static List<String> getDifference(String time1, String time2) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date date1 = simpleDateFormat.parse(time1);
            Date date2 = simpleDateFormat.parse(time2);
            List<java.sql.Time> intervals = new ArrayList<>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            intervals.add(new java.sql.Time(cal.getTimeInMillis()));
            while (cal.getTime().before(date2)) {
                cal.add(Calendar.MINUTE, 15);
                intervals.add(new java.sql.Time(cal.getTimeInMillis()));
            }

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            List<String> lisStrings = new ArrayList<>();
            for (java.sql.Time s : intervals) {
//		    System.out.println(sdf.format(s));
                lisStrings.add(sdf.format(s));
            }
            return lisStrings;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDate(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date d = simpleDateFormat.parse(date);
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDate1(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date d = simpleDateFormat.parse(date);
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getEdittextData(EditText editText) {
        return editText.getText().toString();
    }

    public static String getPaymentMode(String mode) {
        switch (mode) {
            case "1":
                return "Cash";
            case "2":
                return "Card";
            case "3":
                return "Cheque";
            case "4":
                return "Online";
        }

        return "";
    }

    public static String getPaymentModeID(String mode) {
        switch (mode.toLowerCase()) {
            case "cash":
                return "1";
            case "card":
                return "2";
            case "cheque":
                return "3";
            case "online":
                return "4";
        }

        return "";
    }

    public static String getTransType(String mode) {
        switch (mode) {
            case "1":
                return "Credit";
            case "2":
                return "Debit";
        }

        return "";
    }

    public static String getTransTypeID(String mode) {
        switch (mode) {
            case "credit":
                return "1";
            case "debit":
                return "2";
        }

        return "";
    }

    public static int getAge(Date dateOfBirth) {

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();

        int age = 0;

        birthDate.setTime(dateOfBirth);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }

        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
        if ((birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
                (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
            age--;

            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        } else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH)) &&
                (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age;
    }

}
