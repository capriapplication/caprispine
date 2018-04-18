package com.caprispine.caprispine.activity.assessment.add.motor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ImageUtil;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.caprispine.caprispine.webservice.WebUploadService;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSacrollicActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.et_Ant_Innominate_Left)
    EditText et_Ant_Innominate_Left;
    @BindView(R.id.et_Ant_Innominate_Right)
    EditText et_Ant_Innominate_Right;
    @BindView(R.id.et_post_innominate_Left)
    EditText et_post_innominate_Left;
    @BindView(R.id.et_up_slip_Left)
    EditText et_up_slip_Left;
    @BindView(R.id.et_up_slip_Right)
    EditText et_up_slip_Right;
    @BindView(R.id.et_down_slip_Left)
    EditText et_down_slip_Left;
    @BindView(R.id.et_down_slip_Right)
    EditText et_down_slip_Right;
    @BindView(R.id.et_Ant_tilt_Left)
    EditText et_Ant_tilt_Left;
    @BindView(R.id.et_Ant_tilt_Right)
    EditText et_Ant_tilt_Right;
    @BindView(R.id.et_post_tilt_Left)
    EditText et_post_tilt_Left;
    @BindView(R.id.et_post_tilt_Right)
    EditText et_post_tilt_Right;
    @BindView(R.id.et_nutration_left)
    EditText et_nutration_left;
    @BindView(R.id.et_nutration_left_Right)
    EditText et_nutration_left_Right;
    @BindView(R.id.et_et_counter_nutration_Left)
    EditText et_et_counter_nutration_Left;
    @BindView(R.id.et_counter_nutration_Right)
    EditText et_counter_nutration_Right;

    Button savebtn;
    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sacrollic);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Sacrollic Exam");

        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");

        savebtn = (Button) findViewById(R.id.savebtn);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "button", Toast.LENGTH_LONG);
                saveData(ImageUtil.saveBitmaptoFile(ImageUtil.takeScreenShots(scrollView)));
            }
        });

    }

    private void saveData(String file_path) {
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (new File(file_path).exists()) {
                FileBody bin1 = new FileBody(new File(file_path));
                reqEntity.addPart("image", bin1);
            } else {
                reqEntity.addPart("image", new StringBody(""));
            }
            reqEntity.addPart("request_action", new StringBody("insert_complaint"));
            reqEntity.addPart("patient_id", new StringBody(patientPOJO.getId()));
            reqEntity.addPart("date", new StringBody(UtilityFunction.getCurrentDate()));


            reqEntity.addPart("ant_innominate_left", new StringBody(et_Ant_Innominate_Left.getText().toString()));
            reqEntity.addPart("post_innominate_left", new StringBody(et_post_innominate_Left.getText().toString()));
            reqEntity.addPart("upsleep_left", new StringBody(et_up_slip_Left.getText().toString()));
            reqEntity.addPart("down_slip_left", new StringBody(et_down_slip_Left.getText().toString()));
            reqEntity.addPart("ant_till_left", new StringBody(et_Ant_tilt_Left.getText().toString()));
            reqEntity.addPart("post_till_left", new StringBody(et_Ant_tilt_Right.getText().toString()));
            reqEntity.addPart("nutation_left", new StringBody(et_nutration_left.getText().toString()));
            reqEntity.addPart("counter_left", new StringBody(et_et_counter_nutration_Left.getText().toString()));
            reqEntity.addPart("ant_innominate_right", new StringBody(et_Ant_Innominate_Right.getText().toString()));
            reqEntity.addPart("post_innominate_right", new StringBody(et_post_innominate_Left.getText().toString()));
            reqEntity.addPart("upsleep_right", new StringBody(et_up_slip_Right.getText().toString()));
            reqEntity.addPart("down_slip_right", new StringBody(et_down_slip_Right.getText().toString()));
            reqEntity.addPart("ant_till_right", new StringBody(et_Ant_tilt_Right.getText().toString()));
            reqEntity.addPart("post_till_right", new StringBody(et_post_tilt_Right.getText().toString()));
            reqEntity.addPart("nutation_right", new StringBody(et_nutration_left_Right.getText().toString()));
            reqEntity.addPart("counter_right", new StringBody(et_counter_nutration_Right.getText().toString()));


            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String[] msg) {
                    Log.d(TagUtils.getTag(), msg[0] + ":-" + msg[1]);
//                    ResponsePOJO<HipExamPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponsePOJO<HipExamPOJO>>() {
//                    }.getType());
//                    if (responseListPOJO.isSuccess()) {
//                        finish();
//                    } else {
//                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
//                    }
                    try{
                        JSONObject jsonObject=new JSONObject(msg[1]);
                        if(jsonObject.optBoolean("success")){
                            finish();
                        }else{
                            ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }, "CALL_HIP_CRUD_API", true).execute(WebServicesUrls.SACROILIC_CRUD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
