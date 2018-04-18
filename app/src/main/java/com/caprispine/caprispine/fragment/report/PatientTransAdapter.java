package com.caprispine.caprispine.fragment.report;

/**
 * Created by sunil on 26-05-2017.
 */

public class PatientTransAdapter {
//    extends
//} RecyclerView.Adapter<PatientTransAdapter.MyViewHolder> {
//
//    private List<PatientAmountPOJO> horizontalList;
//    private Activity activity;
//    Fragment fragment;
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        public TextView tv_id, tv_amount, tv_status, tv_timings, tv_mode;
//        public LinearLayout ll_trans_amount;
//        public MyViewHolder(View view) {
//            super(view);
//            tv_id = (TextView) view.findViewById(R.id.tv_id);
//            tv_amount = (TextView) view.findViewById(R.id.tv_amount);
//            tv_status = (TextView) view.findViewById(R.id.tv_status);
//            tv_timings = (TextView) view.findViewById(R.id.tv_timings);
//            tv_mode = (TextView) view.findViewById(R.id.tv_mode);
//            ll_trans_amount = (LinearLayout) view.findViewById(R.id.ll_trans_amount);
//        }
//    }
//
//
//    public PatientTransAdapter(Activity activity, Fragment fragment, List<PatientAmountPOJO> horizontalList) {
//        this.horizontalList = horizontalList;
//        this.activity = activity;
//        this.fragment=fragment;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.inflate_trans_amount, parent, false);
//
//        return new MyViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        holder.tv_id.setText(String.valueOf(position + 1));
//        holder.tv_amount.setText(horizontalList.get(position).getPt_amount());
//        holder.tv_mode.setText(horizontalList.get(position).getMode());
//        String status = "";
//        if (horizontalList.get(position).getPt_deduetedby().length() > 0) {
//            status = "debited";
//        } else {
//            status = "credited";
//        }
//        holder.tv_status.setText(status);
//
//        final String finalStatus = status;
//        holder.ll_trans_amount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(finalStatus.equals("credited")){
//                    showReceiptDialog(horizontalList.get(position));
//                }
//            }
//        });
//
//        holder.tv_timings.setText(horizontalList.get(position).getPt_time() + " " + horizontalList.get(position).getPt_date());
//    }
//
//    public void showReceiptDialog(final PatientAmountPOJO patientAmountPOJO){
//        final Dialog dialog1 = new Dialog(activity, android.R.style.Theme_DeviceDefault_Light_Dialog);
//        dialog1.setCancelable(true);
//        dialog1.setContentView(R.layout.dialog_receipt);
//        dialog1.setTitle("Transaction");
//        dialog1.show();
//        dialog1.setCancelable(true);
//        Window window = dialog1.getWindow();
//        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//
//        TextView tv_date= (TextView) dialog1.findViewById(R.id.tv_date);
//        TextView tv_receipt_number= (TextView) dialog1.findViewById(R.id.tv_receipt_number);
//        TextView tv_received_from= (TextView) dialog1.findViewById(R.id.tv_received_from);
//        TextView tv_amount= (TextView) dialog1.findViewById(R.id.tv_amount);
//        Button btn_print= (Button) dialog1.findViewById(R.id.btn_print);
//
//        String branch_code=AppPreferences.getInstance(activity.getApplicationContext()).getUSER_BRANCH_CODE();
//        String treatment_type=AppPreferences.getInstance(activity.getApplicationContext()).getTREATMENT_TYPE();
//
//
//        tv_date.setText(patientAmountPOJO.getPt_date());
//        tv_amount.setText("INR "+patientAmountPOJO.getPt_amount());
//        tv_receipt_number.setText("R"+branch_code+treatment_type+patientAmountPOJO.getReceipt_no());
//        tv_received_from.setText(AppPreferences.getInstance(activity.getApplicationContext()).getFirstName()+" "+AppPreferences.getInstance(activity.getApplicationContext()).getLastName());
//
//        btn_print.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Intent intent=new Intent(activity, IncomeReportPrintActivity.class);
////                intent.putExtra("type","patientreceipt");
////                intent.putExtra("pt_id",patientAmountPOJO.getPt_id());
////                intent.putExtra("patient_id",patientAmountPOJO.getPt_p_id());
////                intent.putExtra("branch_code",patientAmountPOJO.getBranch_code());
////                activity.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        if (horizontalList != null) {
//            return horizontalList.size();
//        } else {
//            return 0;
//        }
//    }
}
