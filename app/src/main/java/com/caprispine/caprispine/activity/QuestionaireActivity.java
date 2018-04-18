package com.caprispine.caprispine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.NVP;
import com.caprispine.caprispine.Util.UtilityFunction;
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

public class QuestionaireActivity extends AppCompatActivity {

    private static final String CALL_SAVE_CHIEF_COMPLAINTS = "call_save_chief_complaints";
    @BindView(R.id.btn_submit)
    Button btn_submit;
    String patient_id = "";
//    String problems = "";

    @BindView(R.id.et_complaint)
    EditText et_complaint;
    @BindView(R.id.et_problem_duration)
    EditText et_problem_duration;
    @BindView(R.id.rg_problem_before)
    RadioGroup rg_problem_before;
    @BindView(R.id.rb_problem_yes)
    RadioButton rb_problem_yes;
    @BindView(R.id.rb_problem_no)
    RadioButton rb_problem_no;
    @BindView(R.id.et_cause)
    EditText et_cause;
    @BindView(R.id.et_problem_suffering)
    EditText et_problem_suffering;
    @BindView(R.id.et_surgery)
    EditText et_surgery;
    @BindView(R.id.et_present_medicine)
    EditText et_present_medicine;
    ArrayList<NVP> nameValuePairs = new ArrayList<>();
    String problem_before = "no";
    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);
        ButterKnife.bind(this);


        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");

        if (patientPOJO != null) {
            patient_id = patientPOJO.getId();
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameValuePairs.clear();
                getData();
                saveChiefComplaints();
            }
        });

        rg_problem_before.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = findViewById(rg_problem_before.getCheckedRadioButtonId());
                if (radioButton.getText().toString().equalsIgnoreCase("Yes")) {
                    problem_before = "yes";
                } else {
                    problem_before = "no";
                }
            }
        });
    }

    private void saveChiefComplaints() {

        ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("request_action", "insert_complaint"));
        nameValuePairArrayList.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));
        nameValuePairArrayList.add(new BasicNameValuePair("complaints", et_complaint.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("problem_duration", et_problem_duration.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("problem_before", problem_before));
        nameValuePairArrayList.add(new BasicNameValuePair("date", UtilityFunction.getCurrentDate()));

        new WebServiceBase(nameValuePairArrayList, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    JSONObject jsonObject=new JSONObject(msg[1]);
                    if(jsonObject.optString("success").equals("true")){
                        Intent intent = new Intent(QuestionaireActivity.this, HabitHistoryActivity.class);
                        intent.putExtra("patientPOJO", patientPOJO);
                        intent.putExtra("params", nameValuePairs);
                        startActivityForResult(intent,1);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "CALL_SAVE_CHIEF_COMPLAINTS", true).execute(WebServicesUrls.CHIEF_COMPLAINT_CRUD);
    }


    public void getData() {
        nameValuePairs.add(new NVP("problem_cause", et_cause.getText().toString()));
        nameValuePairs.add(new NVP("medical_problem", et_problem_suffering.getText().toString()));
        nameValuePairs.add(new NVP("any_surgery", et_surgery.getText().toString()));
        nameValuePairs.add(new NVP("present_treatment", et_present_medicine.getText().toString()));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
//                String result=data.getStringExtra("result");
//                Intent returnIntent = new Intent();
//                returnIntent.putExtra("result",result);
//                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
