package com.example.enes.materialdesignfromgoogle.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.enes.materialdesignfromgoogle.Data.DatabaseHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Enes on 26/12/2017.
 */

public class InfoContentDAO {

    private static InfoContentDAO instance;
    private List<InfoContent> contentList;
    private File mImageFile;
    private DatabaseHandler db;
    private Context context;

    private InfoContentDAO(Context context){
        contentList = new ArrayList<>();
        db = new DatabaseHandler(context);
        this.context = context;
    }
    public static InfoContentDAO getInstance(Context context){

        if(instance == null){
            instance = new InfoContentDAO(context);
        }

        return instance;
    }


    public List<InfoContent> getContentList() {
        contentList = db.getAllContent();
        return contentList;
    }

    public void deleteContent(InfoContent content){
        db.deleteContent(content.getId());
        contentList.remove(content);
    }

    public void addContent(InfoContent content){
        db.addContent(content);
        contentList.add(0,content);
    }

    public void saveImage(List<Bitmap> finalBitmaps, List<String> image_names) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        int index = 0;
        for (String image_name: image_names) {

            File file = new File(myDir, image_name);
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmaps.get(index).compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            ++index;
        }
    }

    public List<Bitmap> readImages(List<String> image_names){
        String root = Environment.getExternalStorageDirectory().toString();
        List<Bitmap> listBitmaps = new ArrayList<>();
        for (int i = 0; i < image_names.size(); i++) {
            Bitmap bMap = BitmapFactory.decodeFile(root+"/saved_images/" + image_names.get(i));
            listBitmaps.add(bMap);
        }
        return listBitmaps;
    }
    public Bitmap readImages(String image_name){
        String root = Environment.getExternalStorageDirectory().toString();
        Bitmap bMap = BitmapFactory.decodeFile(root+"/saved_images/" + image_name);
        return bMap;
    }

    public void setContentList(List<InfoContent> contentList) {
        this.contentList = contentList;
    }

}
