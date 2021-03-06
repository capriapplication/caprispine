package com.caprispine.caprispine.fragment.report;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.activity.report.IncomeReportPrintActivity;
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

/**
 * Created by sunil on 30-08-2017.
 */

@SuppressLint("ValidFragment")
public class IncomeBranchTreatWiseFragment extends Fragment{
    private static final String GET_ALL_INCOMES = "get_all_invoices";
    String branch_code;
    String treatment_id;

    @BindView(R.id.btn_print)
    Button btn_print;

    public IncomeBranchTreatWiseFragment(String branch_code, String treatmen_id) {
        this.branch_code = branch_code;
        this.treatment_id= treatmen_id;
    }

    @BindView(R.id.tv_total_amount)
    TextView tv_total_amount;
    @BindView(R.id.rv_report)
    RecyclerView rv_report;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_income_branch_wise, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("request_action", "income_treatment"));
            nameValuePairs.add(new BasicNameValuePair("branch_id", branch_code));
            nameValuePairs.add(new BasicNameValuePair("treatment_id", treatment_id));
            new WebServiceBase(nameValuePairs, getActivity(), new WebServicesCallBack() {
                @Override
                public void onGetMsg(String[] msg) {
                    try {

                        ResponseListPOJO<IncomePOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<IncomePOJO>>() {}.getType());
                        if (responseListPOJO.isSuccess()) {
                            IncomeAdapter invoiceAdapter = new IncomeAdapter(getActivity(), responseListPOJO.getResultList());
                            LinearLayoutManager horizontalLayoutManagaer
                                    = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            rv_report.setLayoutManager(horizontalLayoutManagaer);
                            rv_report.setHasFixedSize(true);
                            rv_report.setItemAnimator(new DefaultItemAnimator());
                            rv_report.setAdapter(invoiceAdapter);
                            tv_total_amount.setText("INR " + responseListPOJO.getMessage());
                        }else{
                            ToastClass.showShortToast(getActivity().getApplicationContext(),responseListPOJO.getMessage());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastClass.showShortToast(getActivity().getApplicationContext(), "Something went wrong");
                    }
                }
            }, GET_ALL_INCOMES,true).execute(WebServicesUrls.REPORT_CRUD);

            btn_print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), IncomeReportPrintActivity.class);
                    intent.putExtra("type", "incometreatmentwise");
                    intent.putExtra("branch_id", branch_code);
                    intent.putExtra("treatment_id", treatment_id);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
