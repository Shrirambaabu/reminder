package in.myreminder.srb.utils;

import android.support.v7.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String showFancyDate(String normalDate) {

        String date = "";
        SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyy");
        Date newDate = null;
        try {
            newDate = spf.parse(normalDate);
            spf = new SimpleDateFormat("dd MMM yyyy");
            date = spf.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return date;
    }


    public static void backButtonOnToolbar(AppCompatActivity mActivity) {
        if (mActivity.getSupportActionBar() != null) {
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

}
