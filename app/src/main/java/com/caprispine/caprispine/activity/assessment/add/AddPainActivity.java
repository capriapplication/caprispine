package com.caprispine.caprispine.activity.assessment.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableRow;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.activity.assessment.AddBodyChartActivity;
import com.caprispine.caprispine.pojo.ResponsePOJO;
import com.caprispine.caprispine.pojo.patientassessment.PainPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.views.ToggleButtonGroupTableLayout;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.dsb_pain)
    DiscreteSeekBar dsb_pain;
    @BindView(R.id.btn_save)
    Button btn_save;
    String problems = "";

    @BindView(R.id.rb_aggravating)
    RadioButton rb_aggravating;
    @BindView(R.id.et_specify_aggravating)
    EditText et_specify_aggravating;
    @BindView(R.id.rb_releiving_other)
    RadioButton rb_releiving_other;
    @BindView(R.id.et_relieving)
    EditText et_relieving;
    @BindView(R.id.tbgt_nature)
    ToggleButtonGroupTableLayout tbgt_nature;
    @BindView(R.id.tgbt_onset)
    ToggleButtonGroupTableLayout tgbt_onset;
    @BindView(R.id.tgbt_pain)
    ToggleButtonGroupTableLayout tgbt_pain;
    @BindView(R.id.tgbt_paint_aggravating)
    ToggleButtonGroupTableLayout tgbt_paint_aggravating;
    @BindView(R.id.tgbt_relieving)
    ToggleButtonGroupTableLayout tgbt_relieving;
    @BindView(R.id.tgbt_more_pain)
    ToggleButtonGroupTableLayout tgbt_more_pain;

    PatientPOJO patientPOJO;
    PainPOJO painPOJO;
    boolean is_questionaire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pain);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pain Exam");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        painPOJO = (PainPOJO) getIntent().getSerializableExtra("pain");

        if (painPOJO != null) {
            btn_save.setText("Update");
            setValues();
        }

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            is_questionaire=bundle.getBoolean("is_questionaire");
        }

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHistory();
            }
        });
    }

    public void setValues(){

        try{
            int progress=Integer.parseInt(painPOJO.getPainIntensity());
            dsb_pain.setProgress(progress);
        }catch (Exception e){
            e.printStackTrace();
        }

        setRgValue(painPOJO.getPainNature(),tbgt_nature);
        setRgValue(painPOJO.getPainOnset(),tgbt_onset);
        setRgValue(painPOJO.getPain(),tgbt_pain);
        setRgValue(painPOJO.getFeelMorePain(),tgbt_more_pain);
        setRgValue(painPOJO.getAggravatingFactors(),tgbt_paint_aggravating);
        setRgValue(painPOJO.getRelievingFactors(),tgbt_relieving);

    }

    public void saveHistory() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("pain_intensity", String.valueOf(dsb_pain.getProgress())));
        nameValuePairs.add(new BasicNameValuePair("threshold_site", ""));
        nameValuePairs.add(new BasicNameValuePair("pain_nature", getRadioGroupValues(tbgt_nature)));
        nameValuePairs.add(new BasicNameValuePair("pain_onset", getRadioGroupValues(tgbt_onset)));
        nameValuePairs.add(new BasicNameValuePair("pain", getRadioGroupValues(tgbt_pain)));
        nameValuePairs.add(new BasicNameValuePair("feel_more_pain", getRadioGroupValues(tgbt_more_pain)));
        nameValuePairs.add(new BasicNameValuePair("aggravating_factors", getRadioGroupValues(tgbt_paint_aggravating)));
        nameValuePairs.add(new BasicNameValuePair("relieving_factors", getRadioGroupValues(tgbt_relieving)));
        nameValuePairs.add(new BasicNameValuePair("specify_aggrevating", et_specify_aggravating.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("specify_relieving", et_relieving.getText().toString()));

        nameValuePairs.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));

        if (painPOJO != null) {
            nameValuePairs.add(new BasicNameValuePair("request_action", "update_complaint"));
            nameValuePairs.add(new BasicNameValuePair("date", painPOJO.getDate()));
            nameValuePairs.add(new BasicNameValuePair("id", painPOJO.getId()));
        } else {
            nameValuePairs.add(new BasicNameValuePair("request_action", "insert_complaint"));
            nameValuePairs.add(new BasicNameValuePair("date", UtilityFunction.getCurrentDate()));
        }

        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try {
                    ResponsePOJO<PainPOJO> historyPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponsePOJO<PainPOJO>>() {
                    }.getType());
                    if (historyPOJO.isSuccess()) {
                        if(is_questionaire){
                            Intent intent = new Intent(AddPainActivity.this, AddBodyChartActivity.class);
                            intent.putExtra("patientPOJO", patientPOJO);
                            startActivityForResult(intent,1);
                        }else {
                            if(painPOJO!=null){
                                ToastClass.showShortToast(getApplicationContext(),"Pain Updated");
                            }else{
                                ToastClass.showShortToast(getApplicationContext(),"Pain Added");
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result","");
                                setResult(Activity.RESULT_OK,returnIntent);
                            }

                            finish();
                        }
                    }
                    ToastClass.showShortToast(getApplicationContext(), historyPOJO.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "INSERT_PATIENT_HISTORY", true).execute(WebServicesUrls.PAIN_CRUD);
    }

    public String getRadioGroupValues(ToggleButtonGroupTableLayout rg) {
        try {
            int checked_id = rg.getCheckedRadioButtonId();
            RadioButton rb = findViewById(checked_id);
            return rb.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void setRgValue(String text,ToggleButtonGroupTableLayout radioGroup){
        Log.d(TagUtils.getTag(),"text:-"+text);
        try{
            int count = radioGroup.getChildCount();
            Log.d(TagUtils.getTag(),"radio group size:-"+radioGroup.getChildCount());
            ArrayList<TableRow> tableRowArrayList = new ArrayList<TableRow>();
            for (int i=0;i<count;i++) {
                View o = radioGroup.getChildAt(i);
                if (o instanceof TableRow) {
                    tableRowArrayList.add((TableRow) o);
                }
            }

            Log.d(TagUtils.getTag(),"table row size:-"+tableRowArrayList.size());

            ArrayList<RadioButton> radioButtons=new ArrayList<>();

            for(int i=0;i<tableRowArrayList.size();i++){
                TableRow tableRow=tableRowArrayList.get(i);
                int radioCount=tableRow.getChildCount();
                for(int j=0;j<radioCount;j++){
                    View o=tableRow.getChildAt(j);
                    if(o instanceof RadioButton){
                        radioButtons.add((RadioButton)o);
                    }
                }
            }

            Log.d(TagUtils.getTag(),"radio count:-"+radioButtons.size());

            for(RadioButton rb:radioButtons){
                if(text.equals(rb.getText().toString())){
                    rb.setChecked(true);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
