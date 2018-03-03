package com.example.enes.materialdesignfromgoogle.Util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Enes on 20.02.2018.
 */

public class DownloadUtils {
    public static ProgressDialog createProgressDialog(Context context, String title, String message){
        ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setTitle(title);
        pd.setTitle(message);
        return pd;
    }
}
