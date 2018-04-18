package com.caprispine.caprispine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.Constants;
import com.caprispine.caprispine.Util.Pref;
import com.caprispine.caprispine.Util.StringUtils;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.pojo.user.AdminUserPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements WebServicesCallBack{

    private static final String CALL_LOGIN_API = "call_login_api";
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.tv_forgot_password)
    TextView tv_forgot_password;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_register)
    TextView tv_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callLoginAPI();
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    public void callLoginAPI(){
        if(et_email.getText().toString().length()>0&&
                et_password.getText().toString().length()>0) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("request_action", "login_user"));
            nameValuePairs.add(new BasicNameValuePair("email", et_email.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("password", et_password.getText().toString()));
            nameValuePairs.add(new BasicNameValuePair("device_token", Pref.GetDeviceToken(getApplicationContext())));
            nameValuePairs.add(new BasicNameValuePair("device_type", "android"));
            new WebServiceBase(nameValuePairs, this, this, CALL_LOGIN_API, true).execute(WebServicesUrls.USER_CRUD);
        }else{
            ToastClass.showShortToast(getApplicationContext(),"Please enter proper email and password");
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall=msg[0];
        String response=msg[1];
        Log.d(TagUtils.getTag(),apicall+" :- "+response);
        switch (apicall){
            case CALL_LOGIN_API:
                parseLoginResponse(response);
                break;
        }
    }

    public void parseLoginResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("success").equals("true")){

//                String userProfile=jsonObject.optJSONObject("result").toString();
//
//                UserPOJO userPOJO=new Gson().fromJson(userProfile,UserPOJO.class);
//

                if(jsonObject.optJSONObject("result").optString("user_type").equals("4")){
                    AdminUserPOJO adminUserPOJO=new Gson().fromJson(jsonObject.optJSONObject("result").toString(),AdminUserPOJO.class);
                    Constants.adminUserPOJO=adminUserPOJO;
                    Pref.SetStringPref(getApplicationContext(), StringUtils.ADMIN_POJO,jsonObject.optJSONObject("result").toString());
                    Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_ADMIN_LOGIN,true);
                    startActivity(new Intent(LoginActivity.this,AdminDashboardActivity.class).putExtra("adminPOJO",adminUserPOJO));
                    finishAffinity();
                }else if(jsonObject.optJSONObject("result").optString("user_type").equals("0")){
                    PatientPOJO patientPOJO=new Gson().fromJson(jsonObject.optJSONObject("result").toString(),PatientPOJO.class);
                    Constants.patientPOJO=patientPOJO;
                    Pref.SetStringPref(getApplicationContext(),StringUtils.PATIENT_POJO,jsonObject.optJSONObject("result").toString());
                    Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PATIENT_LOGIN,true);
                    startActivity(new Intent(LoginActivity.this,PatientDashBoardActivity.class).putExtra("patientPOJO",patientPOJO));
                    finishAffinity();
                }else if(jsonObject.optJSONObject("result").optString("user_type").equals("2")) {
                    TherapistPOJO therapistPOJO=new Gson().fromJson(jsonObject.optJSONObject("result").toString(),TherapistPOJO.class);
                    Constants.therapistPOJO=therapistPOJO;
                    Pref.SetStringPref(getApplicationContext(),StringUtils.THERAPIST_POJO,jsonObject.optJSONObject("result").toString());
                    Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_THERAPIST_LOGIN,true);
                    startActivity(new Intent(LoginActivity.this,TherapistDashboardActivity.class).putExtra("therapistPOJO",therapistPOJO));
                    finishAffinity();
                }else if(jsonObject.optJSONObject("result").optString("user_type").equals("1")) {
                    StaffPOJO staffPOJO=new Gson().fromJson(jsonObject.optJSONObject("result").toString(),StaffPOJO.class);
                    Constants.staffPOJO=staffPOJO;
                    Pref.SetStringPref(getApplicationContext(),StringUtils.STAFF_POJO,jsonObject.optJSONObject("result").toString());
                    Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_STAFF_LOGIN,true);
                    startActivity(new Intent(LoginActivity.this,StaffDashboardActivity.class).putExtra("staffPOJO",staffPOJO));
                    finishAffinity();
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"Invalid User");
                }
            }else{
                ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
