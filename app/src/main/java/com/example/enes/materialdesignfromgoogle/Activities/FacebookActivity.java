package com.example.enes.materialdesignfromgoogle.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enes.materialdesignfromgoogle.API.FacebookApi;
import com.example.enes.materialdesignfromgoogle.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class FacebookActivity extends AppCompatActivity {

    private LoginManager manager;
    private LoginButton login_btn_facebook;
    private CallbackManager callbackManager;
    private FacebookApi apiFB;

    private TextView txt_nic_facebook;
    private ImageView image_avatar_facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        apiFB = new FacebookApi(this);
        txt_nic_facebook = findViewById(R.id.txt_nic_facebook);
        image_avatar_facebook = findViewById(R.id.image_avatar_vk);

        callbackManager = CallbackManager.Factory.create();
        manager = LoginManager.getInstance();

        login_btn_facebook = findViewById(R.id.login_btn_facebook);
        login_btn_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
