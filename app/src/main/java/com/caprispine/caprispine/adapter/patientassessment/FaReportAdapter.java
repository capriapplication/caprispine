package com.caprispine.caprispine.adapter.patientassessment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.pojo.patientassessment.FaPOJO;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class FaReportAdapter extends RecyclerView.Adapter<FaReportAdapter.ViewHolder> {
    private List<List<FaPOJO>> items;
    Activity activity;
    Fragment fragment;
    public FaReportAdapter(Activity activity, Fragment fragment, List<List<FaPOJO>> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public FaReportAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_new_fa_score, parent, false);
        return new FaReportAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FaReportAdapter.ViewHolder holder, final int position) {

        List<FaPOJO> faDateDataPOJOS=items.get(position);
        if(faDateDataPOJOS.size()>0){
            String problem_text="";
            for(int i=0;i<faDateDataPOJOS.size();i++){
                FaPOJO faDateDataPOJO=faDateDataPOJOS.get(i);
                Log.d(TagUtils.getTag(),"fa score:-"+faDateDataPOJO.toString());
                if(i==(faDateDataPOJOS.size()-1)) {
                    problem_text += faDateDataPOJO.getProblemDescriptionPOJO().getName() + " :- " + faDateDataPOJO.getTotal();
                }else{
                    problem_text += faDateDataPOJO.getProblemDescriptionPOJO().getName() + " :- " + faDateDataPOJO.getTotal() + "\n";
                }
            }
            holder.tv_score.setText(problem_text);
        }

        if(faDateDataPOJOS.size()>0) {
            holder.tv_date.setText(faDateDataPOJOS.get(0).getDate());
        }

        holder.itemView.setTag(items.get(position));
    }


    private final String TAG = getClass().getSimpleName();

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView tv_score1,tv_score2,tv_score3,tv_date;
        public TextView tv_date,tv_score;
        public CardView ll_score_card;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_score=itemView.findViewById(R.id.tv_score);
            tv_date=itemView.findViewById(R.id.tv_date);
            ll_score_card=itemView.findViewById(R.id.ll_score_card);
        }
    }
}
