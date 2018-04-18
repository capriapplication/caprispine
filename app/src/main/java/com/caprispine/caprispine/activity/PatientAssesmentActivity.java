package com.caprispine.caprispine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.caprispine.caprispine.R;
import com.caprispine.caprispine.adapter.PatientAssessmentAdpater;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PatientAssesmentActivity extends AppCompatActivity {

    @BindView(R.id.rv_patient_assessment)
    RecyclerView rv_patient_assessment;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_email)
    TextView tv_email;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_patient_code)
    TextView tv_patient_code;
    @BindView(R.id.img_profile)
    CircleImageView img_profile;
    @BindView(R.id.iv_edit)
    ImageView iv_edit;
    @BindView(R.id.txt_age)
    TextView txt_age;
    @BindView(R.id.tv_weight)
    TextView tv_weight;
    @BindView(R.id.tv_height)
    TextView tv_height;

    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_assesment);
        ButterKnife.bind(this);

        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        if (patientPOJO != null) {
            setValues();
        } else {
            finish();
        }

        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientAssesmentActivity.this,CreatePatientActivity.class).putExtra("patientPOJO",patientPOJO));
            }
        });

        attachAdapter();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPatient();
    }

    public void getPatient(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","get_patient_by_id"));
        nameValuePairs.add(new BasicNameValuePair("patient_id",patientPOJO.getId()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    JSONObject jsonObject=new JSONObject(msg[1]);
                    if(jsonObject.optBoolean("success")){
                        PatientPOJO patientPOJO1=new Gson().fromJson(jsonObject.optJSONObject("result").toString(),PatientPOJO.class);
                        patientPOJO=patientPOJO1;
//                        try {
//                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat()
//                            txt_age.setText(UtilityFunction.getAge());
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
                        tv_height.setText(patientPOJO1.getHeight()+" cm");
                        tv_weight.setText(patientPOJO1.getWeight()+" KG");

                        tv_name.setText(patientPOJO.getFirstName()+" "+patientPOJO.getLastName());
                        tv_email.setText(patientPOJO.getEmail());
                        tv_phone.setText(patientPOJO.getMobile());
                        tv_patient_code.setText(patientPOJO.getPatientCode());
                        Glide.with(getApplicationContext())
                                .load(WebServicesUrls.PROFILE_PIC_BASE_URL+patientPOJO.getProfilePic())
                                .error(R.drawable.ic_action_person)
                                .placeholder(R.drawable.ic_action_person)
                                .dontAnimate()
                                .into(img_profile);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"GET_PATIENT_DATA",true).execute(WebServicesUrls.USER_CRUD);
    }

    public void setValues() {
        tv_name.setText(patientPOJO.getFirstName()+" "+patientPOJO.getLastName());
        tv_email.setText(patientPOJO.getEmail());
        tv_phone.setText(patientPOJO.getMobile());
        tv_patient_code.setText(patientPOJO.getPatientCode());
        Glide.with(getApplicationContext())
                .load(patientPOJO.getProfilePic())
                .error(R.drawable.ic_action_person)
                .placeholder(R.drawable.ic_action_person)
                .dontAnimate()
                .into(img_profile);
    }

    PatientAssessmentAdpater patientAssessmentAdpater;
    List<String> assessmentList;

    public void attachAdapter() {

        assessmentList = Arrays.asList(getResources().getStringArray(R.array.menu_myclinic_patient));

        patientAssessmentAdpater = new PatientAssessmentAdpater(this, null, assessmentList,patientPOJO);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_patient_assessment.setHasFixedSize(true);
        rv_patient_assessment.setAdapter(patientAssessmentAdpater);
        rv_patient_assessment.setLayoutManager(layoutManager);
        rv_patient_assessment.setNestedScrollingEnabled(false);
        rv_patient_assessment.setItemAnimator(new DefaultItemAnimator());
    }
}
