package com.caprispine.caprispine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.adapter.TreatmentAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.treatment.TreatmentPOJO;
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

public class TreatmentListActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_treatment_list)
    RecyclerView rv_treatment_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        attachAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        callTreatmentAPI();
    }

    public void callTreatmentAPI(){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","all_treatment"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                ResponseListPOJO<TreatmentPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<TreatmentPOJO>>() {
                }.getType());
                treatmentPOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    treatmentPOJOS.addAll(responseListPOJO.getResultList());
                } else {
                    ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                }
                adapter.notifyDataSetChanged();
            }
        },"CALL_TREATMENT",true).execute(WebServicesUrls.TREATMENT_CRUD);
    }

    public void callUpdateTreatment(TreatmentPOJO treatmentPOJO){
        Intent intent=new Intent(TreatmentListActivity.this,AddTreatmentPackagerActivity.class);
        intent.putExtra("treatmentPOJO",treatmentPOJO);
        startActivity(intent);
    }


    TreatmentAdapter adapter;
    List<TreatmentPOJO> treatmentPOJOS=new ArrayList<>();

    public void attachAdapter() {
        adapter = new TreatmentAdapter(this, null, treatmentPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_treatment_list.setHasFixedSize(true);
        rv_treatment_list.setAdapter(adapter);
        rv_treatment_list.setLayoutManager(layoutManager);
        rv_treatment_list.setNestedScrollingEnabled(false);
        rv_treatment_list.setItemAnimator(new DefaultItemAnimator());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_amount:
                startActivity(new Intent(TreatmentListActivity.this,AddTreatmentPackagerActivity.class));
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
