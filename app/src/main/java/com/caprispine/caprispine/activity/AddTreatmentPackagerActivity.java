package com.caprispine.caprispine.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.pojo.treatment.TreatmentPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTreatmentPackagerActivity extends AppCompatActivity {

    private static final String ADD_TREATMENT_API = "add_treatment_api";
    private static final String UPDATE_TREATMENT_API = "update_treatment_api";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.et_treatment_name)
    EditText et_treatment_name;
    @BindView(R.id.et_treatment_price)
    EditText et_treatment_price;

    TreatmentPOJO treatmentPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treatment_packager);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Treatment Plan");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        treatmentPOJO= (TreatmentPOJO) getIntent().getSerializableExtra("treatmentPOJO");

        if(treatmentPOJO!=null){
            btn_submit.setText("Update");
            et_treatment_name.setText(treatmentPOJO.getName());
            et_treatment_price.setText(treatmentPOJO.getPrice());
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isValid(et_treatment_name, et_treatment_price)) {
                    callAddTreatmentApi();
//                } else {
//                    ToastClass.showShortToast(getApplicationContext(), "Please Fill All Fields");
//                }
            }
        });
    }

    public void callAddTreatmentApi() {
        ArrayList<NameValuePair> nameValuePairList = new ArrayList<>();

        nameValuePairList.add(new BasicNameValuePair("name", et_treatment_name.getText().toString()));
        nameValuePairList.add(new BasicNameValuePair("price", et_treatment_price.getText().toString()));
        if(treatmentPOJO!=null){
            nameValuePairList.add(new BasicNameValuePair("id",treatmentPOJO.getId()));
            nameValuePairList.add(new BasicNameValuePair("request_action", "update_treatment"));
        }else{
            nameValuePairList.add(new BasicNameValuePair("request_action", "create_treatment"));
        }
        new WebServiceBase(nameValuePairList, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try {
                    JSONObject jsonObject = new JSONObject(msg[1]);
                    if (jsonObject.optBoolean("success")) {
                        if(treatmentPOJO!=null) {
                            ToastClass.showShortToast(getApplicationContext(), "Treatment updated successfully");
                        }else{
                            ToastClass.showShortToast(getApplicationContext(), "Treatment inserted successfully");
                        }
                        finish();
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, ADD_TREATMENT_API, true).execute(WebServicesUrls.TREATMENT_CRUD);
//        }
    }


    public boolean isValid(EditText... editText) {
        for (EditText editText1 : editText) {
            if (editText1.getText().toString().length() == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
