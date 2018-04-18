package com.caprispine.caprispine.adapter.patientassessment;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.activity.assessment.ProblemListActivity;
import com.caprispine.caprispine.pojo.problem.ProblemPOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class ProblemListAdapter extends RecyclerView.Adapter<ProblemListAdapter.MyViewHolder> {

    private List<ProblemPOJO> horizontalList;
    private Activity activity;
    private Fragment fragment;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_problem;
        public TextView tv_problem_name, tv_score;

        public MyViewHolder(View view) {
            super(view);
            ll_problem = view.findViewById(R.id.ll_problem);
            tv_problem_name = view.findViewById(R.id.tv_problem_name);
            tv_score = view.findViewById(R.id.tv_score);
        }
    }

    public ProblemListAdapter(Activity activity, Fragment fragment, List<ProblemPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.fragment = fragment;
        this.activity = activity;
    }

    @Override
    public ProblemListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_problems, parent, false);

        return new ProblemListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProblemListAdapter.MyViewHolder holder, final int position) {

        holder.tv_problem_name.setText(horizontalList.get(position).getName());
        holder.tv_score.setText("");

        holder.ll_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity instanceof ProblemListActivity) {
                    ProblemListActivity problemListActivity= (ProblemListActivity) activity;
                    problemListActivity.startQuestionaire(horizontalList.get(position),position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        if (horizontalList != null) {
            return horizontalList.size();
        } else {
            return 0;
        }
    }
}
