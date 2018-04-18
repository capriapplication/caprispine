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

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.pojo.ResponsePOJO;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPhysicalActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_bp)
    EditText et_bp;
    @BindView(R.id.et_temp)
    EditText et_temp;
    @BindView(R.id.et_heart_rate)
    EditText et_heart_rate;
    @BindView(R.id.et_resp_rate)
    EditText et_resp_rate;
    @BindView(R.id.et_posture)
    EditText et_posture;
    @BindView(R.id.et_gait)
    EditText et_gait;
    @BindView(R.id.et_scar_type)
    EditText et_scar_type;
    @BindView(R.id.et_swelling)
    EditText et_swelling;
    @BindView(R.id.et_desc)
    EditText et_desc;
    @BindView(R.id.btn_save)
    Button btn_save;
    PatientPOJO patientPOJO;
    PhysicalPOJO physicalPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_physical);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Physical Exam");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        physicalPOJO= (PhysicalPOJO) getIntent().getSerializableExtra("pojo");
        if(physicalPOJO!=null){
            btn_save.setText("Update");
            setValues();
        }
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    public void setValues(){
        et_bp.setText(physicalPOJO.getBloodPressure());
        et_temp.setText(physicalPOJO.getTemperature());
        et_heart_rate.setText(physicalPOJO.getHeartRate());
        et_resp_rate.setText(physicalPOJO.getRespiratoryRate());
        et_posture.setText(physicalPOJO.getPosture());
        et_gait.setText(physicalPOJO.getGalt());
        et_scar_type.setText(physicalPOJO.getScareType());
        et_swelling.setText(physicalPOJO.getSwelling());
        et_desc.setText(physicalPOJO.getDescription());
    }

    public void save(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("blood_pressure",et_bp.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("temperature",et_temp.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("heart_rate",et_heart_rate.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("respiratory_rate",et_resp_rate.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("posture",et_posture.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("galt",et_gait.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("scare_type",et_scar_type.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("swelling",et_swelling.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("description",et_desc.getText().toString()));

        nameValuePairs.add(new BasicNameValuePair("patient_id",patientPOJO.getId()));
//
        if(physicalPOJO!=null){
            nameValuePairs.add(new BasicNameValuePair("request_action","update_complaint"));
            nameValuePairs.add(new BasicNameValuePair("date", physicalPOJO.getDate()));
            nameValuePairs.add(new BasicNameValuePair("id", physicalPOJO.getId()));
        }else{
            nameValuePairs.add(new BasicNameValuePair("request_action","insert_complaint"));
            nameValuePairs.add(new BasicNameValuePair("date", UtilityFunction.getCurrentDate()));
        }

        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    ResponsePOJO<PhysicalPOJO> POJO = new Gson().fromJson(msg[1], new TypeToken<ResponsePOJO<PhysicalPOJO>>() {}.getType());
                    if(POJO.isSuccess()){
                        if(physicalPOJO!=null){
                            ToastClass.showShortToast(getApplicationContext(),"Physical Updated");
                        }else{
                            ToastClass.showShortToast(getApplicationContext(),"Physical Added");
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result","");
                            setResult(Activity.RESULT_OK,returnIntent);
                        }
                    }
                    ToastClass.showShortToast(getApplicationContext(),POJO.getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"INSERT_PHYSICAL",true).execute(WebServicesUrls.PHYSICAL_CRUD);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
