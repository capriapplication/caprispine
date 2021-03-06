package com.caprispine.caprispine.activity.assessment.add.motor;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ImageUtil;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.adapter.ViewPagerAdapter;
import com.caprispine.caprispine.fragment.ForeArmFragment;
import com.caprispine.caprispine.pojo.ResponsePOJO;
import com.caprispine.caprispine.pojo.patientassessment.motor.HipExamPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.caprispine.caprispine.webservice.WebUploadService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddForeArmActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;


    ForeArmFragment frag_left;
    ForeArmFragment frag_right;
    PatientPOJO patientPOJO;
    Button savebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hip_exam);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Forearm Exam");

        setupViewPager(viewpager);

        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");

        savebtn = (Button) findViewById(R.id.savebtn);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "button", Toast.LENGTH_LONG);
//				addMotorAPi();
                captureBitmap();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        frag_left = new ForeArmFragment();
        frag_right = new ForeArmFragment();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(frag_left, "Left");
        adapter.addFrag(frag_right, "Right");
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void captureBitmap() {
        Bitmap left_bitmap = frag_left.takeScreenShots();
        Bitmap right_bitmap = frag_right.takeScreenShots();

        ShowDialog(left_bitmap, right_bitmap);

        Toast.makeText(getApplicationContext(), "bitmap saved", Toast.LENGTH_SHORT).show();
    }

    public void ShowDialog(Bitmap lef, Bitmap right) {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);

        dialog.setContentView(R.layout.custom_fragment_acti_dialog);
        dialog.setTitle("Save");

        final ImageView iv_right = (ImageView) dialog.findViewById(R.id.iv_right);
        final ImageView iv_parameter = (ImageView) dialog.findViewById(R.id.iv_parameter);
        final ImageView iv_left = (ImageView) dialog.findViewById(R.id.iv_left);
        final ScrollView scroll_dialog = (ScrollView) dialog.findViewById(R.id.scroll_dialog);

        iv_left.setImageBitmap(lef);
        iv_right.setImageBitmap(right);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file_path = ImageUtil.saveBitmaptoFile(ImageUtil.takeScreenShots(scroll_dialog));
                saveData(file_path);
                Toast.makeText(getApplicationContext(), "saved image", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void saveData(String file_path) {
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (new File(file_path).exists()) {
                FileBody bin1 = new FileBody(new File(file_path));
                reqEntity.addPart("image", bin1);
            } else {
                reqEntity.addPart("image", new StringBody(""));
            }
            reqEntity.addPart("request_action", new StringBody("insert_complaint"));
            reqEntity.addPart("patient_id", new StringBody(patientPOJO.getId()));
            reqEntity.addPart("date", new StringBody(UtilityFunction.getCurrentDate()));


            reqEntity.addPart("left_tone1", new StringBody(UtilityFunction.getToneSpinnerData(getApplicationContext(), frag_left.getSpinner1().getSelectedItemPosition())));
            reqEntity.addPart("left_tone2", new StringBody(UtilityFunction.getToneSpinnerData(getApplicationContext(), frag_left.getSpinner2().getSelectedItemPosition())));
            reqEntity.addPart("left_power1", new StringBody(UtilityFunction.getPowerSpinnerData(getApplicationContext(), frag_left.getSpinner8().getSelectedItemPosition())));
            reqEntity.addPart("left_power2", new StringBody(UtilityFunction.getPowerSpinnerData(getApplicationContext(), frag_left.getSpinner9().getSelectedItemPosition())));
            reqEntity.addPart("left_rom1", new StringBody(UtilityFunction.getEdittextData(frag_left.getEdtxt_1())));
            reqEntity.addPart("left_rom3", new StringBody(UtilityFunction.getEdittextData(frag_left.getEdtxt_3())));
            reqEntity.addPart("right_tone1", new StringBody(UtilityFunction.getToneSpinnerData(getApplicationContext(), frag_right.getSpinner1().getSelectedItemPosition())));
            reqEntity.addPart("right_tone2", new StringBody(UtilityFunction.getToneSpinnerData(getApplicationContext(), frag_right.getSpinner2().getSelectedItemPosition())));
            reqEntity.addPart("right_power1", new StringBody(UtilityFunction.getPowerSpinnerData(getApplicationContext(), frag_right.getSpinner8().getSelectedItemPosition())));
            reqEntity.addPart("right_power2", new StringBody(UtilityFunction.getPowerSpinnerData(getApplicationContext(), frag_right.getSpinner9().getSelectedItemPosition())));
            reqEntity.addPart("right_rom1", new StringBody(UtilityFunction.getEdittextData(frag_right.getEdtxt_1())));
            reqEntity.addPart("right_rom3", new StringBody(UtilityFunction.getEdittextData(frag_right.getEdtxt_3())));


            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String[] msg) {
                    Log.d(TagUtils.getTag(), msg[0] + ":-" + msg[1]);
                    ResponsePOJO<HipExamPOJO> responseListPOJO = new Gson().fromJson(msg[1], new TypeToken<ResponsePOJO<HipExamPOJO>>() {
                    }.getType());
                    if (responseListPOJO.isSuccess()) {
                        finish();
                    } else {
                        ToastClass.showShortToast(getApplicationContext(), responseListPOJO.getMessage());
                    }

                }
            }, "CALL_HIP_CRUD_API", true).execute(WebServicesUrls.FOREARM_CRUD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
