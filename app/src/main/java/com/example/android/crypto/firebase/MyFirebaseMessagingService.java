package com.example.android.crypto.firebase;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by pc on 1/29/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final  String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived (RemoteMessage remoteMessage){
        String notificationText = "";
        if (remoteMessage.getData().containsKey("vesti")){
       notificationText =  remoteMessage.getData().get("vesti");}
        Log.d(TAG, "Notification: " + notificationText);

        String notificationBody = "";
        if (remoteMessage.getNotification().getBody() != null) {
            notificationBody = remoteMessage.getNotification().getBody();
        }

        Intent i = new Intent("android.intent.action.FIREBASENOTIFICATION");
        i.putExtra("notification", notificationText);
        i.putExtra("notificationBody", notificationBody);
        this.sendBroadcast(i);

        Log.d("remotemessage", notificationBody);

    }

}
