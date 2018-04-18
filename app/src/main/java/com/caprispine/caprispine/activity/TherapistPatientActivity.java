package com.caprispine.caprispine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.adapter.AllocatePatientAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.user.AllocatePatientPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.caprispine.caprispine.webservice.ResponseListCallback;
import com.caprispine.caprispine.webservice.ResponseListWebservice;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TherapistPatientActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_patient)
    RecyclerView rv_patient;
    @BindView(R.id.btn_unallocate)
    Button btn_unallocate;
    TherapistPOJO therapistPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapist_patient);
        ButterKnife.bind(this);

        therapistPOJO = (TherapistPOJO) getIntent().getSerializableExtra("therapist");

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        attachAdapter();

        btn_unallocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJsonData();
            }
        });
    }


    public void getJsonData() {
        try {
            String jsonData = "";
            JSONObject mainJson = new JSONObject();
            final JSONArray jsonArray = new JSONArray();
            for (AllocatePatientPOJO allocatePatientPOJO : allocatePatientPOJOS) {

                if (allocatePatientPOJO.isChecked()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("patient_id", allocatePatientPOJO.getId());

                    jsonArray.put(jsonObject);
                }

            }
            if (jsonArray.length() > 0) {
                mainJson.put("success", true);
                mainJson.put("result", jsonArray);

                ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("request_action", "unallocate_patient"));
                nameValuePairs.add(new BasicNameValuePair("json_data", mainJson.toString()));
                new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String[] msg) {
                        try {
                            JSONObject jsonObject = new JSONObject(msg[1]);
                            if (jsonObject.optBoolean("success")) {
                                onResume();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "INSERT_PATIENT_DATA", true).execute(WebServicesUrls.PATIENT_ALLOCATION_CRUD);

            } else {
                mainJson.put("success", false);
                ToastClass.showShortToast(getApplicationContext(), "Please Select Patients");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAllocatedPatients();
    }

    public void getAllocatedPatients() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_allocated_patient"));
        nameValuePairs.add(new BasicNameValuePair("therapist_id", therapistPOJO.getId()));
        new ResponseListWebservice<AllocatePatientPOJO>(nameValuePairs, this, new ResponseListCallback<AllocatePatientPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<AllocatePatientPOJO> responseListPOJO) {
                allocatePatientPOJOS.clear();
                try {
                    allocatePatientPOJOS.addAll(responseListPOJO.getResultList());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        }, AllocatePatientPOJO.class, "GET_ALLOCATED_PATIENTS", true).execute(WebServicesUrls.PATIENT_ALLOCATION_CRUD);
    }


    AllocatePatientAdapter adapter;
    List<AllocatePatientPOJO> allocatePatientPOJOS = new ArrayList<>();

    public void attachAdapter() {
        adapter = new AllocatePatientAdapter(this, null, allocatePatientPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_patient.setHasFixedSize(true);
        rv_patient.setAdapter(adapter);
        rv_patient.setLayoutManager(layoutManager);
        rv_patient.setNestedScrollingEnabled(false);
        rv_patient.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_amount:
                startActivity(new Intent(TherapistPatientActivity.this, AllocatePatientsActivity.class).putExtra("therapist", therapistPOJO));
                return true;
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

}
