package com.caprispine.caprispine.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.caprispine.caprispine.R;
import com.caprispine.caprispine.activity.SearchUserActivity;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 26-05-2017.
 */

public class StaffListAdapter extends RecyclerView.Adapter<StaffListAdapter.MyViewHolder> {

    private List<StaffPOJO> horizontalList;
    private Activity activity;
    private Fragment fragment;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_patient;
        public TextView tv_name, tv_email;
        public CircleImageView img_profile;

        public MyViewHolder(View view) {
            super(view);
            ll_patient = view.findViewById(R.id.ll_patient);
            tv_name = view.findViewById(R.id.tv_name);
            tv_email = view.findViewById(R.id.tv_email);
            img_profile = view.findViewById(R.id.img_profile);
        }
    }

    public StaffListAdapter(Activity activity, Fragment fragment, List<StaffPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.fragment = fragment;
        this.activity = activity;
    }

    @Override
    public StaffListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_patient_list_item, parent, false);

        return new StaffListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StaffListAdapter.MyViewHolder holder, final int position) {

        holder.tv_name.setText(horizontalList.get(position).getFirstName() + " " + horizontalList.get(position).getLastName());
        holder.tv_email.setText(horizontalList.get(position).getEmail());

        Glide.with(activity.getApplicationContext())
                .load(WebServicesUrls.PROFILE_PIC_BASE_URL + horizontalList.get(position).getProfilePic())
                .error(R.drawable.ic_action_person)
                .placeholder(R.drawable.ic_action_person)
                .dontAnimate()
                .into(holder.img_profile);


        holder.ll_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activity instanceof SearchUserActivity) {
                    SearchUserActivity searchUserActivity= (SearchUserActivity) activity;
                    searchUserActivity.openChatPatientActivity(horizontalList.get(position).getId(),horizontalList.get(position).getFirstName() + " " + horizontalList.get(position).getLastName());
                }
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
