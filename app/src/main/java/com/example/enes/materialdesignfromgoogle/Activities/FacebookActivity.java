package com.example.enes.materialdesignfromgoogle.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaCas;
import android.media.tv.TvInputService;
import android.service.textservice.SpellCheckerService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.enes.materialdesignfromgoogle.API.FacebookApi;
import com.example.enes.materialdesignfromgoogle.Model.FacebookService;
import com.example.enes.materialdesignfromgoogle.Model.User;
import com.example.enes.materialdesignfromgoogle.Model.UserDAO;
import com.example.enes.materialdesignfromgoogle.R;
import com.example.enes.materialdesignfromgoogle.Util.Constants;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FacebookActivity extends AppCompatActivity {

    private LoginManager manager;
    private LoginButton login_btn_facebook;
    private CallbackManager callbackManager;
    private Context context;
    private FacebookApi apiFB;
    private String urlImage;
    private User user;
    private UserDAO userDAO;

    private TextView txt_nic_facebook;
    private ImageView image_avatar_facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        context = this;
        userDAO = UserDAO.getInstance(this);
        apiFB = new FacebookApi(this);

        List<User> list;
        list = userDAO.getAllUser();

        txt_nic_facebook = findViewById(R.id.txt_nic_facebook);
        image_avatar_facebook = findViewById(R.id.imageAvatar_Facebook);


        if (list.size()>0) {
            for (User user1 : list) {
                if (user1.getService() instanceof FacebookService){
                    user = user1;
                    txt_nic_facebook.setText(user.getNic());
                    image_avatar_facebook.setImageBitmap(user.getAvatar());
                }
            }
        }

        callbackManager = CallbackManager.Factory.create();
        manager = LoginManager.getInstance();
        login_btn_facebook = findViewById(R.id.login_btn_facebook);
        List< String > permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken" , "picture");
        login_btn_facebook.registerCallback(callbackManager,
                new FacebookCallback < LoginResult > () {@Override
                public void onSuccess(LoginResult loginResult) {

                    System.out.println("onSuccess");

                    String accessToken = loginResult.getAccessToken()
                            .getToken();
                    Log.i("accessToken", accessToken);

                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {@Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {
                                user = new User();
                                Log.i("LoginActivity", response.toString());
                                try {
                                    String id = object.getString("id");
                                    urlImage = "https://graph.facebook.com/" + id + "/picture?type=large";
                                    Picasso.with(context).load(urlImage)
                                            .into(new Target() {
                                            @Override
                                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                image_avatar_facebook.setImageBitmap(bitmap);
                                                user.setAvatar(bitmap);
                                                userDAO.addUser(user);
                                            }

                                            @Override
                                            public void onBitmapFailed(Drawable errorDrawable) {
                                                Log.d(Constants.MyLog, "facebook avatar load failed ");
                                            }

                                            @Override
                                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                                            }
                                        });
                                    Log.i("profile_pic",
                                            urlImage + "");

                                    String name = object.getString("name");
                                    user.setNic(name);
                                    user.setService(new FacebookService());

                                    txt_nic_facebook.setText(name);
                                    String email = object.getString("email");
                                    String gender = object.getString("gender");
                                    String birthday = object.getString("birthday");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields",
                            "id,name,email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Log.v("LoginActivity", exception.getCause().toString());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
