package com.caprispine.caprispine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.adapter.ViewPagerAdapter;
import com.caprispine.caprispine.fragment.SearchUserFragment;
import com.caprispine.caprispine.pojo.ResponsePOJO;
import com.caprispine.caprispine.pojo.user.AdminUserPOJO;
import com.caprispine.caprispine.pojo.user.AllUserPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.caprispine.caprispine.webservice.ResponseCallBack;
import com.caprispine.caprispine.webservice.WebServiceBaseResponse;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchUserActivity extends AppCompatActivity {

    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;

    TherapistPOJO therapistPOJO;
    StaffPOJO staffPOJO;
    PatientPOJO patientPOJO;
    AdminUserPOJO adminUserPOJO;

    String user_id = "";
    String is_admin = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        ButterKnife.bind(this);

        adminUserPOJO = (AdminUserPOJO) getIntent().getSerializableExtra("adminPOJO");
        therapistPOJO = (TherapistPOJO) getIntent().getSerializableExtra("therapistPOJO");
        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        staffPOJO = (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");

        setupViewPager(viewPager);

        if (therapistPOJO != null) {
            user_id = therapistPOJO.getUserPOJO().getUserId();
        } else if (staffPOJO != null) {
            user_id = staffPOJO.getUserPOJO().getUserId();
        } else if (patientPOJO != null) {
            user_id = patientPOJO.getUserPOJO().getUserId();
        } else if (adminUserPOJO != null) {
            user_id = adminUserPOJO.getUserPOJO().getUserId();
            is_admin = "1";
        } else {
            finish();
        }

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_search.getText().toString().length() > 0) {
                    searchUser(et_search.getText().toString());
                    ll_search.setVisibility(View.VISIBLE);
                } else {
                    ll_search.setVisibility(View.GONE);
                }
            }
        });
    }

    public void searchUser(String string) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "search_all_user"));
        nameValuePairs.add(new BasicNameValuePair("string", string));
        new WebServiceBaseResponse<AllUserPOJO>(nameValuePairs, this, new ResponseCallBack<AllUserPOJO>() {
            @Override
            public void onGetMsg(ResponsePOJO<AllUserPOJO> responsePOJO) {
                if (responsePOJO.isSuccess()) {
//                    List<PatientPOJO> patientPOJOS=responsePOJO.getResult().getPatientPOJOS();
                    if(patientSearchUserFragment!=null) {
                        patientSearchUserFragment.setPatients(responsePOJO.getResult().getPatientPOJOS());
                    }
                    if(therapistSearchUserFragment!=null) {
                        therapistSearchUserFragment.setTherapist(responsePOJO.getResult().getTherapistPOJOS());
                    }
                    if(staffSearchUserFragment!=null) {
                        staffSearchUserFragment.setStaff(responsePOJO.getResult().getStaffPOJOS());
                    }
                    if(adminSearchUserFragment!=null) {
                        adminSearchUserFragment.setAdmin(responsePOJO.getResult().getAdminUserPOJOS());
                    }
                } else {

                }
            }
        }, AllUserPOJO.class, "GET_USERS", false).execute(WebServicesUrls.USER_CRUD);
    }

    SearchUserFragment patientSearchUserFragment;
    SearchUserFragment therapistSearchUserFragment;
    SearchUserFragment staffSearchUserFragment;
    SearchUserFragment adminSearchUserFragment;

    private void setupViewPager(ViewPager viewPager) {


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        if (adminUserPOJO != null) {
            patientSearchUserFragment = new SearchUserFragment();
            therapistSearchUserFragment = new SearchUserFragment();
            staffSearchUserFragment = new SearchUserFragment();

            adapter.addFrag(patientSearchUserFragment, "Patient");
            adapter.addFrag(therapistSearchUserFragment, "Therapist");
            adapter.addFrag(staffSearchUserFragment, "Staff");

            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(3);
            tabs.setupWithViewPager(viewPager);
        } else if (staffPOJO!=null) {
            patientSearchUserFragment = new SearchUserFragment();
            therapistSearchUserFragment = new SearchUserFragment();

            adapter.addFrag(patientSearchUserFragment, "Patient");
            adapter.addFrag(therapistSearchUserFragment, "Therapist");

            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(2);
            tabs.setupWithViewPager(viewPager);
        }else if(therapistPOJO!=null){
            patientSearchUserFragment = new SearchUserFragment();
            staffSearchUserFragment = new SearchUserFragment();

            adapter.addFrag(patientSearchUserFragment, "Patient");
            adapter.addFrag(staffSearchUserFragment, "Staff");

            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(2);
            tabs.setupWithViewPager(viewPager);
        }else if(patientPOJO!=null){
            adminSearchUserFragment = new SearchUserFragment();
            therapistSearchUserFragment = new SearchUserFragment();
            staffSearchUserFragment = new SearchUserFragment();

            adapter.addFrag(therapistSearchUserFragment, "Therapist");
            adapter.addFrag(staffSearchUserFragment, "Staff");
            adapter.addFrag(adminSearchUserFragment, "Admin");

            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(3);
            tabs.setupWithViewPager(viewPager);
        }


    }


    public void openChatPatientActivity(String id,String name) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("fri_id", id);
        intent.putExtra("is_admin", is_admin);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}
