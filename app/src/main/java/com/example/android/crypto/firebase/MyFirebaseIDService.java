package com.example.android.crypto.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by pc on 1/29/2018.
 */

public class MyFirebaseIDService extends FirebaseInstanceIdService {
    private static final  String TAG = "MyFirebaseIIDService";
    @Override
    public void  onTokenRefresh (){

        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed Token: " + refreshedToken);
        sendRegistrationToService(refreshedToken);
    }

    private void sendRegistrationToService(String refreshedToken) {
        //Imeplement this method to send token to your app server.
    }
}
