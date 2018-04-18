package com.caprispine.caprispine.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.adapter.AdminListAdapter;
import com.caprispine.caprispine.adapter.PatientListAdapter;
import com.caprispine.caprispine.adapter.StaffListAdapter;
import com.caprispine.caprispine.adapter.TherapistListAdapter;
import com.caprispine.caprispine.pojo.user.AdminUserPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 13-04-2018.
 */

public class SearchUserFragment extends Fragment {

    @BindView(R.id.rv_users)
    RecyclerView rv_users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_search_user_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    PatientListAdapter patientListAdapter;
    List<PatientPOJO> patientPOJOS = new ArrayList<>();

    public void setPatients(List<PatientPOJO> patients) {
        if (patientListAdapter != null) {

        } else {
            attachPatientAdapter();
        }
        this.patientPOJOS.clear();
        this.patientPOJOS.addAll(patients);
        patientListAdapter.notifyDataSetChanged();
    }

    public void setTherapist(List<TherapistPOJO> therapist) {
        if (therapistListAdapter != null) {

        } else {
            attachTherapistAdapter();
        }
        this.therapistPOJOS.clear();
        this.therapistPOJOS.addAll(therapist);
        therapistListAdapter.notifyDataSetChanged();
    }

    public void setStaff(List<StaffPOJO> staff) {
        if (staffListAdapter != null) {

        } else {
            attachStaffAdapter();
        }
        this.staffPOJOS.clear();
        this.staffPOJOS.addAll(staff);
        staffListAdapter.notifyDataSetChanged();
    }

    public void setAdmin(List<AdminUserPOJO> adminUserPOJOS) {
        if (adminListAdapter != null) {

        } else {
            attachAdminAdapter();
        }
        this.adminUserPOJOS.clear();
        this.adminUserPOJOS.addAll(adminUserPOJOS);
        adminListAdapter.notifyDataSetChanged();
    }

    public void attachPatientAdapter() {

        patientListAdapter = new PatientListAdapter(getActivity(), null, patientPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_users.setHasFixedSize(true);
        rv_users.setAdapter(patientListAdapter);
        rv_users.setLayoutManager(layoutManager);
        rv_users.setNestedScrollingEnabled(false);
        rv_users.setItemAnimator(new DefaultItemAnimator());
    }

    TherapistListAdapter therapistListAdapter;
    List<TherapistPOJO> therapistPOJOS = new ArrayList<>();

    public void attachTherapistAdapter() {

        therapistListAdapter = new TherapistListAdapter(getActivity(), null, therapistPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_users.setHasFixedSize(true);
        rv_users.setAdapter(therapistListAdapter);
        rv_users.setLayoutManager(layoutManager);
        rv_users.setNestedScrollingEnabled(false);
        rv_users.setItemAnimator(new DefaultItemAnimator());
    }

    StaffListAdapter staffListAdapter;
    List<StaffPOJO> staffPOJOS = new ArrayList<>();

    public void attachStaffAdapter() {

        staffListAdapter = new StaffListAdapter(getActivity(), null, staffPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_users.setHasFixedSize(true);
        rv_users.setAdapter(staffListAdapter);
        rv_users.setLayoutManager(layoutManager);
        rv_users.setNestedScrollingEnabled(false);
        rv_users.setItemAnimator(new DefaultItemAnimator());
    }
    AdminListAdapter adminListAdapter;
    List<AdminUserPOJO> adminUserPOJOS = new ArrayList<>();

    public void attachAdminAdapter() {

        adminListAdapter = new AdminListAdapter(getActivity(), null, adminUserPOJOS);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_users.setHasFixedSize(true);
        rv_users.setAdapter(staffListAdapter);
        rv_users.setLayoutManager(layoutManager);
        rv_users.setNestedScrollingEnabled(false);
        rv_users.setItemAnimator(new DefaultItemAnimator());
    }
}
