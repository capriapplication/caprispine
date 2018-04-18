package com.caprispine.caprispine.activity.assessment.add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMedicalActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_text)
    AutoCompleteTextView et_text;
    @BindView(R.id.btn_save)
    Button btn_save;
    PatientPOJO patientPOJO;

    private DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medical_exam);
        ButterKnife.bind(this);

        root = FirebaseDatabase.getInstance().getReference().getRoot();

        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Medical Diagnosis");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMore=false;
                addExam();
            }
        });

        setMemoryAdapter(et_text,"medical");
    }


    List<String> list_doses = new ArrayList<>();
    Set<String> set_doses= new HashSet<>();
    ArrayAdapter<String> arrayAdapter;
    public void setMemoryAdapter(AutoCompleteTextView act, String child){

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


    public void addExam(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "insert_complaint"));
        nameValuePairs.add(new BasicNameValuePair("description",et_text.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("patient_id",et_text.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("date", UtilityFunction.getCurrentDate()));
        nameValuePairs.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));

        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    JSONObject jsonObject=new JSONObject(msg[1]);
                    if(jsonObject.optBoolean("success")){
                        if(addMore){
                            startActivity(new Intent(AddMedicalActivity.this,AddMedicalActivity.class).putExtra("patientPOJO",patientPOJO));
                            finish();
                        }else {
                            ToastClass.showShortToast(getApplicationContext(),"Medical Exam Added");
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result","");
                            setResult(Activity.RESULT_OK,returnIntent);
                            finish();
                        }
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),jsonObject.optString("message"));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CALL_ADD_EXAM",true).execute(WebServicesUrls.MEDICAL_CRUD);

        if(et_text.getText().toString().length()>0){
            String mGroupId = root.push().getKey();
            root.child("medical").child(mGroupId).setValue(et_text.getText().toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);//Menu Resource, Menu
        return true;
    }
    boolean addMore=false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_amount:
                addMore=true;
                addExam();
                return true;
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
