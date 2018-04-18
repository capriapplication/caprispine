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
import com.caprispine.caprispine.pojo.patientassessment.NdtPOJO;
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

public class AddNdtpActivity extends AppCompatActivity {
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.Ulnar_left)
    EditText Ulnar_left;
    @BindView(R.id.Ulnar_right)
    EditText Ulnar_right;
    @BindView(R.id.Radial_left)
    EditText Radial_left;
    @BindView(R.id.Radial_right)
    EditText Radial_right;
    @BindView(R.id.Median1)
    EditText Median1;
    @BindView(R.id.Median2)
    EditText Median2;
    @BindView(R.id.Musculocutaneous1)
    EditText Musculocutaneous1;
    @BindView(R.id.Musculocutaneous2)
    EditText Musculocutaneous2;
    @BindView(R.id.Sciatic2_1)
    EditText Sciatic2_1;
    @BindView(R.id.Sciatic2_2)
    EditText Sciatic2_2;
    @BindView(R.id.Tibial1)
    EditText Tibial1;
    @BindView(R.id.Tibia2)
    EditText Tibia2;
    @BindView(R.id.Comman_peronial1)
    EditText Comman_peronial1;
    @BindView(R.id.Comman_peronial2)
    EditText Comman_peronial2;
    @BindView(R.id.Femoral1)
    EditText Femoral1;
    @BindView(R.id.Femoral2)
    EditText Femoral2;
    @BindView(R.id.Lateral_cutaneous1)
    EditText Lateral_cutaneous1;
    @BindView(R.id.Lateral_cutaneous2)
    EditText Lateral_cutaneous2;
    @BindView(R.id.Obturator1)
    EditText Obturator1;
    @BindView(R.id.Obturator2)
    EditText Obturator2;
    @BindView(R.id.Sural1)
    EditText Sural1;
    @BindView(R.id.Sural2)
    EditText Sural2;
    @BindView(R.id.sapp_left)
    EditText sapp_left;
    @BindView(R.id.sapp_right)
    EditText sapp_right;
    @BindView(R.id.Ulnar5_1)
    EditText Ulnar5_1;
    @BindView(R.id.Ulnar5_2)
    EditText Ulnar5_2;
    @BindView(R.id.Radial5_1)
    EditText Radial5_1;
    @BindView(R.id.Radial5_2)
    EditText Radial5_2;
    @BindView(R.id.Median5_1)
    EditText Median5_1;
    @BindView(R.id.Median5_2)
    EditText Median5_2;
    @BindView(R.id.Sciatic5_1)
    EditText Sciatic5_1;
    @BindView(R.id.Sciatic5_2)
    EditText Sciatic5_2;
    @BindView(R.id.Tibial5_1)
    EditText Tibial5_1;
    @BindView(R.id.Tibial5_2)
    EditText Tibial5_2;
    @BindView(R.id.Comman_peronial5_1)
    EditText Comman_peronial5_1;
    @BindView(R.id.Comman_peronial5_2)
    EditText Comman_peronial5_2;
    @BindView(R.id.Femoral5_1)
    EditText Femoral5_1;
    @BindView(R.id.Femoral5_2)
    EditText Femoral5_2;
    @BindView(R.id.Sural5_1)
    EditText Sural5_1;
    @BindView(R.id.Sural5_2)
    EditText Sural5_2;
    @BindView(R.id.Obturator5_1)
    EditText Obturator5_1;
    @BindView(R.id.Obturator5_2)
    EditText Obturator5_2;
    @BindView(R.id.btn_save)
    Button btn_save;
    PatientPOJO patientPOJO;
    NdtPOJO ndtPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ndtp);
        ButterKnife.bind(this);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Add NDT/NTP Exam");
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        ndtPOJO= (NdtPOJO) getIntent().getSerializableExtra("ndtPOJO");

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExam();
            }
        });


        if(ndtPOJO!=null){
            btn_save.setText("update");
            setValues();
        }

    }

    public void setValues(){
        Ulnar_left.setText(ndtPOJO.getNdtulnarleft());
        Ulnar_right.setText(ndtPOJO.getNdtulnarright());
        Radial_left.setText(ndtPOJO.getNdtradianleft());
        Radial_right.setText(ndtPOJO.getNdtradianright());
        Median1.setText(ndtPOJO.getNdtmedianleft());
        Median2.setText(ndtPOJO.getNdtmedianright());
        Musculocutaneous1.setText(ndtPOJO.getNdtmusculocleft());
        Musculocutaneous2.setText(ndtPOJO.getNdtmusculocright());
        Sciatic2_1.setText(ndtPOJO.getNdtsciaticleft());
        Sciatic2_2.setText(ndtPOJO.getNdtsciaticright());
        Tibial1.setText(ndtPOJO.getNdttibialleft());
        Tibia2.setText(ndtPOJO.getNdttibialright());
        Comman_peronial1.setText(ndtPOJO.getNdtcpleft());
        Comman_peronial2.setText(ndtPOJO.getNdtcpright());
        Femoral1.setText(ndtPOJO.getNdtfemoralleft());
        Femoral2.setText(ndtPOJO.getNdtfemoralright());
        Lateral_cutaneous1.setText(ndtPOJO.getNdtcutaneousleft());
        Lateral_cutaneous2.setText(ndtPOJO.getNdtcutaneousright());
        Obturator1.setText(ndtPOJO.getNdtobturatorleft());
        Obturator2.setText(ndtPOJO.getNdtobturatorright());
        Sural1.setText(ndtPOJO.getNdtsuralleft());
        Sural2.setText(ndtPOJO.getNdtsuralright());
        sapp_left.setText(ndtPOJO.getNdtsaphenousleft());
        sapp_right.setText(ndtPOJO.getNdtsaphenousright());
        Ulnar5_1.setText(ndtPOJO.getNtpulnarleft());
        Ulnar5_2.setText(ndtPOJO.getNtpulnarright());
        Radial5_1.setText(ndtPOJO.getNtpradianleft());
        Radial5_2.setText(ndtPOJO.getNtpradianright());
        Median5_1.setText(ndtPOJO.getNtpmedianleft());
        Median5_2.setText(ndtPOJO.getNtpmedianright());
        Sciatic5_1.setText(ndtPOJO.getNtpsciaticleft());
        Sciatic5_2.setText(ndtPOJO.getNtpsciaticright());
        Tibial5_1.setText(ndtPOJO.getNtptibialleft());
        Tibial5_2.setText(ndtPOJO.getNtptibialright());
        Comman_peronial5_1.setText(ndtPOJO.getNtpperonialleft());
        Comman_peronial5_2.setText(ndtPOJO.getNtpparonialright());
        Femoral5_1.setText(ndtPOJO.getNtpfemoralleft());
        Femoral5_2.setText(ndtPOJO.getNtpfemoralright());
        Sural5_1.setText(ndtPOJO.getNtpsuralleft());
        Sural5_2.setText(ndtPOJO.getNtpsuralright());
        Obturator5_1.setText(ndtPOJO.getNtpsaphenousleft());
        Obturator5_2.setText(ndtPOJO.getNtpsaphenousright());
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

        nameValuePairArrayList.add(new BasicNameValuePair("ndtulnarleft", Ulnar_left.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtulnarright", Ulnar_right.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtradianleft", Radial_left.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtradianright", Radial_right.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtmedianleft", Median1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtmedianright", Median2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtmusculocleft", Musculocutaneous1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtmusculocright", Musculocutaneous2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtsciaticleft", Sciatic2_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtsciaticright", Sciatic2_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndttibialleft", Tibial1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndttibialright", Tibia2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtcpleft", Comman_peronial1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtcpright", Comman_peronial2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtfemoralleft", Femoral1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtfemoralright", Femoral2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtcutaneousleft", Lateral_cutaneous1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtcutaneousright", Lateral_cutaneous2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtobturatorleft", Obturator1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtobturatorright", Obturator2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtsuralleft", Sural1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtsuralright", Sural2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtsaphenousleft", sapp_left.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ndtsaphenousright", sapp_right.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpulnarleft", Ulnar5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpulnarright", Ulnar5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpradianleft", Radial5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpradianright", Radial5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpmedianleft", Median5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpmedianright", Median5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpsciaticleft", Sciatic5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpsciaticright", Sciatic5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntptibialleft", Tibial5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntptibialright", Tibial5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpperonialleft", Comman_peronial5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpparonialright", Comman_peronial5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpfemoralleft", Femoral5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpfemoralright", Femoral5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpsuralleft", Sural5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpsuralright", Sural5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpsaphenousleft", Obturator5_1.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("ntpsaphenousright", Obturator5_2.getText().toString()));
        nameValuePairArrayList.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));

        if(ndtPOJO!=null){
            nameValuePairArrayList.add(new BasicNameValuePair("request_action", "update_complaint"));
            nameValuePairArrayList.add(new BasicNameValuePair("id", ndtPOJO.getId()));
        }else{
            nameValuePairArrayList.add(new BasicNameValuePair("date", UtilityFunction.getCurrentDate()));
            nameValuePairArrayList.add(new BasicNameValuePair("request_action", "insert_complaint"));
        }

        new WebServiceBase(nameValuePairArrayList, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    JSONObject jsonObject=new JSONObject(msg[1]);
                    if(jsonObject.optBoolean("success")){
                        if(ndtPOJO!=null){
                            ToastClass.showShortToast(getApplicationContext(),"NDT Updated");
                        }else{
                            ToastClass.showShortToast(getApplicationContext(),"NDT Added");
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
        }, "CALL_NDT_EXAM",true).execute(WebServicesUrls.NTP_CRUD);
    }

}
