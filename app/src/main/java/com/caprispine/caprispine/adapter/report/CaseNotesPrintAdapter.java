package com.caprispine.caprispine.adapter.report;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.pojo.patientassessment.DiagnosisPOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class CaseNotesPrintAdapter extends RecyclerView.Adapter<CaseNotesPrintAdapter.MyViewHolder> {

    private List<DiagnosisPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date;
        public TextView tv_name;
        public TextView tv_report;



        public MyViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
//            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_report = (TextView) view.findViewById(R.id.tv_report);
        }
    }


    public CaseNotesPrintAdapter(Activity activity, List<DiagnosisPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_notes, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_date.setText(horizontalList.get(position).getDate());
//        holder.tv_name.setText(horizontalList.get(position).getFirstName()+" "+horizontalList.get(position).getLastName());
        holder.tv_report.setText(horizontalList.get(position).getDescription());
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
