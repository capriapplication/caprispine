package com.caprispine.caprispine.fragment.report;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.activity.report.IncomeReportPrintActivity;
import com.caprispine.caprispine.adapter.report.CaseNotesPrintAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.patientassessment.DiagnosisPOJO;
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
 * Created by sunil on 29-03-2018.
 */

@SuppressLint("ValidFragment")
public class NotesReportFragment extends Fragment implements WebServicesCallBack {
    private static final String GET_ALL_INCOMES = "get_all_invoices";
    String branch_code;
    String patient_id;
    String from_date;

    @BindView(R.id.btn_print)
    Button btn_print;

    public NotesReportFragment(String branch_code, String patient_id, String from_date,String notetype) {
        this.branch_code = branch_code;
        this.patient_id = patient_id;
        this.from_date = from_date;
        this.notetype=notetype;
    }


    @BindView(R.id.rv_report)
    RecyclerView rv_report;
    String notetype="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_notes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            String[] start_date = from_date.split("-");

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("start_date", start_date[2] + "-" + start_date[1] + "-" + start_date[0]));
            nameValuePairs.add(new BasicNameValuePair("end_date", UtilityFunction.getCurrentDate()));
            nameValuePairs.add(new BasicNameValuePair("patient_id", patient_id));
            nameValuePairs.add(new BasicNameValuePair("request_action", "all_notes"));
            nameValuePairs.add(new BasicNameValuePair("note_type", notetype));
            Log.d(TagUtils.getTag(),"note type:-"+notetype);
            switch (notetype){
                case "case":
                    new WebServiceBase(nameValuePairs, getActivity(), this, GET_ALL_INCOMES,true).execute(WebServicesUrls.REPORT_CRUD);
                    break;
                case "progress":
                    new WebServiceBase(nameValuePairs, getActivity(), this, GET_ALL_INCOMES,true).execute(WebServicesUrls.REPORT_CRUD);
                    break;
                case "remark":
                    new WebServiceBase(nameValuePairs, getActivity(), this, GET_ALL_INCOMES,true).execute(WebServicesUrls.REPORT_CRUD);
                    break;
            }

            btn_print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), IncomeReportPrintActivity.class);
                    intent.putExtra("type", notetype);
                    intent.putExtra("branch_id", branch_code);
                    intent.putExtra("patient_id", patient_id);
                    String[] start_date = from_date.split("-");
                    intent.putExtra("start_date", start_date[2] + "-" + start_date[1] + "-" + start_date[0]);
                    intent.putExtra("end_date", UtilityFunction.getCurrentDate());
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case GET_ALL_INCOMES:
                parseAllInvoices(response);
                break;
        }
    }

    public void parseAllInvoices(String response) {
        Log.d(TagUtils.getTag(), "report response:-" + response);

        try{

            ResponseListPOJO<DiagnosisPOJO> responseListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<DiagnosisPOJO>>() {}.getType());
            if (responseListPOJO.isSuccess()) {
                CaseNotesPrintAdapter invoiceAdapter = new CaseNotesPrintAdapter(getActivity(), responseListPOJO.getResultList());
                LinearLayoutManager horizontalLayoutManagaer
                        = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                rv_report.setLayoutManager(horizontalLayoutManagaer);
                rv_report.setHasFixedSize(true);
                rv_report.setItemAnimator(new DefaultItemAnimator());
                rv_report.setAdapter(invoiceAdapter);
            }else{
                ToastClass.showShortToast(getActivity().getApplicationContext(),responseListPOJO.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
