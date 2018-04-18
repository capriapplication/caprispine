package com.caprispine.caprispine.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.pojo.branch.BranchPOJO;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddExpenseActivity extends AppCompatActivity {
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.et_bill_number)
    EditText et_bill_number;
    @BindView(R.id.et_vendor_name)
    EditText et_vendor_name;
    @BindView(R.id.et_total_expense)
    EditText et_total_expense;
    @BindView(R.id.btn_save)
    Button btn_save;
    StaffPOJO staffPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        ButterKnife.bind(this);
        staffPOJO= (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExpense();
            }
        });

        if(staffPOJO!=null){
            branch_id=staffPOJO.getBranchId();
        }else{
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("request_action", "all_branch"));
            new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String[] msg) {
                    parseAllBranch(msg[1]);
                }
            }, "CALL_GET_ALL_BRANCH", true).execute(WebServicesUrls.BRANCH_CRUD);
        }

    }
    String branch_id="";
    public void saveExpense(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","insert_amount"));
        nameValuePairs.add(new BasicNameValuePair("exp_billno",et_bill_number.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("exp_vendor",et_vendor_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("exp_total",et_total_expense.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("exp_branch_id",branch_id));

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
        },"CALL_EXPENSE_API",true).execute(WebServicesUrls.EXPENSE_CRUD);
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

                if(branchResultPOJOS.size()>0){
                    branch_id=branchResultPOJOS.get(0).getBranchId();
                }

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                        getApplicationContext(), R.layout.dropsimpledown, braStringList);
                spinner_branch.setAdapter(spinnerArrayAdapter);

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
}
