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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.FileUtils;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.pojo.branch.BranchPOJO;
import com.caprispine.caprispine.pojo.branch.BranchResultPOJO;
import com.caprispine.caprispine.pojo.user.StaffPOJO;
import com.caprispine.caprispine.webservice.WebServiceBase;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.caprispine.caprispine.webservice.WebUploadService;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.NameValuePair;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateStaffActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private static final int CAMERA_REQUEST = 101;
    private static int PICK_IMAGE_REQUEST = 102;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.et_first_name)
    EditText et_first_name;
    @BindView(R.id.et_last_name)
    EditText et_last_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_age)
    EditText et_age;
    @BindView(R.id.et_dob)
    EditText et_dob;
    @BindView(R.id.et_date_of_joining)
    EditText et_date_of_joining;
    @BindView(R.id.et_date_of_contract)
    EditText et_date_of_contract;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_city)
    EditText et_city;
    @BindView(R.id.et_state)
    EditText et_state;
    @BindView(R.id.et_country)
    EditText et_country;
    @BindView(R.id.et_pin_code)
    EditText et_pin_code;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_degree)
    EditText et_degree;
    @BindView(R.id.et_institute)
    EditText et_institute;
    @BindView(R.id.et_university)
    EditText et_university;
    @BindView(R.id.et_duration)
    EditText et_duration;
    @BindView(R.id.et_average)
    EditText et_average;
    @BindView(R.id.et_organization)
    EditText et_organization;
    @BindView(R.id.et_designation)
    EditText et_designation;
    @BindView(R.id.et_experience_duration)
    EditText et_experience_duration;
    @BindView(R.id.et_work)
    EditText et_work;
    @BindView(R.id.et_height)
    EditText et_height;
    @BindView(R.id.et_weight)
    EditText et_weight;

    @BindView(R.id.rg_gender)
    RadioGroup rg_gender;
    @BindView(R.id.rg_male)
    RadioButton rg_male;
    @BindView(R.id.rg_female)
    RadioButton rg_female;


    @BindView(R.id.rg_martial)
    RadioGroup rg_martial;
    @BindView(R.id.rb_single)
    RadioButton rb_single;
    @BindView(R.id.rb_married)
    RadioButton rb_married;

    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.btn_dob)
    Button btn_dob;
    @BindView(R.id.btn_doj)
    Button btn_doj;
    @BindView(R.id.btn_eoc)
    Button btn_eoc;
    @BindView(R.id.img_profile)
    CircleImageView img_profile;

    private static final String DATE_OF_BIRTH = "DATE_OF_BIRTH";
    private static final String DATE_OF_JOURNEY = "DATE_OF_JOURNEY";
    private static final String END_DATE_OF_CONTRACT = "END_DATE_OF_CONTRACT";


    public static String DATE_TYPE = DATE_OF_BIRTH;
    StaffPOJO staffPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_staff);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Create Staff");

        staffPOJO = (StaffPOJO) getIntent().getSerializableExtra("staffPOJO");
        if (staffPOJO != null) {
            getSupportActionBar().setTitle("Update Staff");
            btn_save.setText("Save");
            setValues();
        }

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "all_branch"));
        new WebServiceBase(nameValuePairs, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String[] msg) {
                parseAllBranch(msg[1]);
            }
        }, "CALL_GET_ALL_BRANCH", true).execute(WebServicesUrls.BRANCH_CRUD);


        btn_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DATE_TYPE = DATE_OF_BIRTH;
                openCalendar();
            }
        });

        btn_doj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DATE_TYPE = DATE_OF_JOURNEY;
                openCalendar();
            }
        });

        btn_eoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DATE_TYPE = END_DATE_OF_CONTRACT;
                openCalendar();
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(CreateStaffActivity.this, view);

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

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createStaff();
            }
        });
    }

    public void setValues() {
        Log.d(TagUtils.getTag(),"profile pic:-"+WebServicesUrls.PROFILE_PIC_BASE_URL + staffPOJO.getProfilePic());

        Glide.with(getApplicationContext())
                .load(WebServicesUrls.PROFILE_PIC_BASE_URL + staffPOJO.getProfilePic())
                .placeholder(R.drawable.ic_action_person)
                .error(R.drawable.ic_action_person)
                .dontAnimate()
                .into(img_profile);

        et_first_name.setText(staffPOJO.getFirstName());
        et_last_name.setText(staffPOJO.getLastName());
        et_email.setText(staffPOJO.getEmail());
        et_password.setText(staffPOJO.getUserPOJO().getFirstName());
        et_height.setText(staffPOJO.getHeight());
        et_weight.setText(staffPOJO.getWeight());
        et_age.setText(staffPOJO.getAge());
        et_dob.setText(staffPOJO.getDob());

        if(!staffPOJO.getDob().equals("0000-00-00")){
            try{
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                Date d=simpleDateFormat.parse(staffPOJO.getDob());
                et_age.setText(String.valueOf(UtilityFunction.getAge(d)));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        et_date_of_joining.setText(staffPOJO.getDoj());
        et_date_of_contract.setText(staffPOJO.getEndingDateOfContract());
        switch (staffPOJO.getGender()) {
            case "1":
                rg_male.setChecked(true);
                break;
            case "2":
                rg_female.setChecked(true);
                break;
        }
        switch (staffPOJO.getMartialStatus()) {
            case "1":
                rb_single.setChecked(true);
                break;
            case "2":
                rb_married.setChecked(true);
                break;
        }

        et_address.setText(staffPOJO.getAddress());
        et_city.setText(staffPOJO.getCity());
        et_state.setText(staffPOJO.getState());
        et_country.setText(staffPOJO.getCountry());
        et_pin_code.setText(staffPOJO.getPinCode());
        et_phone.setText(staffPOJO.getMobile());
        et_degree.setText(staffPOJO.getDegree());
        et_institute.setText(staffPOJO.getInstitution());
        et_university.setText(staffPOJO.getUniversity());
        et_duration.setText(staffPOJO.getQualificationDuration());
        et_average.setText(staffPOJO.getAverage());
        et_organization.setText(staffPOJO.getOrganisation());
        et_designation.setText(staffPOJO.getDesignation());
        et_experience_duration.setText(staffPOJO.getExperienceDuration());
        et_work.setText(staffPOJO.getNatureOfWork());
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


    public void openCalendar() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                CreateStaffActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void createStaff() {
        try {
            MultipartEntity reqEntity = new MultipartEntity();
            File file1 = new File(image_path_string);
            if (file1.exists()) {
                Log.e(TagUtils.getTag(), "profile pic:-" + image_path_string);
                FileBody bin1 = new FileBody(file1);
                reqEntity.addPart("profile_pic", bin1);
            } else {
                if (staffPOJO != null) {
                    reqEntity.addPart("profile_pic", new StringBody(staffPOJO.getProfilePic()));
                } else {
                    reqEntity.addPart("profile_pic", new StringBody(""));
                }
            }

            String gender = "";
            try {
                if (rg_gender.getCheckedRadioButtonId() == R.id.rg_male) {
                    gender = "1";
                } else {
                    gender = "2";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String martial_status = "";
            try {
                if (rg_martial.getCheckedRadioButtonId() == R.id.rb_single) {
                    martial_status = "1";
                } else {
                    martial_status = "2";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            reqEntity.addPart("first_name", new StringBody(et_first_name.getText().toString()));
            reqEntity.addPart("last_name", new StringBody(et_last_name.getText().toString()));
            reqEntity.addPart("email", new StringBody(et_email.getText().toString()));
            reqEntity.addPart("mobile", new StringBody(et_phone.getText().toString()));
            reqEntity.addPart("address", new StringBody(et_address.getText().toString()));
            reqEntity.addPart("city", new StringBody(et_city.getText().toString()));
            reqEntity.addPart("state", new StringBody(et_state.getText().toString()));
            reqEntity.addPart("country", new StringBody(et_country.getText().toString()));
            reqEntity.addPart("pin_code", new StringBody(et_pin_code.getText().toString()));
            reqEntity.addPart("dob", new StringBody(et_dob.getText().toString()));
            reqEntity.addPart("doj", new StringBody(et_date_of_joining.getText().toString()));
            reqEntity.addPart("gender", new StringBody(String.valueOf(gender)));
            reqEntity.addPart("martial_status", new StringBody(martial_status));
            reqEntity.addPart("height", new StringBody(et_height.getText().toString()));
            reqEntity.addPart("weight", new StringBody(et_weight.getText().toString()));
            reqEntity.addPart("branch_id", new StringBody(branchResultPOJOS.get(spinner_branch.getSelectedItemPosition()).getBranchId()));
            reqEntity.addPart("degree", new StringBody(et_degree.getText().toString()));
            reqEntity.addPart("institution", new StringBody(et_institute.getText().toString()));
            reqEntity.addPart("university", new StringBody(et_university.getText().toString()));
            reqEntity.addPart("qualification_duration", new StringBody(et_duration.getText().toString()));
            reqEntity.addPart("experience_duration", new StringBody(et_experience_duration.getText().toString()));
            reqEntity.addPart("average", new StringBody(et_average.getText().toString()));
            reqEntity.addPart("organisation", new StringBody(et_organization.getText().toString()));
            reqEntity.addPart("designation", new StringBody(et_designation.getText().toString()));
            reqEntity.addPart("nature_of_work", new StringBody(et_work.getText().toString()));
            reqEntity.addPart("ending_date_of_contract", new StringBody(et_date_of_contract.getText().toString()));
            reqEntity.addPart("user_type", new StringBody("1"));
            reqEntity.addPart("device_type", new StringBody("android"));
            reqEntity.addPart("show_password", new StringBody(et_password.getText().toString()));
            reqEntity.addPart("device_token", new StringBody(""));
            reqEntity.addPart("otp", new StringBody(""));
            reqEntity.addPart("otp_status", new StringBody("1"));
            reqEntity.addPart("request_action", new StringBody("create_user"));

            if (staffPOJO != null) {
                reqEntity.addPart("request_action", new StringBody("update_user"));
                reqEntity.addPart("id", new StringBody(staffPOJO.getId()));
                reqEntity.addPart("user_id", new StringBody(staffPOJO.getUserPOJO().getUserId()));
            } else {
                reqEntity.addPart("request_action", new StringBody("create_user"));
            }

            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String[] msg) {
                    Log.d(TagUtils.getTag(), msg[0] + ":-" + msg[1]);
                    try {
                        JSONObject jsonObject = new JSONObject(msg[1]);
                        if (jsonObject.optBoolean("success")) {
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "CALL_STAFF_CRUD", true).execute(WebServicesUrls.USER_CRUD);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    List<BranchResultPOJO> branchResultPOJOS = new ArrayList<>();

    public void parseAllBranch(String response) {
        try {
            Gson gson = new Gson();
            BranchPOJO branchPOJO = gson.fromJson(response, BranchPOJO.class);
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
            }
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

        switch (DATE_TYPE) {
            case DATE_OF_BIRTH:
                et_dob.setText(date);
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = simpleDateFormat.parse(date);
                    et_age.setText(String.valueOf(UtilityFunction.getAge(d)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case DATE_OF_JOURNEY:
                et_date_of_joining.setText(date);
                break;
            case END_DATE_OF_CONTRACT:
                et_date_of_contract.setText(date);
                break;

        }
    }

    String image_path_string = "";

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
                        this, selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TagUtils.getTag(), "selected path:-" + selectedImagePath);
                    setAttach(image_path_string);
                } else {
                    Toast.makeText(this, "Selected File is Corrupted", Toast.LENGTH_LONG).show();
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
                    setAttach(image_path_string);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        }
    }


    public void setAttach(String path) {
        Glide.with(getApplicationContext())
                .load(path)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .dontAnimate()
                .into(img_profile);
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
