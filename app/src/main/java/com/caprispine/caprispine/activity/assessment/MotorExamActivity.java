package com.caprispine.caprispine.activity.assessment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.MotorExamGraph;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.activity.assessment.add.motor.AddAnkleActivity;
import com.caprispine.caprispine.activity.assessment.add.motor.AddCervicalSpineActivity;
import com.caprispine.caprispine.activity.assessment.add.motor.AddCombinedSpineActivity;
import com.caprispine.caprispine.activity.assessment.add.motor.AddElbowActivity;
import com.caprispine.caprispine.activity.assessment.add.motor.AddFingerActivity;
import com.caprispine.caprispine.activity.assessment.add.motor.AddForeArmActivity;
import com.caprispine.caprispine.activity.assessment.add.motor.AddHipActivity;
import com.caprispine.caprispine.activity.assessment.add.motor.AddKneeActivity;
import com.caprispine.caprispine.activity.assessment.add.motor.AddLumbarActivity;
import com.caprispine.caprispine.activity.assessment.add.motor.AddSacrollicActivity;
import com.caprispine.caprispine.activity.assessment.add.motor.AddShoulderActivity;
import com.caprispine.caprispine.activity.assessment.add.motor.AddThoraccicActivity;
import com.caprispine.caprispine.activity.assessment.add.motor.AddToesActivity;
import com.caprispine.caprispine.activity.assessment.add.motor.AddWristActivity;
import com.caprispine.caprispine.adapter.ComplaintAdapter;
import com.caprispine.caprispine.adapter.patientassessment.CervicalAdapter;
import com.caprispine.caprispine.adapter.patientassessment.CombinedAdapter;
import com.caprispine.caprispine.adapter.patientassessment.HipExamAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.patientassessment.motor.CervicalPOJO;
import com.caprispine.caprispine.pojo.patientassessment.motor.CombinedPOJO;
import com.caprispine.caprispine.pojo.patientassessment.motor.HipExamPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.testing.GraphTesting;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MotorExamActivity extends AppCompatActivity {
    @BindView(R.id.rv_complaint)
    RecyclerView rv_complaint;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_motor)
    Spinner spinner_motor;

    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.btn_skip)
    Button btn_skip;
    String[] items = {"Select Joint", "Combined Movement Assesment of Spine",
            "Cervical Spine", "Thoracic Spine", "Lumbar Spine", "Hip", "Knee", "Ankle", "Toes"
            , "Shoulder", "Elbow", "Forearm", "Wrist", "Fingers", "Sacroiliac Joint"};
    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor_exam);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Motor Exam");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        attachAdapter();
        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");


        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MotorExamActivity.this, SensoryActivity.class).putExtra("patientPOJO",patientPOJO));
                finish();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExam();
            }
        });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, items);
        spinner_motor.setAdapter(spinnerArrayAdapter);

        spinner_motor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TagUtils.getTag(), "items selectd:-" + i);
                showMotorExamData(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void showMotorExamData(final int position) {
        if (show_graph != null) {
            show_graph.setVisible(false);
        }
        if (position == 0) {
            rv_complaint.setVisibility(View.INVISIBLE);
        } else {
            rv_complaint.setVisibility(View.VISIBLE);
            String url = "";
            switch (position) {
                case 1:
                    url = WebServicesUrls.COMBINED_CRUD;
                    break;
                case 2:
                    url = WebServicesUrls.CERVICAL_CRUD;
                    break;
                case 3:
                    url = WebServicesUrls.THORACIC_CRUD;
                    break;
                case 4:
                    url = WebServicesUrls.LUMBAR_CRUD;
                    break;
                case 5:
                    show_graph.setVisible(true);
                    url = WebServicesUrls.HIP_CRUD;
                    break;
                case 6:
                    show_graph.setVisible(true);
                    url = WebServicesUrls.KNEE_CRUD;
                    break;
                case 7:
                    show_graph.setVisible(true);
                    url = WebServicesUrls.ANKLE_CRUD;
                    break;
                case 8:
                    show_graph.setVisible(true);
                    url = WebServicesUrls.TOES_CRUD;
                    break;
                case 9:
                    show_graph.setVisible(true);
                    url = WebServicesUrls.SHOULDER_CRUD;
                    break;
                case 10:
                    show_graph.setVisible(true);
                    url = WebServicesUrls.ELBOW_CRUD;
                    break;
                case 11:
                    show_graph.setVisible(true);
                    url = WebServicesUrls.FOREARM_CRUD;
                    break;
                case 12:
                    show_graph.setVisible(true);
                    url = WebServicesUrls.WRIST_CRUD;
                    break;
                case 13:
                    show_graph.setVisible(true);
                    url = WebServicesUrls.FINGER_CRUD;
                    break;
                case 14:
                    url = WebServicesUrls.SACROILIC_CRUD;
                    break;
            }

            if (!url.equals("")) {
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));
                nameValuePairs.add(new BasicNameValuePair("request_action", "get_all_complaint"));
                new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String[] msg) {
                        parseResult(position, msg[1]);
                    }
                }, "CALL_EXAM_DATA:-" + position, true).execute(url);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showMotorExamData(spinner_motor.getSelectedItemPosition());
    }

    ResponseListPOJO<HipExamPOJO> hipExamPOJOResponseListPOJO;
    ResponseListPOJO<HipExamPOJO> kneeListPOJO;
    ResponseListPOJO<HipExamPOJO> ankleListPOJO;
    ResponseListPOJO<HipExamPOJO> toesListPOJO;
    ResponseListPOJO<HipExamPOJO> ShoulderListPOJO;
    ResponseListPOJO<HipExamPOJO> elbowListPOJO;
    ResponseListPOJO<HipExamPOJO> forearmListPOJO;
    ResponseListPOJO<HipExamPOJO> wristListPOJO;
    ResponseListPOJO<HipExamPOJO> fingersListPOJO;
    ResponseListPOJO<HipExamPOJO> sacrollicListPOJO;

    public void parseResult(int position, String response) {
        try {
            switch (position) {
                case 1:
                    ResponseListPOJO<CombinedPOJO> combinedPOJOResponseListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<CombinedPOJO>>() {
                    }.getType());
                    if (combinedPOJOResponseListPOJO.isSuccess()) {
                        attachAdapter(new CombinedAdapter(this, null, combinedPOJOResponseListPOJO.getResultList()));
                    } else {
                        attachAdapter();
                    }

                    break;
                case 2:
                    ResponseListPOJO<CervicalPOJO> cervicalListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<CervicalPOJO>>() {
                    }.getType());
                    if (cervicalListPOJO.isSuccess()) {
                        attachAdapter(new CervicalAdapter(this, null, cervicalListPOJO.getResultList(), WebServicesUrls.CERVICAL_CRUD));
                    } else {
                        attachAdapter();
                    }
                    break;
                case 3:
                    ResponseListPOJO<CervicalPOJO> thoracicListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<CervicalPOJO>>() {
                    }.getType());
                    if (thoracicListPOJO.isSuccess()) {
                        attachAdapter(new CervicalAdapter(this, null, thoracicListPOJO.getResultList(), WebServicesUrls.THORACIC_CRUD));
                    } else {
                        attachAdapter();
                    }
                    break;
                case 4:
                    ResponseListPOJO<CervicalPOJO> lumbarListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<CervicalPOJO>>() {
                    }.getType());
                    if (lumbarListPOJO.isSuccess()) {
                        attachAdapter(new CervicalAdapter(this, null, lumbarListPOJO.getResultList(), WebServicesUrls.LUMBAR_CRUD));
                    } else {
                        attachAdapter();
                    }
                    break;
                case 5:
                    hipExamPOJOResponseListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<HipExamPOJO>>() {
                    }.getType());
                    if (hipExamPOJOResponseListPOJO.isSuccess()) {
                        attachAdapter(new HipExamAdapter(this, null, hipExamPOJOResponseListPOJO.getResultList(), WebServicesUrls.HIP_CRUD));
                    } else {
                        attachAdapter();
                    }
                    break;
                case 6:
                    kneeListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<HipExamPOJO>>() {
                    }.getType());
                    if (kneeListPOJO.isSuccess()) {
                        attachAdapter(new HipExamAdapter(this, null, kneeListPOJO.getResultList(), WebServicesUrls.KNEE_CRUD));
                    } else {
                        attachAdapter();
                    }
                    break;
                case 7:
                    ankleListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<HipExamPOJO>>() {
                    }.getType());
                    if (ankleListPOJO.isSuccess()) {
                        attachAdapter(new HipExamAdapter(this, null, ankleListPOJO.getResultList(), WebServicesUrls.ANKLE_CRUD));
                    } else {
                        attachAdapter();
                    }
                    break;
                case 8:
                    toesListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<HipExamPOJO>>() {
                    }.getType());
                    if (toesListPOJO.isSuccess()) {
                        attachAdapter(new HipExamAdapter(this, null, toesListPOJO.getResultList(), WebServicesUrls.TOES_CRUD));
                    } else {
                        attachAdapter();
                    }
                    break;
                case 9:
                    ShoulderListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<HipExamPOJO>>() {
                    }.getType());
                    if (ShoulderListPOJO.isSuccess()) {
                        attachAdapter(new HipExamAdapter(this, null, ShoulderListPOJO.getResultList(), WebServicesUrls.SHOULDER_CRUD));
                    } else {
                        attachAdapter();
                    }
                    break;
                case 10:
                    elbowListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<HipExamPOJO>>() {
                    }.getType());
                    if (elbowListPOJO.isSuccess()) {
                        attachAdapter(new HipExamAdapter(this, null, elbowListPOJO.getResultList(), WebServicesUrls.ELBOW_CRUD));
                    } else {
                        attachAdapter();
                    }
                    break;
                case 11:
                    forearmListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<HipExamPOJO>>() {
                    }.getType());
                    if (forearmListPOJO.isSuccess()) {
                        attachAdapter(new HipExamAdapter(this, null, forearmListPOJO.getResultList(), WebServicesUrls.ELBOW_CRUD));
                    } else {
                        attachAdapter();
                    }
                    break;
                case 12:
                    wristListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<HipExamPOJO>>() {
                    }.getType());
                    if (wristListPOJO.isSuccess()) {
                        attachAdapter(new HipExamAdapter(this, null, wristListPOJO.getResultList(), WebServicesUrls.WRIST_CRUD));
                    } else {
                        attachAdapter();
                    }
                    break;
                case 13:
                    fingersListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<HipExamPOJO>>() {
                    }.getType());
                    if (fingersListPOJO.isSuccess()) {
                        attachAdapter(new HipExamAdapter(this, null, fingersListPOJO.getResultList(), WebServicesUrls.FINGER_CRUD));
                    } else {
                        attachAdapter();
                    }
                    break;
                case 14:
                    sacrollicListPOJO = new Gson().fromJson(response, new TypeToken<ResponseListPOJO<HipExamPOJO>>() {
                    }.getType());
                    if (sacrollicListPOJO.isSuccess()) {
                        attachAdapter(new HipExamAdapter(this, null, sacrollicListPOJO.getResultList(), WebServicesUrls.SACROILIC_CRUD));
                    } else {
                        attachAdapter();
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void attachAdapter(RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_complaint.setHasFixedSize(true);
        rv_complaint.setAdapter(adapter);
        rv_complaint.setLayoutManager(layoutManager);
        rv_complaint.setNestedScrollingEnabled(false);
        rv_complaint.setItemAnimator(new DefaultItemAnimator());
    }

    public void addExam() {
        switch (spinner_motor.getSelectedItemPosition()) {
            case 1:
                startActivity(new Intent(MotorExamActivity.this, AddCombinedSpineActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
            case 2:
                startActivity(new Intent(MotorExamActivity.this, AddCervicalSpineActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
            case 3:
                startActivity(new Intent(MotorExamActivity.this, AddThoraccicActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
            case 4:
                startActivity(new Intent(MotorExamActivity.this, AddLumbarActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
            case 5:
                startActivity(new Intent(MotorExamActivity.this, AddHipActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
            case 6:
                startActivity(new Intent(MotorExamActivity.this, AddKneeActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
            case 7:
                startActivity(new Intent(MotorExamActivity.this, AddAnkleActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
            case 8:
                startActivity(new Intent(MotorExamActivity.this, AddToesActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
            case 9:
                startActivity(new Intent(MotorExamActivity.this, AddShoulderActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
            case 10:
                startActivity(new Intent(MotorExamActivity.this, AddElbowActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
            case 11:
                startActivity(new Intent(MotorExamActivity.this, AddForeArmActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
            case 12:
                startActivity(new Intent(MotorExamActivity.this, AddWristActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
            case 13:
                startActivity(new Intent(MotorExamActivity.this, AddFingerActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
            case 14:
                startActivity(new Intent(MotorExamActivity.this, AddSacrollicActivity.class).putExtra("patientPOJO", patientPOJO));
                break;
        }
    }

    ComplaintAdapter patientListAdapter;
    List<String> patientList = new ArrayList<>();

    public void attachAdapter() {
        patientListAdapter = new ComplaintAdapter(this, null, patientList, 0);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_complaint.setHasFixedSize(true);
        rv_complaint.setAdapter(patientListAdapter);
        rv_complaint.setLayoutManager(layoutManager);
        rv_complaint.setNestedScrollingEnabled(false);
        rv_complaint.setItemAnimator(new DefaultItemAnimator());
    }

    MenuItem show_graph;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph, menu);//Menu Resource, Menu
        show_graph = menu.findItem(R.id.show_graph);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_graph:
                showGraphMenu();
//                startActivity(new Intent(this, GraphTesting.class));
                return true;
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void showGraphMenu() {
        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_graph_view);
        dialog1.setTitle("Select");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_left = dialog1.findViewById(R.id.btn_left);
        Button btn_right = dialog1.findViewById(R.id.btn_right);
        Button btn_cancel = dialog1.findViewById(R.id.btn_cancel);

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                getLeftGraphPosition(spinner_motor.getSelectedItemPosition());
            }
        });

        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                getRightGraphPosition(spinner_motor.getSelectedItemPosition());
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

    }

    public void getLeftGraphPosition(int position) {
        switch (position) {
            case 5:
                if (hipExamPOJOResponseListPOJO != null) {
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getHipLeftDateValueList(hipExamPOJOResponseListPOJO.getResultList())));
                }
                break;
            case 6:
                if (kneeListPOJO != null) {
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getKneeLeftDateValueList(kneeListPOJO.getResultList())));
                }
                break;
            case 7:
                //ankle
                if (ankleListPOJO != null) {
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getAnkleLeftDateValueList(ankleListPOJO.getResultList())));
                }
                break;

            case 8:
                //toes
                if (toesListPOJO != null) {
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getToesLeftDateValueList(toesListPOJO.getResultList())));
                }
                break;
            case 9:
                //shoulder
                if (ShoulderListPOJO != null) {
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getShoulderLeftDateValueList(ShoulderListPOJO.getResultList())));
                }
                break;
            case 10:
                //elbow
                if (elbowListPOJO != null) {
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getKneeLeftDateValueList(elbowListPOJO.getResultList())));
                }
                break;
            case 11:
                //forearm
                if(forearmListPOJO!=null){
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getForearmLeftDateValueList(forearmListPOJO.getResultList())));
                }
                break;
            case 12:
                //wrist
                if(wristListPOJO!=null){
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getWristLeftDateValueList(wristListPOJO.getResultList())));
                }
                break;
            case 13:
                //finger
                if(fingersListPOJO!=null){
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getFingetLeftDateValueList(fingersListPOJO.getResultList())));
                }
                break;
        }
    }

    public void getRightGraphPosition(int position) {
        switch (position) {
            case 5:
                if (hipExamPOJOResponseListPOJO != null) {
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getHipRightDateValueList(hipExamPOJOResponseListPOJO.getResultList())));
                }
                break;
            case 6:
                if (kneeListPOJO != null) {
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getKneeRightDateValueList(kneeListPOJO.getResultList())));
                }
                break;
            case 7:
                //ankle
                if (ankleListPOJO != null) {
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getAnkleRightDateValueList(ankleListPOJO.getResultList())));
                }
                break;

            case 8:
                //toes
                if (toesListPOJO != null) {
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getToesRightDateValueList(toesListPOJO.getResultList())));
                }
                break;
            case 9:
                //shoulder
                if (ShoulderListPOJO != null) {
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getShoulderRightDateValueList(ShoulderListPOJO.getResultList())));
                }
                break;
            case 10:
                //elbow
                if (elbowListPOJO != null) {
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getKneeRightDateValueList(elbowListPOJO.getResultList())));
                }
                break;
            case 11:
                //forearm
                if(forearmListPOJO!=null){
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getForearmRightDateValueList(forearmListPOJO.getResultList())));
                }
                break;
            case 12:
                //wrist
                if(wristListPOJO!=null){
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getWristRightDateValueList(wristListPOJO.getResultList())));
                }
                break;
            case 13:
                //finger
                if(fingersListPOJO!=null){
                    startActivity(new Intent(MotorExamActivity.this, GraphTesting.class).putExtra("graphPOJO", MotorExamGraph.getFingetRightDateValueList(fingersListPOJO.getResultList())));
                }
                break;
        }
    }


}