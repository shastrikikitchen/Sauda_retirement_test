package com.traveller.enthusiastic.goldwise.helper;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.traveller.enthusiastic.goldwise.R;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by sauda on 25/02/17.
 */

public class Utility {
    public static void showToast(Context ctx,String str){
        if(ctx!= null && !TextUtils.isEmpty(str)) {
            Toast mToast = Toast.makeText(ctx, str, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }
    public static String getFormatedAmount(Context context, String amount) {

        if (null == amount || amount.length() == 0) {

            return ("N/A");
        }
        try {
            Format df = null;
                df = new DecimalFormat("##,##,##,###.##");

            double val = Double.parseDouble(amount);
            return context.getString(R.string.rupee_symbol) + df.format(val);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return amount;
        } catch (Exception e) {
            e.printStackTrace();
            return amount;
        }
    }

}
