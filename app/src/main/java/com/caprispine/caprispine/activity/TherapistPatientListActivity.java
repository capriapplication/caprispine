package com.caprispine.caprispine.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.adapter.PatientListAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.caprispine.caprispine.webservice.ResponseListCallback;
import com.caprispine.caprispine.webservice.ResponseListWebservice;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TherapistPatientListActivity extends AppCompatActivity {

    @BindView(R.id.rv_patients)
    RecyclerView rv_patients;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    TherapistPOJO therapistPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_patient_list);
        ButterKnife.bind(this);
        therapistPOJO= (TherapistPOJO) getIntent().getSerializableExtra("therapist");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        attachAdapter();
        getAllocatedPatients();
    }

    public void getAllocatedPatients() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_allocated_patient"));
        nameValuePairs.add(new BasicNameValuePair("therapist_id", therapistPOJO.getId()));
        new ResponseListWebservice<PatientPOJO>(nameValuePairs, this, new ResponseListCallback<PatientPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PatientPOJO> responseListPOJO) {
                patientList.clear();
                try {
                    patientList.addAll(responseListPOJO.getResultList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                patientListAdapter.notifyDataSetChanged();
            }
        }, PatientPOJO.class, "GET_ALLOCATED_PATIENTS", true).execute(WebServicesUrls.PATIENT_ALLOCATION_CRUD);
    }


    PatientListAdapter patientListAdapter;
    List<PatientPOJO> patientList = new ArrayList<>();

    public void attachAdapter() {

        patientListAdapter = new PatientListAdapter(this, null, patientList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_patients.setHasFixedSize(true);
        rv_patients.setAdapter(patientListAdapter);
        rv_patients.setLayoutManager(layoutManager);
        rv_patients.setNestedScrollingEnabled(false);
        rv_patients.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
