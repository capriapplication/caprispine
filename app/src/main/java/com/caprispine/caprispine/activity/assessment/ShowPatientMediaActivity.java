package com.caprispine.caprispine.activity.assessment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowPatientMediaActivity extends AppCompatActivity {
    private static final String CALL_PATIENT_DATA = "call_patient_data";

    @BindView(R.id.rv_media)
    RecyclerView rv_media;
    PatientPOJO patientPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_patient_media);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
//        attachAdapter();
        callAPI();
    }

//
//    MediaDateAdapter mediaDateAdapter;
//    List<PatientMediaResultPOJO> patientMediaResultPOJOS=new ArrayList<>();
//    public void attachAdapter() {
//        mediaDateAdapter = new MediaDateAdapter(this, null, patientMediaResultPOJOS);
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
//        rv_media.setHasFixedSize(true);
//        rv_media.setAdapter(mediaDateAdapter);
//        rv_media.setLayoutManager(layoutManager);
//        rv_media.setItemAnimator(new DefaultItemAnimator());
//    }

    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_all_media"));
        nameValuePairs.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {

            }
        },"CALL_PATIENT_DATA",true).execute(WebServicesUrls.PROBLEM_CRUD);

    }


//    public void parseResponse(String response) {
//        Log.d(TagUtils.getTag(), "media response:-" + response);
//        try {
//            Gson gson = new Gson();
//            PatientMediaPOJO patientMediaPOJO = gson.fromJson(response, PatientMediaPOJO.class);
//            if (patientMediaPOJO.getSuccess().equals("true")) {
//                patientMediaResultPOJOS.addAll(patientMediaPOJO.getPatientMediaResultPOJOS());
//                mediaDateAdapter.notifyDataSetChanged();
//            } else {
//                ToastClass.showShortToast(getApplicationContext(), "No Images or Videos Found");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
