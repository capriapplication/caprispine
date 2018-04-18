package com.caprispine.caprispine.activity.report;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ScrollView;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.FileUtils;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncomeReportPrintActivity extends AppCompatActivity {

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.btn_print)
    Button btn_print;

    String bundle_data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_report_print);
        ButterKnife.bind(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                Log.d(TagUtils.getTag(), "url loaded:-" + url);
//            }
//        });
        webView.setWebViewClient(new AppWebViewClients(this));

//        webView.loadUrl("http://caprispine.in/casereport/casereport.php");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            bundle_data = bundle.getString("type");

            switch (bundle_data) {
                case "incomestatement":
                    webView.loadUrl(WebServicesUrls.getIncomeStatementReport(bundle.getString("branch_code"),bundle.getString("start_date"),bundle.getString("end_date")));
                    break;
                case "incometreatmentwise":
                    webView.loadUrl(WebServicesUrls.getIncomeTreatmentWiseReportUrl(bundle.getString("branch_id"),bundle.getString("treatment_id")));
                    break;
                case "incometherapistwise":
                    webView.loadUrl(WebServicesUrls.getIncomeStatementTherapistWise(bundle.getString("branch_id"),
                            bundle.getString("therapist_id"),bundle.getString("start_date"),bundle.getString("end_date")));
                    break;
                case "patientwise":
                    webView.loadUrl(WebServicesUrls.getIncomePatientWise(bundle.getString("branch_id"),bundle.getString("patient_id"),bundle.getString("start_date"),bundle.getString("end_date")));
                    break;
                case "expensereport":
                    webView.loadUrl(WebServicesUrls.getExpenseurl(bundle.getString("branch_id"),bundle.getString("start_date"),bundle.getString("end_date")));
                    break;
                case "balancesheet":
                    webView.loadUrl("http://caprispine.in/casereport/casereport.php");
                    break;
                case "productivity":
                    webView.loadUrl("http://caprispine.in/invoice_report/productivitytherapist.php?branch_code=" + bundle.getString("branch_code") + "&start_date=" + bundle.getString("start_date") + "&end_date=" + bundle.getString("end_date") + "&therapist_id=" + bundle.getString("therapist_id"));
                    break;
                case "patientbill":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/patientreport.php?patient_id=" + bundle.getString("patient_id")+"&branch_code="+bundle.getString("branch_code"));
                    break;
                case "patientreceipt":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/patientreceipt.php?pt_id=" + bundle.getString("pt_id") + "&patient_id=" + bundle.getString("patient_id")+"&branch_code="+bundle.getString("branch_code"));
                    break;
                case "studentreport":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/studentbill.php?full_fees="+bundle.getString("full_fees")+"&date="+bundle.getString("date")+"&name="+bundle.getString("name")+"&course="+bundle.getString("course")+"&reg_fees="+bundle.getString("reg_fees")+"&rem_fees="+bundle.getString("rem_fees")+"&rem_gst="+bundle.getString("rem_gst")+"&reg_gst="+bundle.getString("reg_gst")+"&full_gst="+bundle.getString("full_gst")+"&total="+bundle.getString("total")+"&comt="+bundle.getString("comt")+"&id="+bundle.getString("id")+"&stu_id="+bundle.getString("stu_id"));
                    break;
                case "incomefrompatient":
                    webView.loadUrl(WebServicesUrls.getPatientWalletIncome(bundle.getString("branch_id"),bundle.getString("start_date"),bundle.getString("end_date"),bundle.getString("payment_mode")));
                    break;
                case "studentlist":
                    webView.loadUrl("http://caprispine.in/invoice_report/report/report/studentlist.php?course_id="+bundle.getString("course_id"));
                    break;
                case "case":
                    webView.loadUrl(WebServicesUrls.getCaseReport(bundle.getString("branch_id"),bundle.getString("patient_id"),bundle.getString("start_date"),bundle.getString("end_date")));
                    break;
                case "progress":
                    webView.loadUrl(WebServicesUrls.getProgressReport(bundle.getString("branch_id"),bundle.getString("patient_id"),bundle.getString("date"),bundle.getString("end_date")));
                    break;
                case "remark":
                    webView.loadUrl(WebServicesUrls.getRemarkReport(bundle.getString("branch_id"),bundle.getString("patient_id"),bundle.getString("date"),bundle.getString("end_date")));
                    break;
                case "CERTIFICATE":
                    webView.loadUrl("http://caprispine.in/invoice_report/pdf/certificates/certificate.php?student_name="+bundle.getString("student_name")+"&course_name="+bundle.getString("course_name")+"&duration="+bundle.getString("duration")+"&exams="+bundle.getString("exams")+"&s_id="+bundle.getString("id")+"&venu="+bundle.getString("venu")+"&dates="+bundle.getString("dates"));
                    break;
                case "IDCARD":
                    webView.loadUrl("http://caprispine.in/invoice_report/pdf/certificates/idcard.php?student_name="+bundle.getString("student_name")+"&comt="+bundle.getString("comt")+"&s_id="+bundle.getString("id")+"&date="+bundle.getString("date")+"&course_id="+bundle.getString("course_id"));
                    break;


                default:
                    webView.loadUrl("http://caprispine.in/invoice_report/newcase/case/casereport.php?patient_id=190&branch_code=SPHCAP");
                    break;
            }
            btn_print.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaveBitmapPDF1(bundle_data);
                }
            });

        } else {
            finish();
        }
    }

    public class AppWebViewClients extends WebViewClient {
        private ProgressDialog progressDialog;
        Activity activity;
        public AppWebViewClients(Activity activity) {
            this.activity=activity;
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            Log.d(TagUtils.getTag(),"loaded url:-"+url);
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
        }
    }


    private void SaveBitmapPDF1(final String type) {
        new AsyncTask<Void, Void, Void>() {
            File f;
            ProgressDialog pd;
            ArrayList<Bitmap> list;
            Bitmap main_bitmap;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = new ProgressDialog(IncomeReportPrintActivity.this);
                pd.setMessage("Please Wait...");
                pd.setCancelable(false);
                pd.show();

                Bitmap bitmap = getBitmapByView(scrollView);
                main_bitmap=bitmap;
//                SaveBitmapPDF(bitmap);
                int height = bitmap.getHeight();
                Log.d(TagUtils.getTag(), "height:-" + height);

                int chunks = height / 500;
                Log.d(TagUtils.getTag(), "chunks:-" + chunks);
                if(!type.equals("studentreport")) {
                    list = splitImage1(bitmap, chunks);
                }else{
                    list=new ArrayList<Bitmap>();
                    list.add(bitmap);
                }
            }

            @Override
            protected Void doInBackground(Void... params) {

//                SaveBitmapPDF1(list);
                saveBitmap(main_bitmap);
                try {
                    switch (bundle_data) {
                        case "incomestatement":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "income_branch_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "incometreatmentwise":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "income_treat_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "expensereport":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "expense_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "balancesheet":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "balance_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "productivity":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "productivity_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "patientbill":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "patient_bill_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "patientreceipt":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "patient_receipt_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "incomefrompatient":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "patientwallet_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "patientcasereport":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "casereport_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "studentlist":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "studentlist_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "case":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "case_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "progress":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "progress_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "remark":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "remark_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "CERTIFICATE":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "certificate_" + System.currentTimeMillis() + ".pdf");
                            break;
                        case "IDCARD":
                            f = new File(FileUtils.getreportDir()+ File.separator  + "idcard_" + System.currentTimeMillis() + ".pdf");
                            break;

                        default:
                            f = new File(FileUtils.getreportDir()+ File.separator + "case_report.pdf");
                            break;
                    }
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream(f));
                    document.open();
                    for (Bitmap bmp : list) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);


                        Image image = Image.getInstance(stream.toByteArray());
                        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                                - document.rightMargin() - 0) / image.getWidth()) * 100;
