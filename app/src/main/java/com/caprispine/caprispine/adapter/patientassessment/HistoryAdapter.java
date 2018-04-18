package com.caprispine.caprispine.adapter.patientassessment;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.activity.assessment.HistoryActivity;
import com.caprispine.caprispine.pojo.patientassessment.HistoryPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<HistoryPOJO> horizontalList;
    private Activity activity;
    private Fragment fragment;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_complaint;
        public TextView tv_date;
        public ImageView iv_delete;
        public MyViewHolder(View view) {
            super(view);
            ll_complaint=view.findViewById(R.id.ll_complaint);
            tv_date=view.findViewById(R.id.tv_date);
            iv_delete=view.findViewById(R.id.iv_delete);
        }
    }

    public HistoryAdapter(Activity activity, Fragment fragment, List<HistoryPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.fragment=fragment;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_chief_complaint, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_date.setText(horizontalList.get(position).getDate());
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteComplaint(horizontalList.get(position),position);
            }
        });
        holder.ll_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistoryActivity complaintActivity= (HistoryActivity) activity;
                complaintActivity.updateComplaint(horizontalList.get(position));
            }
        });
    }

    public void deleteComplaint(HistoryPOJO historyPOJO, final int position){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","delete_complaint"));
        nameValuePairs.add(new BasicNameValuePair("id",historyPOJO.getId()));
        new WebServiceBase(nameValuePairs, activity.getApplicationContext(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                Log.d(TagUtils.getTag(),msg[0]+":-"+msg[1]);
                try{
                    JSONObject jsonObject=new JSONObject(msg[1]);
                    if(jsonObject.optString("success").equals("true")){
                        ToastClass.showShortToast(activity.getApplicationContext(),"record deleted");
                        horizontalList.remove(position);
                        notifyDataSetChanged();
                    }else{
                        ToastClass.showShortToast(activity.getApplicationContext(),"No Patients Found");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, "",false).execute(WebServicesUrls.HISTORY_CRUD);
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
