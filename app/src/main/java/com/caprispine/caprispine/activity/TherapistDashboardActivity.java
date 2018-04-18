package com.caprispine.caprispine.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.GPSTracker;
import com.caprispine.caprispine.Util.Pref;
import com.caprispine.caprispine.Util.StringUtils;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TherapistDashboardActivity extends AppCompatActivity {

    @BindView(R.id.rl_patients)
    RelativeLayout rl_patients;
    @BindView(R.id.rl_schedule)
    RelativeLayout rl_schedule;
    @BindView(R.id.rl_chat)
    RelativeLayout rl_chat;
    @BindView(R.id.rl_report)
    RelativeLayout rl_report;
    @BindView(R.id.rl_notification)
    RelativeLayout rl_notification;
    @BindView(R.id.rl_logout)
    RelativeLayout rl_logout;

    TherapistPOJO therapistPOJO;

    private DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_dashboard);
        ButterKnife.bind(this);
        root = FirebaseDatabase.getInstance().getReference().getRoot();
        therapistPOJO = (TherapistPOJO) getIntent().getSerializableExtra("therapistPOJO");

        rl_patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TherapistDashboardActivity.this, TherapistPatientListActivity.class).putExtra("therapist", therapistPOJO));
            }
        });

        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_THERAPIST_LOGIN, false);
                startActivity(new Intent(TherapistDashboardActivity.this, SplashActivity.class));
                finishAffinity();
            }
        });
        rl_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(TherapistDashboardActivity.this, AppointmentActivity.class).putExtra("therapistPOJO", therapistPOJO));
            }
        });

        rl_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TherapistDashboardActivity.this, ViewReportActivity.class).putExtra("therapistPOJO", therapistPOJO));
            }
        });

        rl_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TherapistDashboardActivity.this, AllUsersChatActivity.class).putExtra("therapistPOJO", therapistPOJO));
            }
        });

        getLocation();
    }


    GPSTracker gps;

    public void getLocation() {
        gps = new GPSTracker(this);
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Log.d(TagUtils.getTag(), "location:-latitude:-" + latitude);
            Log.d(TagUtils.getTag(), "location:-longitude:-" + longitude);

            Pref.SetStringPref(getApplicationContext(), StringUtils.CURRENT_LATITUDE, String.valueOf(latitude));
            Pref.SetStringPref(getApplicationContext(), StringUtils.CURRENT_LONGITUDE, String.valueOf(longitude));
            getAddress(latitude, longitude);

        } else {
            gps.showSettingsAlert();
        }
    }

    public void updateOnFirebase(String address) {
        root.child("therapist").child(therapistPOJO.getId()).setValue(address);
    }

    public String getAddress(double latitude, double longitude) {
        String address = "";
//                    LocationAddress.getAddressFromLocation(latitude,longitude,LocationService.this,new GeocoderHandler());
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address returnedAddress = addresses.get(0);

                StringBuilder strReturnedAddress = new StringBuilder();
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                Log.d("sunil", strReturnedAddress.toString());
//                address = strReturnedAddress.toString();
                updateOnFirebase(strReturnedAddress.toString());
                return strReturnedAddress.toString();
            } else {
                Log.d("sunil", "No Address returned!");
                return "";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("sunil", "Canont get Address!");
            return "";
        }
    }

}
