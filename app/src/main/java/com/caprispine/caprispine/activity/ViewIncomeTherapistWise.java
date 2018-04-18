package com.caprispine.caprispine.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.fragment.report.IncomeTherapistWiseFragment;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.branch.BranchPOJO;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
import com.caprispine.caprispine.pojo.treatment.TreatmentPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.commons.lang.time.DateUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewIncomeTherapistWise extends AppCompatActivity implements com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener  {

    private static final String GET_ALL_BRANCHES = "get_all_branches";
    List<String> all_treatment;
    List<TreatmentPOJO> listadminTreatments;
    @BindView(R.id.spinner_therapist)
    Spinner spinner_therapist;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    List<BranchResultPOJO> branchResultPOJOS = new ArrayList<>();
    List<TherapistPOJO> doctorResultPOJOList = new ArrayList<>();
    @BindView(R.id.btn_submit)
    Button btn_submit;
    String branch_code = "";
    String treatment_id = "";
    private static final String GET_DOCTORS_API = "get_doctors_api";
    String therapist_id = "";
    String start_date = "", end_date = "";
    @BindView(R.id.et_from)
    EditText et_from;
    @BindView(R.id.iv_from)
    ImageView iv_from;
    @BindView(R.id.et_to)
    EditText et_to;
    @BindView(R.id.iv_to)
    ImageView iv_to;
    @BindView(R.id.ll_branch)
    LinearLayout ll_branch;
    @BindView(R.id.ll_therapist)
    LinearLayout ll_therapist;

    boolean is_from = true;

    TherapistPOJO therapistPOJO;
    StaffPOJO staffPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_income_therapist_wise);
        ButterKnife.bind(this);

        therapistPOJO= (TherapistPOJO) getIntent().getSerializableExtra("therapistPOJO");
        staffPOJO= (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TagUtils.getTag(), "branc_code:-" + branch_code);
                Log.d(TagUtils.getTag(), "therapist_id:-" + therapist_id);
                Log.d(TagUtils.getTag(), "start_date:-" + start_date);
                Log.d(TagUtils.getTag(), "end_date:-" + end_date);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date from_d = sdf.parse(start_date);
                    Date to_d = sdf.parse(end_date);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    if (from_d.before(new Date())) {
                        if (DateUtils.isSameDay(to_d, new Date()) || to_d.after(new Date())) {
                            if (branch_code.length() > 0 && therapist_id.length() > 0) {
                                viewReportFragment(branch_code, therapist_id, simpleDateFormat.format(from_d), simpleDateFormat.format(to_d));
                            } else {
                                ToastClass.showShortToast(getApplicationContext(), "Invalid Branch code");
                                return;
                            }
                        } else {
                            ToastClass.showShortToast(getApplicationContext(), "you have selected passed to date");
                        }
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), "You have selected coming from date");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastClass.showShortToast(getApplicationContext(), "Invalid Date Formats");
                }
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        et_from.setText(sdf.format(new Date()));
        start_date = sdf.format(new Date());
        end_date = sdf.format(new Date());
        et_to.setText(sdf.format(new Date()));
        iv_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_from = true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ViewIncomeTherapistWise.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Select from date");
            }
        });

        iv_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_from = false;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ViewIncomeTherapistWise.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "select to date");
            }
        });

//        if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("4")) {
//            new GetWebServicesFragment(this, GET_ALL_BRANCHES).execute(ApiConfig.GetURL);
//        } else {
//            ll_branch.setVisibility(View.GONE);
//            branch_code = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
//            if (AppPreferences.getInstance(getApplicationContext()).getUserType().equals("2")) {
//                ll_therapist.setVisibility(View.GONE);
//                therapist_id = AppPreferences.getInstance(getApplicationContext()).getUserID();
//            } else {
//                getTherapists(AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE());
//            }
//        }

        if(therapistPOJO!=null){
            branch_code=therapistPOJO.getBranchId();
            therapist_id=therapistPOJO.getId();
            ll_branch.setVisibility(View.GONE);
            ll_therapist.setVisibility(View.GONE);
        }else if(staffPOJO!=null) {
            branch_code=staffPOJO.getBranchId();
            ll_branch.setVisibility(View.GONE);
            getTherapists(branch_code);
        }else{
            getBranches();
        }
    }


    public void getBranches(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "all_branch"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try {
                    Gson gson = new Gson();
                    BranchPOJO branchPOJO = gson.fromJson(msg[1], BranchPOJO.class);
                    if (branchPOJO.getSuccess().equals("true")) {
                        branchResultPOJOS.clear();
                        branchResultPOJOS.addAll(branchPOJO.getBranchResultPOJOS());
                        List<String> braStringList = new ArrayList<>();
                        for (BranchResultPOJO branchResultPOJO : branchPOJO.getBranchResultPOJOS()) {
                            braStringList.add(branchResultPOJO.getBranchName() + " (" + branchResultPOJO.getBranchCode() + ")");
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, braStringList);
                        spinner_branch.setAdapter(spinnerArrayAdapter);


                        if(branchResultPOJOS.size()>0){
                            branch_code=branchResultPOJOS.get(0).getBranchId();
                            getTherapists(branchResultPOJOS.get(0).getBranchId());
                        }

                        spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                branch_code=branchResultPOJOS.get(i).getBranchId();
                                getTherapists(branchResultPOJOS.get(i).getBranchId());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_GET_ALL_BRANCH", true).execute(WebServicesUrls.BRANCH_CRUD);
    }



    public void getTherapists(String branch_code) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_all_therapist"));
        nameValuePairs.add(new BasicNameValuePair("branch_id", branch_code));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    ResponseListPOJO<TherapistPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<TherapistPOJO>>() {}.getType());
                    doctorResultPOJOList.clear();
                    if (responseListPOJO.isSuccess()) {

                        doctorResultPOJOList.addAll(responseListPOJO.getResultList());

                        List<String> therapistStringList = new ArrayList<>();
                        for (TherapistPOJO therapistPOJO : doctorResultPOJOList) {
                            therapistStringList.add(therapistPOJO.getFirstName() + " " + therapistPOJO.getLastName());
                        }

                        if(doctorResultPOJOList.size()>0){
                            therapist_id=doctorResultPOJOList.get(0).getId();
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, therapistStringList);
                        spinner_therapist.setAdapter(spinnerArrayAdapter);

                        spinner_therapist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                therapist_id=doctorResultPOJOList.get(spinner_therapist.getSelectedItemPosition()).getId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }else{
                        ToastClass.showShortToast(getApplicationContext(),responseListPOJO.getMessage());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },GET_DOCTORS_API,true).execute(WebServicesUrls.USER_CRUD);
    }

    public void viewReportFragment(String branch_code, String treatment_id, String start_date, String end_date) {
        IncomeTherapistWiseFragment incomeBranchWiseFragment = new IncomeTherapistWiseFragment(branch_code, therapist_id, start_date, end_date);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fram_main, incomeBranchWiseFragment, "incomeBranchWiseFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }




    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.d(TagUtils.getTag(), "date:-" + dayOfMonth + "-" + monthOfYear + "-" + year);
        String day = "";
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }
        String month = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }
        if (is_from) {
            et_from.setText(day + "-" + month + "-" + year);
            start_date = day + "-" + month + "-" + year;
        } else {
            et_to.setText(day + "-" + month + "-" + year);
            end_date = day + "-" + month + "-" + year;
        }
    }
}

