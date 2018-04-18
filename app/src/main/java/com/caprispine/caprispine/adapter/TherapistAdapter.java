package com.caprispine.caprispine.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.activity.CreateTherapistActivity;
import com.caprispine.caprispine.activity.TherapistPatientActivity;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class TherapistAdapter extends RecyclerView.Adapter<TherapistAdapter.MyViewHolder> {

    private List<TherapistPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();
    private DatabaseReference root;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_user, tv_email, tv_time, tv_location;
        public LinearLayout ll_doctors;
        public ImageView iv_therapist_menu;


        public MyViewHolder(View view) {
            super(view);
            tv_user = (TextView) view.findViewById(R.id.tv_user);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_location = (TextView) view.findViewById(R.id.tv_location);
            tv_email = (TextView) view.findViewById(R.id.tv_email);
            ll_doctors = (LinearLayout) view.findViewById(R.id.ll_doctors);
            iv_therapist_menu = (ImageView) view.findViewById(R.id.iv_therapist_menu);

        }
    }


    public TherapistAdapter(Activity activity, List<TherapistPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
        root = FirebaseDatabase.getInstance().getReference().getRoot();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_therapist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_user.setText(horizontalList.get(position).getFirstName() + " " + horizontalList.get(position).getLastName());
        holder.tv_email.setText(horizontalList.get(position).getEmail());
        holder.tv_time.setText(horizontalList.get(position).getStartingTime() + " - " + horizontalList.get(position).getEndTime());

        holder.iv_therapist_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(activity, view);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        switch (menuitem.getItemId()) {
                            case R.id.popup_edit:
                                Intent intent = new Intent(activity, CreateTherapistActivity.class);
                                intent.putExtra("therapistPOJO", horizontalList.get(position));
                                activity.startActivity(intent);
                                break;
                            case R.id.popup_delete:
                                showDoctorDeleteDialog(horizontalList.get(position), position);
                                break;
                            case R.id.popup_patients:
                                Intent intent1=new Intent(activity, TherapistPatientActivity.class);
                                intent1.putExtra("therapist",horizontalList.get(position));
                                activity.startActivity(intent1);
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
                Intent intent = new Intent(activity, CreateTherapistActivity.class);
                intent.putExtra("therapistPOJO", horizontalList.get(position));
                activity.startActivity(intent);
            }
        });
        Log.d(TagUtils.getTag(), "doctor id:-" + horizontalList.get(position).getId());
        //        DatabaseReference root= FirebaseDatabase.getInstance().getReference().getRoot();
        root.child("therapist").child(horizontalList.get(position).getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if(dataSnapshot.getValue().toString().equals("")) {
                        holder.tv_location.setText(dataSnapshot.getValue().toString());
                    }else{
                        holder.tv_location.setText(horizontalList.get(position).getAddress());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TagUtils.getTag(), "Failed to read app title value.", databaseError.toException());
            }
        });
    }

    public void showDoctorDeleteDialog(final TherapistPOJO doctorResultPOJO, final int position) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage("Do you want to delete " + doctorResultPOJO.getFirstName() + " " + doctorResultPOJO.getLastName());
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        deleteUser(doctorResultPOJO,position);
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

    public void deleteUser(TherapistPOJO therapistPOJO, final int position){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","delete_user"));
        nameValuePairs.add(new BasicNameValuePair("user_id",therapistPOJO.getUserPOJO().getUserType()));
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
