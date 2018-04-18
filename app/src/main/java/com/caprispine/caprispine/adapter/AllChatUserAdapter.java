package com.caprispine.caprispine.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.caprispine.caprispine.R;
import com.caprispine.caprispine.activity.ChatActivity;
import com.caprispine.caprispine.pojo.chat.AllChatPOJO;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sunil on 26-05-2017.
 */

public class AllChatUserAdapter extends RecyclerView.Adapter<AllChatUserAdapter.MyViewHolder> {

    private List<AllChatPOJO> horizontalList;
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

    public AllChatUserAdapter(Activity activity, Fragment fragment, List<AllChatPOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.fragment=fragment;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflat_all_chat_users, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.tv_name.setText(horizontalList.get(position).getUser().getFirstName() + " " + horizontalList.get(position).getUser().getLastName());
        holder.tv_email.setText(horizontalList.get(position).getUser().getEmail());

        Glide.with(activity.getApplicationContext())
                .load(WebServicesUrls.PROFILE_PIC_BASE_URL + horizontalList.get(position).getUser().getProfilePic())
                .error(R.drawable.ic_action_person)
                .placeholder(R.drawable.ic_action_person)
                .dontAnimate()
                .into(holder.img_profile);


        holder.ll_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity,ChatActivity.class);
                intent.putExtra("user_id",horizontalList.get(position).getUserId());
                intent.putExtra("fri_id",horizontalList.get(position).getFriId());
                intent.putExtra("is_admin","1");
                intent.putExtra("name",horizontalList.get(position).getUser().getFirstName() + " " + horizontalList.get(position).getUser().getLastName());
                activity.startActivity(intent);
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
