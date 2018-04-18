package com.caprispine.caprispine.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.pojo.appointment.AppointmentPOJO;
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

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {

    private List<AppointmentPOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();
    String user_type="";
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date,tv_time,tv_status,tv_confirm;
        public LinearLayout ll_appointment;
        public ImageView iv_delete;


        public MyViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
            ll_appointment = (LinearLayout) view.findViewById(R.id.ll_appointment);
            iv_delete = (ImageView) view.findViewById(R.id.iv_delete);

        }
    }


    public AppointmentAdapter(Activity activity, List<AppointmentPOJO> horizontalList,String user_type) {
        this.horizontalList = horizontalList;
        this.activity = activity;
        this.user_type=user_type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_doctor_appointments, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       holder.tv_date.setText(horizontalList.get(position).getBookingDate());
       holder.tv_time.setText(horizontalList.get(position).getBookingTime());
        if(horizontalList.get(position).getStatus().equals("1")){
            holder.tv_status.setText("Appointment Confirmed");
            holder.tv_confirm.setVisibility(View.GONE);
        }else{
            holder.tv_status.setText("Appointment Pending");
            holder.tv_confirm.setVisibility(View.VISIBLE);
        }
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ManageAppointmentActivity viewAllAppointmentsActivity= (ManageAppointmentActivity) activity;
//                viewAllAppointmentsActivity.cancelAppointment(horizontalList.get(position).getId(),horizontalList.get(position).getPatientId(),position);
                deleteAppointment(horizontalList.get(position).getId(),position);
            }
        });
        if(user_type.equals("0")){
            holder.tv_confirm.setVisibility(View.GONE);
            holder.iv_delete.setVisibility(View.GONE);
        }

        holder.tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAppointment(horizontalList.get(position),holder.tv_confirm,holder.tv_status);
            }
        });
    }

    public void confirmAppointment(AppointmentPOJO appointmentPOJO, final TextView textView,final TextView tv_status){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","confirm_appointment"));
        nameValuePairs.add(new BasicNameValuePair("id",appointmentPOJO.getId()));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    JSONObject jsonObject=new JSONObject(msg[1]);
                    if(jsonObject.optBoolean("success")){
                        textView.setVisibility(View.GONE);
                        tv_status.setText("Appointment Confirmed");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CALL_APPOINTMENT_CONFIRM",true).execute(WebServicesUrls.APPOINTMENT_CRUD);

    }

    public void deleteAppointment(String appointment_id, final int position){
        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action","delete_appointment"));
        nameValuePairs.add(new BasicNameValuePair("id",appointment_id));
        new WebServiceBase(nameValuePairs, activity, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    JSONObject jsonObject=new JSONObject(msg[1]);
                    if(jsonObject.optBoolean("success")){
                        horizontalList.remove(position);
                        notifyDataSetChanged();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"CALL_APPOINTMENT_CONFIRM",true).execute(WebServicesUrls.APPOINTMENT_CRUD);
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
