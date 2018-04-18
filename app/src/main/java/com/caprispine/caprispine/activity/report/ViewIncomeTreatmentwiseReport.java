package com.caprispine.caprispine.activity.report;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.fragment.report.IncomeBranchTreatWiseFragment;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.branch.BranchPOJO;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
import com.caprispine.caprispine.pojo.treatment.TreatmentPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewIncomeTreatmentwiseReport extends AppCompatActivity {

    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.spinner_treatment)
    Spinner spinner_treatment;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.ll_branch)
    LinearLayout ll_branch;

    List<BranchResultPOJO> branchResultPOJOS = new ArrayList<>();
    List<TreatmentPOJO> treatmentPOJOS = new ArrayList<>();

    String branch_id="";
    String treatment_id="";
    StaffPOJO staffPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_income_treatmentwise_report);
        ButterKnife.bind(this);
        staffPOJO= (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");

        getAllTreatment();

        spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branch_id=branchResultPOJOS.get(spinner_branch.getSelectedItemPosition()).getBranchId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_treatment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                treatment_id=treatmentPOJOS.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(branch_id.length()>0&&treatment_id.length()>0){
                    viewReportFragment(branch_id,treatment_id);
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"Please select fields properly");
                    return;
                }
            }
        });


        if(staffPOJO!=null){
            branch_id=staffPOJO.getBranchId();
            ll_branch.setVisibility(View.GONE);
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_GET_ALL_BRANCH", true).execute(WebServicesUrls.BRANCH_CRUD);
    }


    public void getAllTreatment() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "all_treatment"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                ResponseListPOJO<TreatmentPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<TreatmentPOJO>>() {
                }.getType());
                treatmentPOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    treatmentPOJOS.addAll(responseListPOJO.getResultList());
//                    Log.d(TagUtils.getTag(), "patient list:-" + patientList.toString());

                    List<String> treStringList = new ArrayList<>();
                    for (TreatmentPOJO treatmentPOJO : treatmentPOJOS) {
                        treStringList.add(treatmentPOJO.getName() + " (" + treatmentPOJO.getPrice() + ")");
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, treStringList);
                    spinner_treatment.setAdapter(spinnerArrayAdapter);

                } else {
                    ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                }

            }
        }, "GET_TREATMENT_API", true).execute(WebServicesUrls.TREATMENT_CRUD);
    }


    public void viewReportFragment(String branch_code,String treatment_id){
        IncomeBranchTreatWiseFragment incomeBranchWiseFragment = new IncomeBranchTreatWiseFragment(branch_code,treatment_id);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fram_main, incomeBranchWiseFragment, "incomeBranchWiseFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
