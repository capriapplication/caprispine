package com.caprispine.caprispine.activity.assessment.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.activity.SignActivity;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.treatment.TreatmentPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.caprispine.caprispine.webservice.WebUploadService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTreatmentGivenActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_treatment)
    Spinner spinner_treatment;
    @BindView(R.id.act_doses)
    AutoCompleteTextView act_doses;
    @BindView(R.id.spinner_therapist)
    Spinner spinner_therapist;
    @BindView(R.id.tv_select_time_in)
    TextView tv_select_time_in;
    @BindView(R.id.tv_select_time_out)
    TextView tv_select_time_out;
    @BindView(R.id.iv_signature)
    ImageView iv_signature;
    @BindView(R.id.et_comment)
    EditText et_comment;
    @BindView(R.id.btn_add_signature)
    Button btn_add_signature;
    @BindView(R.id.btn_save)
    Button btn_save;

    private static final String IN_TIME = "in_time";
    private static final String OUT_TIME = "out_time";

    private static String SELECT_TIME = IN_TIME;
    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treatment_given);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Treatment Plan");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");

        getTherapists();
        getAllTreatment();

        btn_add_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AddTreatmentGivenActivity.this, SignActivity.class), 1001);
            }
        });

        tv_select_time_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECT_TIME = IN_TIME;
                TimePickerDialog tpd = TimePickerDialog
                        .newInstance(AddTreatmentGivenActivity.this, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

                tpd.show(getFragmentManager(), "Time In Time Picker");
            }
        });

        tv_select_time_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECT_TIME = OUT_TIME;
                TimePickerDialog tpd = TimePickerDialog
                        .newInstance(AddTreatmentGivenActivity.this, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

                tpd.show(getFragmentManager(), "Time Out Time Picker");
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_comment.getText().toString().length()>0){
                    addExam();
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"Please Add Comments");
                }
            }
        });

    }

    public void addExam(){
        if(new File(signature_image_path).exists()) {
            try {
                MultipartEntity reqEntity = new MultipartEntity();

                File file1 = new File(signature_image_path);
                Log.e("signature", signature_image_path);
                FileBody bin1 = new FileBody(file1);

                reqEntity.addPart("request_action", new StringBody("insert_treatment"));
                reqEntity.addPart("signature", bin1);
                reqEntity.addPart("treatment_id", new StringBody(treatmentPOJOS.get(spinner_treatment.getSelectedItemPosition()).getId()));
                reqEntity.addPart("dose", new StringBody(act_doses.getText().toString()));
                reqEntity.addPart("therapist_id", new StringBody(therapistPOJOS.get(spinner_therapist.getSelectedItemPosition()).getId()));
                reqEntity.addPart("time_in", new StringBody(tv_select_time_in.getText().toString()));
                reqEntity.addPart("time_out", new StringBody(tv_select_time_out.getText().toString()));
                reqEntity.addPart("comment", new StringBody(et_comment.getText().toString()));
                reqEntity.addPart("patient_id", new StringBody(patientPOJO.getId()));
                reqEntity.addPart("branch_id", new StringBody(patientPOJO.getBranchId()));
                reqEntity.addPart("date", new StringBody(UtilityFunction.getCurrentDate()));
//                reqEntity.addPart("amount", new StringBody(treatmentPOJOS.get(spinner_treatment.getSelectedItemPosition()).getPrice()));

                new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String[] msg) {
                        try {
                            JSONObject jsonObject = new JSONObject(msg[1]);
                            if(jsonObject.optString("success").equals("true")){
                                ToastClass.showShortToast(getApplicationContext(),"Treatment Given Added");
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result","");
                                setResult(Activity.RESULT_OK,returnIntent);
                                finish();
                            }else{
                                ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, "CALL_INVESTIGATION_UPLOAD", true).execute(WebServicesUrls.TREATMENT_GIVEN_CRUD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            ToastClass.showShortToast(getApplicationContext(),"Please Add Signature");
        }
    }

    List<TreatmentPOJO> treatmentPOJOS = new ArrayList<>();

    public void getAllTreatment() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "all_treatment"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                ResponseListPOJO<TreatmentPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<TreatmentPOJO>>() {
                }.getType());
                treatmentPOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    treatmentPOJOS.addAll(responseListPOJO.getResultList());
//                    Log.d(TagUtils.getTag(), "patient list:-" + patientList.toString());

                    List<String> treStringList = new ArrayList<>();
                    for (TreatmentPOJO treatmentPOJO : treatmentPOJOS) {
                        treStringList.add(treatmentPOJO.getName() + " (" + treatmentPOJO.getPrice() + ")");
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, treStringList);
                    spinner_treatment.setAdapter(spinnerArrayAdapter);

                } else {
                    ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                }

            }
        }, "GET_TREATMENT_API", true).execute(WebServicesUrls.TREATMENT_CRUD);
    }

    List<TherapistPOJO> therapistPOJOS = new ArrayList<>();

    public void getTherapists() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_all_therapist"));
        nameValuePairs.add(new BasicNameValuePair("branch_id", patientPOJO.getBranchId()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                ResponseListPOJO<TherapistPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<TherapistPOJO>>() {
                }.getType());
                therapistPOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    therapistPOJOS.addAll(responseListPOJO.getResultList());
//                    Log.d(TagUtils.getTag(), "patient list:-" + patientList.toString());

                    List<String> therapistStringList = new ArrayList<>();
                    for (TherapistPOJO therapistPOJO : therapistPOJOS) {
                        therapistStringList.add(therapistPOJO.getFirstName() + " " + therapistPOJO.getLastName());
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, therapistStringList);
                    spinner_therapist.setAdapter(spinnerArrayAdapter);

                } else {
                    ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                }

            }
        }, "GET_DOCTORS_API", true).execute(WebServicesUrls.USER_CRUD);
    }


    String signature_image_path = "";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d(TagUtils.getTag(), "on activity result");
                String file_path = data.getStringExtra("result");
                try {
                    Glide.with(getApplicationContext())
                            .load(file_path)
                            .into(iv_signature);

                    signature_image_path = file_path;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hour = "";
        if (hourOfDay < 10) {
            hour = "0" + hourOfDay;
        } else {
            hour = String.valueOf(hourOfDay);
        }

        String minutes = "";

        if (minute < 10) {
            minutes = "0" + minute;
        } else {
            minutes = String.valueOf(minute);
        }

        String time = hour + ":" + minutes;
        Log.d(TagUtils.getTag(), "time selected:-" + time);
        if (SELECT_TIME.equals(IN_TIME)) {
            tv_select_time_in.setText(time);
        } else {
            tv_select_time_out.setText(time);
        }
    }
}
