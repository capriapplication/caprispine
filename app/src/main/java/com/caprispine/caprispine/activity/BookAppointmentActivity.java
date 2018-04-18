package com.caprispine.caprispine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.branch.BranchPOJO;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
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

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookAppointmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.spinner_doctor)
    Spinner spinner_doctor;
    @BindView(R.id.et_reason)
    EditText et_reason;
    @BindView(R.id.et_date)
    EditText et_date;
    @BindView(R.id.rv_appointment_time)
    RecyclerView rv_appointment_time;

    @BindView(R.id.btn_select_date)
    Button btn_select_date;
    @BindView(R.id.rl_patients)
    RelativeLayout rl_patients;
    @BindView(R.id.rl_doctors)
    RelativeLayout rl_doctors;
    @BindView(R.id.rl_branch)
    RelativeLayout rl_branch;
    @BindView(R.id.spinner_patients)
    Spinner spinner_patients;

    String branch_code = "";
    private String therapist_id = "";
    String patient_id = "";
    TherapistPOJO therapistPOJO;
    PatientPOJO patientPOJO;
    StaffPOJO staffPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        therapistPOJO = (TherapistPOJO) getIntent().getSerializableExtra("therapistPOJO");
        staffPOJO = (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");

        et_date.setText(UtilityFunction.getddMMYYYY());

        btn_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        BookAppointmentActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
//
        if (patientPOJO != null) {
            Log.d(TagUtils.getTag(), "patient pojo:-" + patientPOJO.toString());
            patient_id = patientPOJO.getId();
            branch_code = patientPOJO.getBranchId();
            getAssignedTHerapist();
            rl_branch.setVisibility(View.GONE);
            rl_patients.setVisibility(View.GONE);
            rl_doctors.setVisibility(View.GONE);
        } else if (therapistPOJO != null) {
            branch_code = therapistPOJO.getBranchId();
            therapist_id = therapistPOJO.getId();
            rl_branch.setVisibility(View.GONE);
            rl_doctors.setVisibility(View.GONE);
            getAllocatedPatients();
            showTimings(BookAppointmentActivity.this.therapistPOJO.getId()
                    , BookAppointmentActivity.this.therapistPOJO.getStartingTime()
                    , BookAppointmentActivity.this.therapistPOJO.getEndTime());
        } else if(staffPOJO!=null){
            branch_code = staffPOJO.getBranchId();
            rl_branch.setVisibility(View.GONE);
            getbranchdoctors(branch_code);
        } else{
            getAllBranches();
        }
//
//        }

        et_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_date.getText().toString().length() > 0) {
                    if (therapistPOJO != null) {
                        showTimings(therapist_id, therapistPOJO.getStartingTime(), therapistPOJO.getEndTime());
                    }
                }
            }
        });
    }

    List<PatientPOJO> patientList = new ArrayList<>();

    public void getAllocatedPatients() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_allocated_patient"));
        nameValuePairs.add(new BasicNameValuePair("therapist_id", therapist_id));

        new ResponseListWebservice<PatientPOJO>(nameValuePairs, getApplicationContext(), new ResponseListCallback<PatientPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PatientPOJO> responseListPOJO) {
                try {
                    patientList.clear();
                    if (responseListPOJO.isSuccess()) {
                        patientList.addAll(responseListPOJO.getResultList());

                        if (patientList.size() > 0) {
                            patient_id = patientList.get(0).getId();
                        }
                        List<String> patientStringList = new ArrayList<>();
                        for (PatientPOJO patientPOJO : patientList) {
                            patientStringList.add(patientPOJO.getFirstName() + " " + patientPOJO.getLastName());
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, patientStringList);
                        spinner_patients.setAdapter(spinnerArrayAdapter);


                        spinner_patients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                patient_id = patientList.get(i).getId();
//                                if(BookAppointmentActivity.this.therapistPOJO!=null) {
//                                    showTimings(BookAppointmentActivity.this.therapistPOJO.getId()
//                                            , BookAppointmentActivity.this.therapistPOJO.getStartingTime()
//                                            , BookAppointmentActivity.this.therapistPOJO.getEndTime());
//                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });


                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                        List<String> patientStringList = new ArrayList<>();
                        for (PatientPOJO patientPOJO : patientList) {
                            patientStringList.add(patientPOJO.getFirstName() + " " + patientPOJO.getLastName());
                        }

                        patient_id = "";

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, patientStringList);
                        spinner_patients.setAdapter(spinnerArrayAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, PatientPOJO.class, "GET_PATIENT_LIST", false).execute(WebServicesUrls.PATIENT_ALLOCATION_CRUD);

    }

//    TherapistPOJO assignedTherapistPOJO;

    public void getAssignedTHerapist() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_patient_therapist"));
        nameValuePairs.add(new BasicNameValuePair("patient_id", patient_id));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try {
                    JSONObject jsonObject = new JSONObject(msg[1]);
                    if (jsonObject.optBoolean("success")) {
                        TherapistPOJO therapistPOJO = new Gson().fromJson(jsonObject.optJSONObject("result").toString(), TherapistPOJO.class);
                        BookAppointmentActivity.this.therapistPOJO = therapistPOJO;
                        therapist_id = therapistPOJO.getId();

                        showTimings(BookAppointmentActivity.this.therapistPOJO.getId()
                                , BookAppointmentActivity.this.therapistPOJO.getStartingTime()
                                , BookAppointmentActivity.this.therapistPOJO.getEndTime());
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "GET_THERAPIST_ID", true).execute(WebServicesUrls.PATIENT_ALLOCATION_CRUD);

    }

    public void getAllBranches() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "all_branch"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                parseAllBranch(msg[1]);
            }
        }, "CALL_GET_ALL_BRANCH", true).execute(WebServicesUrls.BRANCH_CRUD);
    }


    List<BranchResultPOJO> branchResultPOJOS = new ArrayList<>();

    public void parseAllBranch(String response) {
        try {
            Gson gson = new Gson();
            BranchPOJO branchPOJO = gson.fromJson(response, BranchPOJO.class);
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

                if (branchResultPOJOS.size() > 0) {
                    branch_code = branchResultPOJOS.get(0).getBranchId();
                }

                spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        branch_code = branchResultPOJOS.get(position).getBranchId();
                        Log.d(TagUtils.getTag(), "called");
                        getbranchdoctors(branch_code);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<TherapistPOJO> doctorResultPOJOList = new ArrayList<>();

    private void getbranchdoctors(String branch_code) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_all_therapist"));
        nameValuePairs.add(new BasicNameValuePair("branch_id", branch_code));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try {
                    ResponseListPOJO<TherapistPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<TherapistPOJO>>() {
                    }.getType());
                    doctorResultPOJOList.clear();
                    if (responseListPOJO.isSuccess()) {
                        rv_appointment_time.setVisibility(View.VISIBLE);
                        doctorResultPOJOList.addAll(responseListPOJO.getResultList());

                        List<String> therapistStringList = new ArrayList<>();
                        for (TherapistPOJO therapistPOJO : doctorResultPOJOList) {
                            therapistStringList.add(therapistPOJO.getFirstName() + " " + therapistPOJO.getLastName());
                        }

                        if (doctorResultPOJOList.size() > 0) {
                            therapist_id = doctorResultPOJOList.get(0).getId();
                            showTimings(doctorResultPOJOList.get(0).getId()
                                    , doctorResultPOJOList.get(0).getStartingTime()
                                    , doctorResultPOJOList.get(0).getEndTime());
                            getAllocatedPatients();
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, therapistStringList);
                        spinner_doctor.setAdapter(spinnerArrayAdapter);

                        spinner_doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                therapist_id = doctorResultPOJOList.get(spinner_doctor.getSelectedItemPosition()).getId();

                                showTimings(doctorResultPOJOList.get(i).getId()
                                        , doctorResultPOJOList.get(i).getStartingTime()
                                        , doctorResultPOJOList.get(i).getEndTime());
                                getAllocatedPatients();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                        List<String> therapistStringList = new ArrayList<>();
                        for (TherapistPOJO therapistPOJO : doctorResultPOJOList) {
                            therapistStringList.add(therapistPOJO.getFirstName() + " " + therapistPOJO.getLastName());
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, therapistStringList);
                        spinner_doctor.setAdapter(spinnerArrayAdapter);
                        therapist_id = "";
                        getAllocatedPatients();
                        rv_appointment_time.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "GET_DOCTORS_API", true).execute(WebServicesUrls.USER_CRUD);
    }


    public void showTimings(String doctor_id, String start_time, String end_time) {
        if (start_time.length() > 0 && end_time.length() > 0) {
            callBookedAppointmentsAPI(doctor_id);
        } else {
            ToastClass.showShortToast(getApplicationContext(), "Please select another doctor");
        }
    }

    List<String> all_times;
    HorizontalAdapter adapter;

    private void callBookedAppointmentsAPI(String id) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date d = sdf.parse(et_date.getText().toString());
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayofweek == 1) {
                ToastClass.showShortToast(getApplicationContext(), "You cannot Book Appointment on sunday");
            } else {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("request_action", "get_therapist_appointments"));
                nameValuePairs.add(new BasicNameValuePair("therapist_id", id));
                nameValuePairs.add(new BasicNameValuePair("booking_date", UtilityFunction.getParsedDate(et_date.getText().toString())));
                new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String[] msg) {
                        try {
                            JSONObject jsonObject = new JSONObject(msg[1]);
                            if (jsonObject.optBoolean("success")) {

                                JSONArray jsonArray = jsonObject.optJSONArray("result");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    list_removed_position.add(jsonArray.optJSONObject(i).optString("booking_time").replace(":00", ""));
                                }
                                TherapistPOJO therapistPOJO;
                                if (BookAppointmentActivity.this.therapistPOJO != null) {
                                    therapistPOJO = BookAppointmentActivity.this.therapistPOJO;
                                } else {
                                    therapistPOJO = doctorResultPOJOList.get(spinner_doctor.getSelectedItemPosition());
                                }
                                all_times = UtilityFunction.getDifference(therapistPOJO.getStartingTime(), therapistPOJO.getEndTime());
                                all_times.removeAll(list_removed_position);
                                adapter = new HorizontalAdapter(BookAppointmentActivity.this, all_times);
                                GridLayoutManager layoutManager = new GridLayoutManager(BookAppointmentActivity.this, 5);
                                rv_appointment_time.setHasFixedSize(true);
                                rv_appointment_time.setLayoutManager(layoutManager);
                                rv_appointment_time.setAdapter(adapter);
                            } else {
                                TherapistPOJO therapistPOJO;
                                if (BookAppointmentActivity.this.therapistPOJO != null) {
                                    therapistPOJO = BookAppointmentActivity.this.therapistPOJO;
                                } else {
                                    therapistPOJO = doctorResultPOJOList.get(spinner_doctor.getSelectedItemPosition());
                                }
                                all_times = UtilityFunction.getDifference(therapistPOJO.getStartingTime(), therapistPOJO.getEndTime());
                                adapter = new HorizontalAdapter(BookAppointmentActivity.this, all_times);
                                GridLayoutManager layoutManager = new GridLayoutManager(BookAppointmentActivity.this, 5);
                                rv_appointment_time.setHasFixedSize(true);
                                rv_appointment_time.setLayoutManager(layoutManager);
                                rv_appointment_time.setAdapter(adapter);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "GET_BOOKED_APPOINTMENTS", true).execute(WebServicesUrls.APPOINTMENT_CRUD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "";
        String month = "";

        if (dayOfMonth < 10) {
            date = "0" + dayOfMonth;
        } else {
            date = String.valueOf(dayOfMonth);
        }

        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }

        String dates = date + "-" + month + "-" + year;

        Log.d(TagUtils.getTag(), "dates:-" + dates);
        et_date.setText(dates);
        try {
            callBookedAppointmentsAPI(doctorResultPOJOList.get(spinner_doctor.getSelectedItemPosition()).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    List<String> list_removed_position = new ArrayList<>();

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<String> horizontalList;
        Boolean allvalue;
        private Context context;
        Activity activity;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView tv_time;
            public LinearLayout ll_time;

            public MyViewHolder(View view) {
                super(view);
                tv_time = (TextView) view.findViewById(R.id.tv_time);
                ll_time = (LinearLayout) view.findViewById(R.id.ll_time);
                allvalue = false;
            }
        }

        public HorizontalAdapter(Context context, List<String> horizontalList) {
            this.horizontalList = horizontalList;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.infalte_time, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.tv_time.setText(horizontalList.get(position));
            holder.ll_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (et_reason.getText().toString().length() > 0) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setMessage("Do you want to book appointment at " + horizontalList.get(position));
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        String time_selected = horizontalList.get(position);
                                        String date = et_date.getText().toString();

                                        String date_time = date + " " + time_selected;

                                        try {
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                                            Date selected_date = sdf.parse(date_time);
                                            Date current_date = new Date();
                                            Log.d(TagUtils.getTag(), "selected:-" + selected_date.toString());
                                            Log.d(TagUtils.getTag(), "current:-" + current_date.toString());
                                            if (current_date.before(selected_date)) {
                                                try {
                                                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                                                    Date d = sdf1.parse(et_date.getText().toString());
                                                    Calendar cal = Calendar.getInstance();
                                                    cal.setTime(d);
                                                    int dayofweek = cal.get(Calendar.DAY_OF_WEEK);

//                                                    if (dayofweek == 1) {
//                                                        ToastClass.showShortToast(getApplicationContext(), "You cannot book appointment at sunday");
//                                                    } else {

//                                                            Intent i = new Intent(BookAppointmentActivity.this, Pa.class);
//                                                            String url = "http://caprispine.in/payumoney/PayUMoney_form.php?amount=500" +
//                                                                    "&name=" + AppPreferences.getInstance(getApplicationContext()).getFirstName()
//                                                                    + " " + AppPreferences.getInstance(getApplicationContext()).getLastName()
//                                                                    + "&email=" + AppPreferences.getInstance(getApplicationContext()).getEmail() + "&phone="
//                                                                    + AppPreferences.getInstance(getApplicationContext()).getMobile() + "&productinfo=appointment";
//                                                            i.putExtra("url", url);
//                                                            i.putExtra("time", horizontalList.get(position));
//                                                            startActivityForResult(i, 105);
                                                        callAddAppointmentAPI(horizontalList.get(position));
//                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    ToastClass.showShortToast(getApplicationContext(), "Invalid Date");
                                                }
                                            } else {
                                                ToastClass.showShortToast(getApplicationContext(), "You selected passed date and time");
                                            }

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                            ToastClass.showShortToast(getApplicationContext(), "Please Select Proper date time");
                                        }
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), "Please Enter the reason first");
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }

    String appointment_selected_time = "";

    public void callAddAppointmentAPI(final String time) {
        try {
            if (therapist_id.length() > 0 && patient_id.length() > 0 && branch_code.length() > 0) {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("request_action", "insert_appointment"));
                nameValuePairs.add(new BasicNameValuePair("patient_id", patient_id));
                nameValuePairs.add(new BasicNameValuePair("therapist_id", therapist_id));
                nameValuePairs.add(new BasicNameValuePair("booking_date", UtilityFunction.getParsedDate(et_date.getText().toString())));
                nameValuePairs.add(new BasicNameValuePair("booking_time", time));
                nameValuePairs.add(new BasicNameValuePair("reason", "First Visit"));
                nameValuePairs.add(new BasicNameValuePair("branch_id", branch_code));

                appointment_selected_time = time;
                et_reason.setText("");
                new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String[] msg) {
                        try {
                            if (new JSONObject(msg[1]).optBoolean("success")) {
                                all_times.remove(time);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "ADD_APPOINTMENT_API", true).execute(WebServicesUrls.APPOINTMENT_CRUD);
            } else {
                ToastClass.showShortToast(getApplicationContext(), "Please Select Fields Properly");
            }
        } catch (Exception e) {
            ToastClass.showShortToast(getApplicationContext(), "sorry appointment cannot booked at this time.");
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
