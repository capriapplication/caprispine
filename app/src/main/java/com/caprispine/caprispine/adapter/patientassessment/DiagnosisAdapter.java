package com.caprispine.caprispine.adapter.patientassessment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.pojo.patientassessment.DiagnosisPOJO;
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

public class DiagnosisAdapter extends RecyclerView.Adapter<DiagnosisAdapter.MyViewHolder> {

    private List<DiagnosisPOJO> horizontalList;
    private Activity activity;
    private Fragment fragment;
    private String type;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_complaint;
        public TextView tv_date;
        public ImageView iv_delete;

        public MyViewHolder(View view) {
            super(view);
            ll_complaint = view.findViewById(R.id.ll_complaint);
            tv_date = view.findViewById(R.id.tv_date);
            iv_delete = view.findViewById(R.id.iv_delete);
        }
    }

    public DiagnosisAdapter(Activity activity, Fragment fragment, List<DiagnosisPOJO> horizontalList, String type) {
        this.horizontalList = horizontalList;
        this.fragment = fragment;
        this.activity = activity;
        this.type = type;
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
                deleteComplaint(horizontalList.get(position), position);
            }
        });
        holder.ll_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiagnosis(horizontalList.get(position), type);
            }
        });
    }

    public void showDiagnosis(final DiagnosisPOJO diagnosisPOJO, String type) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_DeviceDefault_Light_Dialog);

        //setting custom layout to dialog
        dialog.setContentView(R.layout.dialog_edit_casenotes);
        dialog.setTitle("Report");

        //adding text dynamically
        final EditText editTextcontents = (EditText) dialog.findViewById(R.id.edtxt_old_note);
        editTextcontents.setText(diagnosisPOJO.getDescription());

        String update_url = "";
        final ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        if (type.equals("physio")) {
            update_url = WebServicesUrls.PHYSIOTHERAPUTIC_CRUD;
            nameValuePairs.add(new BasicNameValuePair("request_action", "update_complaint"));
            nameValuePairs.add(new BasicNameValuePair("id", diagnosisPOJO.getId()));
        }else if(type.equals("case")){
            update_url = WebServicesUrls.CASE_NOTE_CRUD;
            nameValuePairs.add(new BasicNameValuePair("request_action", "update_complaint"));
            nameValuePairs.add(new BasicNameValuePair("id", diagnosisPOJO.getId()));
        }else if(type.equals("medical")){
            update_url = WebServicesUrls.MEDICAL_CRUD;
            nameValuePairs.add(new BasicNameValuePair("request_action", "update_complaint"));
            nameValuePairs.add(new BasicNameValuePair("id", diagnosisPOJO.getId()));
        }else if(type.equals("remark")){
            update_url = WebServicesUrls.REMARK_NOTE_CRUD;
            nameValuePairs.add(new BasicNameValuePair("request_action", "update_complaint"));
            nameValuePairs.add(new BasicNameValuePair("id", diagnosisPOJO.getId()));
        }else if(type.equals("progress")){
            update_url = WebServicesUrls.PROGRESS_NOTE_CRUD;
            nameValuePairs.add(new BasicNameValuePair("request_action", "update_complaint"));
            nameValuePairs.add(new BasicNameValuePair("id", diagnosisPOJO.getId()));
        }


        //adding button click event
        Button dismissButton = (Button) dialog.findViewById(R.id.btn_save);
        final String finalUpdate_url = update_url;
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameValuePairs.add(new BasicNameValuePair("patient_id",diagnosisPOJO.getPatientId()));
                nameValuePairs.add(new BasicNameValuePair("description",editTextcontents.getText().toString()));

                new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String[] msg) {
                        try{
                            JSONObject jsonObject=new JSONObject(msg[1]);
                            if(jsonObject.optBoolean("success")){
                                diagnosisPOJO.setDescription(editTextcontents.getText().toString());
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                }, "", true).execute(finalUpdate_url);
            }
        });
        dialog.show();
    }

    public void deleteComplaint(DiagnosisPOJO historyPOJO, final int position) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "delete_complaint"));
        nameValuePairs.add(new BasicNameValuePair("id", historyPOJO.getId()));
        new WebServiceBase(nameValuePairs, activity.getApplicationContext(), new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                Log.d(TagUtils.getTag(), msg[0] + ":-" + msg[1]);
                try {
                    JSONObject jsonObject = new JSONObject(msg[1]);
                    if (jsonObject.optString("success").equals("true")) {
                        ToastClass.showShortToast(activity.getApplicationContext(), "Exam Deleted Successfully");
                        horizontalList.remove(position);
                        notifyDataSetChanged();
                    } else {
                        ToastClass.showShortToast(activity.getApplicationContext(), "No Patients Found");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "", false).execute(WebServicesUrls.PAIN_CRUD);
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
