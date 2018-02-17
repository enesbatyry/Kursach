package com.example.enes.materialdesignfromgoogle.API;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.tv.TvInputService;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Enes on 22.01.2018.
 */

public class FacebookApi {
    private CallbackManager callbackManager;
    private LoginManager manager;
    private Context context;


    public FacebookApi(Context context) {
        this.context = context;
        callbackManager = CallbackManager.Factory.create();
        manager = LoginManager.getInstance();
    }

    public void initialize(){
        FacebookSdk.sdkInitialize(context);
    }

    public void publishImage(Bitmap image){
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("My caption for facebook")
                .build();
        SharePhotoContent photoContent = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(photoContent, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(context,"Succes share facebook", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    public void readAvatarImage(int userId, ImageView imageView){
    }
}
