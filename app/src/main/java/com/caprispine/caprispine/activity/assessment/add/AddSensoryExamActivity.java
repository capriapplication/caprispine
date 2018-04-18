package com.caprispine.caprispine.activity.assessment.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.adapter.ViewPagerAdapter;
import com.caprispine.caprispine.fragment.SensoryDermatomeFragment;
import com.caprispine.caprispine.fragment.SensoryMyotomeFragment;
import com.caprispine.caprispine.fragment.SensoryReflexesFragment;
import com.caprispine.caprispine.pojo.ResponsePOJO;
import com.caprispine.caprispine.pojo.patientassessment.SensoryPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
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

public class AddSensoryExamActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    PatientPOJO patientPOJO;
    @BindView(R.id.savebtn)
    Button savebtn;
    SensoryPOJO sensoryPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sensory_exam);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sensory Exam");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        sensoryPOJO = (SensoryPOJO) getIntent().getSerializableExtra("SensoryPOJO");

        setupViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExam();
            }
        });

        if(sensoryPOJO!=null){
            savebtn.setText("Update");
            getSupportActionBar().setTitle("Update Exam");
            setValue();
        }
    }

    public void setValue(){
//        sensoryDermatomeFragment.setValues(sensoryPOJO);
//        sensoryMyotomeFragment.setValues(sensoryPOJO);
//        sensoryReflexesFragment.setValues(sensoryPOJO);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    SensoryDermatomeFragment sensoryDermatomeFragment;
    SensoryMyotomeFragment sensoryMyotomeFragment;
    SensoryReflexesFragment sensoryReflexesFragment;

    private void setupViewPager(ViewPager viewPager) {

        sensoryDermatomeFragment = new SensoryDermatomeFragment(sensoryPOJO);
        sensoryMyotomeFragment = new SensoryMyotomeFragment(sensoryPOJO);
        sensoryReflexesFragment = new SensoryReflexesFragment(sensoryPOJO);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(sensoryDermatomeFragment, "Dermatome");
        adapter.addFrag(sensoryMyotomeFragment, "Myotome");
        adapter.addFrag(sensoryReflexesFragment, "Reflexes");
        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(3);
    }

    public void addExam() {

        ArrayList<NameValuePair> nameValuePairArrayList = new ArrayList<>();

        nameValuePairArrayList.add(new BasicNameValuePair("derma_c2", sensoryDermatomeFragment.getRg_c2()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_c3", sensoryDermatomeFragment.getRg_c3()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_c4", sensoryDermatomeFragment.getRg_c4()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_c5", sensoryDermatomeFragment.getRg_c5()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_c6", sensoryDermatomeFragment.getRg_c6()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_c7", sensoryDermatomeFragment.getRg_c7()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_c8", sensoryDermatomeFragment.getRg_c8()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_t1", sensoryDermatomeFragment.getRg_t1()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_t2", sensoryDermatomeFragment.getRg_t2()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_t4", sensoryDermatomeFragment.getRg_t4()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_t6", sensoryDermatomeFragment.getRg_t6()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_t10", sensoryDermatomeFragment.getRg_t10()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_t12", sensoryDermatomeFragment.getRg_t12()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_l2", sensoryDermatomeFragment.getRg_l2()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_l3", sensoryDermatomeFragment.getRg_l3()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_l4", sensoryDermatomeFragment.getRg_l4()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_l5", sensoryDermatomeFragment.getRg_l5()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_s1", sensoryDermatomeFragment.getRg_s1()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_s2", sensoryDermatomeFragment.getRg_s2()));
        nameValuePairArrayList.add(new BasicNameValuePair("derma_s5", sensoryDermatomeFragment.getRg_s5()));

        nameValuePairArrayList.add(new BasicNameValuePair("myo_c2", sensoryMyotomeFragment.getRg_c2()));
        nameValuePairArrayList.add(new BasicNameValuePair("myo_c3", sensoryMyotomeFragment.getRg_c3()));
        nameValuePairArrayList.add(new BasicNameValuePair("myo_c4", sensoryMyotomeFragment.getRg_c4()));
        nameValuePairArrayList.add(new BasicNameValuePair("myo_c5", sensoryMyotomeFragment.getRg_c5()));
        nameValuePairArrayList.add(new BasicNameValuePair("myo_c6", sensoryMyotomeFragment.getRg_c6()));
        nameValuePairArrayList.add(new BasicNameValuePair("myo_c7", sensoryMyotomeFragment.getRg_c7()));
        nameValuePairArrayList.add(new BasicNameValuePair("myo_c8", sensoryMyotomeFragment.getRg_c8()));
        nameValuePairArrayList.add(new BasicNameValuePair("myo_l2", sensoryMyotomeFragment.getRg_l2()));
        nameValuePairArrayList.add(new BasicNameValuePair("myo_l3", sensoryMyotomeFragment.getRg_l3()));
        nameValuePairArrayList.add(new BasicNameValuePair("myo_l4", sensoryMyotomeFragment.getRg_l4()));
        nameValuePairArrayList.add(new BasicNameValuePair("myo_l5", sensoryMyotomeFragment.getRg_l5()));
        nameValuePairArrayList.add(new BasicNameValuePair("myo_s1", sensoryMyotomeFragment.getRg_s1()));
        nameValuePairArrayList.add(new BasicNameValuePair("myo_s2", sensoryMyotomeFragment.getRg_s2()));
        nameValuePairArrayList.add(new BasicNameValuePair("myo_s35", sensoryMyotomeFragment.getRg_s5()));


        nameValuePairArrayList.add(new BasicNameValuePair("ref_c5", sensoryReflexesFragment.getRg_c5()));
        nameValuePairArrayList.add(new BasicNameValuePair("ref_c6", sensoryReflexesFragment.getRg_c6()));
        nameValuePairArrayList.add(new BasicNameValuePair("ref_c7", sensoryReflexesFragment.getRg_c7()));
        nameValuePairArrayList.add(new BasicNameValuePair("ref_c8", sensoryReflexesFragment.getRg_c8()));
        nameValuePairArrayList.add(new BasicNameValuePair("ref_l2", sensoryReflexesFragment.getRg_l2()));
        nameValuePairArrayList.add(new BasicNameValuePair("ref_l3", sensoryReflexesFragment.getRg_l3()));
        nameValuePairArrayList.add(new BasicNameValuePair("ref_s1", sensoryReflexesFragment.getRg_s1()));
        nameValuePairArrayList.add(new BasicNameValuePair("ref_s2", sensoryReflexesFragment.getRg_s2()));

        if(sensoryPOJO!=null){
            nameValuePairArrayList.add(new BasicNameValuePair("request_action", "update_complaint"));
            nameValuePairArrayList.add(new BasicNameValuePair("id", sensoryPOJO.getId()));
        }else{
            nameValuePairArrayList.add(new BasicNameValuePair("request_action", "insert_complaint"));
            nameValuePairArrayList.add(new BasicNameValuePair("date", UtilityFunction.getCurrentDate()));
        }

        nameValuePairArrayList.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));
        new WebServiceBase(nameValuePairArrayList, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                ResponsePOJO<SensoryPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponsePOJO<SensoryPOJO>>() {
                }.getType());
                if (responseListPOJO.isSuccess()) {
                    if(sensoryPOJO!=null){
                        ToastClass.showShortToast(getApplicationContext(),"Sensory Updated");
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),"Sensory Added");
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result","");
                        setResult(Activity.RESULT_OK,returnIntent);
                    }
                }
                ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
            }
        }, "CALL_SENSORY_API", true).execute(WebServicesUrls.SENSORY_CRUD);
    }
}
