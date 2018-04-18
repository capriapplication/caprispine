package com.caprispine.caprispine.activity.assessment;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.FileUtils;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.adapter.patientassessment.FileListResultAdapter;
import com.caprispine.caprispine.pojo.ResponseListPOJO;
import com.caprispine.caprispine.pojo.patientassessment.PhotoVideoPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
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

public class PhotoVideoActivity extends AppCompatActivity {

    @BindView(R.id.rv_complaint)
    RecyclerView rv_complaint;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;

    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chief_complaint);
        ButterKnife.bind(this);

        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        attachAdapter();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Photo/Video");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ll_bottom.setVisibility(View.GONE);
//        attachAdapter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllComplaints();
    }

    public void getAllComplaints() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "get_all_media"));
        nameValuePairs.add(new BasicNameValuePair("patient_id", patientPOJO.getId()));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                Log.d(TagUtils.getTag(), msg[0] + ":-" + msg[1]);
                try {
                    ResponseListPOJO<PhotoVideoPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponseListPOJO<PhotoVideoPOJO>>() {
                    }.getType());
                    patientMediaDataResultPOJOS.clear();
                    if (responseListPOJO.isSuccess()) {
                        patientMediaDataResultPOJOS.addAll(responseListPOJO.getResultList());
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                    }
                    mediaDateAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "", false).execute(WebServicesUrls.PROBLEM_CRUD);
    }

    FileListResultAdapter mediaDateAdapter;
    List<PhotoVideoPOJO> patientMediaDataResultPOJOS = new ArrayList<>();

    public void attachAdapter() {
        mediaDateAdapter = new FileListResultAdapter(this, null, patientMediaDataResultPOJOS);
        GridLayoutManager layoutManager
                = new GridLayoutManager(getApplicationContext(), 3);
        rv_complaint.setHasFixedSize(true);
        rv_complaint.setAdapter(mediaDateAdapter);
        rv_complaint.setLayoutManager(layoutManager);
        rv_complaint.setItemAnimator(new DefaultItemAnimator());
    }


    public void downloadFile(String url) {
        try {
            String[] namearr = url.split("/");
            String name = namearr[namearr.length - 1];
            Log.d(TagUtils.getTag(), "name:-" + name);
            if (new File(FileUtils.getMediaDir() + File.separator + name).exists()) {
                openFile(new File(FileUtils.getMediaDir() + File.separator + name));
            } else {
                new DownloadFile(name).execute(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class DownloadFile extends AsyncTask<String, Integer, String> {
        ProgressDialog mProgressDialog;
        String file_name;

        public DownloadFile(String file_name) {
            this.file_name = file_name;
        }

        File f;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create progress dialog
            f = new File(FileUtils.getMediaDir() + File.separator + file_name);
            mProgressDialog = new ProgressDialog(PhotoVideoActivity.this);
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
            openFile(f);
        }
    }

    public void openFile(File f) {
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
                        Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.capri4physio.fileProvider", f);
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
        } catch (Exception e) {
            e.printStackTrace();
            ToastClass.showShortToast(getApplicationContext(), "File is corrupted");
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
