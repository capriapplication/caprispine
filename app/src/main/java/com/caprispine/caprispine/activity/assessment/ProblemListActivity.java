package com.caprispine.caprispine.activity.assessment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.activity.MultipleFileSelectActivity;
import com.caprispine.caprispine.adapter.patientassessment.ProblemListAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.problem.AnsPOJO;
import com.caprispine.caprispine.pojo.problem.AnswersPOJO;
import com.caprispine.caprispine.pojo.problem.ProblemPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.ResponseListCallback;
import com.caprispine.caprispine.webservice.ResponseListWebservice;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProblemListActivity extends AppCompatActivity {

    @BindView(R.id.rv_problems)
    RecyclerView rv_problems;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    PatientPOJO patientPOJO;
    boolean is_questionaire=false;
    List<AnswersPOJO> answersPOJOS=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_list);
        ButterKnife.bind(this);
        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            is_questionaire=bundle.getBoolean("is_questionaire");
        }

        getAllproblems();
        attachAdapter();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertFinalData();
            }
        });
    }

    private void insertFinalData() {
        try {
            if (getAnsweredJoints() != 0) {
                String jsonData = "";
                JSONObject mainJson=new JSONObject();
                JSONArray jsonArray=new JSONArray();
                for (AnswersPOJO answersPOJO : finalAnswersPOJOS) {

                    for(AnsPOJO ansPOJO:answersPOJO.getAnsPOJOS()) {

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("patient_id", patientPOJO.getId());
                        jsonObject.put("problem_id", answersPOJO.getProblem_id());
                        jsonObject.put("date", UtilityFunction.getCurrentDate());
//                        jsonObject.put("date", "2018-04-18");
                        jsonObject.put("question_id", ansPOJO.getQuestionId());
                        jsonObject.put("answer_id", ansPOJO.getId());
                        jsonObject.put("answered_on", UtilityFunction.getCurrentDateTime());
                        jsonObject.put("answer_mark", ansPOJO.getAnswerMark());

                        jsonArray.put(jsonObject);
                    }

                }
                if(jsonArray.length()>0){
                    mainJson.put("success",true);
                    mainJson.put("result",jsonArray);
                    ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
                    nameValuePairs.add(new BasicNameValuePair("request_action","insert_patient_scores"));
                    nameValuePairs.add(new BasicNameValuePair("patient_id",patientPOJO.getId()));
                    nameValuePairs.add(new BasicNameValuePair("date",UtilityFunction.getCurrentDate()));
//                    nameValuePairs.add(new BasicNameValuePair("date","2018-04-18"));
                    nameValuePairs.add(new BasicNameValuePair("json_data",mainJson.toString()));
                    new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
                        @Override
                        public void onGetMsg(String[] msg) {
                            try{
                                JSONObject jsonObject=new JSONObject(msg[1]);
                                if(jsonObject.optString("success").equals("true")){
                                    if(is_questionaire){
                                        Intent i = new Intent(ProblemListActivity.this, MultipleFileSelectActivity.class);
                                        i.putExtra("patientPOJO",patientPOJO);
                                        startActivityForResult(i, 155);
                                    }else {
                                        finish();
                                    }
                                }else{
                                    ToastClass.showShortToast(getApplicationContext(),"Something went wrong");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    },"CALL_INSERT_PROBLEM_DATA",true).execute(WebServicesUrls.PROBLEM_CRUD);
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"Please Ans Joints Questions Properly");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"Please Ans Joints Questions Properly");
        }
    }



    List<AnswersPOJO> finalAnswersPOJOS=new ArrayList<>();

    public int getAnsweredJoints(){
        int count=0;
        finalAnswersPOJOS.clear();
        for(AnswersPOJO answersPOJO:answersPOJOS){
            if(answersPOJO.getScore()!=null&&!answersPOJO.getScore().equals("")){
                try{
                    int score=Integer.parseInt(answersPOJO.getScore());
                    finalAnswersPOJOS.add(answersPOJO);
                    count++;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    public void getAllproblems(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","get_all_problems"));
        new ResponseListWebservice<ProblemPOJO>(nameValuePairs, getApplicationContext(), new ResponseListCallback<ProblemPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<ProblemPOJO> responseListPOJO) {
                problemPOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    for(ProblemPOJO problemPOJO:responseListPOJO.getResultList()){
                        AnswersPOJO answersPOJO=new AnswersPOJO();
                        answersPOJO.setScore("");
                        answersPOJO.setAnsPOJOS(new ArrayList<AnsPOJO>());

                        answersPOJOS.add(answersPOJO);
                        problemPOJOS.add(problemPOJO);
                    }

                } else {
                    ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                }
                problemListAdapter.notifyDataSetChanged();
            }
        }, ProblemPOJO.class,"GET_PROBLEM_LIST", false).execute(WebServicesUrls.PROBLEM_CRUD);

    }

    ProblemListAdapter problemListAdapter;
    List<ProblemPOJO> problemPOJOS= new ArrayList<>();

    public void attachAdapter() {

        problemListAdapter = new ProblemListAdapter(this, null, problemPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_problems.setHasFixedSize(true);
        rv_problems.setAdapter(problemListAdapter);
        rv_problems.setLayoutManager(layoutManager);
        rv_problems.setNestedScrollingEnabled(false);
        rv_problems.setItemAnimator(new DefaultItemAnimator());
    }

    public void startQuestionaire(ProblemPOJO problemPOJO,int position) {
        Intent intent=new Intent(this, ProblemQuestionActivity.class);
        intent.putExtra("problemPOJO",problemPOJO);
        intent.putExtra("patientPOJO",patientPOJO);
        if(answersPOJOS.get(position).getAnsPOJOS()!=null&&answersPOJOS.get(position).getAnsPOJOS().size()>0) {
            intent.putExtra("givenAns", (Serializable) answersPOJOS.get(position).getAnsPOJOS());
        }
        intent.putExtra("position",position);
        startActivityForResult(intent,155);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==155) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    List<AnsPOJO> answersPOJOS1 = (List<AnsPOJO>) data.getSerializableExtra("answesPOJO");
                    int position = data.getIntExtra("position",-1);
                    String problem_id = data.getStringExtra("problem_id");
                    if(position!=-1) {
                        String score = data.getStringExtra("score");
                        answersPOJOS.get(position).setAnsPOJOS(answersPOJOS1);
                        answersPOJOS.get(position).setScore(score);
                        answersPOJOS.get(position).setProblem_id(problem_id);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
