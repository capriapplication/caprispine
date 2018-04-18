package com.caprispine.caprispine.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.activity.CreateStaffActivity;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
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

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.MyViewHolder> {

    private List<StaffPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();
//    private DatabaseReference root;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_user, tv_email;
        public LinearLayout ll_doctors;
        public ImageView iv_therapist_menu;


        public MyViewHolder(View view) {
            super(view);
            tv_user = (TextView) view.findViewById(R.id.tv_user);
            tv_email = (TextView) view.findViewById(R.id.tv_email);
            ll_doctors = (LinearLayout) view.findViewById(R.id.ll_doctors);
            iv_therapist_menu = (ImageView) view.findViewById(R.id.iv_therapist_menu);
        }
    }


    public StaffAdapter(Activity activity, List<StaffPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
//        root = FirebaseDatabase.getInstance().getReference().getRoot();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_staff, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_user.setText(horizontalList.get(position).getFirstName() + " " + horizontalList.get(position).getLastName());
        holder.tv_email.setText(horizontalList.get(position).getEmail());

        holder.iv_therapist_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(activity, view);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        switch (menuitem.getItemId()) {
                            case R.id.popup_edit:
                                Intent intent = new Intent(activity, CreateStaffActivity.class);
                                intent.putExtra("staffPOJO", horizontalList.get(position));
                                activity.startActivity(intent);
                                break;
                            case R.id.popup_delete:
                                showDoctorDeleteDialog(horizontalList.get(position), position);
                                break;
                            case R.id.popup_patients:
//                                Intent intent=new Intent(activity, TherapistPatientActivity.class);
//                                intent.putExtra("therapist",horizontalList.get(position));
//                                activity.startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
                menu.inflate(R.menu.menu_therapist_option);

                menu.show();
            }
        });
        holder.ll_doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CreateStaffActivity.class);
                intent.putExtra("staffPOJO", horizontalList.get(position));
                activity.startActivity(intent);
            }
        });
    }

    public void showDoctorDeleteDialog(final StaffPOJO staffPOJO, final int position) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage("Do you want to delete " + staffPOJO.getFirstName() + " " + staffPOJO.getLastName());
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        deleteUser(staffPOJO,position);
//                        ManageTherapistActivity manageTherapistActivity = (ManageTherapistActivity) activity;
//                        manageTherapistActivity.deleteTherapist(doctorResultPOJO, position);
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


    public void deleteUser(StaffPOJO therapistPOJO, final int position){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","delete_user"));
        nameValuePairs.add(new BasicNameValuePair("user_id",therapistPOJO.getUserPOJO().getUserId()));
        nameValuePairs.add(new BasicNameValuePair("user_type",therapistPOJO.getUserPOJO().getUserType()));
        nameValuePairs.add(new BasicNameValuePair("id",therapistPOJO.getId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    JSONObject jsonObject=new JSONObject(msg[1]);
                    if(jsonObject.optBoolean("success")){
                        horizontalList.remove(position);
                        notifyDataSetChanged();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CALL_DELETE_USER",true).execute(WebServicesUrls.USER_CRUD);
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
