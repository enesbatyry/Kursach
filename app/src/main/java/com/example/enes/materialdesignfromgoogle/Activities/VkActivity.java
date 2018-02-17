package com.example.enes.materialdesignfromgoogle.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enes.materialdesignfromgoogle.API.VkontakteAPI;
import com.example.enes.materialdesignfromgoogle.Model.FacebookService;
import com.example.enes.materialdesignfromgoogle.Model.User;
import com.example.enes.materialdesignfromgoogle.Model.UserDAO;
import com.example.enes.materialdesignfromgoogle.Model.VkService;
import com.example.enes.materialdesignfromgoogle.R;
import com.example.enes.materialdesignfromgoogle.Util.Constants;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import java.util.List;


public class VkActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button btn_acivityVk;
    private TextView textUserName;
    private VkontakteAPI apiVk;
    private Activity activity;
    private Bitmap bitmap;
    private User user;
    private UserDAO userDAO;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vk);

        activity = this;

        apiVk = VkontakteAPI.getVkontakteApi(this);

        pd = new ProgressDialog(this);
        pd.setTitle("Wait...");
        imageView = findViewById(R.id.image_avatar_vk);
        bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        textUserName = findViewById(R.id.txt_vk_nic);
        btn_acivityVk = findViewById(R.id.btn_login_vk);

/*        List<User> list;
        userDAO = UserDAO.getInstance(this);
        list = userDAO.getAllUser();

        Log.d(Constants.MyLog, "list size :" + list.size());

        if (list.size()>0) {
            for (User user1 : list) {
                if (user1.getService() instanceof VkService){
                    btn_acivityVk.setText("Выйти");
                    user = user1;
                    textUserName.setText(user.getNic());
                    imageView.setImageBitmap(user.getAvatar());
                }
            }
        }*/

        if (VKSdk.isLoggedIn()){
            pd.show();
            btn_acivityVk.setText("Выйти");
            apiVk.getAvatarImage(imageView);
            apiVk.readUserName(textUserName);

            pd.cancel();
        }else {
            btn_acivityVk.setText("Войти");
        }
        btn_acivityVk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!VKSdk.isLoggedIn()){
                    VKSdk.login(activity, Constants.SCOPE);
                    apiVk.getAvatarImage(imageView);
                    apiVk.readUserName(textUserName);
                    btn_acivityVk.setText(R.string.logout);
                }else{
                    VKSdk.logout();
                    imageView.setImageBitmap(bitmap);
                    textUserName.setText(R.string.auth);
                    btn_acivityVk.setText(R.string.login);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (VKSdk.isLoggedIn()) {
            apiVk.getAvatarImage(imageView);
            apiVk.readUserName(textUserName);
        }

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {

            @Override
            public void onResult(VKAccessToken res) {

            }

            @Override
            public void onError(VKError error) {
                btn_acivityVk.setText("Войти");
            }
        }));
    }
}
