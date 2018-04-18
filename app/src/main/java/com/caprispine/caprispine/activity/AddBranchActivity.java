package com.caprispine.caprispine.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBranchActivity extends AppCompatActivity {
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.et_state)
    EditText et_state;
    @BindView(R.id.et_country)
    EditText et_country;
    @BindView(R.id.et_pincode)
    EditText et_pincode;
    @BindView(R.id.et_website)
    EditText et_website;
    BranchResultPOJO branchResultPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch);
        ButterKnife.bind(this);
        branchResultPOJO= (BranchResultPOJO) getIntent().getSerializableExtra("branchPOJO");

        if(branchResultPOJO!=null){
            btn_save.setText("Update");
            setValues();
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UtilityFunction.isDataValid(et_address,et_city,et_code,et_country,et_email,et_mobile,et_name,et_phone,et_pincode,et_state,et_website)){
                    saveBranch();
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"Please Enter all Fields Properly");
                }
            }
        });
    }

    public void setValues(){
        et_name.setText(branchResultPOJO.getBranchName());
        et_code.setText(branchResultPOJO.getBranchCode());
        et_mobile.setText(branchResultPOJO.getMobile());
        et_phone.setText(branchResultPOJO.getPhone());
        et_email.setText(branchResultPOJO.getEmail());
        et_address.setText(branchResultPOJO.getAddress());
        et_city.setText(branchResultPOJO.getCity());
        et_state.setText(branchResultPOJO.getState());
        et_country.setText(branchResultPOJO.getCountry());
        et_pincode.setText(branchResultPOJO.getPincode());
        et_website.setText(branchResultPOJO.getWebsite());
    }

    public void saveBranch(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("branch_name",et_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("branch_code",et_code.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("branch_status","1"));
        nameValuePairs.add(new BasicNameValuePair("mobile",et_mobile.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("phone",et_phone.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("email",et_email.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("address",et_address.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("city",et_city.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("state",et_state.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("country",et_country.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("pincode",et_pincode.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("website",et_website.getText().toString()));

        if(branchResultPOJO!=null){
            nameValuePairs.add(new BasicNameValuePair("request_action","update_branch"));
            nameValuePairs.add(new BasicNameValuePair("branch_id",branchResultPOJO.getBranchId()));
        }else{
            nameValuePairs.add(new BasicNameValuePair("request_action","create_branch"));
        }


        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
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
        },"CALL_SAVE_BRANCH",true).execute(WebServicesUrls.BRANCH_CRUD);
    }
}
