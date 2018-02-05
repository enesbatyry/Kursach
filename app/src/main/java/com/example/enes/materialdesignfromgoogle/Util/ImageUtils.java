package com.example.enes.materialdesignfromgoogle.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Enes on 11.01.2018.
 */

public class ImageUtils {

    private Context context;

    public ImageUtils(Context context) {
        this.context = context;
    }

    public byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public void writeImage(Bitmap bitmap, String filename){
        byte[] image = getBitmapAsByteArray(bitmap);
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(image);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Bitmap readImage(byte[] image){
        //File file = new File(context.getFilesDir(), filename);
        return BitmapFactory.decodeByteArray(image,0,image.length);
    }


}
