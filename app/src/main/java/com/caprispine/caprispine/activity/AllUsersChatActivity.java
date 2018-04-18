package com.caprispine.caprispine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.adapter.AllChatUserAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.chat.AllChatPOJO;
import com.caprispine.caprispine.pojo.user.AdminUserPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.caprispine.caprispine.webservice.ResponseListCallback;
import com.caprispine.caprispine.webservice.ResponseListWebservice;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllUsersChatActivity extends AppCompatActivity {

    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.rv_users)
    RecyclerView rv_users;

    AdminUserPOJO adminUserPOJO;
    TherapistPOJO therapistPOJO;
    StaffPOJO staffPOJO;
    PatientPOJO patientPOJO;

    String user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users_chat);
        ButterKnife.bind(this);

        adminUserPOJO = (AdminUserPOJO) getIntent().getSerializableExtra("adminPOJO");
        therapistPOJO = (TherapistPOJO) getIntent().getSerializableExtra("therapistPOJO");
        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        staffPOJO = (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");

        if (therapistPOJO != null) {
            user_id = therapistPOJO.getUserPOJO().getUserId();
        } else if (staffPOJO != null) {
            user_id = staffPOJO.getUserPOJO().getUserId();
        } else if (patientPOJO != null) {
            user_id = patientPOJO.getUserPOJO().getUserId();
        } else if (adminUserPOJO != null) {
            user_id = adminUserPOJO.getUserPOJO().getUserId();
        } else {
            finish();
        }
        attachAdapter();
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllUsersChatActivity.this, SearchUserActivity.class);
                if (adminUserPOJO != null) {
                    intent.putExtra("adminPOJO", adminUserPOJO);
                }
                if (therapistPOJO != null) {
                    intent.putExtra("therapistPOJO", therapistPOJO);
                }
                if (staffPOJO != null) {
                    intent.putExtra("staffPOJO", staffPOJO);
                }
                if (patientPOJO != null) {
                    intent.putExtra("patientPOJO", patientPOJO);
                }
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        callAPI();
    }

    public void callAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_all_user_chatted"));
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        new ResponseListWebservice<AllChatPOJO>(nameValuePairs, this, new ResponseListCallback<AllChatPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<AllChatPOJO> responseListPOJO) {
                allChatPOJOS.clear();
                if (responseListPOJO.isSuccess()) {
                    allChatPOJOS.addAll(responseListPOJO.getResultList());
                }
                adapter.notifyDataSetChanged();
            }
        }, AllChatPOJO.class, "GET_ALL_CHATS", true).execute(WebServicesUrls.CHAT_CRUD);
    }


    AllChatUserAdapter adapter;
    List<AllChatPOJO> allChatPOJOS = new ArrayList<>();

    public void attachAdapter() {
        adapter = new AllChatUserAdapter(this, null, allChatPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_users.setHasFixedSize(true);
        rv_users.setAdapter(adapter);
        rv_users.setLayoutManager(layoutManager);
        rv_users.setNestedScrollingEnabled(false);
        rv_users.setItemAnimator(new DefaultItemAnimator());
    }
}
