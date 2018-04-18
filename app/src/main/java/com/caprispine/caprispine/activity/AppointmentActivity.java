package com.caprispine.caprispine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.Pref;
import com.caprispine.caprispine.Util.StringUtils;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppointmentActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.cv_manage_appointment)
    CardView cv_manage_appointment;
    @BindView(R.id.cv_new_appointment)
    CardView cv_new_appointment;

    StaffPOJO staffPOJO;
    TherapistPOJO therapistPOJO;
    PatientPOJO patientPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        staffPOJO= (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");
        therapistPOJO= (TherapistPOJO) getIntent().getSerializableExtra("therapistPOJO");
        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");

        cv_new_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AppointmentActivity.this,BookAppointmentActivity.class);
                if(Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_STAFF_LOGIN,true)){
                    intent.putExtra("staffPOJO",staffPOJO);
                }
                if(Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_PATIENT_LOGIN,true)){
                    intent.putExtra("patientPOJO",patientPOJO);
                }
                if(Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_THERAPIST_LOGIN,true)){
                    intent.putExtra("therapistPOJO",therapistPOJO);
                }

                startActivity(intent);
            }
        });


        cv_manage_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AppointmentActivity.this, ManageAppointmentActivity.class);
                if(Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_STAFF_LOGIN,true)){
                    intent.putExtra("staffPOJO",staffPOJO);
                }
                if(Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_PATIENT_LOGIN,true)){
                    intent.putExtra("patientPOJO",patientPOJO);
                }
                if(Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_THERAPIST_LOGIN,true)){
                    intent.putExtra("therapistPOJO",therapistPOJO);
                }

                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
