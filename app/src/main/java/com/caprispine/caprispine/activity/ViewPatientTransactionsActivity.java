package com.caprispine.caprispine.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.adapter.WalletTransactionsAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.ResponsePOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.wallet.PatientWalletPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPatientTransactionsActivity extends AppCompatActivity {

    PatientPOJO patientPOJO;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_transactions)
    RecyclerView rv_transactions;
    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_transactions);
        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        ButterKnife.bind(this);
        setPatientAdapter();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Patient Bills");

        getPatientWallet();
    }

    public void getPatientWallet(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_all_transactions"));
        nameValuePairs.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                ResponseListPOJO<PatientWalletPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<PatientWalletPOJO>>() {
                }.getType());
                patientResultPOJOs.clear();
                if (responseListPOJO.isSuccess()) {
                    patientResultPOJOs.addAll(responseListPOJO.getResultList());
                    tv_total_amount.setText("Rs. " + responseListPOJO.getResultList().get(0).getWalletAmount());
                } else {
                    ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                }
                adapter.notifyDataSetChanged();
            }
        }, "CALL_PATIENT_WALLET", true).execute(WebServicesUrls.PATIENT_WALLET);
    }

    List<PatientWalletPOJO> patientResultPOJOs = new ArrayList<>();
    WalletTransactionsAdapter adapter;

    public void setPatientAdapter() {
        if (patientResultPOJOs.size() > 0) {

        } else {
            ToastClass.showShortToast(getApplicationContext(), "No Patients Found");
        }
        adapter = new WalletTransactionsAdapter(this, null, patientResultPOJOs, patientPOJO);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        rv_transactions.setLayoutManager(horizontalLayoutManagaer);
        rv_transactions.setHasFixedSize(true);
        rv_transactions.setItemAnimator(new DefaultItemAnimator());
        rv_transactions.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_amount:
                showPatientWallerDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showPatientWallerDialog() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "latest_amount"));
        nameValuePairs.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                ResponsePOJO<PatientWalletPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponsePOJO<PatientWalletPOJO>>() {
                }.getType());
                patientResultPOJOs.clear();
                if (responseListPOJO.isSuccess()) {
                    showDialog(responseListPOJO.getResult().getWalletAmount());
                } else {
                    showDialog("");
//                    ToastClass.showShortToast(getApplicationContext(),responseListPOJO.getMessage());
                }
            }
        }, "GET_PATIENT_LAST_AMOUNT", true).execute(WebServicesUrls.PATIENT_WALLET);
    }


    public void showDialog(final String amount) {
        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_patient_amount_add);
        dialog1.setTitle("Wallet Amount");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_add = (Button) dialog1.findViewById(R.id.btn_add);
        final EditText et_amount = (EditText) dialog1.findViewById(R.id.et_amount);
        final EditText et_tans_id = (EditText) dialog1.findViewById(R.id.et_tans_id);
        final TextView tv_patient_wallet = (TextView) dialog1.findViewById(R.id.tv_patient_wallet);
        final RadioGroup rg_mode = (RadioGroup) dialog1.findViewById(R.id.rg_mode);

        if (amount.length() > 0) {
            tv_patient_wallet.setText("Balance Amount :- " + amount);
        } else {
            tv_patient_wallet.setText("No Balance in patient Account");
        }

        final String[] mode = {"cash"};

        rg_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_cash) {
                    mode[0] = "1";
                    et_tans_id.setVisibility(View.GONE);
                } else if (checkedId == R.id.rb_card) {
                    mode[0] = "2";
                    et_tans_id.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rb_cheque) {
                    mode[0] = "3";
                    et_tans_id.setVisibility(View.VISIBLE);
                }
            }
        });
        mode[0]="1";


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_amount.getText().toString().length() > 0) {
                    dialog1.dismiss();
                    callAddAmountAPI(et_amount.getText().toString(), et_tans_id.getText().toString(), "credit", mode[0]);
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Enter Amount");
                }
            }
        });
    }

    public void callAddAmountAPI(String amount, String trans_id, String trans_type, String mode) {

        ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();
        nameValuePairArrayList.add(new BasicNameValuePair("request_action", "insert_amount"));
        nameValuePairArrayList.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));
        nameValuePairArrayList.add(new BasicNameValuePair("admin_id", ""));
        nameValuePairArrayList.add(new BasicNameValuePair("amount", amount));
        nameValuePairArrayList.add(new BasicNameValuePair("purpose", "checking"));
        nameValuePairArrayList.add(new BasicNameValuePair("trans_id", trans_id));
        nameValuePairArrayList.add(new BasicNameValuePair("trans_type", UtilityFunction.getTransTypeID(trans_type)));
        nameValuePairArrayList.add(new BasicNameValuePair("payment_mode", mode));

        new WebServiceBase(nameValuePairArrayList, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try {
                    JSONObject jsonObject=new JSONObject(msg[1]);
                    if(jsonObject.optBoolean("success")){
                        ToastClass.showShortToast(getApplicationContext(),"Transaction Successfull");
                        getPatientWallet();
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),"Transaction Failed");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "ADD_AMOUNT_API", true).execute(WebServicesUrls.PATIENT_WALLET);
    }
}
