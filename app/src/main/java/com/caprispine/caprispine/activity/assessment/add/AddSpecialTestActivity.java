package com.caprispine.caprispine.activity.assessment.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.pojo.patientassessment.SpecialExamPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSpecialTestActivity extends AppCompatActivity {
    //    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.et_special_test)
    EditText et_special_test;
    @BindView(R.id.Description1)
    EditText et_special_description;

    @BindView(R.id.btn_save)
    Button btn_save;
    PatientPOJO patientPOJO;
    SpecialExamPOJO specialExamPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_special_test);
        ButterKnife.bind(this);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Add NDT/NTP Exam");
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        specialExamPOJO= (SpecialExamPOJO) getIntent().getSerializableExtra("specialExamPOJO");

        if(specialExamPOJO!=null){
            btn_save.setText("Update");
            setValues();
        }else{
            btn_save.setText("Save");
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExam();
            }
        });
    }

    public void setValues(){
        et_special_description.setText(specialExamPOJO.getDescription());
        et_special_test.setText(specialExamPOJO.getSpecialTests());
    }


    public void addExam() {
        ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();

        nameValuePairArrayList.add(new BasicNameValuePair("special_tests", et_special_test.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("description", et_special_description.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));

        if(specialExamPOJO!=null){
            nameValuePairArrayList.add(new BasicNameValuePair("request_action", "update_complaint"));
            nameValuePairArrayList.add(new BasicNameValuePair("date", specialExamPOJO.getDate()));
            nameValuePairArrayList.add(new BasicNameValuePair("id", specialExamPOJO.getId()));
        }else{
            nameValuePairArrayList.add(new BasicNameValuePair("request_action", "insert_complaint"));
            nameValuePairArrayList.add(new BasicNameValuePair("date", UtilityFunction.getCurrentDate()));
        }

        new WebServiceBase(nameValuePairArrayList, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    JSONObject jsonObject=new JSONObject(msg[1]);
                    if(jsonObject.optBoolean("success")){
                        if(specialExamPOJO!=null){
                            ToastClass.showShortToast(getApplicationContext(),"Special Exam Updated");
                        }else{
                            ToastClass.showShortToast(getApplicationContext(),"Special Exam Added");
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result","");
                            setResult(Activity.RESULT_OK,returnIntent);
                        }
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "CALL_SPECIAL_EXAM", true).execute(WebServicesUrls.SPECIAL_CRUD);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
