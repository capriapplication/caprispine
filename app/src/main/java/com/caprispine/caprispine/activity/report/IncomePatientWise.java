package com.caprispine.caprispine.activity.report;

import android.os.Bundle;
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
import com.caprispine.caprispine.fragment.report.IncomePatientWiseFragment;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.branch.BranchPOJO;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
import com.caprispine.caprispine.pojo.treatment.TreatmentPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.caprispine.caprispine.webservice.ResponseListCallback;
import com.caprispine.caprispine.webservice.ResponseListWebservice;
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

public class IncomePatientWise extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
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
    @BindView(R.id.et_to)
    EditText et_to;
    @BindView(R.id.iv_to)
    ImageView iv_to;
    @BindView(R.id.ll_branch)
    LinearLayout ll_branch;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.ll_selection)
    LinearLayout ll_selection;
    @BindView(R.id.ll_search_patients)
    LinearLayout ll_search_patients;

    boolean is_from = true;

    List<PatientPOJO> patientResultPOJOArrayList = new ArrayList<>();
    String patient_id = "";
    PatientPOJO patientPOJO;
    TherapistPOJO therapistPOJO;
    StaffPOJO staffPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_patient_wise);
        ButterKnife.bind(this);

        therapistPOJO= (TherapistPOJO) getIntent().getSerializableExtra("therapistPOJO");
        staffPOJO= (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");
        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        if(patientPOJO!=null){
            ll_selection.setVisibility(View.GONE);
            patient_id=patientPOJO.getId();
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
                    Date from_d = sdf.parse(start_date);
                    Date to_d = sdf.parse(end_date);
                    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
                    if (from_d.before(new Date())) {
                        if (DateUtils.isSameDay(to_d, new Date()) || to_d.after(new Date())) {
                            if (branch_code.length() > 0 && patient_id.length() > 0) {
                                viewReportFragment(branch_code, patient_id, simpleDateFormat.format(from_d), simpleDateFormat.format(to_d));
                            } else {
                                ToastClass.showShortToast(getApplicationContext(), "Invalid Branch code or patientID");
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
                        IncomePatientWise.this,
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
                        IncomePatientWise.this,
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
//            getAllPatients(branch_code);
//        }
        if(therapistPOJO!=null){
            therapist_id=therapistPOJO.getId();
            branch_code=therapistPOJO.getBranchId();
            ll_branch.setVisibility(View.GONE);
            ll_search_patients.setVisibility(View.GONE);
            getAllocatedPatients();
        }else if(staffPOJO!=null) {
            branch_code=staffPOJO.getBranchId();
            ll_branch.setVisibility(View.GONE);
            getAllPatients(branch_code,"");
        }else{
            getAllBranches();
        }

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
    }

    private void getAllocatedPatients() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_allocated_patient"));
        nameValuePairs.add(new BasicNameValuePair("therapist_id", therapistPOJO.getId()));
        new ResponseListWebservice<PatientPOJO>(nameValuePairs, this, new ResponseListCallback<PatientPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PatientPOJO> responseListPOJO) {
               try{
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
        }, PatientPOJO.class, "GET_ALLOCATED_PATIENTS", true).execute(WebServicesUrls.PATIENT_ALLOCATION_CRUD);
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

    public void viewReportFragment(String branch_code, String patient_id, String start_date, String end_date) {
        IncomePatientWiseFragment incomeBranchWiseFragment = new IncomePatientWiseFragment(branch_code, patient_id, start_date, end_date);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fram_main, incomeBranchWiseFragment, "incomeBranchWiseFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void setPatientSpinner(final List<PatientPOJO> patientResultPOJOArrayList) {

        if (patientResultPOJOArrayList.size() > 0) {
            final List<String> patientStringPOJOList = new ArrayList<>();
            for (PatientPOJO patientResultPOJO : patientResultPOJOArrayList) {
                patientStringPOJOList.add(patientResultPOJO.getFirstName() + " " + patientResultPOJO.getLastName());
            }

            if (patientStringPOJOList.size() > 0) {
                patient_id = patientResultPOJOArrayList.get(0).getId();
            }
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.dropsimpledown, patientStringPOJOList);
            spinner_patients.setAdapter(spinnerArrayAdapter);

            spinner_patients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    patient_id = patientResultPOJOArrayList.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            ToastClass.showShortToast(getApplicationContext(), "No Patients Found");
            patient_id = "";
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.dropsimpledown, new ArrayList<String>());
            spinner_patients.setAdapter(spinnerArrayAdapter);
            spinner_patients.setOnItemSelectedListener(null);
        }
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
