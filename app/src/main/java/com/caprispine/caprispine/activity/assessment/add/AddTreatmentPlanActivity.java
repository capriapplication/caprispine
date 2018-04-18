package com.caprispine.caprispine.activity.assessment.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.treatment.TreatmentPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddTreatmentPlanActivity extends AppCompatActivity {

    @BindView(R.id.et_goal)
    AutoCompleteTextView et_goal;
    @BindView(R.id.spinner_therapy)
    Spinner spinner_therapy;
    @BindView(R.id.et_planned_treatment)
    AutoCompleteTextView et_planned_treatment;
    @BindView(R.id.btn_save)
    Button btn_save;

    PatientPOJO patientPOJO;
    private DatabaseReference root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treatment_plan);
        ButterKnife.bind(this);

        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        root = FirebaseDatabase.getInstance().getReference().getRoot();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "all_treatment"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                ResponseListPOJO<TreatmentPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<TreatmentPOJO>>() {
                }.getType());
                treatmentPOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    treatmentPOJOS.addAll(responseListPOJO.getResultList());

                    List<String> braStringList=new ArrayList<>();
                    for (TreatmentPOJO treatmentPOJO: treatmentPOJOS) {
                        braStringList.add(treatmentPOJO.getName() + " (" + treatmentPOJO.getPrice() + ")");
                    }

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            getApplicationContext(), R.layout.dropsimpledown, braStringList);
                    spinner_therapy.setAdapter(spinnerArrayAdapter);
                } else {
                    ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                }
            }
        }, "CALL_GET_ALL_TREATMENT", true).execute(WebServicesUrls.TREATMENT_CRUD);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
        setMemoryAdapter(et_goal,"treatmentgoal");
        setMemoryAdapter(et_planned_treatment,"treatmentplan");
    }



    public void setMemoryAdapter(AutoCompleteTextView act, String child){
        final List<String> list_doses = new ArrayList<>();
        final Set<String> set_doses= new HashSet<>();
        final ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, list_doses);
        //Used to specify minimum number of
        //characters the user has to type in order to display the drop down hint.
        act.setThreshold(1);
        //Setting adapter
        act.setAdapter(arrayAdapter);

        root.child(child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_doses.clear();
                set_doses.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Log.d(TagUtils.getTag(), "doses datashapshot:-" + postSnapshot.getValue());
                    set_doses.add(postSnapshot.getValue().toString());
                }
                list_doses.addAll(set_doses);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TagUtils.getTag(), "Failed to read app title value.", databaseError.toException());
            }
        });
    }

    public void save(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","insert_treatment_plan"));
        nameValuePairs.add(new BasicNameValuePair("patient_id",patientPOJO.getId()));
        nameValuePairs.add(new BasicNameValuePair("goal",et_goal.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("therapy",treatmentPOJOS.get(spinner_therapy.getSelectedItemPosition()).getId()));
        nameValuePairs.add(new BasicNameValuePair("planned_treatment",et_planned_treatment.getText().toString()));

        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    JSONObject jsonObject=new JSONObject(msg[1]);
                    if(jsonObject.optBoolean("success")){
                        ToastClass.showShortToast(getApplicationContext(),"Treatment Plan Added");
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result","");
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CALL_SAVE_API",true).execute(WebServicesUrls.TREATMENT_PLAN_CRUD);


        if(et_goal.getText().toString().length()>0){
            String mGroupId = root.push().getKey();
            root.child("treatmentgoal").child(mGroupId).setValue(et_goal.getText().toString());
        }
        if(et_planned_treatment.getText().toString().length()>0){
            String mGroupId = root.push().getKey();
            root.child("treatmentplan").child(mGroupId).setValue(et_planned_treatment.getText().toString());
        }

    }

    List<TreatmentPOJO> treatmentPOJOS=new ArrayList<>();
}
