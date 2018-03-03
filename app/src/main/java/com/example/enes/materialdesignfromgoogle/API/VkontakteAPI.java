package com.example.enes.materialdesignfromgoogle.API;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enes.materialdesignfromgoogle.Model.User;
import com.example.enes.materialdesignfromgoogle.Model.UserDAO;
import com.example.enes.materialdesignfromgoogle.Model.VkService;
import com.example.enes.materialdesignfromgoogle.R;
import com.example.enes.materialdesignfromgoogle.Util.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKBatchRequest;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.api.model.VKWallPostResult;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.util.VKStringJoiner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enes on 26/12/2017.
 */

public class VkontakteAPI {

    private final String LOG_TAG = "MyLog";
    private String userName = "Не авторизованы";
    private String urlImage = "";
    private boolean isAuthorize;

    private UserDAO userDAO;
    private User userVk;

    private ProgressDialog pd;
    private static Context context;

    private static VkontakteAPI api;

    private VkontakteAPI() {
    }

    public static VkontakteAPI getVkontakteApi(Context ctx){
        if (api == null){
            context = ctx;
            api = new VkontakteAPI();
        }
        return api;
    }

    public void createProgressDialog(Context context){
        //pd = new ProgressDialog(context);
        //pd.setCancelable(false);
    }


    public void loadInfoContent(Bitmap photo, final String message, final String where) {
        Log.v(LOG_TAG,"loadPhotoToMyWall");

        //pd.setTitle("Upload");
        //pd.setMessage("Please wait...");
        //pd.setCancelable(false);
       // pd.show();

        int myId = 0;
        switch (where){
            case Constants.TO_WALL: {
                myId = getMyId();
                break;
            }
            case Constants.TO_GROUP:{
                myId = getIdGroup();
                break;
            }

        }
        VKRequest request =  VKApi.uploadWallPhotoRequest(new VKUploadImage(photo,
                VKImageParameters.jpgImage(0.9f)), getMyId(), 0);
        Log.v(LOG_TAG,"start Request");
        final int finalMyId = myId;

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKApiPhoto photoModel = ((VKPhotoArray) response.parsedModel).get(0);
                makePost(new VKAttachments(photoModel), message, finalMyId);
                Log.v(LOG_TAG,"start onComplete loadPhotoToMyWall");

            }
            @Override
            public void onError(VKError error) {
                super.onError(error);
                Log.e(LOG_TAG,"onError loadPhotoToMyWall");
            }
        });
    }


    public void loadInfoContents(List<Bitmap> photos, final String message, final String where) {
        Log.v(LOG_TAG,"loadPhotoToMyWall");

        //pd.setTitle("Upload");
        //pd.setMessage("Please wait...");
        //pd.setCancelable(false);
        // pd.show();

        int myId = 0;
        switch (where){
            case Constants.TO_WALL: {
                myId = getMyId();
                break;
            }
            case Constants.TO_GROUP:{
                myId = getIdGroup();
                break;
            }

        }
        final VKRequest[] requests = new VKRequest[photos.size()];
        Log.v(LOG_TAG,"start Request");

        int i = 0;
        for (Bitmap bmp : photos) {
            VKRequest request =  VKApi.uploadWallPhotoRequest(new VKUploadImage(bmp,
                    VKImageParameters.jpgImage(0.9f)), getMyId(), 0);
            requests[i] = request;
            ++i;
        }
        final int finalMyId = myId;

        final VKApiPhoto[] photoModels = new VKApiPhoto[photos.size()];
        VKBatchRequest batchRequest = new VKBatchRequest(requests);
        batchRequest.executeWithListener(new VKBatchRequest.VKBatchRequestListener() {
            @Override
            public void onComplete(VKResponse[] responses) {
                for (int j = 0; j < responses.length; j++) {
                    VKApiPhoto photoModel = ((VKPhotoArray) responses[j].parsedModel).get(0);
                    photoModels[j] = photoModel;
                }
                makePost(new VKAttachments(photoModels), message, finalMyId);
                Log.v(LOG_TAG,"start onComplete loadPhotoToMyWall");
            }

            @Override
            public void onError(VKError error) {

            }
        });


    }

    public void makePost(VKAttachments att, String msg, final int id) {

        VKParameters parameters = new VKParameters();
        parameters.put(VKApiConst.OWNER_ID, id);
        parameters.put(VKApiConst.ATTACHMENTS, att);
        parameters.put(VKApiConst.MESSAGE, msg);
        VKRequest post = VKApi.wall().post(parameters);
        post.setModelClass(VKWallPostResult.class);
        Log.v(LOG_TAG,"makePost");

        post.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                Log.v(LOG_TAG,"start onComplete makePost()");
                //pd.cancel();
                Toast.makeText(context,"Complete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                pd.cancel();
                Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makePost(VKAttachments[] att, String msg, final int id) {

        VKParameters parameters = new VKParameters();
        parameters.put(VKApiConst.OWNER_ID, id);
        parameters.put(VKApiConst.ATTACHMENTS, att);
        parameters.put(VKApiConst.MESSAGE, msg);
        VKRequest post = VKApi.wall().post(parameters);
        post.setModelClass(VKWallPostResult.class);
        Log.v(LOG_TAG,"makePost");

        post.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                Log.v(LOG_TAG,"start onComplete makePost()");
                //pd.cancel();
                Toast.makeText(context,"Complete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                pd.cancel();
                Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void readUserName(final TextView view){
        Log.v(LOG_TAG,"readUserName");
        VKRequest request = VKApi.users().get(VKParameters.from(R.integer.com_vk_sdk_AppId,1,VKApiConst.LANG,3));
        request.executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList list = (VKList) response.parsedModel;
                if (list.size() > 0) {
                    userName = list.get(0).toString();
                    view.setText(userName);
                    userVk.setNic(userName);
                    userVk.setService(new VkService());
                }else{
                    userName = "Не авторизованы";
                }
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, final long bytesLoaded, final long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);

            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }

    public String getUserName(){
       return this.userName;
    }

    public String getAvatarImage(final ImageView imageView){
        Log.v(LOG_TAG,"getAvatarImage");
        userVk = new User();
        userDAO = UserDAO.getInstance(context);
        final VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,"photo_50,photo_100,photo_200"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                if (((VKList)response.parsedModel).size()>0) {
                    final VKApiUser user = (VKApiUser) ((VKList) response.parsedModel).get(0);
                    if (user == null) {
                        urlImage = "";
                    } else {
                        urlImage = user.photo_200;
                        Picasso.with(context)
                                .load(urlImage)
                                .into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        imageView.setImageBitmap(bitmap);
                                        userVk.setAvatar(bitmap);
                                        //userDAO.addUser(userVk);
                                    }

                                    @Override
                                    public void onBitmapFailed(Drawable errorDrawable) {

                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                                    }
                                });
                    }
                }else{
                    urlImage = "";
                }
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });

        return urlImage;
    }

    public static void setContext(Context context) {
        VkontakteAPI.context = context;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public boolean isAuthorize() {
        return isAuthorize;
    }

    public int getIdGroup() {
        return -155582666;
    }

    public int getMyId() {
        final VKAccessToken vkAccessToken = VKAccessToken.currentToken();
        return vkAccessToken != null ? Integer.parseInt(vkAccessToken.userId) : 0;
    }
}
