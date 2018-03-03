package com.example.enes.materialdesignfromgoogle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.enes.materialdesignfromgoogle.API.VkontakteAPI;
import com.example.enes.materialdesignfromgoogle.Model.InfoContent;
import com.example.enes.materialdesignfromgoogle.Model.InfoContentDAO;
import com.example.enes.materialdesignfromgoogle.Util.Constants;

import java.util.List;

/**
 * Created by Enes on 01.03.2018.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private VkontakteAPI apiVk;
    private InfoContentDAO infoContentDAO;
    private InfoContent infoContent;
    private List<InfoContent> contentList;
    public static int position;


    @Override
    public void onReceive(Context context, Intent intent) {
        apiVk = VkontakteAPI.getVkontakteApi(context);
        infoContentDAO = InfoContentDAO.getInstance(context);
        contentList = infoContentDAO.getContentList();
        infoContent = contentList.get(position);

        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification("Upload", "completed:)");
        notificationHelper.getManager().notify(1, nb.build());

        Log.v(Constants.MyLog,"toGroupButton");
        apiVk.loadInfoContents(infoContentDAO.readImages(infoContent.getImageFileNames()), infoContent.getMessage(),Constants.TO_GROUP);
    }
}
