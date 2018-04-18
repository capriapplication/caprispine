package com.caprispine.caprispine.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.Pref;
import com.caprispine.caprispine.Util.StringUtils;
import com.caprispine.caprispine.pojo.user.StaffPOJO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StaffDashboardActivity extends AppCompatActivity {

    @BindView(R.id.rl_logout)
    RelativeLayout rl_logout;
    @BindView(R.id.rl_report)
    RelativeLayout rl_report;
    @BindView(R.id.rl_amount)
    RelativeLayout rl_amount;
    @BindView(R.id.rl_expense)
    RelativeLayout rl_expense;
    @BindView(R.id.rl_patients)
    RelativeLayout rl_patients;
    @BindView(R.id.schedule)
    RelativeLayout schedule;
    @BindView(R.id.img_chat1)
    RelativeLayout img_chat1;

    StaffPOJO staffPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);
        ButterKnife.bind(this);

        staffPOJO= (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");

        rl_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StaffDashboardActivity.this,ViewReportActivity.class).putExtra("staffPOJO",staffPOJO));
            }
        });
        rl_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StaffDashboardActivity.this,PatientAmountActivity.class).putExtra("staffPOJO",staffPOJO));
            }
        });

        rl_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StaffDashboardActivity.this,AddExpenseActivity.class).putExtra("staffPOJO",staffPOJO));
            }
        });

        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StaffDashboardActivity.this,AppointmentActivity.class).putExtra("staffPOJO",staffPOJO));
            }
        });

        rl_patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPatientDialog();
            }
        });


        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_STAFF_LOGIN,false);
                startActivity(new Intent(StaffDashboardActivity.this,SplashActivity.class));
                finishAffinity();
            }
        });

        img_chat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StaffDashboardActivity.this,AllUsersChatActivity.class).putExtra("staffPOJO",staffPOJO));
            }
        });
    }


    public void showPatientDialog() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        dialog.setContentView(R.layout.dialog_patient_treat);
        dialog.setTitle("Select Patients");
        dialog.setCancelable(true);
        Button btn_opd = (Button) dialog.findViewById(R.id.btn_opd);
        Button btn_hv = (Button) dialog.findViewById(R.id.btn_hv);
        Button btn_online_paid = (Button) dialog.findViewById(R.id.btn_online_paid);
        Button btn_online_free = (Button) dialog.findViewById(R.id.btn_online_free);


        btn_hv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffDashboardActivity.this, PatientListActivity.class).putExtra("treatment_type","2").putExtra("staffPOJO",staffPOJO));
                dialog.dismiss();
            }
        });

        btn_opd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffDashboardActivity.this, PatientListActivity.class).putExtra("treatment_type","1").putExtra("staffPOJO",staffPOJO));
                dialog.dismiss();
            }
        });

        btn_online_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffDashboardActivity.this, PatientListActivity.class).putExtra("treatment_type","3").putExtra("staffPOJO",staffPOJO));
                dialog.dismiss();
            }
        });

        btn_online_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StaffDashboardActivity.this, PatientListActivity.class).putExtra("treatment_type","4").putExtra("staffPOJO",staffPOJO));
                dialog.dismiss();
            }
        });


        dialog.show();
    }
}
