package com.caprispine.caprispine.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.adapter.AppointmentAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.appointment.AppointmentPOJO;
import com.caprispine.caprispine.pojo.branch.BranchPOJO;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.caprispine.caprispine.webservice.ResponseListCallback;
import com.caprispine.caprispine.webservice.ResponseListWebservice;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageAppointmentActivity extends AppCompatActivity {
    @BindView(R.id.rv_manage_appointment)
    RecyclerView rv_manage_appointment;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.spinner_doctor)
    Spinner spinner_doctor;
    @BindView(R.id.ll_doctors)
    LinearLayout ll_doctors;
    @BindView(R.id.ll_branch)
    LinearLayout ll_branch;

    String branch_code="";
    String patient_id="";
    String therapist_id="";

    StaffPOJO staffPOJO;
    TherapistPOJO therapistPOJO;
    PatientPOJO patientPOJO;

    String user_type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_appointment);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("All Appointments");

        staffPOJO= (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");
        therapistPOJO= (TherapistPOJO) getIntent().getSerializableExtra("therapistPOJO");
        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");


        if(patientPOJO!=null){
            user_type="0";
        }else if(therapistPOJO!=null){
            user_type="2";
        }else if(staffPOJO!=null){
            user_type="1";
        }

        attachAdapter();

        if(patientPOJO!=null){
            user_type="0";
            branch_code=patientPOJO.getBranchId();
            ll_branch.setVisibility(View.GONE);
            ll_doctors.setVisibility(View.GONE);
            patient_id=patientPOJO.getId();
            getAllAppointments();
        }else if(therapistPOJO!=null){
            user_type="2";
            branch_code=therapistPOJO.getBranchId();
            therapist_id=therapistPOJO.getId();
            ll_doctors.setVisibility(View.GONE);
            ll_branch.setVisibility(View.GONE);
            getAllAppointments();
        }else if(staffPOJO!=null){
            user_type="1";
            branch_code=staffPOJO.getBranchId();
            ll_branch.setVisibility(View.GONE);
            getbranchdoctors(branch_code);
        }else{
            getAllBranches();
        }

    }



    public void getAllBranches() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "all_branch"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                parseAllBranch(msg[1]);
            }
        }, "CALL_GET_ALL_BRANCH", true).execute(WebServicesUrls.BRANCH_CRUD);
    }


    List<BranchResultPOJO> branchResultPOJOS = new ArrayList<>();

    public void parseAllBranch(String response) {
        try {
            Gson gson = new Gson();
            BranchPOJO branchPOJO = gson.fromJson(response, BranchPOJO.class);
            if (branchPOJO.getSuccess().equals("true")) {
                branchResultPOJOS.clear();
                branchResultPOJOS.addAll(branchPOJO.getBranchResultPOJOS());
                List<String> braStringList = new ArrayList<>();
                for (BranchResultPOJO branchResultPOJO : branchPOJO.getBranchResultPOJOS()) {
                    braStringList.add(branchResultPOJO.getBranchName() + " (" + branchResultPOJO.getBranchCode() + ")");
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(), R.layout.dropsimpledown, braStringList);
                spinner_branch.setAdapter(spinnerArrayAdapter);

                if (branchResultPOJOS.size() > 0) {
                    branch_code = branchResultPOJOS.get(0).getBranchId();
                }

                spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        branch_code = branchResultPOJOS.get(position).getBranchId();
                        Log.d(TagUtils.getTag(), "called");
                        getbranchdoctors(branch_code);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    List<TherapistPOJO> doctorResultPOJOList = new ArrayList<>();

    private void getbranchdoctors(String branch_code) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_all_therapist"));
        nameValuePairs.add(new BasicNameValuePair("branch_id", branch_code));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try {
                    ResponseListPOJO<TherapistPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<TherapistPOJO>>() {
                    }.getType());
                    doctorResultPOJOList.clear();
                    if (responseListPOJO.isSuccess()) {
                        doctorResultPOJOList.addAll(responseListPOJO.getResultList());

                        List<String> therapistStringList = new ArrayList<>();
                        for (TherapistPOJO therapistPOJO : doctorResultPOJOList) {
                            therapistStringList.add(therapistPOJO.getFirstName() + " " + therapistPOJO.getLastName());
                        }

                        if (doctorResultPOJOList.size() > 0) {
                            therapist_id = doctorResultPOJOList.get(0).getId();
                            getAllAppointments();
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, therapistStringList);
                        spinner_doctor.setAdapter(spinnerArrayAdapter);

                        spinner_doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                therapist_id = doctorResultPOJOList.get(spinner_doctor.getSelectedItemPosition()).getId();
                                getAllAppointments();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                        List<String> therapistStringList = new ArrayList<>();
                        for (TherapistPOJO therapistPOJO : doctorResultPOJOList) {
                            therapistStringList.add(therapistPOJO.getFirstName() + " " + therapistPOJO.getLastName());
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, therapistStringList);
                        spinner_doctor.setAdapter(spinnerArrayAdapter);
                        therapist_id = "";
                        patient_id="";
                        getAllAppointments();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "GET_DOCTORS_API", true).execute(WebServicesUrls.USER_CRUD);
    }


    public void getAllAppointments(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","get_appointment_list"));
        nameValuePairs.add(new BasicNameValuePair("therapist_id",therapist_id));
        nameValuePairs.add(new BasicNameValuePair("patient_id",patient_id));
        new ResponseListWebservice<AppointmentPOJO>(nameValuePairs, this, new ResponseListCallback<AppointmentPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<AppointmentPOJO> responseListPOJO) {
                appointmentPOJOS.clear();
                if(responseListPOJO.isSuccess()) {
                    appointmentPOJOS.addAll(responseListPOJO.getResultList());
                }
                adapter.notifyDataSetChanged();
            }
        },AppointmentPOJO.class,"CALL_PATIENTS_APPOINTMENT",true).execute(WebServicesUrls.APPOINTMENT_CRUD);
    }


    AppointmentAdapter adapter;
    List<AppointmentPOJO> appointmentPOJOS=new ArrayList<>();

    public void attachAdapter() {

        adapter = new AppointmentAdapter(this,appointmentPOJOS,user_type);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_manage_appointment.setHasFixedSize(true);
        rv_manage_appointment.setAdapter(adapter);
        rv_manage_appointment.setLayoutManager(layoutManager);
        rv_manage_appointment.setNestedScrollingEnabled(false);
        rv_manage_appointment.setItemAnimator(new DefaultItemAnimator());
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
