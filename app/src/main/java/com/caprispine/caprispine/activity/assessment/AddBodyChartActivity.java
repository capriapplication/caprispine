package com.caprispine.caprispine.activity.assessment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.FileUtils;
import com.caprispine.caprispine.Util.NVP;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.adapter.ViewPagerAdapter;
import com.caprispine.caprispine.fragment.BodyFragment;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.views.CustomViewPager;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.caprispine.caprispine.webservice.WebUploadService;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBodyChartActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    @BindView(R.id.btn_front)
    Button btn_front;
    @BindView(R.id.btn_back)
    Button btn_back;
    @BindView(R.id.btn_left)
    Button btn_left;
    @BindView(R.id.btn_right)
    Button btn_right;
    @BindView(R.id.ll_pain_code)
    LinearLayout ll_pain_code;
    @BindView(R.id.ll_stiff_code)
    LinearLayout ll_stiff_code;
    @BindView(R.id.ll_weakness_code)
    LinearLayout ll_weakness_code;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_body_chart);
        ButterKnife.bind(this);

        patientPOJO= (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");

        setupViewPager(viewPager);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
            }
        });
        btn_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });

        ll_pain_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bodyFragment1.setColor("#FF0000");
                bodyFragment2.setColor("#FF0000");
                bodyFragment3.setColor("#FF0000");
                bodyFragment4.setColor("#FF0000");
            }
        });

        ll_stiff_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bodyFragment1.setColor("#0000FF");
                bodyFragment2.setColor("#0000FF");
                bodyFragment3.setColor("#0000FF");
                bodyFragment4.setColor("#0000FF");
            }
        });

        ll_weakness_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bodyFragment1.setColor("#008000");
                bodyFragment2.setColor("#008000");
                bodyFragment3.setColor("#008000");
                bodyFragment4.setColor("#008000");
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBitmapPaths();
            }
        });
    }


    public void saveBitmapPaths() {
        Bitmap bmp1 = bodyFragment1.getBitmap();
        Bitmap bmp2 = bodyFragment2.getBitmap();
        Bitmap bmp3 = bodyFragment3.getBitmap();
        Bitmap bmp4 = bodyFragment4.getBitmap();

        String file1 = saveBitmapToFile(bmp1);
        String file2 = saveBitmapToFile(bmp2);
        String file3 = saveBitmapToFile(bmp3);
        String file4 = saveBitmapToFile(bmp4);

        ArrayList<NVP> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new NVP("front_image", file1));
        nameValuePairs.add(new NVP("back_image", file2));
        nameValuePairs.add(new NVP("left_image", file3));
        nameValuePairs.add(new NVP("right_image", file4));
        nameValuePairs.add(new NVP("date", UtilityFunction.getCurrentDate()));

        saveBodyChart(nameValuePairs);
    }

    public void saveBodyChart(ArrayList<NVP> bodyArrayList) {

        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("request_action", new StringBody("insert_chart"));
            reqEntity.addPart("patient_id", new StringBody(patientPOJO.getId()));
            reqEntity.addPart("date", new StringBody(UtilityFunction.getCurrentDate()));
            for (NVP nvp : bodyArrayList) {
                if (new File(nvp.getValue()).exists()) {
                    reqEntity.addPart(nvp.getName(), new FileBody(new File(nvp.getValue())));
                }
            }

            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String[] msg) {
                    try{
                        JSONObject jsonObject=new JSONObject(msg[1]);
                        if(jsonObject.optString("success").equals("true")){
                            Intent intent = new Intent(AddBodyChartActivity.this, ProblemListActivity.class);
                            intent.putExtra("patientPOJO", patientPOJO);
                            intent.putExtra("is_questionaire",true);
                            startActivityForResult(intent, 1);
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            },"CALL_SAVE_BODY_CHARTS",true).execute(WebServicesUrls.BODY_CHART_CRUD);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String saveBitmapToFile(Bitmap bmp) {
        FileOutputStream out = null;
        try {
            File file = new File(FileUtils.getBaseFilePath() + File.separator + System.currentTimeMillis() + "_bmp.png");
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
            return file.toString();
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
        return "";
    }

    BodyFragment bodyFragment1, bodyFragment2, bodyFragment3, bodyFragment4;

    private void setupViewPager(CustomViewPager viewPager) {
        bodyFragment1 = new BodyFragment("front");
        bodyFragment2 = new BodyFragment("left");
        bodyFragment3 = new BodyFragment("right");
        bodyFragment4 = new BodyFragment("back");
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(bodyFragment1, "ROM");
        adapter.addFrag(bodyFragment2, "ROM");
        adapter.addFrag(bodyFragment3, "ROM");
        adapter.addFrag(bodyFragment4, "ROM");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPagingEnabled(false);
    }


    public void parseSaveBodyCharts(String response){
        Log.d(TagUtils.getTag(),"save body chart response:-"+response);
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.optString("success").equals("true")){
                Intent intent = new Intent(AddBodyChartActivity.this, ProblemListActivity.class);
                intent.putExtra("patient_id", patientPOJO.getId());
                intent.putExtra("show_file_select",true);
                startActivityForResult(intent, 1);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }



}
