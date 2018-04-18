package com.caprispine.caprispine.activity.assessment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.adapter.patientassessment.FaReportAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.graph.DateValue;
import com.caprispine.caprispine.pojo.graph.GraphPOJO;
import com.caprispine.caprispine.pojo.graph.GraphResultPOJO;
import com.caprispine.caprispine.pojo.patientassessment.FaPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.testing.GraphTesting;
import com.caprispine.caprispine.webservice.ResponseListCallback;
import com.caprispine.caprispine.webservice.ResponseListWebservice;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FAReportActivity extends AppCompatActivity {

    @BindView(R.id.btn_again)
    Button btn_again;
    @BindView(R.id.rv_report)
    RecyclerView rv_report;
    @BindView(R.id.ll_score1)
    LinearLayout ll_score1;
    @BindView(R.id.ll_score2)
    LinearLayout ll_score2;
    @BindView(R.id.ll_score3)
    LinearLayout ll_score3;
    @BindView(R.id.tv_score1)
    TextView tv_score1;
    @BindView(R.id.tv_score2)
    TextView tv_score2;
    @BindView(R.id.tv_score3)
    TextView tv_score3;
    @BindView(R.id.btn_graph)
    Button btn_graph;

    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fareport);
        ButterKnife.bind(this);

        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");

        attachAdapter();

        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FAReportActivity.this,ProblemListActivity.class).putExtra("patientPOJO",patientPOJO));
            }
        });

        btn_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGraph();
            }
        });
    }

    public void showGraph(){
        List<FaPOJO> faPOJOS=new ArrayList<>(graphFaPOJOs);
        Collections.reverse(faPOJOS);
        Set<String> joints=new HashSet<>();
        for(FaPOJO faPOJO:faPOJOS){
            joints.add(faPOJO.getProblemDescriptionPOJO().getName());
        }
        List<GraphResultPOJO> graphResultPOJOS=new ArrayList<>();
        for(String s:joints){
            GraphResultPOJO graphResultPOJO=new GraphResultPOJO();
            List<DateValue> dateValues=new ArrayList<>();
            for(FaPOJO faPOJO:faPOJOS){
                if(s.equals(faPOJO.getProblemDescriptionPOJO().getName())){
                    dateValues.add(new DateValue(faPOJO.getDate(),faPOJO.getTotal()));
                }
            }
            graphResultPOJO.setDateValues(dateValues);
            graphResultPOJO.setLine_name(s);

            graphResultPOJOS.add(graphResultPOJO);
        }

        if(graphResultPOJOS.size()>0){
            GraphPOJO graphPOJO=new GraphPOJO();
            graphPOJO.setGraphResultPOJOS(graphResultPOJOS);
            startActivity(new Intent(this, GraphTesting.class).putExtra("graphPOJO", graphPOJO));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        callPatientScore();
    }
    List<FaPOJO> graphFaPOJOs=new ArrayList<>();
    public void callPatientScore(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","get_patient_scores"));
        nameValuePairs.add(new BasicNameValuePair("patient_id",patientPOJO.getId()));
        new ResponseListWebservice<FaPOJO>(nameValuePairs, this, new ResponseListCallback<FaPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<FaPOJO> responseListPOJO) {
                faPOJOS.clear();
                graphFaPOJOs.clear();
                if(responseListPOJO.isSuccess()){
                    graphFaPOJOs.addAll(responseListPOJO.getResultList());
                    faPOJOS.addAll(getFaPOJOS(responseListPOJO.getResultList()));
                }
                faReportAdapter.notifyDataSetChanged();
            }

        },FaPOJO.class,"CALL_FA_REPORT_API",true).execute(WebServicesUrls.PROBLEM_CRUD);
    }


    FaReportAdapter faReportAdapter;
    List<List<FaPOJO>> faPOJOS= new ArrayList<>();

    public void attachAdapter() {

        faReportAdapter = new FaReportAdapter(this, null, faPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_report.setHasFixedSize(true);
        rv_report.setAdapter(faReportAdapter);
        rv_report.setLayoutManager(layoutManager);
        rv_report.setNestedScrollingEnabled(false);
        rv_report.setItemAnimator(new DefaultItemAnimator());
    }



    public List<List<FaPOJO>> getFaPOJOS(List<FaPOJO> faPOJOS){
        List<List<FaPOJO>> faLists=new ArrayList<>();

        Set<String> faStringSet=new HashSet<>();
        for(FaPOJO faPOJO:faPOJOS){
            faStringSet.add(faPOJO.getDate());
        }

        for(String date:faStringSet){
            List<FaPOJO> faPOJOS1=new ArrayList<>();
            for(FaPOJO faPOJO:faPOJOS){
                if(faPOJO.getDate().equals(date)){
                    faPOJOS1.add(faPOJO);
                }
            }
            faLists.add(faPOJOS1);
        }

        return faLists;
    }
}
