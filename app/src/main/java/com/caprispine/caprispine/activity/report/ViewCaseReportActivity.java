package com.caprispine.caprispine.activity.report;

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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.FileUtils;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.adapter.PatientListAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.branch.BranchPOJO;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.pojo.user.TherapistPOJO;
import com.caprispine.caprispine.webservice.ResponseListCallback;
import com.caprispine.caprispine.webservice.ResponseListWebservice;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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

public class ViewCaseReportActivity extends AppCompatActivity {
    private static final String GET_ALL_PATIENTS_API = "get_all_patients_api";
    private static final String CALL_SEARCH_PATIENTS = "call_search_patients";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_patients)
    RecyclerView rv_patients;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.ll_branch)
    LinearLayout ll_branch;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.ll_search_patients)
    LinearLayout ll_search_patients;

    List<BranchResultPOJO> branchPOJOList = new ArrayList<>();
    private static final String GET_ALL_BRANCHES = "get_all_branches";
    List<PatientPOJO> patientResultPOJOArrayList = new ArrayList<>();
    String branch_code="";

    TherapistPOJO therapistPOJO;
    StaffPOJO staffPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_case_report);

        ButterKnife.bind(this);

        therapistPOJO= (TherapistPOJO) getIntent().getSerializableExtra("therapistPOJO");
        staffPOJO= (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Case Report");

        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_name.setText("");
            }
        });

        attachAdapter();

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(et_name.getText().toString().length()>0){
                    getAllPatients(branch_code,et_name.getText().toString());
                }else{

                }
            }
        });
        if(therapistPOJO!=null){
            branch_code=therapistPOJO.getBranchId();
            ll_branch.setVisibility(View.GONE);
            ll_search_patients.setVisibility(View.GONE);
            getAllocatedPatients();
        }else if(staffPOJO!=null){
            branch_code=staffPOJO.getBranchId();
            ll_branch.setVisibility(View.GONE);
            getAllPatients(branch_code,"");
        }else{
            getAllBranches();
        }
    }

    public void getAllocatedPatients(){
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_allocated_patient"));
        nameValuePairs.add(new BasicNameValuePair("therapist_id", therapistPOJO.getId()));
        new ResponseListWebservice<PatientPOJO>(nameValuePairs, this, new ResponseListCallback<PatientPOJO>() {
            @Override
            public void onGetMsg(ResponseListPOJO<PatientPOJO> responseListPOJO) {
                try{
                    patientResultPOJOArrayList.clear();
                    if (responseListPOJO.isSuccess()) {
                        patientResultPOJOArrayList.addAll(responseListPOJO.getResultList());
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),responseListPOJO.getMessage());
                    }
                    Log.d(TagUtils.getTag(),"patient size:-"+patientResultPOJOArrayList.size());
                    patientListAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, PatientPOJO.class, "GET_ALLOCATED_PATIENTS", true).execute(WebServicesUrls.PATIENT_ALLOCATION_CRUD);
    }

    List<BranchResultPOJO> branchResultPOJOS=new ArrayList<>();
    public void getAllBranches() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "all_branch"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try {
                    Gson gson = new Gson();
                    BranchPOJO branchPOJO = gson.fromJson(msg[1], BranchPOJO.class);
                    if (branchPOJO.getSuccess().equals("true")) {
                        branchResultPOJOS.clear();
                        branchResultPOJOS.addAll(branchPOJO.getBranchResultPOJOS());
                        List<String> braStringList = new ArrayList<>();
                        for (BranchResultPOJO branchResultPOJO : branchPOJO.getBranchResultPOJOS()) {
                            braStringList.add(branchResultPOJO.getBranchName() + " (" + branchResultPOJO.getBranchCode() + ")");
                        }

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(), R.layout.dropsimpledown, braStringList);
                        spinner_branch.setAdapter(spinnerArrayAdapter);


                        if(branchResultPOJOS.size()>0){
                            branch_code=branchResultPOJOS.get(0).getBranchId();
                            getAllPatients(branchResultPOJOS.get(0).getBranchId(),"");
                        }

                        spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                branch_code=branchResultPOJOS.get(i).getBranchId();
                                getAllPatients(branchResultPOJOS.get(i).getBranchId(),"");
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_GET_ALL_BRANCH", true).execute(WebServicesUrls.BRANCH_CRUD);
    }


    public void getAllPatients(String branch_code,String string) {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_all_patients"));
        nameValuePairs.add(new BasicNameValuePair("branch_id", branch_code));
        nameValuePairs.add(new BasicNameValuePair("string", string));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                try{
                    ResponseListPOJO<PatientPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<PatientPOJO>>() {}.getType());
                    patientResultPOJOArrayList.clear();
                    if (responseListPOJO.isSuccess()) {
                        patientResultPOJOArrayList.addAll(responseListPOJO.getResultList());
                    }else{
                        ToastClass.showShortToast(getApplicationContext(),responseListPOJO.getMessage());
                    }
                    Log.d(TagUtils.getTag(),"patient size:-"+patientResultPOJOArrayList.size());
                    patientListAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },GET_ALL_PATIENTS_API,false).execute(WebServicesUrls.USER_CRUD);
    }


    PatientListAdapter patientListAdapter;

    public void attachAdapter() {

        patientListAdapter = new PatientListAdapter(this, null, patientResultPOJOArrayList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);

        rv_patients.setHasFixedSize(true);
        rv_patients.setAdapter(patientListAdapter);
        rv_patients.setLayoutManager(layoutManager);
        rv_patients.setNestedScrollingEnabled(false);
        rv_patients.setItemAnimator(new DefaultItemAnimator());
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
            mProgressDialog = new ProgressDialog(ViewCaseReportActivity.this);
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
