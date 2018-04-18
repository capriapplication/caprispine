package com.caprispine.caprispine.activity.report;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.adapter.report.ExpenseAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.expense.ExpensePOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewIncomeExpensetReport extends AppCompatActivity {
    private static final String GET_ALL_TRANSACTIONS = "get_all_transactions";
    String branch_id = "";
    String start_date = "";
    String end_date = "";
    @BindView(R.id.rv_transactions)
    RecyclerView rv_transactions;
    @BindView(R.id.btn_print)
    Button btn_print;
    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_income_expenset_report);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            branch_id = bundle.getString("branch_id");
            start_date = bundle.getString("start_date");
            end_date = bundle.getString("end_date");

            callExpenseAPI();
        } else {
            finish();
        }

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent=new Intent(ViewIncomeExpensetReport.this, IncomeReportPrintActivity.class);
                    intent.putExtra("type","expensereport");
                    intent.putExtra("branch_id",branch_id);
                    intent.putExtra("start_date", start_date);
                    intent.putExtra("end_date", end_date);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }


    public void callExpenseAPI() {
        try {
            ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();
            nameValuePairArrayList.add(new BasicNameValuePair("request_action", "expense_report"));
            nameValuePairArrayList.add(new BasicNameValuePair("start_date", start_date));
            nameValuePairArrayList.add(new BasicNameValuePair("end_date", end_date));
            nameValuePairArrayList.add(new BasicNameValuePair("branch_id", branch_id));
            new WebServiceBase(nameValuePairArrayList, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String[] msg) {
                    try {
                        ResponseListPOJO<ExpensePOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<ExpensePOJO>>() {
                        }.getType());
                        if (responseListPOJO.isSuccess()) {
                            ExpenseAdapter invoiceAdapter = new ExpenseAdapter(ViewIncomeExpensetReport.this, responseListPOJO.getResultList());
                            LinearLayoutManager horizontalLayoutManagaer
                                    = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            rv_transactions.setLayoutManager(horizontalLayoutManagaer);
                            rv_transactions.setHasFixedSize(true);
                            rv_transactions.setItemAnimator(new DefaultItemAnimator());
                            rv_transactions.setAdapter(invoiceAdapter);
                            tv_total_amount.setText("INR " + responseListPOJO.getMessage());
                        } else {
                            ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                        }
                    } catch (Exception e) {

                    }
                }
            }, GET_ALL_TRANSACTIONS, true).execute(WebServicesUrls.REPORT_CRUD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public void parseAllTransactions(String response){
//        Log.d(TagUtils.getTag(),"patients Transactions:-"+response);
//        try{
//            Gson gson=new Gson();
//            PatientAmount patientAmount=gson.fromJson(response,PatientAmount.class);
//            if(patientAmount.getSuccess().equals("true")){
//                List<PatientAmountPOJO> patientTransAdapters=patientAmount.getPatientAmountPOJOs();
//                Collections.reverse(patientTransAdapters);
//                PatientTransAdapter adapter = new PatientTransAdapter(this, null,patientTransAdapters);
//                LinearLayoutManager horizontalLayoutManagaer
//                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//                rv_transactions.setLayoutManager(horizontalLayoutManagaer);
//                rv_transactions.setHasFixedSize(true);
//                rv_transactions.setItemAnimator(new DefaultItemAnimator());
//                rv_transactions.setAdapter(adapter);
//
//                tv_total_amount.setText("Rs "+patientAmount.getTotal());
//            }else{
//                ToastClass.showShortToast(getApplicationContext(),"No Transaction Done.");
//                tv_total_amount.setText("Rs 0");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
//            tv_total_amount.setText("Rs 0");
//        }
//    }
}


