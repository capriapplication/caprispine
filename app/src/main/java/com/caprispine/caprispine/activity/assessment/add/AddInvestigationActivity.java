package com.caprispine.caprispine.activity.assessment.add;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.caprispine.caprispine.webservice.WebUploadService;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URISyntaxException;

import butterknife.ButterKnife;

public class AddInvestigationActivity extends AppCompatActivity {
    private static final String KEY_PATIENT_ID = "patient_id";
    private static final String KEY_TYPE = "type";
    private EditText mReportType;
    private EditText mDesc;
    private TextView mPath;
    private TextView mChooseFile;
    private String selectedImagePath = "";
    String imgString = null;
    private String patientId = "";
    String response_str;
    Activity activity;
    private String assessmentType = "";
    private int PICK_IMAGE_CAPTURE = 1;
    private Button mSavebtn;
    private int PICK_IMAGE_REQUEST = 1;
    private ProgressDialog pDialog;

    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_investigation);
        ButterKnife.bind(this);
        initView();
        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");
        mChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOption();
            }
        });
        mSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMore = false;
                addExam();
            }
        });
    }

    protected void initView() {
        mReportType = (EditText) findViewById(R.id.edtxt_reporttype);
        mDesc = (EditText) findViewById(R.id.edtxt_desc);
        mPath = (TextView) findViewById(R.id.file_path);
        mChooseFile = (TextView) findViewById(R.id.choose_file);
        mSavebtn = (Button) findViewById(R.id.btn_save);
    }

    public void addExam() {
        try {
            MultipartEntity reqEntity = new MultipartEntity();
            File file1 = new File(selectedImagePath);
            Log.e("file1", selectedImagePath);
            FileBody bin1 = new FileBody(file1);

            reqEntity.addPart("request_action", new StringBody("insert_investigation"));
            reqEntity.addPart("attachment[0]", bin1);
            reqEntity.addPart("patient_id", new StringBody(patientPOJO.getId()));
            reqEntity.addPart("report_type", new StringBody(mReportType.getText().toString()));
            reqEntity.addPart("description", new StringBody(mDesc.getText().toString()));

            new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                @Override
                public void onGetMsg(String[] msg) {
                    try {
                        JSONObject jsonObject = new JSONObject(msg[1]);
                        if (jsonObject.optBoolean("success")) {
                            if (addMore) {
                                startActivity(new Intent(AddInvestigationActivity.this, AddInvestigationActivity.class).putExtra("patientPOJO", patientPOJO));
                                finish();
                            } else {
                                ToastClass.showShortToast(getApplicationContext(), "Special Exam Added");
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra("result", "");
                                setResult(Activity.RESULT_OK, returnIntent);
                            }
                        } else {
                            ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "CALL_INVESTIGATION_UPLOAD", true).execute(WebServicesUrls.INVESTIGATION_CRUD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dialogOption() {
        final String[] items = new String[]{"Take from camera", "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    dispatchTakePictureIntent();
                } else {
                    showFileChooser();
                }
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, PICK_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            if (photoFile != null) {

            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        final String[] ACCEPT_MIME_TYPES = {
                "application/pdf",
                "image/*"
        };
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, ACCEPT_MIME_TYPES);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);//Menu Resource, Menu
        return true;
    }

    boolean addMore = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_amount:
                addMore = true;
                addExam();
                return true;
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    public String getPath(Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
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

    private void getRealPathFromUri(Context ctx, Uri uri) {

        String[] filePathColumn = {MediaStore.Files.FileColumns.DATA};

        Cursor cursor = ctx.getContentResolver().query(uri, filePathColumn,
                null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        selectedImagePath = cursor.getString(columnIndex);
        mPath.setText(selectedImagePath);
        Log.e("picturePath", "picturePath : " + selectedImagePath);
        cursor.close();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        try {
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            Uri uri = data.getData();
            Log.d("TAG", "File Uri: " + uri.toString());
            // Get the path
            selectedImagePath = getPath(
                    this, uri);
            try {
                Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bytes);
            } catch (Exception e) {
                e.toString();
            }
            if (selectedImagePath != null && selectedImagePath != "") {
                mPath.setText(selectedImagePath);
                selectedImagePath = selectedImagePath.trim();
                Log.d("shubham", selectedImagePath);
            }
        } else if (requestCode == PICK_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);
//            knop.setVisibility(Button.VISIBLE);


                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(this, photo);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));
                selectedImagePath = finalFile.getPath();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "File Selected is corrupted", Toast.LENGTH_LONG).show();
            }

//            System.out.println(mImageCaptureUri);
        } else {
            Toast.makeText(getApplicationContext(), "File Selected is corrupted", Toast.LENGTH_LONG).show();
        }
//        }
//        catch (Exception e){
//            Toast.makeText(getActivity(), "File Selected is corrupted", Toast.LENGTH_LONG).show();
//        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 50, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        selectedImagePath = cursor.getString(columnIndex);
        mPath.setText(selectedImagePath);
        Log.e("picturePath", "picturePath : " + selectedImagePath);
//        cursor.close();
        return cursor.getString(columnIndex);
    }

}
