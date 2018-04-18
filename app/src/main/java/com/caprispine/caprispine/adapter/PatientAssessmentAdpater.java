package com.caprispine.caprispine.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.activity.assessment.BodyChartActivity;
import com.caprispine.caprispine.activity.assessment.CaseNewsActivity;
import com.caprispine.caprispine.activity.assessment.ChiefComplaintActivity;
import com.caprispine.caprispine.activity.assessment.FAReportActivity;
import com.caprispine.caprispine.activity.assessment.HistoryActivity;
import com.caprispine.caprispine.activity.assessment.InvestigationActivity;
import com.caprispine.caprispine.activity.assessment.MedicalActivity;
import com.caprispine.caprispine.activity.assessment.MotorExamActivity;
import com.caprispine.caprispine.activity.assessment.NDTPActivity;
import com.caprispine.caprispine.activity.assessment.NeuroActivity;
import com.caprispine.caprispine.activity.assessment.PainActivity;
import com.caprispine.caprispine.activity.assessment.PhotoVideoActivity;
import com.caprispine.caprispine.activity.assessment.PhysicalActivity;
import com.caprispine.caprispine.activity.assessment.PhysioTheraputicActivity;
import com.caprispine.caprispine.activity.assessment.ProgressNotesActivity;
import com.caprispine.caprispine.activity.assessment.RemarkActivity;
import com.caprispine.caprispine.activity.assessment.SensoryActivity;
import com.caprispine.caprispine.activity.assessment.SpecialExamActivity;
import com.caprispine.caprispine.activity.assessment.TreatmentGivenActivity;
import com.caprispine.caprispine.activity.assessment.TreatmentPlanActivity;
import com.caprispine.caprispine.pojo.user.PatientPOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class PatientAssessmentAdpater extends RecyclerView.Adapter<PatientAssessmentAdpater.MyViewHolder> {

    private List<String> items;
    private Activity activity;
    private Fragment fragment;
    private PatientPOJO patientPOJO;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rl_row;
        public TextView tv_title;

        public MyViewHolder(View view) {
            super(view);
            rl_row = view.findViewById(R.id.rl_row);
            tv_title = view.findViewById(R.id.tv_title);
        }
    }

    public PatientAssessmentAdpater(Activity activity, Fragment fragment, List<String> items, PatientPOJO patientPOJO) {
        this.items = items;
        this.fragment = fragment;
        this.activity = activity;
        this.patientPOJO=patientPOJO;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_patient_assessment_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_title.setText(items.get(position));
        holder.rl_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0:
                        activity.startActivity(new Intent(activity, ChiefComplaintActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 1:
                        activity.startActivity(new Intent(activity, HistoryActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 2:
                        activity.startActivity(new Intent(activity, PainActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 3:
                        activity.startActivity(new Intent(activity, PhysicalActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 4:
                        activity.startActivity(new Intent(activity, MotorExamActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 5:
                        activity.startActivity(new Intent(activity, SensoryActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 6:
                        activity.startActivity(new Intent(activity, NeuroActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 7:
                        activity.startActivity(new Intent(activity, NDTPActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 8:
                        activity.startActivity(new Intent(activity, SpecialExamActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 9:
                        activity.startActivity(new Intent(activity, FAReportActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 10:
                        activity.startActivity(new Intent(activity, InvestigationActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 11:
                        activity.startActivity(new Intent(activity, PhysioTheraputicActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 12:
                        activity.startActivity(new Intent(activity, MedicalActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 13:
                        activity.startActivity(new Intent(activity, TreatmentPlanActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 14:
                        activity.startActivity(new Intent(activity, TreatmentGivenActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 15:
                        activity.startActivity(new Intent(activity, CaseNewsActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 16:
                        activity.startActivity(new Intent(activity, ProgressNotesActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 17:
                        activity.startActivity(new Intent(activity, RemarkActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 18:
                        activity.startActivity(new Intent(activity, BodyChartActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    case 19:
                        activity.startActivity(new Intent(activity, PhotoVideoActivity.class).putExtra("patientPOJO",patientPOJO));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        } else {
            return 0;
        }
    }
}
