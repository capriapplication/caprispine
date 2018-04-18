package com.caprispine.caprispine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.adapter.PatientListAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.webservice.ResponseListCallback;
import com.caprispine.caprispine.webservice.ResponseListWebservice;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_search)
    EditText et_search;

    @BindView(R.id.iv_search_close)
    ImageView iv_search_close;
    @BindView(R.id.rv_patient)
    RecyclerView rv_patient;
    String treatment_type="";

    StaffPOJO staffPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        ButterKnife.bind(this);
        staffPOJO= (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Patient List");

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            treatment_type=bundle.getString("treatment_type");
        }

        attachAdapter();

        iv_search_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_search.getText().toString().length() > 0) {
                    iv_search_close.setVisibility(View.VISIBLE);
                    getAllPatients(et_search.getText().toString());
                } else {
                    iv_search_close.setVisibility(View.GONE);
                }
            }
        });

        if(staffPOJO!=null){
            branch_id=staffPOJO.getBranchId();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllPatients("");
    }

    String branch_id="";
    public void getAllPatients(String search_string) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "search_patient"));
        nameValuePairs.add(new BasicNameValuePair("string", search_string));
        nameValuePairs.add(new BasicNameValuePair("branch_id", branch_id));
        nameValuePairs.add(new BasicNameValuePair("treatment_type", treatment_type));

        new ResponseListWebservice<PatientPOJO>(nameValuePairs, getApplicationContext(), new ResponseListCallback<PatientPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PatientPOJO> responseListPOJO) {
                patientList.clear();
                if (responseListPOJO.isSuccess()) {
                    patientList.addAll(responseListPOJO.getResultList());
                } else {
                    ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                }
                patientListAdapter.notifyDataSetChanged();
            }
        }, PatientPOJO.class,"GET_PATIENT_LIST", false).execute(WebServicesUrls.USER_CRUD);

//        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
//            @Override
//            public void onGetMsg(String[] msg) {
//                ResponseListPOJO<PatientPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<PatientPOJO>>() {
//                }.getType());
//                if (responseListPOJO.isSuccess()) {
//                    patientList = new ArrayList<>(responseListPOJO.getResultList());
//                    attachAdapter();
//                    Log.d(TagUtils.getTag(), "patient list:-" + patientList.toString());
//                } else {
//                    ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
//                }
//            }
//        }, "GET_PATIENTS", true).execute(WebServicesUrls.USER_CRUD);

    }

    PatientListAdapter patientListAdapter;
    List<PatientPOJO> patientList = new ArrayList<>();

    public void attachAdapter() {

        patientListAdapter = new PatientListAdapter(this, null, patientList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_patient.setHasFixedSize(true);
        rv_patient.setAdapter(patientListAdapter);
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
                startActivity(new Intent(PatientListActivity.this,CreatePatientActivity.class));
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
