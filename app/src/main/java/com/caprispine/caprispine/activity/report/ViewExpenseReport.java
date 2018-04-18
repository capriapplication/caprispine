package com.caprispine.caprispine.activity.report;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.caprispine.caprispine.pojo.branch.BranchPOJO;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;
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

/**
 * Created by Emobi-Android-002 on 10/12/2016.
 */
public class ViewExpenseReport extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.btn_view_report)
    Button btn_view_report;
    @BindView(R.id.iv_to)
    ImageView iv_to;
    @BindView(R.id.et_to)
    EditText et_to;
    @BindView(R.id.et_from)
    EditText et_from;
    @BindView(R.id.iv_from)
    ImageView iv_from;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.ll_branch)
    LinearLayout ll_branch;

    StaffPOJO staffPOJO;
    String branch_id="";
    boolean is_from=true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expense);
        ButterKnife.bind(this);

        staffPOJO= (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");
        iv_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_from = true;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ViewExpenseReport.this,
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
                        ViewExpenseReport.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "select to date");
            }
        });

        btn_view_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date from_d = sdf.parse(et_from.getText().toString());
                    Date to_d = sdf.parse(et_to.getText().toString());

                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

                    if (from_d.before(new Date())) {
                        if (DateUtils.isSameDay(to_d, new Date()) || to_d.after(new Date())) {
                            Intent intent=new Intent(ViewExpenseReport.this, ViewIncomeExpensetReport.class);
                            intent.putExtra("branch_id",branch_id);
                            intent.putExtra("start_date",simpleDateFormat.format(from_d));
                            intent.putExtra("end_date",simpleDateFormat.format(to_d));
                            startActivity(intent);
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
        if(staffPOJO!=null){
            branch_id=staffPOJO.getBranchId();
            ll_branch.setVisibility(View.GONE);
        }else{
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
                            branch_id=branchResultPOJOS.get(0).getBranchId();
                        }

                        spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                branch_id=branchResultPOJOS.get(i).getBranchId();
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
        } else {
            et_to.setText(day + "-" + month + "-" + year);
        }
    }

//        ButterKnife.bind(this);
//        arrayList = new ArrayList<String>();
//        ed1 = (EditText) findViewById(R.id.ed1);
//        ed2 = (EditText) findViewById(R.id.ed2);
//        btn_submit= (Button) findViewById(R.id.btn_submit);
//        spinnerbranchloca= (Spinner) findViewById(R.id.spinnerbranchloca);
//
//
//        newaddress = AppPreferences.getInstance(getApplicationContext()).getUSER_BRANCH_CODE();
//        Log.e("item",newaddress);
//
//        new CatagoryViewAsynTask().execute();
//
//        spinnerbranchloca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                String address = spinnerbranchloca.getSelectedItem().toString();
//
//                String ad[]=address.split("\\(");
//                newaddress = ad[1];
//                newaddress =newaddress.replace(")", "");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        myCalendar = Calendar.getInstance();
//
//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
//            }
//
//        };
//
//        myCalendar1 = Calendar.getInstance();
//
//        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar1.set(Calendar.YEAR, year);
//                myCalendar1.set(Calendar.MONTH, monthOfYear);
//                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel1();
//            }
//
//        };
//
//        btn_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                try {
//                    Date from_d = sdf.parse(ed1.getText().toString());
//                    Date to_d = sdf.parse(ed2.getText().toString());
//
//                    if (from_d.before(new Date())) {
//                        if (DateUtils.isSameDay(to_d, new Date()) || to_d.after(new Date())) {
//                            startActivity(new Intent(ViewExpenseReport.this, ViewIncomeExpensetReport.class));
//                        } else {
//                            ToastClass.showShortToast(getApplicationContext(), "you have selected passed to date");
//                        }
//                    } else {
//                        ToastClass.showShortToast(getApplicationContext(), "You have selected coming from date");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    ToastClass.showShortToast(getApplicationContext(), "Invalid Date Formats");
//                }
//            }
//        });
//
//        /*setListener();
//        viewStaffApiCall();*/
//
//        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
//        ed1.setText( sdf.format( new Date() ));
//        ed2.setText( sdf.format( new Date() ));
//
//        iv_from.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(ViewExpenseReport.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//        iv_to.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(ViewExpenseReport.this, date1, myCalendar1
//                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
//                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//    }
//    private void updateLabel() {
//
//        String myFormat = "yyyy-MM-dd"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//        ed1.setText(sdf.format(myCalendar.getTime()));
//    }
//
//    private void updateLabel1() {
//
//        String myFormat = "yyyy-MM-dd"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//        ed2.setText(sdf.format(myCalendar1.getTime()));
//    }
//
//
//
//    @Override
//    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//    }
//
//
//
//    private void initProgressDialog(String loading) {
//        pDialog = new ProgressDialog(getApplicationContext());
//        pDialog.setMessage(loading);
//        pDialog.setCancelable(false);
//        pDialog.show();
//    }
//
//    private class CatagoryViewAsynTask extends AsyncTask<String, String, String> {
//        String id, catagoryName;
//
//
//        @Override
//        protected String doInBackground(String... params) {
////            URL url = new URL("23.22.9.33/SongApi/singer.php?action_type=Latest");
//                /*String json = Holder.CATAGOARY_URL;
//                String cont = Html.fromHtml(json).toString();*/
//            String content = HttpULRConnect.getData(ApiConfig.GetURL);
//            Log.e("DoInBackGround ---->", String.valueOf(content));
//            return content;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//
//            try {
//                Log.e("DoInBackGroundtr", String.valueOf(s));
//                ///     pDialog.dismiss();
////                Log.e("Post Method Call  here ....", "Method ...");
//                JSONArray jsonArray = new JSONArray(s);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
//                    Log.e("2", jsonObject2.toString());
//                    String branch_name = jsonObject2.getString("branch_name");
//                    String bracch_code = jsonObject2.getString("branch_code");
//                    //branch_code
////                    arrayList.add(bracch_code);
//
//                    detailapps = new InfoApps1();
//                    detailapps.setName(branch_name);
//                    detailapps.setId(bracch_code);
//                    arrayList.add(detailapps.getName() + "  " + "(" + detailapps.getId() + ")");
//
//                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
//                            getApplicationContext(), R.layout.dropsimpledown, arrayList);
//                    spinnerbranchloca.setAdapter(spinnerArrayAdapter);
//                }
//                }catch(Exception e){
//                    Log.e("error", e.toString());
//
//                }
//            }
//        }
}
