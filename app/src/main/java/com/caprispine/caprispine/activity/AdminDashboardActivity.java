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
import com.caprispine.caprispine.pojo.user.AdminUserPOJO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminDashboardActivity extends AppCompatActivity {

    @BindView(R.id.rl_patient)
    RelativeLayout rl_patient;
    @BindView(R.id.rl_schedule)
    RelativeLayout rl_schedule;
    @BindView(R.id.rl_staff)
    RelativeLayout rl_staff;
    @BindView(R.id.rl_therapist)
    RelativeLayout rl_therapist;
    @BindView(R.id.rl_report)
    RelativeLayout rl_report;
    @BindView(R.id.rl_notification)
    RelativeLayout rl_notification;
    @BindView(R.id.rl_amount)
    RelativeLayout rl_amount;
    @BindView(R.id.rl_expense)
    RelativeLayout rl_expense;
    @BindView(R.id.rl_treatment)
    RelativeLayout rl_treatment;
    @BindView(R.id.rl_branch)
    RelativeLayout rl_branch;
    @BindView(R.id.rl_chat)
    RelativeLayout rl_chat;
    @BindView(R.id.rl_logout)
    RelativeLayout rl_logout;

    AdminUserPOJO adminUserPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        ButterKnife.bind(this);

        adminUserPOJO= (AdminUserPOJO) getIntent().getSerializableExtra("adminPOJO");

        rl_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPatientDialog();
            }
        });
        rl_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,StaffActivity.class));
            }
        });
        rl_therapist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,TherapistActivity.class));
            }
        });
        rl_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,PatientAmountActivity.class));
            }
        });
        rl_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,AddExpenseActivity.class));
            }
        });
        rl_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,BranchListActivity.class));
            }
        });
        rl_treatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,TreatmentListActivity.class));
            }
        });
        rl_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,ViewReportActivity.class));
            }
        });
        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_ADMIN_LOGIN,false);
                startActivity(new Intent(AdminDashboardActivity.this,SplashActivity.class));
                finishAffinity();
            }
        });

        rl_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,AppointmentActivity.class));
            }
        });

        rl_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboardActivity.this,AllUsersChatActivity.class).putExtra("adminPOJO",adminUserPOJO));
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
                startActivity(new Intent(AdminDashboardActivity.this, PatientListActivity.class).putExtra("treatment_type","2"));
                dialog.dismiss();
            }
        });

        btn_opd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, PatientListActivity.class).putExtra("treatment_type","1"));
                dialog.dismiss();
            }
        });

        btn_online_paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, PatientListActivity.class).putExtra("treatment_type","3"));
                dialog.dismiss();
            }
        });

        btn_online_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDashboardActivity.this, PatientListActivity.class).putExtra("treatment_type","4"));
                dialog.dismiss();
            }
        });


        dialog.show();
    }
}
