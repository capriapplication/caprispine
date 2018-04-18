package com.caprispine.caprispine.adapter.report;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.pojo.expense.ExpensePOJO;
import com.caprispine.caprispine.pojo.report.IncomePOJO;

import java.util.List;

/**
 * Created by sunil on 26-05-2017.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private List<ExpensePOJO> horizontalList;
    private Activity activity;
    private final String TAG = getClass().getSimpleName();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date;
        public TextView tv_vendor;
        public TextView tv_bill_no;
        public TextView tv_amount;



        public MyViewHolder(View view) {
            super(view);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_vendor = (TextView) view.findViewById(R.id.tv_vendor);
            tv_amount = (TextView) view.findViewById(R.id.tv_amount);
            tv_bill_no = (TextView) view.findViewById(R.id.tv_bill_no);
        }
    }


    public ExpenseAdapter(Activity activity, List<ExpensePOJO> horizontalList) {
        this.horizontalList = horizontalList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflate_invoice_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv_date.setText(horizontalList.get(position).getExpDate());
        holder.tv_vendor.setText(horizontalList.get(position).getExpVendor());
        holder.tv_amount.setText(horizontalList.get(position).getExpTotal());
        holder.tv_bill_no.setText(horizontalList.get(position).getExpBillno());
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
