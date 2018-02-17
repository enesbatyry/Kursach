package com.example.enes.materialdesignfromgoogle.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enes.materialdesignfromgoogle.API.VkontakteAPI;
import com.example.enes.materialdesignfromgoogle.Adapter.ContentAdapter;
import com.example.enes.materialdesignfromgoogle.Data.RunnableClient;
import com.example.enes.materialdesignfromgoogle.Model.InfoContent;
import com.example.enes.materialdesignfromgoogle.Model.InfoContentDAO;
import com.example.enes.materialdesignfromgoogle.R;
import com.example.enes.materialdesignfromgoogle.Util.Constants;
import com.example.enes.materialdesignfromgoogle.fragments.ContentFragment;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;
import com.vk.sdk.VKSdk;

import java.util.List;
import java.util.UUID;

public class ContentActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_Title;
    private TextView tv_Message;
    private ImageView iv_Image;
    private Button btn_deleteContent;
    private Button toGroupButton;
    private Button toWallButton;
    private Button btn_fb_wall;
    private Button btn_wp;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private TextView title;
    private Button okButton;
    private Activity context;
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;
    private InfoContentDAO infoContentDAO;
    private InfoContent infoContent;
    private List<InfoContent> contentList;
    private VkontakteAPI apiVk;

    private TextView txt_sys, txt_response;
    private EditText edit_msg, edit_ip, edit_port;
    private Button btn_send, btn_clear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        callbackManager = CallbackManager.Factory.create();

        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        tv_Title = findViewById(R.id.tv_title);
        tv_Message = findViewById(R.id.tv_message);

        iv_Image = findViewById(R.id.iv_image);

        btn_deleteContent = findViewById(R.id.btn_deleteContent);
        btn_deleteContent.setOnClickListener(this);

        toGroupButton = findViewById(R.id.toGroupButton);
        toGroupButton.setOnClickListener(this);

        toWallButton = findViewById(R.id.toWallButton);
        toWallButton.setOnClickListener(this);

        btn_fb_wall = findViewById(R.id.btn_fb_wall);
        btn_fb_wall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(infoContent.getImage())
                            .setCaption("My caption for facebook")
                            .build();
                    ShareContent photoContent = new ShareMediaContent.Builder()
                            .addMedium(photo)
                            .build();
                    shareDialog.show(photoContent);
                }
            }
        });

        btn_wp = findViewById(R.id.btn_wp);
        btn_wp.setOnClickListener(this);

        infoContentDAO = InfoContentDAO.getInstance(this);
        contentList = infoContentDAO.getContentList();

        apiVk = VkontakteAPI.getVkontakteApi(this);
        Intent intent = getIntent();
        int position = intent.getIntExtra("id",-1);
        infoContent = contentList.get(position);

        tv_Title.setText(infoContent.getTitle());
        tv_Message.setText(infoContent.getMessage());
        iv_Image.setImageBitmap(infoContent.getImage());
        Log.d(Constants.MyLog, "image_name: " + infoContent.getImageFileName());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_deleteContent:{
                UUID id = infoContent.getId();
                infoContentDAO.deleteContent(infoContent);
                ContentAdapter.infoContentList = infoContentDAO.getContentList();
                ContentAdapter.getAdapter().notifyDataSetChanged();
                finish();
                break;
            }
            case R.id.toWallButton:{
                if (!VKSdk.isLoggedIn()){createPopup(); return;}
                Log.v(Constants.MyLog,"toWallButton");
                apiVk.createProgressDialog(this);
                apiVk.loadPhotoToMyWall(infoContent.getImage(), infoContent.getMessage(),Constants.TO_WALL);
                break;
            }
            case R.id.toGroupButton:{
                if (!VKSdk.isLoggedIn()){createPopup(); return;}
                Log.v(Constants.MyLog,"toGroupButton");
                apiVk.createProgressDialog(this);
                apiVk.loadPhotoToMyWall(infoContent.getImage(), infoContent.getMessage(),Constants.TO_GROUP);
                break;
            }
            case R.id.btn_wp:{
                dialogBuilder = new AlertDialog.Builder(this);
                View view = getLayoutInflater().inflate(R.layout.activity_wordpress, null);
                txt_sys = view.findViewById(R.id.txt_sys);
                txt_sys.setText(
                        System.getProperty("os.arch") + "/"
                                + System.getProperty("os.name"));

                edit_msg = view.findViewById(R.id.edit_msg);
                edit_ip = view.findViewById(R.id.edit_ip);
                edit_port = view.findViewById(R.id.edit_port);

                txt_response = view.findViewById(R.id.txt_response);


                btn_send = view.findViewById(R.id.btn_send);
                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String msg = infoContent.getImageFileName();
                        RunnableClient runnableClient
                                = new RunnableClient(edit_ip.getText().toString(),
                                Integer.parseInt(edit_port.getText().toString()),
                                infoContent);

                        Log.d(Constants.MyLog, "onClick: start sender");

                        new Thread(runnableClient).start();
                    }
                });
                dialogBuilder.setView(view);
                dialog = dialogBuilder.create();
                dialog.show();
                break;
            }
        }
    }

    public void createPopup(){
        dialogBuilder = new AlertDialog.Builder(ContentActivity.this);

        View view = getLayoutInflater().inflate(R.layout.popup, null);

        title = view.findViewById(R.id.title);

        okButton = view.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VKSdk.login(context,Constants.SCOPE);
                dialog.cancel();
            }
        });

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();
    }

}
