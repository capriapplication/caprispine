package com.caprispine.caprispine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.pojo.patientassessment.SensoryPOJO;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 28-09-2017.
 */

public class SensoryReflexesFragment extends Fragment {

    SensoryPOJO sensoryExam;

    public SensoryReflexesFragment(SensoryPOJO sensoryExam) {
        this.sensoryExam = sensoryExam;
    }

    @BindView(R.id.rb_absent_c5)
    RadioButton rb_absent_c5;
    @BindView(R.id.rb_absent_c6)
    RadioButton rb_absent_c6;
    @BindView(R.id.rb_absent_c7)
    RadioButton rb_absent_c7;
    @BindView(R.id.rb_absent_c8)
    RadioButton rb_absent_c8;
    @BindView(R.id.rb_absent_l2)
    RadioButton rb_absent_l2;
    @BindView(R.id.rb_absent_l3)
    RadioButton rb_absent_l3;
    @BindView(R.id.rb_absent_s1)
    RadioButton rb_absent_s1;
    @BindView(R.id.rb_absent_s2)
    RadioButton rb_absent_s2;
    @BindView(R.id.rb_normal_c5)
    RadioButton rb_normal_c5;
    @BindView(R.id.rb_normal_c6)
    RadioButton rb_normal_c6;
    @BindView(R.id.rb_normal_c7)
    RadioButton rb_normal_c7;
    @BindView(R.id.rb_normal_c8)
    RadioButton rb_normal_c8;
    @BindView(R.id.rb_normal_l2)
    RadioButton rb_normal_l2;
    @BindView(R.id.rb_normal_l3)
    RadioButton rb_normal_l3;
    @BindView(R.id.rb_normal_s1)
    RadioButton rb_normal_s1;
    @BindView(R.id.rb_normal_s2)
    RadioButton rb_normal_s2;
    @BindView(R.id.rb_weak_c5)
    RadioButton rb_weak_c5;
    @BindView(R.id.rb_weak_c6)
    RadioButton rb_weak_c6;
    @BindView(R.id.rb_weak_c7)
    RadioButton rb_weak_c7;
    @BindView(R.id.rb_weak_c8)
    RadioButton rb_weak_c8;
    @BindView(R.id.rb_weak_l2)
    RadioButton rb_weak_l2;
    @BindView(R.id.rb_weak_l3)
    RadioButton rb_weak_l3;
    @BindView(R.id.rb_weak_s1)
    RadioButton rb_weak_s1;
    @BindView(R.id.rb_weak_s2)
    RadioButton rb_weak_s2;
    @BindView(R.id.rb_exaggerated_c5)
    RadioButton rb_exaggerated_c5;
    @BindView(R.id.rb_exaggerated_c6)
    RadioButton rb_exaggerated_c6;
    @BindView(R.id.rb_exaggerated_c7)
    RadioButton rb_exaggerated_c7;
    @BindView(R.id.rb_exaggerated_c8)
    RadioButton rb_exaggerated_c8;
    @BindView(R.id.rb_exaggerated_l2)
    RadioButton rb_exaggerated_l2;
    @BindView(R.id.rb_exaggerated_l3)
    RadioButton rb_exaggerated_l3;
    @BindView(R.id.rb_exaggerated_s1)
    RadioButton rb_exaggerated_s1;
    @BindView(R.id.rb_exaggerated_s2)
    RadioButton rb_exaggerated_s2;

    @BindView(R.id.rg_c5)
    RadioGroup rg_c5;
    @BindView(R.id.rg_c6)
    RadioGroup rg_c6;
    @BindView(R.id.rg_c7)
    RadioGroup rg_c7;
    @BindView(R.id.rg_c8)
    RadioGroup rg_c8;
    @BindView(R.id.rg_l2)
    RadioGroup rg_l2;
    @BindView(R.id.rg_l3)
    RadioGroup rg_l3;
    @BindView(R.id.rg_s1)
    RadioGroup rg_s1;
    @BindView(R.id.rg_s2)
    RadioGroup rg_s2;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_sensory_reflexes, container, false);
        this.view = view;
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (sensoryExam!=null) {
            setValues(sensoryExam);
        }
    }

    public void setValues(SensoryPOJO sensoryExam) {
        setRadio(rb_absent_c5, rb_normal_c5, rb_weak_c5, rb_exaggerated_c5, sensoryExam.getRefC5());
        setRadio(rb_absent_c6, rb_normal_c6, rb_weak_c6, rb_exaggerated_c6, sensoryExam.getRefC6());
        setRadio(rb_absent_c7, rb_normal_c7, rb_weak_c7, rb_exaggerated_c7, sensoryExam.getRefC7());
        setRadio(rb_absent_c8, rb_normal_c8, rb_weak_c8, rb_exaggerated_c8, sensoryExam.getRefC8());
        setRadio(rb_absent_l2, rb_normal_l2, rb_weak_l2, rb_exaggerated_l2, sensoryExam.getRefL2());
        setRadio(rb_absent_l3, rb_normal_l3, rb_weak_l3, rb_exaggerated_l3, sensoryExam.getRefL3());
        setRadio(rb_absent_s1, rb_normal_s1, rb_weak_s1, rb_exaggerated_s1, sensoryExam.getRefS1());
        setRadio(rb_absent_s2, rb_normal_s2, rb_weak_s2, rb_exaggerated_s2, sensoryExam.getRefS2());
    }
//
    public void setRadio(RadioButton absent, RadioButton normal, RadioButton weak, RadioButton exaggerated, String value) {
        if (value.equalsIgnoreCase("absent")) {
            absent.setChecked(true);
        } else if (value.equalsIgnoreCase("normal")) {
            normal.setChecked(true);
        } else if (value.equalsIgnoreCase("exaggerated")) {
            exaggerated.setChecked(true);
        } else if (value.equalsIgnoreCase("weak")) {
            weak.setChecked(true);
        }
    }

    public String getSelectedRadioValue(RadioGroup radioGroup) {
        try {
            int selectedId = radioGroup.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
            return radioButton.getText().toString();
        } catch (Exception e) {
            return "";
        }
    }

    public String getRg_c5() {
        return getSelectedRadioValue(rg_c5);
    }

    public String getRg_c6() {
        return getSelectedRadioValue(rg_c6);
    }

    public String getRg_c7() {
        return getSelectedRadioValue(rg_c7);
    }

    public String getRg_c8() {
        return getSelectedRadioValue(rg_c8);
    }

    public String getRg_l2() {
        return getSelectedRadioValue(rg_l2);
    }

    public String getRg_l3() {
        return getSelectedRadioValue(rg_l3);
    }

    public String getRg_s1() {
        return getSelectedRadioValue(rg_s1);
    }

    public String getRg_s2() {
        return getSelectedRadioValue(rg_s2);
    }
}