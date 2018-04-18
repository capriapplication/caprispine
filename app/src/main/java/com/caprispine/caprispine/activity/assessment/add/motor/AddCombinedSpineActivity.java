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
import com.caprispine.caprispine.pojo.ResponsePOJO;
import com.caprispine.caprispine.pojo.patientassessment.motor.CombinedPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.caprispine.caprispine.webservice.WebUploadService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCombinedSpineActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.et_cervical_spine)
    EditText et_cervical_spine;
    @BindView(R.id.et_thoracic_spine)
    EditText et_thoracic_spine;
    @BindView(R.id.et_lumbar_spine)
    EditText et_lumbar_spine;
    @BindView(R.id.savebtn)
    Button savebtn;
    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_combined_spine);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
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


            reqEntity.addPart("cervical_spine", new StringBody(et_cervical_spine.getText().toString()));
            reqEntity.addPart("thoracic_spine", new StringBody(et_thoracic_spine.getText().toString()));
            reqEntity.addPart("lumbar_spine", new StringBody(et_lumbar_spine.getText().toString()));


            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String[] msg) {
                    Log.d(TagUtils.getTag(), msg[0] + ":-" + msg[1]);
                    ResponsePOJO<CombinedPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponsePOJO<CombinedPOJO>>() {
                    }.getType());
                    if (responseListPOJO.isSuccess()) {
                        finish();
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                    }

                }
            }, "CALL_HIP_CRUD_API", false).execute(WebServicesUrls.COMBINED_CRUD);
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
