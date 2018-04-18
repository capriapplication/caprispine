package com.caprispine.caprispine.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.adapter.StaffAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.branch.BranchPOJO;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
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

public class ManageStaffActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.rv_staff)
    RecyclerView rv_staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_staff);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Manage Staff");

        attachAdapter();



    }

    @Override
    protected void onResume() {
        super.onResume();
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
                    getStaffs(branchResultPOJOS.get(0));
                }

                spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        getStaffs(branchResultPOJOS.get(position));
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

    public void getStaffs(BranchResultPOJO branchResultPOJO) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_all_staff"));
        nameValuePairs.add(new BasicNameValuePair("branch_id", branchResultPOJO.getBranchId()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                ResponseListPOJO<StaffPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<StaffPOJO>>() {
                }.getType());
                staffPOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    staffPOJOS.addAll(responseListPOJO.getResultList());
//                    Log.d(TagUtils.getTag(), "patient list:-" + patientList.toString());
                } else {
                    ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                }

                adapter.notifyDataSetChanged();
            }
        }, "GET_STAFF_API", true).execute(WebServicesUrls.USER_CRUD);
    }

    List<StaffPOJO> staffPOJOS=new ArrayList<>();
    StaffAdapter adapter;
    public void attachAdapter(){
        adapter = new StaffAdapter(this, staffPOJOS);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_staff.setLayoutManager(horizontalLayoutManagaer);
        rv_staff.setHasFixedSize(true);
        rv_staff.setItemAnimator(new DefaultItemAnimator());
        rv_staff.setAdapter(adapter);
    }
}
