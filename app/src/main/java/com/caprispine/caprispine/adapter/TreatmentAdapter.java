package com.caprispine.caprispine.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.activity.TreatmentListActivity;
import com.caprispine.caprispine.pojo.treatment.TreatmentPOJO;
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

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.MyViewHolder> {

    private List<TreatmentPOJO> horizontalList;
    private Activity activity;
    private Fragment fragment;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_branch;
        public TextView tv_branch_name;
        public MyViewHolder(View view) {
            super(view);
            ll_branch=view.findViewById(R.id.ll_branch);
            tv_branch_name=view.findViewById(R.id.tv_branch_name);
        }
    }

    public TreatmentAdapter(Activity activity, Fragment fragment, List<TreatmentPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.fragment=fragment;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.infalte_branch_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_branch_name.setText(horizontalList.get(position).getName()+" ("+horizontalList.get(position).getPrice()+")");
        holder.ll_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTreatmentDialog(horizontalList.get(position),position);
            }
        });
    }

    public void showTreatmentDialog(final TreatmentPOJO treatmentPOJO, final int position){
        final Dialog dialog1 = new Dialog(activity, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_branch_menu);
        dialog1.setTitle("Select");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final Button btn_update= (Button) dialog1.findViewById(R.id.btn_update);
        Button btn_delete= (Button) dialog1.findViewById(R.id.btn_delete);

        btn_delete.setText("Delete Treatment");
        btn_update.setText("Update Treatment");

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                showTreatmentDeleteAPI(treatmentPOJO,position);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                if(activity instanceof TreatmentListActivity){
                    TreatmentListActivity treatmentListActivity= (TreatmentListActivity) activity;
                    treatmentListActivity.callUpdateTreatment(treatmentPOJO);
                }

            }
        });
    }

    public void showTreatmentDeleteAPI(final TreatmentPOJO treatmentPOJO, final int position){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage("Do you want to delete "+treatmentPOJO.getName()+" ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        callTreatmentDeleteAPI(treatmentPOJO,position);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void callTreatmentDeleteAPI(TreatmentPOJO treatmentPOJO, final int position){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","delete_treatment"));
        nameValuePairs.add(new BasicNameValuePair("id",treatmentPOJO.getId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    JSONObject jsonObject=new JSONObject(msg[1]);
                    if(jsonObject.optString("success").equals("true")){
                        horizontalList.remove(position);
                        notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CALL_TREATMENT_DELETE",true).execute(WebServicesUrls.TREATMENT_CRUD);
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
