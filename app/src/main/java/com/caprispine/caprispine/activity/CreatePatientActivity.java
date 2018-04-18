package com.caprispine.caprispine.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.FileUtils;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.pojo.branch.BranchPOJO;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.caprispine.caprispine.webservice.WebUploadService;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreatePatientActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final int CAMERA_REQUEST = 101;
    private static final int PICK_IMAGE_REQUEST = 102;

    private String image_path_string = "";

    @BindView(R.id.cv_profile)
    CircleImageView cv_profile;
    @BindView(R.id.et_first_name)
    EditText et_first_name;
    @BindView(R.id.et_last_name)
    EditText et_last_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_dob)
    EditText et_dob;
    @BindView(R.id.img_date_picker)
    ImageView img_date_picker;
    @BindView(R.id.et_age)
    EditText et_age;
    @BindView(R.id.et_height)
    EditText et_height;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.et_weight)
    EditText et_weight;
    @BindView(R.id.rl_branch)
    RelativeLayout rl_branch;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.rg_gender)
    RadioGroup rg_gender;
    @BindView(R.id.radio_male)
    RadioButton radio_male;
    @BindView(R.id.radio_female)
    RadioButton radio_female;
    @BindView(R.id.rg_martial)
    RadioGroup rg_martial;
    @BindView(R.id.radio_single)
    RadioButton radio_single;
    @BindView(R.id.radio_married)
    RadioButton radio_married;
    @BindView(R.id.rg_fh)
    RadioGroup rg_fh;
    @BindView(R.id.radio_veg)
    RadioButton radio_veg;
    @BindView(R.id.radio_nonveg)
    RadioButton radio_nonveg;
    @BindView(R.id.radio_eggeterian)
    RadioButton radio_eggeterian;
    @BindView(R.id.et_aadhar_id)
    EditText et_aadhar_id;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.et_pin_code)
    EditText et_pin_code;
    @BindView(R.id.et_description)
    EditText et_description;
    @BindView(R.id.spinner_treatment)
    Spinner spinner_treatment;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.spinner_reference)
    Spinner spinner_reference;
    @BindView(R.id.et_reference_name)
    EditText et_reference_name;

    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);
        ButterKnife.bind(this);

        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");

        img_date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CreatePatientActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        cv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(CreatePatientActivity.this, view);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        switch (menuitem.getItemId()) {
                            case R.id.popup_camera:
                                startCamera();
                                break;
                            case R.id.popup_gallery:
                                selectImageFromGallery();
                                break;
                        }
                        return false;
                    }
                });
                menu.inflate(R.menu.menu_profile_pic_option);
                menu.show();
            }
        });
        getAllBranch();

        if (patientPOJO != null) {
            setValues();
            btn_save.setText("Update");
        }
    }

    public void setValues() {

            et_first_name.setText(patientPOJO.getFirstName());
            et_last_name.setText(patientPOJO.getLastName());
            et_email.setText(patientPOJO.getEmail());
            et_password.setText("");
            et_mobile.setText(patientPOJO.getMobile());
            et_address.setText(patientPOJO.getAddress());
            et_city.setText(patientPOJO.getCity());
            et_pin_code.setText(patientPOJO.getPincode());
            et_dob.setText(patientPOJO.getDob());
            et_mobile.setText(patientPOJO.getMobile());
            if (patientPOJO.getHeight().length() > 0) {
                et_height.setText(patientPOJO.getHeight());
            } else {
                et_height.setText("-");
            }
            if(patientPOJO.getWeight().length()>0) {
                et_weight.setText(patientPOJO.getWeight());
            }else{
                et_weight.setText("-");
            }
            et_password.setText(patientPOJO.getUserPOJO().getShowPassword());
            et_description.setText(patientPOJO.getUserDescription());


            Glide.with(getApplicationContext())
                    .load(WebServicesUrls.PROFILE_PIC_BASE_URL + patientPOJO.getProfilePic())
                    .placeholder(R.drawable.ic_action_person)
                    .error(R.drawable.ic_action_person)
                    .dontAnimate()
                    .into(cv_profile);

            try {
                if (!patientPOJO.getTreatmentType().equalsIgnoreCase("0")) {
                    spinner_treatment.setSelection(Integer.parseInt(patientPOJO.getTreatmentType()) - 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!patientPOJO.getGender().equalsIgnoreCase("0")) {
                switch (patientPOJO.getGender()) {
                    case "1":
                        radio_male.setChecked(true);
                        break;
                    case "2":
                        radio_female.setChecked(true);
                        break;
                }
            }

            if (!patientPOJO.getMartialStatus().equalsIgnoreCase("0")) {
                switch (patientPOJO.getGender()) {
                    case "1":
                        radio_single.setChecked(true);
                        break;
                    case "2":
                        radio_married.setChecked(true);
                        break;
                }
            }
            try {
                List<String> stringList = Arrays.asList(getResources().getStringArray(R.array.reference_type));
                spinner_reference.setSelection(stringList.indexOf(patientPOJO.getReferenceType()));
                et_reference_name.setText(patientPOJO.getReferenceName());
            }catch (Exception e){
                e.printStackTrace();
            }

            if(!patientPOJO.getDob().equals("0000-00-00")){
                try{
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    Date d=simpleDateFormat.parse(patientPOJO.getDob());
                    et_age.setText(String.valueOf(UtilityFunction.getAge(d)));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if (!patientPOJO.getFoodHabit().equalsIgnoreCase("0")) {
                switch (patientPOJO.getFoodHabit()) {
                    case "1":
                        radio_veg.setChecked(true);
                        break;
                    case "2":
                        radio_nonveg.setChecked(true);
                        break;
                    case "3":
                        radio_eggeterian.setChecked(true);
                        break;
                }
            }
    }

    List<BranchResultPOJO> branchResultPOJOS = new ArrayList<>();

    public void getAllBranch() {
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

                        if (patientPOJO != null) {
                            try {
                                List<String> branchIDString = new ArrayList<>();
                                for (BranchResultPOJO branchResultPOJO : branchResultPOJOS) {
                                    branchIDString.add(branchResultPOJO.getBranchId());
                                }
                                spinner_branch.setSelection(branchIDString.indexOf(patientPOJO.getBranchId()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CALL_GET_ALL_BRANCH", true).execute(WebServicesUrls.BRANCH_CRUD);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPatient();
            }
        });

    }


    String pictureImagePath = "";

    public void startCamera() {
        String strMyImagePath = Environment.getExternalStorageDirectory() + File.separator + "temp.png";

        pictureImagePath = strMyImagePath;
        File file = new File(pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileProvider", file);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        } else {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        }
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    public void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    public void createPatient() {
        try {
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (new File(image_path_string).exists()) {
                FileBody bin1 = new FileBody(new File(image_path_string));
                reqEntity.addPart("profile_pic", bin1);
            } else {
                if(patientPOJO!=null) {
                    reqEntity.addPart("profile_pic", new StringBody(patientPOJO.getProfilePic()));
                }else{
                    reqEntity.addPart("profile_pic", new StringBody(""));
                }
            }

            String gender = "";
            switch (((RadioButton) findViewById(rg_gender.getCheckedRadioButtonId())).getText().toString().toLowerCase()) {
                case "male":
                    gender = "1";
                    break;
                case "female":
                    gender = "2";
                    break;
                case "other":
                    gender = "3";
                    break;
            }
            String martial_status = "";
            switch (((RadioButton) findViewById(rg_martial.getCheckedRadioButtonId())).getText().toString().toLowerCase()) {
                case "single":
                    martial_status = "1";
                    break;
                case "married":
                    martial_status = "2";
                    break;
            }

            String food_habit = "";
            switch (((RadioButton) findViewById(rg_fh.getCheckedRadioButtonId())).getText().toString().toLowerCase()) {
                case "Veg":
                    food_habit = "1";
                    break;
                case "non-veg":
                    food_habit = "2";
                    break;
                case "eggetarian":
                    food_habit = "3";
                    break;
            }

            reqEntity.addPart("first_name", new StringBody(et_first_name.getText().toString()));
            reqEntity.addPart("last_name", new StringBody(et_last_name.getText().toString()));
            reqEntity.addPart("email", new StringBody(et_email.getText().toString()));
            reqEntity.addPart("password", new StringBody(et_password.getText().toString()));
            reqEntity.addPart("user_type", new StringBody("0"));
            reqEntity.addPart("device_type", new StringBody("android"));
            reqEntity.addPart("show_password", new StringBody(et_password.getText().toString()));
            reqEntity.addPart("device_token", new StringBody(""));
            reqEntity.addPart("otp", new StringBody(""));
            reqEntity.addPart("branch_id", new StringBody(branchResultPOJOS.get(spinner_branch.getSelectedItemPosition()).getBranchId()));
            reqEntity.addPart("treatment_type", new StringBody(String.valueOf((spinner_treatment.getSelectedItemPosition() + 1))));
            reqEntity.addPart("mobile", new StringBody(et_mobile.getText().toString()));
            reqEntity.addPart("address", new StringBody(et_address.getText().toString()));
            reqEntity.addPart("city", new StringBody(et_city.getText().toString()));
            reqEntity.addPart("country", new StringBody("India"));
            reqEntity.addPart("pincode", new StringBody(et_pin_code.getText().toString()));
            reqEntity.addPart("dob", new StringBody(et_dob.getText().toString()));
            reqEntity.addPart("gender", new StringBody(gender));
            reqEntity.addPart("martial_status", new StringBody(martial_status));
            reqEntity.addPart("height", new StringBody(et_height.getText().toString()));
            reqEntity.addPart("weight", new StringBody(et_weight.getText().toString()));
            reqEntity.addPart("bmi", new StringBody(""));
            reqEntity.addPart("food_habit", new StringBody(food_habit));
            reqEntity.addPart("occupation", new StringBody(""));
            reqEntity.addPart("user_description", new StringBody(et_description.getText().toString()));
            reqEntity.addPart("reference_type", new StringBody(spinner_reference.getSelectedItem().toString()));
            reqEntity.addPart("reference_name", new StringBody(et_reference_name.getText().toString()));


            if (patientPOJO != null) {
                reqEntity.addPart("request_action", new StringBody("update_user"));
                reqEntity.addPart("user_id", new StringBody(patientPOJO.getUserPOJO().getUserId()));
                reqEntity.addPart("id", new StringBody(patientPOJO.getId()));
            } else {
                reqEntity.addPart("request_action", new StringBody("create_user"));
            }


            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String[] msg) {
                    try {
                        JSONObject jsonObject = new JSONObject(msg[1]);
                        if (jsonObject.optBoolean("success")) {
                            finish();
                        } else {
                            ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "CREATE_PATIENT", false).execute(WebServicesUrls.USER_CRUD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        String day = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }

        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }

//        String date = day + "-" + month + "-" + year;
        String date = year + "-" + month + "-" + day;
        et_dob.setText(date);

        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            et_age.setText(String.valueOf(UtilityFunction.getAge(d)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (null == data)
                    return;
                Uri selectedImageUri = data.getData();
                System.out.println(selectedImageUri.toString());
                // MEDIA GALLERY
                String selectedImagePath = getPath(
                        CreatePatientActivity.this, selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TagUtils.getTag(), "selected path:-" + selectedImagePath);
                    setImage();
                } else {
                    Toast.makeText(CreatePatientActivity.this, "Selected File is Corrupted", Toast.LENGTH_LONG).show();
                }
                System.out.println("Image Path =" + selectedImagePath);
            }
            return;
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            File imgFile = new File(pictureImagePath);
            if (imgFile.exists()) {
                Bitmap bmp = BitmapFactory.decodeFile(pictureImagePath);
                bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 4, bmp.getHeight() / 4, false);
                String strMyImagePath = FileUtils.getChatDir();
                File file_name = new File(strMyImagePath + File.separator + System.currentTimeMillis() + ".png");
                FileOutputStream fos = null;

                try {
                    fos = new FileOutputStream(file_name);
                    Log.d(TagUtils.getTag(), "taking photos");
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    image_path_string = file_name.toString();
                    setImage();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }
    }

    public void setImage() {
        Glide.with(getApplicationContext())
                .load(image_path_string)
                .error(R.drawable.ic_action_person)
                .placeholder(R.drawable.ic_action_person)
                .dontAnimate()
                .into(cv_profile);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

}
