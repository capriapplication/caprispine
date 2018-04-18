package com.caprispine.caprispine.activity.assessment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.Constants;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.activity.assessment.add.AddPhysicalActivity;
import com.caprispine.caprispine.adapter.patientassessment.PhysicalAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.patientassessment.PhysicalPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
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

public class PhysicalActivity extends AppCompatActivity {

    @BindView(R.id.rv_complaint)
    RecyclerView rv_complaint;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.btn_skip)
    Button btn_skip;
    PatientPOJO patientPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chief_complaint);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Physical Exam");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        attachAdapter();

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PhysicalActivity.this,MotorExamActivity.class).putExtra("patientPOJO",patientPOJO));
                finish();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(PhysicalActivity.this, AddPhysicalActivity.class).putExtra("patientPOJO",patientPOJO),Constants.EXAM_START_ACTIVITY_INTENT);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(call_api) {
            getAllComplaints();
        }
    }

    public void getAllComplaints() {
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","get_all_complaint"));
        nameValuePairs.add(new BasicNameValuePair("patient_id",patientPOJO.getId()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                Log.d(TagUtils.getTag(),msg[0]+":-"+msg[1]);
                try{
                    ResponseListPOJO<PhysicalPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<PhysicalPOJO>>() {}.getType());
                    physicalPOJOS.clear();
                    if(responseListPOJO.isSuccess()){
                        physicalPOJOS.addAll(responseListPOJO.getResultList());
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),responseListPOJO.getMessage());
                    }
                    adapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "GET_ALL_PHYSICAL",false).execute(WebServicesUrls.PHYSICAL_CRUD);
    }


    boolean call_api = true;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.EXAM_START_ACTIVITY_INTENT) {
            if (resultCode == Activity.RESULT_OK) {
                call_api = false;
                btn_skip.callOnClick();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateComplaint(PhysicalPOJO physicalPOJO){
        Intent intent=new Intent(this,AddPhysicalActivity.class);
        intent.putExtra("pojo",physicalPOJO);
        intent.putExtra("patientPOJO",patientPOJO);
        startActivity(intent);
    }

    PhysicalAdapter adapter;
    List<PhysicalPOJO> physicalPOJOS=new ArrayList<>();

    public void attachAdapter() {

        adapter = new PhysicalAdapter(this,null, physicalPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_complaint.setHasFixedSize(true);
        rv_complaint.setAdapter(adapter);
        rv_complaint.setLayoutManager(layoutManager);
        rv_complaint.setNestedScrollingEnabled(false);
        rv_complaint.setItemAnimator(new DefaultItemAnimator());
    }
}
