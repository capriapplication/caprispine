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
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.FileUtils;
import com.caprispine.caprispine.Util.Pref;
import com.caprispine.caprispine.Util.StringUtils;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity implements WebServicesCallBack {

    private static final int CAMERA_REQUEST = 101;
    private static final int PICK_IMAGE_REQUEST = 102;

    private static final String CALL_REGISTER_PATIENT_API = "call_register_patient_api";
    private static final String CALL_GET_ALL_BRANCH = "call_get_all_branch";
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.img_profile)
    CircleImageView cv_profile;
    @BindView(R.id.et_first_name)
    EditText et_first_name;
    @BindView(R.id.et_last_name)
    EditText et_last_name;
    @BindView(R.id.rg_gender)
    RadioGroup rg_gender;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_tell_me_about)
    EditText et_tell_me_about;
    @BindView(R.id.rl_branch)
    RelativeLayout rl_branch;
    @BindView(R.id.spinner_branch)
    Spinner spinner_branch;
    @BindView(R.id.spinner_opd)
    Spinner spinner_opd;
    @BindView(R.id.spinner_reference)
    Spinner spinner_reference;
    @BindView(R.id.et_reference_name)
    EditText et_reference_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        getAllBranches();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerPatient();
            }
        });

        cv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(RegisterActivity.this, view);

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



    public void getAllBranches() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("request_action", "all_branch"));
        new WebServiceBase(nameValuePairs, this, this, CALL_GET_ALL_BRANCH, true).execute(WebServicesUrls.BRANCH_CRUD);
    }

    String image_path_string = "";

    public void registerPatient() {
        if (UtilityFunction.isDataValid(et_first_name, et_email, et_last_name, et_mobile, et_password, et_reference_name, et_tell_me_about)) {
            try {
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                if (new File(image_path_string).exists()) {
                    FileBody bin1 = new FileBody(new File(image_path_string));
                    reqEntity.addPart("profile_pic", bin1);
                } else {
                    reqEntity.addPart("profile_pic", new StringBody(""));
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
                reqEntity.addPart("treatment_type", new StringBody(String.valueOf((spinner_opd.getSelectedItemPosition() + 1))));
                reqEntity.addPart("mobile", new StringBody(et_mobile.getText().toString()));
                reqEntity.addPart("address", new StringBody(""));
                reqEntity.addPart("city", new StringBody(""));
                reqEntity.addPart("country", new StringBody("India"));
                reqEntity.addPart("pincode", new StringBody(""));
                reqEntity.addPart("dob", new StringBody(""));
                reqEntity.addPart("gender", new StringBody(gender));
                reqEntity.addPart("martial_status", new StringBody(""));
                reqEntity.addPart("height", new StringBody(""));
                reqEntity.addPart("weight", new StringBody(""));
                reqEntity.addPart("bmi", new StringBody(""));
                reqEntity.addPart("food_habit", new StringBody(""));
                reqEntity.addPart("occupation", new StringBody(""));
                reqEntity.addPart("user_description", new StringBody(et_tell_me_about.getText().toString()));
                reqEntity.addPart("reference_type", new StringBody(spinner_reference.getSelectedItem().toString()));
                reqEntity.addPart("reference_name", new StringBody(et_reference_name.getText().toString()));


                reqEntity.addPart("request_action", new StringBody("create_user"));


                new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String[] msg) {
                        try {
                            String response = msg[1];
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.optString("success").equals("true")) {

                                Gson gson = new Gson();
                                PatientPOJO patientPOJO = gson.fromJson(response, PatientPOJO.class);
                                Pref.SetStringPref(getApplicationContext(), StringUtils.PATIENT_POJO, jsonObject.optJSONObject("result").toString());
                                startActivity(new Intent(RegisterActivity.this, PatientDashBoardActivity.class).putExtra("patientPOJO", patientPOJO));
                                finishAffinity();
                                ToastClass.showShortToast(getApplicationContext(), "Patient Registered Successfully");

                            } else {
                                ToastClass.showShortToast(getApplicationContext(), "Failed to register patient");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "CREATE_PATIENT", false).execute(WebServicesUrls.USER_CRUD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastClass.showShortToast(getApplicationContext(), "Please Enter Fields Properly");
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        Log.d(TagUtils.getTag(), msg[0] + ":" + msg[1]);
        switch (msg[0]) {
            case CALL_REGISTER_PATIENT_API:
                parseRegisterResponse(msg[1]);
                break;
            case CALL_GET_ALL_BRANCH:
                parseAllBranch(msg[1]);
                break;
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

    public void parseRegisterResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.optString("success").equals("true")) {

                Gson gson = new Gson();
                PatientPOJO patientPOJO = gson.fromJson(response, PatientPOJO.class);
                Pref.SetStringPref(getApplicationContext(), StringUtils.PATIENT_POJO, jsonObject.optJSONObject("result").toString());
                ToastClass.showShortToast(getApplicationContext(), "Patient Registered Successfully");

            } else {
                ToastClass.showShortToast(getApplicationContext(), "Failed to register patient");
            }
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
                        this, selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TagUtils.getTag(), "selected path:-" + selectedImagePath);
                    setImage();
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
