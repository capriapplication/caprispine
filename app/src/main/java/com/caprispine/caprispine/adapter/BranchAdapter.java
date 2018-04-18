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
import com.caprispine.caprispine.activity.BranchListActivity;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
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

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.MyViewHolder> {

    private List<BranchResultPOJO> horizontalList;
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

    public BranchAdapter(Activity activity, Fragment fragment, List<BranchResultPOJO> horizontalList) {
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

        holder.tv_branch_name.setText(horizontalList.get(position).getBranchName()+" ("+horizontalList.get(position).getBranchCode()+")");
        holder.ll_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBranchDialog(horizontalList.get(position),position);
            }
        });
    }

    public void showBranchDialog(final BranchResultPOJO branchResultPOJO, final int position){
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

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                showBranchDeleteAlert(branchResultPOJO,position);
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                if(activity instanceof BranchListActivity){
                    BranchListActivity branchListFragment= (BranchListActivity) activity;
                    branchListFragment.callUpdateBranch(branchResultPOJO);
                }

            }
        });
    }

    public void showBranchDeleteAlert(final BranchResultPOJO branchPOJO, final int position){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage("Do you want to delete "+branchPOJO.getBranchName()+" branch?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        callBranchDeleteAPI(branchPOJO,position);
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

    public void callBranchDeleteAPI(BranchResultPOJO branchResultPOJO, final int position){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","delete_branch"));
        nameValuePairs.add(new BasicNameValuePair("branch_id",branchResultPOJO.getBranchId()));
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
        },"CALL_BRANCH_DELETE",true).execute(WebServicesUrls.BRANCH_CRUD);
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
