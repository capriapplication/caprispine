package com.caprispine.caprispine.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.Constants;
import com.caprispine.caprispine.Util.FileUtils;
import com.caprispine.caprispine.Util.Pref;
import com.caprispine.caprispine.Util.StringUtils;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.activity.assessment.AddBodyChartActivity;
import com.caprispine.caprispine.activity.assessment.FAReportActivity;
import com.caprispine.caprispine.activity.report.IncomePatientWise;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.wallet.PatientWalletPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientDashBoardActivity extends AppCompatActivity {

    @BindView(R.id.rl_book_appointment)
    RelativeLayout rl_book_appointment;
    @BindView(R.id.rl_appointments)
    RelativeLayout rl_appointments;
    @BindView(R.id.rl_health_summary)
    RelativeLayout rl_health_summary;
    @BindView(R.id.rl_chat)
    RelativeLayout rl_chat;
    @BindView(R.id.rl_billing)
    RelativeLayout rl_billing;
    @BindView(R.id.rl_wallet)
    RelativeLayout rl_wallet;
    @BindView(R.id.rl_logout)
    RelativeLayout rl_logout;

    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dash_board);
        ButterKnife.bind(this);


        patientPOJO = new Gson().fromJson(Pref.GetStringPref(getApplicationContext(), StringUtils.PATIENT_POJO, ""), PatientPOJO.class);

        rl_book_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiagnoseFragment();
            }
        });


        rl_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientDashBoardActivity.this, AppointmentActivity.class).putExtra("patientPOJO", patientPOJO));
            }
        });

        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pref.SetBooleanPref(getApplicationContext(), StringUtils.IS_PATIENT_LOGIN, false);
                startActivity(new Intent(PatientDashBoardActivity.this, SplashActivity.class));
                finishAffinity();
            }
        });

        rl_billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientDashBoardActivity.this, IncomePatientWise.class).putExtra("patientPOJO", patientPOJO));
            }
        });

        rl_health_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printReport(patientPOJO.getId(),patientPOJO.getBranchId());
            }
        });

        rl_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPatientLastAmount();
            }
        });

        rl_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientDashBoardActivity.this,AllUsersChatActivity.class).putExtra("patientPOJO",patientPOJO));
            }
        });
    }

    public void checkPatientLastAmount() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "latest_amount"));
        nameValuePairs.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try {
                    JSONObject jsonObject = new JSONObject(msg[1]);
                    if (jsonObject.optBoolean("success")) {
                        PatientWalletPOJO patientAmountPOJO = new Gson().fromJson(jsonObject.optJSONObject("result").toString(), PatientWalletPOJO.class);
                        showPatientWalletAmount(patientAmountPOJO.getWalletAmount());

                    } else {
                        showPatientWalletAmount("0");
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, "GET_LAST_PATIENT_AMOUNT", true).execute(WebServicesUrls.PATIENT_WALLET);
    }

    public void showPatientWalletAmount(String amount) {
        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_amount);
        dialog1.setTitle("Wallet Amount");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        Button btn_amount = (Button) dialog1.findViewById(R.id.btn_amount);
        TextView tv_amount = (TextView) dialog1.findViewById(R.id.tv_amount);

        tv_amount.setText("Amount in your wallet is INR " + amount);


        btn_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }

    public void showDiagnoseFragment() {
        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog1.setContentView(R.layout.dialog_diagnose);
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv_questionaire = dialog1.findViewById(R.id.tv_questionaire);
        TextView tv_upload = dialog1.findViewById(R.id.tv_upload);
        TextView tv_fix_appointment = dialog1.findViewById(R.id.tv_fix_appointment);
        TextView tv_arrange_callback = dialog1.findViewById(R.id.tv_arrange_callback);
        TextView tv_body_chart = dialog1.findViewById(R.id.tv_body_chart);
        ImageView iv_close = dialog1.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        tv_questionaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                checkFATaken();
            }
        });
        tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                startActivity(new Intent(PatientDashBoardActivity.this, MultipleFileSelectActivity.class).putExtra("patientPOJO", patientPOJO));
            }
        });

        tv_fix_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
//                startActivity(new Intent(PatientDashBoardActivity.this, AddPatientAppointment.class));
                startActivity(new Intent(PatientDashBoardActivity.this, BookAppointmentActivity.class).putExtra("patientPOJO", patientPOJO));
            }
        });
        tv_arrange_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
//                callarrangCallbackAPI();
            }
        });
        tv_body_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                startActivity(new Intent(PatientDashBoardActivity.this, AddBodyChartActivity.class).putExtra("patientPOJO", patientPOJO));
            }
        });

    }

    public void checkFATaken() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_patient_scores"));
        nameValuePairs.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try {
                    JSONObject jsonObject = new JSONObject(msg[1]);
                    if (jsonObject.optString("success").equals("true")) {
                        Intent intent = new Intent(PatientDashBoardActivity.this, FAReportActivity.class);
                        intent.putExtra("patientPOJO", Constants.patientPOJO);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(PatientDashBoardActivity.this, QuestionaireActivity.class);
                        intent.putExtra("patientPOJO", Constants.patientPOJO);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_PATIENT_SCORE_CHECK", true).execute(WebServicesUrls.PROBLEM_CRUD);
    }


    public void printReport(String patient_id,String branch_id){
//        new DownloadFile().execute("http://caprispine.in/invoice_report/pdf/tcpdf/caseprint.php?patient_id="+patient_id+"&branch_code="+branch_code);
        String url=WebServicesUrls.getCaseUrl(patient_id,branch_id);
        Log.d(TagUtils.getTag(),"url:-"+url);
        new DownloadFile().execute(url);
    }

    private class DownloadFile extends AsyncTask<String, Integer, String> {
        ProgressDialog mProgressDialog;
        String file_name;
        boolean open=true;
        public DownloadFile(){
            this.file_name=System.currentTimeMillis()+"_case.pdf";
        }
        File f;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create progress dialog
            f=new File(FileUtils.getBaseFilePath() + File.separator+ file_name);
            mProgressDialog = new ProgressDialog(PatientDashBoardActivity.this);
            // Set your progress dialog Title
            mProgressDialog.setTitle("Download");
            // Set your progress dialog Message
            mProgressDialog.setMessage("Downloading, Please Wait!");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // Show progress dialog
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... Url) {
            try {
                URL url = new URL(Url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                // Detect the file lenghth
                int fileLength = connection.getContentLength();


                // Download the file
                InputStream input = new BufferedInputStream(url.openStream());

                // Save the downloaded file
                OutputStream output = new FileOutputStream(f);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publish the progress
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

                // Close connection
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                // Error Log
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // Update the progress dialog
            mProgressDialog.setProgress(progress[0]);
            // Dismiss the progress dialog
            //mProgressDialog.dismiss();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ToastClass.showShortToast(getApplicationContext(), "Download Successful");
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            if(open){
                openFile(f);
            }
        }
    }

    public void openFile(File f){
        try {
            if (f != null) {
                if (f.exists()) {
                    Log.d(TagUtils.getTag(), "file path:-" + f.getPath());
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String ext = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                    String type = mime.getMimeTypeFromExtension(ext);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName()+".fileProvider", f);
                        intent.setDataAndType(contentUri, type);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        grantAllUriPermissions(intent, contentUri);
                    } else {
                        intent.setDataAndType(Uri.fromFile(f), type);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        grantAllUriPermissions(intent, Uri.fromFile(f));
                    }
                    startActivity(intent);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(),"File is corrupted");
        }
    }

    private void grantAllUriPermissions(Intent intent, Uri uri) {
        List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }


}
