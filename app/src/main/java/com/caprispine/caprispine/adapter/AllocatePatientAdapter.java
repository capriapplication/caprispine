package com.caprispine.caprispine.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.pojo.user.AllocatePatientPOJO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 26-05-2017.
 */

public class AllocatePatientAdapter extends RecyclerView.Adapter<AllocatePatientAdapter.MyViewHolder> {

    private List<AllocatePatientPOJO> horizontalList;
    private Activity activity;
    private Fragment fragment;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkbox;
        public TextView tv_email, tv_patient_name;
        public CircleImageView cv_patient;

        public MyViewHolder(View view) {
            super(view);
            cv_patient = view.findViewById(R.id.cv_patient);
            checkbox = view.findViewById(R.id.checkbox);
            tv_patient_name = view.findViewById(R.id.tv_patient_name);
            tv_email = view.findViewById(R.id.tv_email);
        }
    }

    public AllocatePatientAdapter(Activity activity, Fragment fragment, List<AllocatePatientPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.fragment = fragment;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_allocate_patient_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_email.setText(horizontalList.get(position).getEmail());
        holder.tv_patient_name.setText(horizontalList.get(position).getFirstName() + " " + horizontalList.get(position).getLastName());
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                horizontalList.get(position).setChecked(b);
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
