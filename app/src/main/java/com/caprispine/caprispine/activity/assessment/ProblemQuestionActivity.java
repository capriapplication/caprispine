package com.caprispine.caprispine.activity.assessment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.problem.AnsPOJO;
import com.caprispine.caprispine.pojo.problem.ProblemPOJO;
import com.caprispine.caprispine.pojo.problem.QuestionPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.ResponseListCallback;
import com.caprispine.caprispine.webservice.ResponseListWebservice;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProblemQuestionActivity extends AppCompatActivity {

    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.ll_questions)
    LinearLayout ll_questions;

    ProblemPOJO problemPOJO;
    PatientPOJO patientPOJO;
    public List<AnsPOJO> answersList = new ArrayList<>();
    List<AnsPOJO> givenAnws;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_question);
        ButterKnife.bind(this);
        problemPOJO = (ProblemPOJO) getIntent().getSerializableExtra("problemPOJO");
        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");

        givenAnws=(List<AnsPOJO>)getIntent().getSerializableExtra("givenAns");

        position=getIntent().getIntExtra("position",-1);
        getAllQuestions();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int scores = calculateScore();
                Log.d(TagUtils.getTag(),"calculated Score:-"+scores);
                if(scores!=-1){
                    Intent intent=new Intent();
                    intent.putExtra("answesPOJO", (Serializable) answersList);
                    intent.putExtra("position",position);
                    intent.putExtra("score",String.valueOf(scores));
                    intent.putExtra("problem_id",problemPOJO.getId());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"check all questions ans");
                }
            }
        });
    }

    public int calculateScore() {
        try {
            Log.d(TagUtils.getTag(), "answers:-" + answersList.toString());
            int score = 0;
            for (AnsPOJO ans : answersList) {
                Integer sc = Integer.parseInt(ans.getAnswerMark());
                score = score + sc;
            }
            return score;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void getAllQuestions() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_problem_question_ans"));
        nameValuePairs.add(new BasicNameValuePair("problem_id", problemPOJO.getId()));
        new ResponseListWebservice<QuestionPOJO>(nameValuePairs, getApplicationContext(), new ResponseListCallback<QuestionPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<QuestionPOJO> responseListPOJO) {
                try {
                    if (responseListPOJO.isSuccess()) {

                        for (int i = 0; i < responseListPOJO.getResultList().size(); i++) {
                            answersList.add(new AnsPOJO());
                        }

                        inflateQuestion(responseListPOJO.getResultList());

                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, QuestionPOJO.class, "GET_PROBLEM_LIST", false).execute(WebServicesUrls.PROBLEM_CRUD);
    }
    Map<String,RadioButton> answersPOJOMap=new HashMap<>();
    public void inflateQuestion(final List<QuestionPOJO> questionPOJOS) {
        for(int position=0;position<questionPOJOS.size();position++) {
            final LayoutInflater inflater_questions = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view_ques = inflater_questions.inflate(R.layout.inflate_questions, null);


            TextView tv_question = view_ques.findViewById(R.id.tv_question);
            RecyclerView rv_answers = view_ques.findViewById(R.id.rv_answers);
            RadioGroup rg_question = view_ques.findViewById(R.id.rg_question);

            tv_question.setText((position + 1) + ". " + questionPOJOS.get(position).getQuestion());

            for (AnsPOJO answersPOJO : questionPOJOS.get(position).getAnsPOJOS()) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(answersPOJO.getAnswer());
                rg_question.addView(radioButton);

                answersPOJOMap.put(answersPOJO.getId(),radioButton);
            }

//
            final int finalPosition = position;
            rg_question.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (radioGroup.getCheckedRadioButtonId() != -1) {
                        RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                        answersList.set(finalPosition, getAnsPOJO(radioButton.getText().toString(), questionPOJOS.get(finalPosition).getAnsPOJOS()));
                    }
                }
            });
            ll_questions.addView(view_ques);

        }

        if(givenAnws!=null&&givenAnws.size()>0){
            checkPreviousAns();
        }
    }

    public void checkPreviousAns(){
        try {
            if (givenAnws.size() > 0) {

                for(int i=0;i<givenAnws.size();i++){
                    answersPOJOMap.get(givenAnws.get(i).getId()).setChecked(true);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public AnsPOJO getAnsPOJO(String ans, List<AnsPOJO> answersPOJOList) {
        for (AnsPOJO answersPOJO : answersPOJOList) {
            if (answersPOJO.getAnswer().equals(ans)) {
                return answersPOJO;
            }
        }
        return null;
    }
}
