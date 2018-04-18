package com.caprispine.caprispine.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.Constants;
import com.caprispine.caprispine.Util.Pref;
import com.caprispine.caprispine.Util.StringUtils;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.pojo.user.AdminUserPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.google.gson.Gson;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (checkAndRequestPermissions()) {
            makesplash();
        }

        AppCenter.start(getApplication(), "e1ae8e7e-bfdd-493e-8e3f-fead9b56086e",
                Analytics.class, Crashes.class);
        AppCenter.start(getApplication(), "e1ae8e7e-bfdd-493e-8e3f-fead9b56086e", Analytics.class, Crashes.class);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean checkAndRequestPermissions() {
        int ACCESS_FINE_LOCATION = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int ACCESS_NETWORK_STATE = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
        int WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int CAMERA = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int RECORD_AUDIO = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int MODIFY_AUDIO_SETTINGS = ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_AUDIO_SETTINGS);
        int READ_PHONE_STATE = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int READ_CONTACTS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int CALL_PHONE = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int VIBRATE = ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE);
        int RECEIVE_SMS = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (ACCESS_FINE_LOCATION != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ACCESS_NETWORK_STATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }

        if (WRITE_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (CAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (RECORD_AUDIO != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (MODIFY_AUDIO_SETTINGS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
        }
        if (READ_PHONE_STATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (READ_CONTACTS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (CALL_PHONE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (VIBRATE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.VIBRATE);
        }
        if (RECEIVE_SMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.d("msg", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions

                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.MODIFY_AUDIO_SETTINGS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.VIBRATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.RECEIVE_SMS, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions


                    if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
                            ) {
                        Log.d("msg", "All Permissions granted");
                        makesplash();
                    } else {
                        Log.d("msg", "Some permissions are not granted ask again ");

                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_NETWORK_STATE)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.MODIFY_AUDIO_SETTINGS)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.VIBRATE)
                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)
//                                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.VIBRATE)
                                ) {
                            showDialogOK("Permisions required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                }
            }
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    public void makesplash() {
        new CountDownTimer(2000, 1000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                createSPlash();
            }
        }.start();
    }

    public void createSPlash() {
        Log.d(TagUtils.getTag(), "admin pojo:-" + Pref.GetStringPref(getApplicationContext(), StringUtils.ADMIN_POJO, ""));
        if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_ADMIN_LOGIN, false)) {
            AdminUserPOJO adminUserPOJO = new Gson().fromJson(Pref.GetStringPref(getApplicationContext(), StringUtils.ADMIN_POJO, ""), AdminUserPOJO.class);
            Constants.adminUserPOJO = adminUserPOJO;
            startActivity(new Intent(SplashActivity.this, AdminDashboardActivity.class).putExtra("adminPOJO", adminUserPOJO));
//        }else if(Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_ADMIN_LOGIN,false)){
//            startActivity(new Intent(SplashActivity.this, AdminDashBoardActivity.class));
        } else if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_PATIENT_LOGIN, false)) {
            Log.d(TagUtils.getTag(), "patient pojo:-" + Pref.GetStringPref(getApplicationContext(), StringUtils.PATIENT_POJO, ""));
            PatientPOJO patientPOJO = new Gson().fromJson(Pref.GetStringPref(getApplicationContext(), StringUtils.PATIENT_POJO, ""), PatientPOJO.class);
            Constants.patientPOJO = patientPOJO;
            startActivity(new Intent(getApplicationContext(), PatientDashBoardActivity.class));
        } else if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_THERAPIST_LOGIN, false)) {
            Log.d(TagUtils.getTag(), "therapist pojo:-" + Pref.GetStringPref(getApplicationContext(), StringUtils.THERAPIST_POJO, ""));
            TherapistPOJO therapistPOJO = new Gson().fromJson(Pref.GetStringPref(getApplicationContext(), StringUtils.THERAPIST_POJO, ""), TherapistPOJO.class);
            Constants.therapistPOJO = therapistPOJO;
            startActivity(new Intent(getApplicationContext(), TherapistDashboardActivity.class).putExtra("therapist", therapistPOJO));
        } else if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_STAFF_LOGIN, false)) {
            Log.d(TagUtils.getTag(), "staff pojo:-" + Pref.GetStringPref(getApplicationContext(), StringUtils.STAFF_POJO, ""));
            StaffPOJO staffPOJO = new Gson().fromJson(Pref.GetStringPref(getApplicationContext(), StringUtils.STAFF_POJO, ""), StaffPOJO.class);
            Constants.staffPOJO = staffPOJO;
            startActivity(new Intent(getApplicationContext(), StaffDashboardActivity.class).putExtra("staffPOJO", staffPOJO));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
//        startActivity(new Intent(SplashActivity.this, GraphTesting.class));

        finish();

//        startActivity(new Intent(SplashActivity.this, TestingAvtivity.class));
    }

}