//                        image.scaleAbsolute(PageSize.A4.rotate());
//                        image.setAbsolutePosition(image.getWidth(), image.getHeight());
                        image.scalePercent(scaler);
                        document.add(image);
                    }
                    document.close();
                } catch (Exception e) {
                    pd.dismiss();
                    Log.d(TagUtils.getTag(), e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (pd != null) {
                    pd.dismiss();
                }
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
            }
        }.execute();
    }

    public Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        //get the actual height of scrollview
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundResource(R.color.white);
        }
        Log.d(TagUtils.getTag(), "bitmap width:-" + scrollView.getWidth());
        Log.d(TagUtils.getTag(), "bitmap height:-" + h);
        // create bitmap with target size
        if (h > 0) {
            bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                    Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            scrollView.draw(canvas);
        } else {

        }
        return bitmap;
    }

    public void saveBitmap(Bitmap bmp){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"bmpimage.jpg");
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
            Log.d(TagUtils.getTag(),"bitmap saved");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<Bitmap> splitImage1(Bitmap bitmap, int chunkNumbers) {

        //For the number of rows and columns of the grid to be displayed
        int rows, cols = 1;

        //For height and width of the small image chunks
        int chunkHeight, chunkWidth;

        //To store all the small image chunks in bitmap format in this list
        ArrayList<Bitmap> chunkedImages = new ArrayList<Bitmap>(chunkNumbers);

        //Getting the scaled bitmap of the source image
//        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        rows = (int) Math.sqrt(chunkNumbers);
        chunkHeight = bitmap.getHeight() / rows;
        chunkWidth = bitmap.getWidth() / cols;

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for (int x = 0; x < rows; x++) {
            int xCoord = 0;
            for (int y = 0; y < cols; y++) {
                chunkedImages.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
        return chunkedImages;
    }

    private void grantAllUriPermissions(Intent intent, Uri uri) {
        List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }
}
