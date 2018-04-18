package com.caprispine.caprispine.activity.assessment.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.pojo.patientassessment.ChiefComplaintPOJO;
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

public class AddChiefComplaintActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.act_chief_complaint)
    AutoCompleteTextView act_chief_complaint;
    @BindView(R.id.et_how_long)
    EditText et_how_long;
    @BindView(R.id.radio_group)
    RadioGroup radio_group;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.radio_yes)
    RadioButton radio_yes;
    @BindView(R.id.radio_no)
    RadioButton radio_no;

    PatientPOJO patientPOJO;

    ChiefComplaintPOJO chiefComplaintPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chief_complaint);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        chiefComplaintPOJO= (ChiefComplaintPOJO) getIntent().getSerializableExtra("pojo");
        if(chiefComplaintPOJO!=null){
            btn_save.setText("Update");
            setValues();
        }else{
            btn_save.setText("Add");
        }


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertComplaint();
            }
        });

    }

    public void setValues(){
        et_how_long.setText(chiefComplaintPOJO.getProblemDuration());
        act_chief_complaint.setText(chiefComplaintPOJO.getComplaints());
        if(chiefComplaintPOJO.getProblemBefore().equals("yes")){
            radio_no.setChecked(false);
            radio_yes.setChecked(true);
        }else{
            radio_yes.setChecked(false);
            radio_no.setChecked(true);
        }
    }

    public void insertComplaint(){

        String problem_before="";
        if(radio_group.getCheckedRadioButtonId()==R.id.radio_yes){
            problem_before="yes";
        }else{
            problem_before="false";
        }

        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("patient_id",patientPOJO.getId()));
        nameValuePairs.add(new BasicNameValuePair("complaints",act_chief_complaint.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("problem_duration",et_how_long.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("problem_before",problem_before));

        if(chiefComplaintPOJO!=null){
            nameValuePairs.add(new BasicNameValuePair("request_action","update_complaint"));
            nameValuePairs.add(new BasicNameValuePair("id",chiefComplaintPOJO.getId()));
            nameValuePairs.add(new BasicNameValuePair("date", chiefComplaintPOJO.getDate()));

        }else{
            nameValuePairs.add(new BasicNameValuePair("date", UtilityFunction.getCurrentDate()));
            nameValuePairs.add(new BasicNameValuePair("request_action","insert_complaint"));
        }

        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                Log.d(TagUtils.getTag(),msg[1]);
                try{
                    JSONObject jsonObject=new JSONObject(msg[1]);
                    if(jsonObject.optString("success").equals("true")){
                        if(chiefComplaintPOJO!=null){
                            ToastClass.showShortToast(getApplicationContext(),"Complaint Updated");
                        }else{
                            ToastClass.showShortToast(getApplicationContext(),"Complaint Added");
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result","");
                            setResult(Activity.RESULT_OK,returnIntent);
                        }

                        finish();
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),"Failed to add complaint");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "",false).execute(WebServicesUrls.CHIEF_COMPLAINT_CRUD);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
