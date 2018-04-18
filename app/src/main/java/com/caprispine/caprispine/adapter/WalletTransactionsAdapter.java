package com.caprispine.caprispine.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.wallet.PatientWalletPOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class WalletTransactionsAdapter extends RecyclerView.Adapter<WalletTransactionsAdapter.MyViewHolder> {

    private List<PatientWalletPOJO> items;
    private Activity activity;
    private Fragment fragment;
    private PatientPOJO patientPOJO;
    private final String TAG = getClass().getSimpleName();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_id, tv_amount, tv_status, tv_timings, tv_mode,tv_trans_id;
        public LinearLayout ll_trans_amount;
        public MyViewHolder(View view) {
            super(view);
            tv_id = (TextView) view.findViewById(R.id.tv_id);
            tv_amount = (TextView) view.findViewById(R.id.tv_amount);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_timings = (TextView) view.findViewById(R.id.tv_timings);
            tv_mode = (TextView) view.findViewById(R.id.tv_mode);
            tv_trans_id = (TextView) view.findViewById(R.id.tv_trans_id);
            ll_trans_amount = (LinearLayout) view.findViewById(R.id.ll_trans_amount);
        }
    }

    public WalletTransactionsAdapter(Activity activity, Fragment fragment, List<PatientWalletPOJO> items, PatientPOJO patientPOJO) {
        this.items = items;
        this.fragment=fragment;
        this.activity = activity;
        this.patientPOJO=patientPOJO;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.infalte_wallet_trans_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.tv_id.setText(String.valueOf(position + 1));
        holder.tv_amount.setText(items.get(position).getAmount());

        holder.tv_mode.setText(UtilityFunction.getPaymentMode(items.get(position).getPaymentMode()));

        holder.tv_status.setText(UtilityFunction.getTransType(items.get(position).getTransType()));
        if(items.get(position).getTransId().equals("")) {
            holder.tv_trans_id.setText("-");
        }else{
            holder.tv_trans_id.setText(items.get(position).getTransId());
        }

//        holder.ll_trans_amount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(holder.tv_status.getText().toString().toLowerCase().equals("credit")){
//                    showReceiptDialog(items.get(position));
//                }
//            }
//        });

        holder.tv_timings.setText(items.get(position).getDate());
    }

    public void showReceiptDialog(final PatientWalletPOJO patientAmountPOJO){
        final Dialog dialog1 = new Dialog(activity, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_receipt);
        dialog1.setTitle("Transaction");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        TextView tv_date= (TextView) dialog1.findViewById(R.id.tv_date);
        TextView tv_receipt_number= (TextView) dialog1.findViewById(R.id.tv_receipt_number);
        TextView tv_received_from= (TextView) dialog1.findViewById(R.id.tv_received_from);
        TextView tv_amount= (TextView) dialog1.findViewById(R.id.tv_amount);
        Button btn_print= (Button) dialog1.findViewById(R.id.btn_print);


        tv_date.setText(patientAmountPOJO.getDate());
        tv_amount.setText("INR "+patientAmountPOJO.getAmount());
//        tv_receipt_number.setText("R"+branch_code+treatment_type+patientAmountPOJO.getReceipt_no());
        tv_received_from.setText(patientPOJO.getFirstName()+" "+patientPOJO.getLastName());

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent=new Intent(activity, IncomeReportPrintActivity.class);
//                intent.putExtra("type","patientreceipt");
//                intent.putExtra("pt_id",patientAmountPOJO.getPt_id());
//                intent.putExtra("patient_id",patientAmountPOJO.getPt_p_id());
//                intent.putExtra("branch_code",patientAmountPOJO.getBranch_code());
//                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        } else {
            return 0;
        }
    }
}
