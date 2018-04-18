package com.caprispine.caprispine.adapter.patientassessment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
import com.caprispine.caprispine.activity.MoterExamViewActivity;
import com.caprispine.caprispine.pojo.patientassessment.motor.HipExamPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class HipExamAdapter extends RecyclerView.Adapter<HipExamAdapter.MyViewHolder> {

    private List<HipExamPOJO> horizontalList;
    private Activity activity;
    private Fragment fragment;
    private String delete_url;

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

    public HipExamAdapter(Activity activity, Fragment fragment, List<HipExamPOJO> horizontalList, String delete_url) {
        this.horizontalList = horizontalList;
        this.fragment=fragment;
        this.activity = activity;
        this.delete_url=delete_url;
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
                deleteComplaint(horizontalList.get(position).getId(),position);
            }
        });
        holder.ll_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, MoterExamViewActivity.class);
                intent.putExtra("image_url",horizontalList.get(position).getImage());
                activity.startActivity(intent);
//                ChiefComplaintActivity chiefComplaintActivity= (ChiefComplaintActivity) activity;
//                chiefComplaintActivity.updateComplaint(horizontalList.get(position));
            }
        });
    }

    public void deleteComplaint(String id, final int position){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","delete_complaint"));
        nameValuePairs.add(new BasicNameValuePair("id",id));
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
        }, "",false).execute(delete_url);
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
