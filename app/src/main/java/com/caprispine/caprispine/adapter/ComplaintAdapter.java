package com.caprispine.caprispine.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.activity.assessment.ChiefComplaintActivity;
import com.caprispine.caprispine.activity.assessment.PainActivity;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.MyViewHolder> {

    private List<String> horizontalList;
    private Activity activity;
    private Fragment fragment;
    private int position;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_complaint;
        public MyViewHolder(View view) {
            super(view);
            ll_complaint=view.findViewById(R.id.ll_complaint);
        }
    }

    public ComplaintAdapter(Activity activity, Fragment fragment, List<String> horizontalList,int position) {
        this.horizontalList = horizontalList;
        this.fragment=fragment;
        this.activity = activity;
        this.position=position;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_complaint_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.ll_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
