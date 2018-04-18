package com.caprispine.caprispine.activity.assessment.add;

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
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.activity.assessment.PainActivity;
import com.caprispine.caprispine.pojo.ResponsePOJO;
import com.caprispine.caprispine.pojo.patientassessment.HistoryPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddHistoryActivity extends AppCompatActivity {

    @BindView(R.id.et_cause)
    EditText et_cause;
    @BindView(R.id.et_problem_suffering)
    EditText et_problem_suffering;
    @BindView(R.id.et_present_medicine)
    EditText et_present_medicine;
    @BindView(R.id.et_surgery)
    EditText et_surgery;
    @BindView(R.id.rg_smoking)
    RadioGroup rg_smoking;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rg_alcholic)
    RadioGroup rg_alcholic;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb4)
    RadioButton rb4;
    @BindView(R.id.rg_fever)
    RadioGroup rg_fever;
    @BindView(R.id.rb5)
    RadioButton rb5;
    @BindView(R.id.rb6)
    RadioButton rb6;
    @BindView(R.id.rg_diabetes)
    RadioGroup rg_diabetes;
    @BindView(R.id.rg7)
    RadioButton rg7;
    @BindView(R.id.rg8)
    RadioButton rg8;
    @BindView(R.id.rg_blood_pressure)
    RadioGroup rg_blood_pressure;
    @BindView(R.id.radio_Normal)
    RadioButton radio_Normal;
    @BindView(R.id.radio_Low)
    RadioButton radio_Low;
    @BindView(R.id.radio_High)
    RadioButton radio_High;
    @BindView(R.id.rg_heart)
    RadioGroup rg_heart;
    @BindView(R.id.rg9)
    RadioButton rg9;
    @BindView(R.id.rg10)
    RadioButton rg10;
    @BindView(R.id.rg_bleeding)
    RadioGroup rg_bleeding;
    @BindView(R.id.rg11)
    RadioButton rg11;
    @BindView(R.id.rg12)
    RadioButton rg12;
    @BindView(R.id.rg_infection)
    RadioGroup rg_infection;
    @BindView(R.id.rb13)
    RadioButton rb13;
    @BindView(R.id.rg14)
    RadioButton rg14;
    @BindView(R.id.rg_red_flag)
    RadioGroup rg_red_flag;
    @BindView(R.id.rb15)
    RadioButton rb15;
    @BindView(R.id.rb16)
    RadioButton rb16;
    @BindView(R.id.rg_yellow)
    RadioGroup rg_yellow;
    @BindView(R.id.rb_16)
    RadioButton rb_16;
    @BindView(R.id.rg17)
    RadioButton rg17;
    @BindView(R.id.rg_limitation)
    RadioGroup rg_limitation;
    @BindView(R.id.rb18)
    RadioButton rb18;
    @BindView(R.id.rb19)
    RadioButton rb19;
    @BindView(R.id.rg_past)
    RadioGroup rg_past;
    @BindView(R.id.rb_19)
    RadioButton rb_19;
    @BindView(R.id.rb20)
    RadioButton rb20;
    @BindView(R.id.rg_allergy)
    RadioGroup rg_allergy;
    @BindView(R.id.rb21)
    RadioButton rb21;
    @BindView(R.id.rb22)
    RadioButton rb22;
    @BindView(R.id.rg_oestrophic)
    RadioGroup rg_oestrophic;
    @BindView(R.id.radio_yes15)
    RadioButton radio_yes15;
    @BindView(R.id.radio_no15)
    RadioButton radio_no15;
    @BindView(R.id.rg_implants)
    RadioGroup rg_implants;
    @BindView(R.id.radio_yes18)
    RadioButton radio_yes18;
    @BindView(R.id.radio_no18)
    RadioButton radio_no18;
    @BindView(R.id.rg_herediatery)
    RadioGroup rg_herediatery;
    @BindView(R.id.radio_yes19)
    RadioButton radio_yes19;
    @BindView(R.id.radio_no19)
    RadioButton radio_no19;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.btn_save)
    Button btn_save;
    PatientPOJO patientPOJO;
    HistoryPOJO historyPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_history);
        ButterKnife.bind(this);
        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        historyPOJO= (HistoryPOJO) getIntent().getSerializableExtra("history");
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddHistoryActivity.this, PainActivity.class));
                finish();
            }
        });

        if(historyPOJO!=null){
            btn_save.setText("Update");
            setValues();
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHistory();
            }
        });
    }

    public void setValues(){
        et_cause.setText(historyPOJO.getProblemCause());
        et_problem_suffering.setText(historyPOJO.getMedicalProblem());
        et_surgery.setText(historyPOJO.getAnySurgery());
        et_present_medicine.setText(historyPOJO.getPresentTreatment());
        et_remark.setText(historyPOJO.getRemark());

        setRgValue(historyPOJO.getSmoking(),rg_smoking);
        setRgValue(historyPOJO.getAlcholic(),rg_alcholic);
        setRgValue(historyPOJO.getFeverChill(),rg_fever);
        setRgValue(historyPOJO.getDiabetes(),rg_diabetes);
        setRgValue(historyPOJO.getBloodPressure(),rg_blood_pressure);
        setRgValue(historyPOJO.getHeartDisease(),rg_heart);
        setRgValue(historyPOJO.getBleedingDisorder(),rg_bleeding);
        setRgValue(historyPOJO.getRecentInfection(),rg_infection);
        setRgValue(historyPOJO.getRedFlags(),rg_red_flag);
        setRgValue(historyPOJO.getYellowFlags(),rg_yellow);
        setRgValue(historyPOJO.getLimitations(),rg_limitation);
        setRgValue(historyPOJO.getPastSurgery(),rg_past);
        setRgValue(historyPOJO.getAllergy(),rg_allergy);
        setRgValue(historyPOJO.getOsteoporotic(),rg_oestrophic);
        setRgValue(historyPOJO.getAnyImplants(),rg_implants);
        setRgValue(historyPOJO.getHerediataryDisease(),rg_herediatery);



    }

    public void saveHistory(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("problem_cause",et_cause.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("medical_problem",et_problem_suffering.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("any_surgery",et_surgery.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("present_treatment",et_present_medicine.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("smoking",getRadioGroupValues(rg_smoking)));
        nameValuePairs.add(new BasicNameValuePair("alcholic",getRadioGroupValues(rg_alcholic)));
        nameValuePairs.add(new BasicNameValuePair("fever_chill",getRadioGroupValues(rg_fever)));
        nameValuePairs.add(new BasicNameValuePair("diabetes",getRadioGroupValues(rg_diabetes)));
        nameValuePairs.add(new BasicNameValuePair("blood_pressure",getRadioGroupValues(rg_blood_pressure)));
        nameValuePairs.add(new BasicNameValuePair("heart_disease",getRadioGroupValues(rg_heart)));
        nameValuePairs.add(new BasicNameValuePair("bleeding_disorder",getRadioGroupValues(rg_bleeding)));
        nameValuePairs.add(new BasicNameValuePair("recent_infection",getRadioGroupValues(rg_infection)));
        nameValuePairs.add(new BasicNameValuePair("red_flags",getRadioGroupValues(rg_red_flag)));
        nameValuePairs.add(new BasicNameValuePair("yellow_flags",getRadioGroupValues(rg_yellow)));
        nameValuePairs.add(new BasicNameValuePair("limitations",getRadioGroupValues(rg_limitation)));
        nameValuePairs.add(new BasicNameValuePair("past_surgery",getRadioGroupValues(rg_past)));
        nameValuePairs.add(new BasicNameValuePair("allergy",getRadioGroupValues(rg_allergy)));
        nameValuePairs.add(new BasicNameValuePair("osteoporotic",getRadioGroupValues(rg_oestrophic)));
        nameValuePairs.add(new BasicNameValuePair("any_implants",getRadioGroupValues(rg_implants)));
        nameValuePairs.add(new BasicNameValuePair("herediatary_disease",getRadioGroupValues(rg_herediatery)));
        nameValuePairs.add(new BasicNameValuePair("remark",et_remark.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("patient_id",patientPOJO.getId()));

        if(historyPOJO!=null){
            nameValuePairs.add(new BasicNameValuePair("request_action","update_complaint"));
            nameValuePairs.add(new BasicNameValuePair("date", historyPOJO.getDate()));
            nameValuePairs.add(new BasicNameValuePair("id", historyPOJO.getId()));
        }else{
            nameValuePairs.add(new BasicNameValuePair("request_action","insert_complaint"));
            nameValuePairs.add(new BasicNameValuePair("date", UtilityFunction.getCurrentDate()));
        }

        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    ResponsePOJO<HistoryPOJO> historyPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponsePOJO<HistoryPOJO>>() {}.getType());
                    if(historyPOJO.isSuccess()){
                        if(historyPOJO!=null){
                            ToastClass.showShortToast(getApplicationContext(),"History Updated");
                        }else{
                            ToastClass.showShortToast(getApplicationContext(),"History Added");
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result","");
                            setResult(Activity.RESULT_OK,returnIntent);
                        }

                        finish();
                    }
                    ToastClass.showShortToast(getApplicationContext(),historyPOJO.getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"INSERT_PATIENT_HISTORY",true).execute(WebServicesUrls.HISTORY_CRUD);
    }

    public String getRadioGroupValues(RadioGroup rg){
        try {
            int checked_id=rg.getCheckedRadioButtonId();
            RadioButton rb=findViewById(checked_id);
            return rb.getText().toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public void setRgValue(String text,RadioGroup radioGroup){
        try{
            int count = radioGroup.getChildCount();
            ArrayList<RadioButton> listOfRadioButtons = new ArrayList<RadioButton>();
            for (int i=0;i<count;i++) {
                View o = radioGroup.getChildAt(i);
                if (o instanceof RadioButton) {
                    listOfRadioButtons.add((RadioButton)o);
                }
            }
            for(RadioButton rb:listOfRadioButtons){
                if(text.equals(rb.getText().toString())){
                    rb.setChecked(true);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
