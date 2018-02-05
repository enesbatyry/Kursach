package com.example.enes.materialdesignfromgoogle.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.enes.materialdesignfromgoogle.Model.FacebookService;
import com.example.enes.materialdesignfromgoogle.Model.InfoContent;
import com.example.enes.materialdesignfromgoogle.Model.Service;
import com.example.enes.materialdesignfromgoogle.Model.User;
import com.example.enes.materialdesignfromgoogle.Model.VkService;
import com.example.enes.materialdesignfromgoogle.R;
import com.example.enes.materialdesignfromgoogle.Util.Constants;
import com.example.enes.materialdesignfromgoogle.Util.ImageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Enes on 11.01.2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper{

    private Context context;
    private ImageUtils imageUtils;

    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
        imageUtils = new ImageUtils(this.context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INFOCONTENT_TABLE = "CREATE TABLE " + Constants.CONTENT_TABLE_NAME + "("
                + Constants.CONTENT_ID + " STRING PRIMARY KEY,"
                + Constants.CONTENT_TITLE + " TEXT,"
                + Constants.CONTENT_TEXT + " TEXT,"
                + Constants.CONTENT_IMAGE + " BLOB,"
                + Constants.SHIPPED + " BOOLEAN);";

        String CREATE_USER_TABLE = "CREATE TABLE " + Constants.USER_TABLE_NAME + "("
                + Constants.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constants.USER_NIC + " TEXT,"
                + Constants.USER_AVATAR + " BLOB,"
                + Constants.USER_SERVICE + " TEXT);";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_INFOCONTENT_TABLE);

        Log.d(Constants.MyLog, "created:  " + CREATE_USER_TABLE);
        Log.d(Constants.MyLog, "created: " + CREATE_INFOCONTENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.CONTENT_TABLE_NAME);
        onCreate(db);
    }

    public void addContent(InfoContent content){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.CONTENT_ID, String.valueOf(content.getId()));
        values.put(Constants.CONTENT_TITLE, content.getTitle());
        values.put(Constants.CONTENT_TEXT, content.getMessage());


        byte[] image = imageUtils.getBitmapAsByteArray(content.getImage());

        values.put(Constants.CONTENT_IMAGE, image);
        values.put(Constants.SHIPPED, content.getShipped());

        db.insert(Constants.CONTENT_TABLE_NAME, null, values);

        Log.d(Constants.MyLog, "added  content: " + content.getTitle());
        db.close();
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.USER_NIC, user.getNic());

        byte[] image = imageUtils.getBitmapAsByteArray(user.getAvatar());
        values.put(Constants.USER_AVATAR, image);
        values.put(Constants.USER_SERVICE, user.getService().toString());

        db.insert(Constants.USER_TABLE_NAME, null, values);

        Log.d(Constants.MyLog, "added user " + user.getNic());

        db.close();
    }

    public InfoContent getContent(UUID id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.CONTENT_TABLE_NAME, new String[]{Constants.CONTENT_ID, Constants.CONTENT_TITLE,
                                                                Constants.CONTENT_TEXT, Constants.CONTENT_IMAGE},
                            Constants.CONTENT_ID + "=?", new String[]{String.valueOf(id)},null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        InfoContent infoContent = new InfoContent();
        infoContent.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_ID))));
        infoContent.setTitle(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_TITLE)));
        infoContent.setMessage(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_TEXT)));


        byte[] image = cursor.getBlob(cursor.getColumnIndex(Constants.CONTENT_IMAGE));

        Bitmap bitmap = imageUtils.readImage(image);
        infoContent.setImage(bitmap);
        infoContent.setShipped((byte) cursor.getInt(cursor.getColumnIndex(Constants.SHIPPED)));

        Log.d(Constants.MyLog, "got one content");

        db.close();
        return infoContent;
    }

    public List<InfoContent> getAllContent(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<InfoContent> list = new ArrayList<>();

        Cursor cursor = db.query(Constants.CONTENT_TABLE_NAME, new String[]{Constants.CONTENT_ID, Constants.CONTENT_TITLE, Constants.CONTENT_TEXT,
                                                                    Constants.CONTENT_IMAGE,Constants.SHIPPED},null, null, null, null,
                                                                    Constants.CONTENT_ID + " DESC");
        if (cursor.moveToFirst()){
            do{
                InfoContent infoContent = new InfoContent();
                infoContent.setId(UUID.fromString(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_ID))));
                infoContent.setTitle(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_TITLE)));
                infoContent.setMessage(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_TEXT)));

                byte[] image = cursor.getBlob(cursor.getColumnIndex(Constants.CONTENT_IMAGE));

                Bitmap bitmap = imageUtils.readImage(image);
                infoContent.setImage(bitmap);

                //infoContent.setImage( BitmapFactory.decodeByteArray(image, 0, image.length));

                infoContent.setShipped((byte) cursor.getInt(cursor.getColumnIndex(Constants.SHIPPED)));

                list.add(infoContent);
            }while (cursor.moveToNext());
        }
        Log.d(Constants.MyLog, "got all content ");
        db.close();
        return list;
    }

    public List<User> getAllUser(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<User> list = new ArrayList<>();

        Cursor cursor = db.query(Constants.USER_TABLE_NAME, new String[]{Constants.USER_NIC, Constants.USER_AVATAR,
                        Constants.USER_SERVICE},null, null, null, null,
                Constants.USER_ID + " DESC");

        if (cursor.moveToFirst()){
            do{
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(Constants.USER_ID)));
                user.setNic(cursor.getString(cursor.getColumnIndex(Constants.USER_NIC)));
                byte[] image = cursor.getBlob(cursor.getColumnIndex(Constants.USER_AVATAR));

                Bitmap bitmap = imageUtils.readImage(image);
                user.setAvatar(bitmap);

                String service_name = cursor.getString(cursor.getColumnIndex(Constants.USER_SERVICE));
                Service service = null;
                switch (service_name){
                    case Constants.VKService:{
                        service = new VkService();
                        break;
                    }
                    case Constants.FacebookService:{
                        service = new FacebookService();
                        break;
                    }
                }
                user.setService(service);

                list.add(user);
            }while (cursor.moveToNext());
        }
        Log.d(Constants.MyLog, "got all user ");
        db.close();
        return list;
    }

    public void deleteContent(UUID id){
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(Constants.CONTENT_TABLE_NAME, Constants.CONTENT_ID + "=?", new String[]{String.valueOf(id)});
        Log.d(Constants.MyLog, "deleteContent: " + id);

        db.close();
    }

    public void deleteUser(User user){
        SQLiteDatabase db = this.getReadableDatabase();

        int id = user.getId();
        db.delete(Constants.USER_TABLE_NAME, Constants.USER_ID + "=?", new String[]{String.valueOf(id)});
        Log.d(Constants.MyLog, "deleteContent: " + user.getNic());
        db.close();
    }
}
