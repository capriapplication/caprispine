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
import com.caprispine.caprispine.adapter.report.IncomeAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.report.IncomePOJO;
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

public class IncomeFromPatientWalletActivity extends AppCompatActivity {
    private static final String GET_ALL_TRANSACTIONS = "get_all_transactions";
    String branch_code="";
    String start_date="";
    String end_date="";
    String payment_mode="";
    @BindView(R.id.rv_transactions)
    RecyclerView rv_transactions;
    @BindView(R.id.btn_print)
    Button btn_print;
    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_from_patient_wallet);
        ButterKnife.bind(this);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            branch_code=bundle.getString("branch_code");
            start_date=bundle.getString("start_date");
            end_date=bundle.getString("end_date");
            payment_mode=bundle.getString("payment_mode");

            callPatientTransactionAPI();
        }
        else{
            finish();
        }

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent=new Intent(IncomeFromPatientWalletActivity.this, IncomeReportPrintActivity.class);
                    intent.putExtra("type","incomefrompatient");
                    intent.putExtra("branch_id",branch_code);
                    String from_date=start_date;
                    String to_date=end_date;
                    intent.putExtra("start_date", from_date.split("-")[2] + "-" + from_date.split("-")[1] + "-" + from_date.split("-")[0]);
                    intent.putExtra("end_date", to_date.split("-")[2] + "-" + to_date.split("-")[1] + "-" + to_date.split("-")[0]);
                    intent.putExtra("payment_mode",payment_mode);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }



    public void callPatientTransactionAPI(){
        try {
            ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();
            nameValuePairArrayList.add(new BasicNameValuePair("request_action", "income_branch_patient_wise"));
            nameValuePairArrayList.add(new BasicNameValuePair("payment_mode", payment_mode));
            nameValuePairArrayList.add(new BasicNameValuePair("branch_id", branch_code));
            nameValuePairArrayList.add(new BasicNameValuePair("start_date", start_date.split("-")[2]+"-"+start_date.split("-")[1]+"-"+start_date.split("-")[0]));
            nameValuePairArrayList.add(new BasicNameValuePair("end_date", end_date.split("-")[2]+"-"+end_date.split("-")[1]+"-"+end_date.split("-")[0]));
            new WebServiceBase(nameValuePairArrayList, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String[] msg) {
                    try {
                        ResponseListPOJO<IncomePOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<IncomePOJO>>() {
                        }.getType());
                        if (responseListPOJO.isSuccess()) {
                            IncomeAdapter invoiceAdapter = new IncomeAdapter(IncomeFromPatientWalletActivity.this, responseListPOJO.getResultList());
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
        }catch (Exception e){
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


