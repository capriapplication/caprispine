package com.caprispine.caprispine.activity.assessment.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.pojo.patientassessment.NeuroExamPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNeuroExamActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_save)
    Button btn_save;

    @BindView(R.id.et_fn_time_taken)
    EditText et_fn_time_taken;
    @BindView(R.id.et_hs_speed)
    EditText et_hs_speed;
    @BindView(R.id.et_al_error)
    EditText et_al_error;
    @BindView(R.id.et_fn_error)
    EditText et_fn_error;

    @BindView(R.id.et_fn_speed)
    EditText et_fn_speed;
    @BindView(R.id.et_al_time)
    EditText et_al_time;
    @BindView(R.id.et_al_speed)
    EditText et_al_speed;
    @BindView(R.id.et_hs_time)
    EditText et_hs_time;
    @BindView(R.id.et_hs_error)
    EditText et_hs_error;
    @BindView(R.id.et_balance_left)
    EditText et_balance_left;
    @BindView(R.id.et_balance_right)
    EditText et_balance_right;
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.spinner3)
    Spinner spinner3;
    @BindView(R.id.spinner7_1)
    Spinner spinner7_1;
    @BindView(R.id.spinner7_2)
    Spinner spinner7_2;
    @BindView(R.id.spinner7_3)
    Spinner spinner7_3;
    @BindView(R.id.spinner7_4)
    Spinner spinner7_4;
    @BindView(R.id.spinner7_5)
    Spinner spinner7_5;
    @BindView(R.id.spinner7_6)
    Spinner spinner7_6;
    @BindView(R.id.spinner7_7)
    Spinner spinner7_7;
    @BindView(R.id.spinner7_8)
    Spinner spinner7_8;
    @BindView(R.id.spinner8_1)
    Spinner spinner8_1;
    @BindView(R.id.spinner8_2)
    Spinner spinner8_2;
    @BindView(R.id.spinner8_3)
    Spinner spinner8_3;
    @BindView(R.id.spinner8_4)
    Spinner spinner8_4;
    @BindView(R.id.spinner8_5)
    Spinner spinner8_5;
    @BindView(R.id.spinner8_6)
    Spinner spinner8_6;
    @BindView(R.id.spinner8_7)
    Spinner spinner8_7;
    @BindView(R.id.spinner8_8)
    Spinner spinner8_8;
    @BindView(R.id.spinner8_9)
    Spinner spinner8_9;
    @BindView(R.id.spinner8_10)
    Spinner spinner8_10;
    PatientPOJO patientPOJO;
    NeuroExamPOJO neuroExamPOJO;

    List<String> neuroGlasEyeStringList = new ArrayList<>();
    List<String> neuroGlasVerbalStringList = new ArrayList<>();
    List<String> neuroGlasMotorStringList = new ArrayList<>();
    List<String> neuroofPaintStringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_neuro_exam);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Neuro Exam");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        neuroExamPOJO = (NeuroExamPOJO) getIntent().getSerializableExtra("neuroPOJO");

        String[] glassEyeString = getResources().getStringArray(R.array.neuro_glass_eye);
        for (String str : glassEyeString) {
            neuroGlasEyeStringList.add(str);
        }

        String[] glassVerbalString = getResources().getStringArray(R.array.neuro_glass_verbal);
        for (String str : glassVerbalString) {
            neuroGlasVerbalStringList.add(str);
        }

        String[] glassMotorString = getResources().getStringArray(R.array.neuro_glass_motor);
        for (String str : glassMotorString) {
            neuroGlasMotorStringList.add(str);
        }

        String[] neuropain = getResources().getStringArray(R.array.neuro_of_pain);
        for (String str : neuropain) {
            neuroofPaintStringList.add(str);
        }


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExam();
            }
        });

        if (neuroExamPOJO != null) {
            btn_save.setText("Update");
            setValues();
        }
    }

    public void setValues() {
        setSpinnerValue(spinner1, neuroGlasEyeStringList, neuroExamPOJO.getGcseye());
        setSpinnerValue(spinner2, neuroGlasVerbalStringList, neuroExamPOJO.getGcsverbal());
        setSpinnerValue(spinner3, neuroGlasMotorStringList, neuroExamPOJO.getGcsmotor());

        setSpinnerValue(spinner7_1, neuroofPaintStringList, neuroExamPOJO.getGaitsurface());
        setSpinnerValue(spinner7_2, neuroofPaintStringList, neuroExamPOJO.getGaitspeed());
        setSpinnerValue(spinner7_3, neuroofPaintStringList, neuroExamPOJO.getGaithorizontal());
        setSpinnerValue(spinner7_4, neuroofPaintStringList, neuroExamPOJO.getGaitvertical());
        setSpinnerValue(spinner7_5, neuroofPaintStringList, neuroExamPOJO.getGaitpivot());
        setSpinnerValue(spinner7_6, neuroofPaintStringList, neuroExamPOJO.getGaitover());
        setSpinnerValue(spinner7_7, neuroofPaintStringList, neuroExamPOJO.getGaitaround());
        setSpinnerValue(spinner7_8, neuroofPaintStringList, neuroExamPOJO.getGaitsteps());

        setSpinnerValue(spinner8_1, neuroofPaintStringList, neuroExamPOJO.getFtbowel());
        setSpinnerValue(spinner8_2, neuroofPaintStringList, neuroExamPOJO.getFtbladder());
        setSpinnerValue(spinner8_3, neuroofPaintStringList, neuroExamPOJO.getFttoilet());
        setSpinnerValue(spinner8_4, neuroofPaintStringList, neuroExamPOJO.getFtgromming());
        setSpinnerValue(spinner8_5, neuroofPaintStringList, neuroExamPOJO.getFtfeeding());
        setSpinnerValue(spinner8_6, neuroofPaintStringList, neuroExamPOJO.getFttransfer());
        setSpinnerValue(spinner8_7, neuroofPaintStringList, neuroExamPOJO.getFtmobility());
        setSpinnerValue(spinner8_8, neuroofPaintStringList, neuroExamPOJO.getFtdressing());
        setSpinnerValue(spinner8_9, neuroofPaintStringList, neuroExamPOJO.getFtstairs());
        setSpinnerValue(spinner8_10, neuroofPaintStringList, neuroExamPOJO.getFtbathing());

        et_fn_time_taken.setText(neuroExamPOJO.getCtfntime());
        et_fn_speed.setText(neuroExamPOJO.getCtfnspeed());
        et_fn_error.setText(neuroExamPOJO.getCtfnerror());
        et_al_time.setText(neuroExamPOJO.getCtastime());
        et_al_speed.setText(neuroExamPOJO.getCtasspeed());
        et_al_error.setText(neuroExamPOJO.getCtaserror());
        et_hs_time.setText(neuroExamPOJO.getCthstime());
        et_hs_speed.setText(neuroExamPOJO.getCthsspeed());
        et_hs_error.setText(neuroExamPOJO.getCthserror());
        et_balance_left.setText(neuroExamPOJO.getBalanceleft());
        et_balance_right.setText(neuroExamPOJO.getBalanceright());


    }

    public void setSpinnerValue(Spinner spinner, List<String> stringList, String value) {
        if (stringList.contains(value)) {
            spinner.setSelection(stringList.indexOf(value));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addExam() {
        ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();

        nameValuePairArrayList.add(new BasicNameValuePair("gcseye", spinner1.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gcsverbal", spinner2.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gcsmotor", spinner3.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ctfntime", et_fn_time_taken.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ctfnspeed", et_fn_speed.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ctfnerror", et_fn_error.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ctastime", et_al_speed.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ctasspeed", et_al_speed.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ctaserror", et_al_error.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("cthstime", et_hs_time.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("cthsspeed", et_hs_speed.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("cthserror", et_hs_error.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitsurface", spinner7_1.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitspeed", spinner7_2.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaithorizontal", spinner7_3.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitvertical", spinner7_4.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitpivot", spinner7_5.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitover", spinner7_6.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitaround", spinner7_7.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("gaitsteps", spinner7_8.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("balanceleft", et_balance_left.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("balanceright", et_balance_right.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftbowel", spinner8_1.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftbladder", spinner8_2.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("fttoilet", spinner8_3.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftgromming", spinner8_4.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftfeeding", spinner8_5.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("fttransfer", spinner8_6.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftmobility", spinner8_7.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftdressing", spinner8_8.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftstairs", spinner8_9.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ftbathing", spinner8_10.getSelectedItem().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));


        if(neuroExamPOJO!=null) {
            nameValuePairArrayList.add(new BasicNameValuePair("request_action", "update_complaint"));
            nameValuePairArrayList.add(new BasicNameValuePair("id", neuroExamPOJO.getId()));

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
                        if(neuroExamPOJO!=null){
                            ToastClass.showShortToast(getApplicationContext(),"Neuro Updated");
                        }else{
                            ToastClass.showShortToast(getApplicationContext(),"Neuro Added");
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
        }, "CALL_NEURO_EXAM", true).execute(WebServicesUrls.NEURO_CRUD);
    }

}
