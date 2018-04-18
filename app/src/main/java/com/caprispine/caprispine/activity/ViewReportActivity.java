package com.caprispine.caprispine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.Pref;
import com.caprispine.caprispine.Util.StringUtils;
import com.caprispine.caprispine.activity.report.IncomePatientWise;
import com.caprispine.caprispine.activity.report.NotesPrintActivity;
import com.caprispine.caprispine.activity.report.ViewCaseReportActivity;
import com.caprispine.caprispine.activity.report.ViewExpenseReport;
import com.caprispine.caprispine.activity.report.ViewIncomeReportActivity;
import com.caprispine.caprispine.activity.report.ViewIncomeTreatmentwiseReport;
import com.caprispine.caprispine.activity.report.ViewPatientWalletReportActivity;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewReportActivity extends AppCompatActivity {
    String[] IteamList;

    @BindView(R.id.list_report)
    ListView list_report;

    TherapistPOJO therapistPOJO;
    StaffPOJO staffPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        ButterKnife.bind(this);

        therapistPOJO= (TherapistPOJO) getIntent().getSerializableExtra("therapistPOJO");
        staffPOJO= (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");
//        IteamList = new String[]{"Case Report", "Income Statement", "Income (Treatment Wise)", "Income (Therapist Wise)", "Income (Patient Wise)", "Expense Report", "Patient Wallet Report", "Case Notes Report", "Progress Report", "Remark Report", "Income Reference By"};
        if(Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_ADMIN_LOGIN,false)) {
            IteamList = new String[]{"Case Report", "Income Statement", "Income (Treatment Wise)", "Income (Therapist Wise)", "Income (Patient Wise)", "Expense Report", "Patient Wallet Report", "Case Notes Report", "Progress Report", "Remark Report"};
        }else if(Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_THERAPIST_LOGIN,false)){
            IteamList = new String[]{"Case Report", "Income", "Income (Patient Wise)"};
        }else if(Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_STAFF_LOGIN,false)){
            IteamList = new String[]{"Case Report", "Income Statement", "Income (Treatment Wise)", "Income (Therapist Wise)", "Income (Patient Wise)", "Expense Report", "Patient Wallet Report", "Case Notes Report", "Progress Report", "Remark Report"};
        }

        list_report.setAdapter(new customAdapter());
    }

    public class customAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return IteamList.length;
        }

        @Override
        public Object getItem(int position) {
            return IteamList;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            convertView = inflater.inflate(R.layout.inflate_report_item, null);
            TextView text_listitem = (TextView) convertView.findViewById(R.id.text_listitem);
            ImageView image_iteam = (ImageView) convertView.findViewById(R.id.image_pic);
            text_listitem.setText(IteamList[position]);
            convertView.setTag(position);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_ADMIN_LOGIN,false)) {
                        openAdminReports(position);
                    }else if(Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_THERAPIST_LOGIN,false)){
                        openTherapistReport(position);
                    }else if(Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_STAFF_LOGIN,false)){
                        openStaffReport(position);
                    }
                }
            });
            return convertView;
        }
    }

    public void openTherapistReport(int position){
        Intent intent;
        if (position == 0) {
            startActivity(new Intent(this, ViewCaseReportActivity.class).putExtra("therapistPOJO",therapistPOJO));
        } else if (position == 1) {
            startActivity(new Intent(this, ViewIncomeTherapistWise.class).putExtra("therapistPOJO",therapistPOJO));
        } else if (position == 2) {
            startActivity(new Intent(this, IncomePatientWise.class).putExtra("therapistPOJO",therapistPOJO));
        }
    }

    public void openAdminReports(int position) {
        Intent intent;
        if (position == 0) {
            startActivity(new Intent(this, ViewCaseReportActivity.class));
        } else if (position == 1) {
            startActivity(new Intent(this, ViewIncomeReportActivity.class));
        } else if (position == 2) {
            startActivity(new Intent(this, ViewIncomeTreatmentwiseReport.class));
        } else if (position == 3) {
            startActivity(new Intent(this, ViewIncomeTherapistWise.class));
        } else if (position == 4) {
            startActivity(new Intent(this, IncomePatientWise.class));
        } else if (position == 5) {
            startActivity(new Intent(this, ViewExpenseReport.class));
        } else if (position == 6) {
            startActivity(new Intent(this, ViewPatientWalletReportActivity.class));
        } else if (position == 7) {
            intent = new Intent(this, NotesPrintActivity.class);
            intent.putExtra("notetype", "case");
            startActivity(intent);
        } else if (position == 8) {
            intent = new Intent(this, NotesPrintActivity.class);
            intent.putExtra("notetype", "progress");
            startActivity(intent);
        } else if (position == 9) {
            intent = new Intent(this, NotesPrintActivity.class);
            intent.putExtra("notetype", "remark");
            startActivity(intent);
        }
    }

    public void openStaffReport(int position){
        Intent intent;
        if (position == 0) {
            startActivity(new Intent(this, ViewCaseReportActivity.class).putExtra("staffPOJO",staffPOJO));
        } else if (position == 1) {
            startActivity(new Intent(this, ViewIncomeReportActivity.class).putExtra("staffPOJO",staffPOJO));
        } else if (position == 2) {
            startActivity(new Intent(this, ViewIncomeTreatmentwiseReport.class).putExtra("staffPOJO",staffPOJO));
        } else if (position == 3) {
            startActivity(new Intent(this, ViewIncomeTherapistWise.class).putExtra("staffPOJO",staffPOJO));
        } else if (position == 4) {
            startActivity(new Intent(this, IncomePatientWise.class).putExtra("staffPOJO",staffPOJO));
        } else if (position == 5) {
            startActivity(new Intent(this, ViewExpenseReport.class).putExtra("staffPOJO",staffPOJO));
        } else if (position == 6) {
            startActivity(new Intent(this, ViewPatientWalletReportActivity.class).putExtra("staffPOJO",staffPOJO));
        } else if (position == 7) {
            intent = new Intent(this, NotesPrintActivity.class);
            intent.putExtra("notetype", "case");
            intent.putExtra("staffPOJO",staffPOJO);
            startActivity(intent);
        } else if (position == 8) {
            intent = new Intent(this, NotesPrintActivity.class);
            intent.putExtra("notetype", "progress");
            intent.putExtra("staffPOJO",staffPOJO);
            startActivity(intent);
        } else if (position == 9) {
            intent = new Intent(this, NotesPrintActivity.class);
            intent.putExtra("notetype", "remark");
            intent.putExtra("staffPOJO",staffPOJO);
            startActivity(intent);
        }
    }

}
