package com.caprispine.caprispine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.caprispine.caprispine.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StaffActivity extends AppCompatActivity {

    @BindView(R.id.cv_add_staff)
    CardView cv_add_staff;
    @BindView(R.id.cv_manage_staff)
    CardView cv_manage_staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        ButterKnife.bind(this);


        cv_add_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StaffActivity.this,CreateStaffActivity.class));
            }
        });

        cv_manage_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StaffActivity.this,ManageStaffActivity.class));
            }
        });
    }
}
