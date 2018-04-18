package com.caprispine.caprispine.activity.report;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.caprispine.caprispine.fragment.report.NotesReportFragment;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.branch.BranchPOJO;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
import com.caprispine.caprispine.pojo.treatment.TreatmentPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesPrintActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private static final String GET_ALL_PATIENTS_API = "get_all_patients";
    private static final String GET_ALL_BRANCHES = "get_all_branches";
    private static final String CALL_SEARCH_PATIENTS = "call_search_patients";
    List<String> all_treatment;
    List<TreatmentPOJO> listadminTreatments;
    @BindView(R.id.spinner_patients)
    Spinner spinner_patients;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    List<BranchPOJO> branchPOJOList = new ArrayList<>();
    @BindView(R.id.btn_submit)
    Button btn_submit;
    String branch_code = "";
    String treatment_id = "";

    String therapist_id = "";
    String start_date = "", end_date = "";
    @BindView(R.id.et_from)
    EditText et_from;
    @BindView(R.id.iv_from)
    ImageView iv_from;
    @BindView(R.id.ll_branch)
    LinearLayout ll_branch;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;

    boolean is_from = true;
    String notetype="";
    StaffPOJO staffPOJO;
    List<PatientPOJO> patientResultPOJOArrayList = new ArrayList<>();
    String patient_id="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_print);
        ButterKnife.bind(this);
        staffPOJO= (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            notetype=bundle.getString("notetype");
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TagUtils.getTag(),"branc_code:-"+branch_code);
//                Log.d(TagUtils.getTag(),"therapist_id:-"+therapist_id);
//                Log.d(TagUtils.getTag(),"start_date:-"+start_date);
//                Log.d(TagUtils.getTag(),"end_date:-"+end_date);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {

                    viewReportFragment(branch_code, patient_id, start_date,notetype);
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
        iv_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_from = true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        NotesPrintActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Select from date");
            }
        });



        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_name.setText("");
            }
        });

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_name.getText().toString().length() > 0) {
                    getAllPatients(branch_code,et_name.getText().toString());
                } else {

                }
            }
        });
        if(staffPOJO!=null){
            branch_code=staffPOJO.getBranchId();
            ll_branch.setVisibility(View.GONE);
            getAllPatients(branch_code,"");
        }else {
            getAllBranches();
        }
    }

    List<BranchResultPOJO> branchResultPOJOS=new ArrayList<>();
    public void getAllBranches() {
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
                            getAllPatients(branchResultPOJOS.get(0).getBranchId(),"");
                        }

                        spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                branch_code=branchResultPOJOS.get(i).getBranchId();
                                getAllPatients(branchResultPOJOS.get(i).getBranchId(),"");
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


    public void getAllPatients(String branch_code,String string) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_all_patients"));
        nameValuePairs.add(new BasicNameValuePair("branch_id", branch_code));
        nameValuePairs.add(new BasicNameValuePair("string", string));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    ResponseListPOJO<PatientPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<PatientPOJO>>() {}.getType());
                    patientResultPOJOArrayList.clear();
                    if (responseListPOJO.isSuccess()) {

                        patientResultPOJOArrayList.addAll(responseListPOJO.getResultList());

                        List<String> patientStringList = new ArrayList<>();
                        for (PatientPOJO patientPOJO: patientResultPOJOArrayList) {
                            patientStringList.add(patientPOJO.getFirstName() + " " + patientPOJO.getLastName());
                        }

                        if(patientResultPOJOArrayList.size()>0){
                            patient_id=patientResultPOJOArrayList.get(0).getId();
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, patientStringList);
                        spinner_patients.setAdapter(spinnerArrayAdapter);

                        spinner_patients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                patient_id=patientResultPOJOArrayList.get(spinner_patients.getSelectedItemPosition()).getId();
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
        },GET_ALL_PATIENTS_API,true).execute(WebServicesUrls.USER_CRUD);
    }

    public void viewReportFragment(String branch_code, String patient_id, String start_date,String notetype) {
        NotesReportFragment incomeBranchWiseFragment = new NotesReportFragment(branch_code, patient_id, start_date,notetype);
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
        et_from.setText(day + "-" + month + "-" + year);
        start_date = day + "-" + month + "-" + year;

    }
}
