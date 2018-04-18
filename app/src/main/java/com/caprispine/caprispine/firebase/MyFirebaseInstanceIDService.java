package com.caprispine.caprispine.firebase;

import android.content.Context;
import android.util.Log;

import com.caprispine.caprispine.Util.Pref;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by Emobi-Android-002 on 9/19/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
Context ctx;
    private static final String TAG = "shubham";

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        PreferenceData.setDevice_Token(getApplicationContext(),refreshedToken);
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        Pref.SaveDeviceToken(getApplicationContext(),refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}