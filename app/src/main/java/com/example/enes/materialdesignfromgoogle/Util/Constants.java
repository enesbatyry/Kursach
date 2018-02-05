package com.example.enes.materialdesignfromgoogle.Util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.vk.sdk.VKScope;

/**
 * Created by Enes on 11.01.2018.
 */

public class Constants {

    // constants for infoContent table
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "infoContentDB";
    public static final String CONTENT_TABLE_NAME = "contentTABLE";

    public static final String CONTENT_ID = "id";
    public static final String CONTENT_TITLE = "content_title";
    public static final String CONTENT_TEXT = "content_text";
    public static final String CONTENT_IMAGE = "content_image";
    public static final String SHIPPED = "content_shipped";

    //constants for user_table
    public static final String USER_TABLE_NAME = "userTABLE";

    public static final String USER_ID = "user_id";
    public static final String USER_NIC = "user_nic";
    public static final String USER_AVATAR = "user_avatar";
    public static final String USER_SERVICE = "user_service";

    //services name
    public static final String VKService = "VKService";
    public static final String FacebookService = "FacebookService";


    public static final String MyLog = "MyLog";
    public static final String TO_WALL = "wall";
    public static final String TO_GROUP = "group";
    public static final String[] SCOPE = {VKScope.FRIENDS,VKScope.GROUPS,VKScope.PHOTOS,VKScope.MESSAGES,VKScope.WALL};
}
