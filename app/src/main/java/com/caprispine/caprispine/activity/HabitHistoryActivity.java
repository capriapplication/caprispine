package com.caprispine.caprispine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.NVP;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.activity.assessment.add.AddPainActivity;
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

public class HabitHistoryActivity extends AppCompatActivity {

    private static final String CALL_SAVE_HISTORY_API = "call_save_history_api";
    @BindView(R.id.btn_submit)
    Button btn_submit;
    String patient_id = "";
//    String problems = "";

    @BindView(R.id.rg_smoking)
    RadioGroup rg_smoking;
    @BindView(R.id.rg_alcholic)
    RadioGroup rg_alcholic;
    @BindView(R.id.rg_fever)
    RadioGroup rg_fever;
    @BindView(R.id.rg_diabetes)
    RadioGroup rg_diabetes;
    @BindView(R.id.rg_blood_pressure)
    RadioGroup rg_blood_pressure;
    @BindView(R.id.rg_heart)
    RadioGroup rg_heart;
    @BindView(R.id.rg_bleeding)
    RadioGroup rg_bleeding;
    @BindView(R.id.rg_infection)
    RadioGroup rg_infection;
    @BindView(R.id.rg_red_flag)
    RadioGroup rg_red_flag;
    @BindView(R.id.rg_yellow)
    RadioGroup rg_yellow;
    @BindView(R.id.rg_limitation)
    RadioGroup rg_limitation;
    @BindView(R.id.rg_past)
    RadioGroup rg_past;
    @BindView(R.id.rg_allergy)
    RadioGroup rg_allergy;
    @BindView(R.id.rg_oestrophic)
    RadioGroup rg_oestrophic;
    @BindView(R.id.rg_implants)
    RadioGroup rg_implants;
    @BindView(R.id.rg_herediatery)
    RadioGroup rg_herediatery;
    @BindView(R.id.et_remark)
    EditText et_remark;

    ArrayList<NVP> passedArrayList = new ArrayList<>();
    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_history);
        ButterKnife.bind(this);
        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            passedArrayList = (ArrayList<NVP>) bundle.get("params");
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHistoryData();
            }
        });
    }

    public void saveHistoryData() {

        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","insert_complaint"));
        for(NVP nvp:passedArrayList){
            nameValuePairs.add(new BasicNameValuePair(nvp.getName(),nvp.getValue()));
        }

        nameValuePairs.add(new BasicNameValuePair("smoking",getRadioText(rg_smoking)));
        nameValuePairs.add(new BasicNameValuePair("alcholic",getRadioText(rg_alcholic)));
        nameValuePairs.add(new BasicNameValuePair("fever_chill",getRadioText(rg_fever)));
        nameValuePairs.add(new BasicNameValuePair("diabetes",getRadioText(rg_diabetes)));
        nameValuePairs.add(new BasicNameValuePair("blood_pressure",getRadioText(rg_blood_pressure)));
        nameValuePairs.add(new BasicNameValuePair("heart_disease",getRadioText(rg_heart)));
        nameValuePairs.add(new BasicNameValuePair("bleeding_disorder",getRadioText(rg_bleeding)));
        nameValuePairs.add(new BasicNameValuePair("recent_infection",getRadioText(rg_infection)));
        nameValuePairs.add(new BasicNameValuePair("red_flags",getRadioText(rg_red_flag)));
        nameValuePairs.add(new BasicNameValuePair("yellow_flags",getRadioText(rg_yellow)));
        nameValuePairs.add(new BasicNameValuePair("limitations",getRadioText(rg_limitation)));
        nameValuePairs.add(new BasicNameValuePair("past_surgery",getRadioText(rg_past)));
        nameValuePairs.add(new BasicNameValuePair("allergy",getRadioText(rg_allergy)));
        nameValuePairs.add(new BasicNameValuePair("osteoporotic",getRadioText(rg_oestrophic)));
        nameValuePairs.add(new BasicNameValuePair("any_implants",getRadioText(rg_implants)));
        nameValuePairs.add(new BasicNameValuePair("herediatary_disease",getRadioText(rg_herediatery)));
        nameValuePairs.add(new BasicNameValuePair("remark",et_remark.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("patient_id",patientPOJO.getId()));
        nameValuePairs.add(new BasicNameValuePair("request_action","insert_complaint"));
        nameValuePairs.add(new BasicNameValuePair("date", UtilityFunction.getCurrentDate()));

        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                parseSaveHistoryReponse(msg[1]);
            }
        },"CALL_SAVE_HISTORY_API",true).execute(WebServicesUrls.HISTORY_CRUD);
    }


    public String getRadioText(RadioGroup rg) {
        try {
            RadioButton rb = findViewById(rg.getCheckedRadioButtonId());
            return rb.getText().toString();
        } catch (Exception e) {

        }
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }


    public void parseSaveHistoryReponse(String response){
        Log.d(TagUtils.getTag(),"save history response:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("success").equals("true")){
                Intent intent = new Intent(HabitHistoryActivity.this, AddPainActivity.class);
                intent.putExtra("patientPOJO", patientPOJO);
                intent.putExtra("is_questionaire",true);
                startActivityForResult(intent,1);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
