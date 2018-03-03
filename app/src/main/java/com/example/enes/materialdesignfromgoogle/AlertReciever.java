package com.example.enes.materialdesignfromgoogle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Enes on 01.03.2018.
 */

public class AlertReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification("Title1", "Message1");
        notificationHelper.getManager().notify(1, nb.build());

    }
}
