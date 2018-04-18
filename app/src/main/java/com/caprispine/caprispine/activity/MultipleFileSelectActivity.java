package com.caprispine.caprispine.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.FileUtils;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.Util.ToastClass;
import com.caprispine.caprispine.adapter.FileListAdapter;
import com.caprispine.caprispine.pojo.MultipleFileUploadPOJO;
import com.caprispine.caprispine.pojo.user.PatientPOJO;
import com.caprispine.caprispine.webservice.WebServicesCallBack;
import com.caprispine.caprispine.webservice.WebServicesUrls;
import com.caprispine.caprispine.webservice.WebUploadService;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MultipleFileSelectActivity extends AppCompatActivity implements WebServicesCallBack {

    private static final String CALL_FILE_UPLOAD_API = "call_file_upload_api";
    @BindView(R.id.btn_select)
    Button btn_select;
    @BindView(R.id.btn_upload)
    Button btn_upload;
    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 1888;
    private int PICK_VIDEO_REQUEST = 102;
    @BindView(R.id.rv_files)
    RecyclerView rv_files;
    FileListAdapter fileListAdapter;
    List<MultipleFileUploadPOJO> file_list = new ArrayList<>();
    PatientPOJO patientPOJO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_file_select);
        ButterKnife.bind(this);

        patientPOJO = (PatientPOJO) getIntent().getSerializableExtra("patientPOJO");

        attachAdapter();
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAttachmentDialog();
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFileToServer(file_list);
            }
        });
    }

    public void attachAdapter() {
        fileListAdapter = new FileListAdapter(this, null, file_list);
        GridLayoutManager layoutManager
                = new GridLayoutManager(getApplicationContext(), 3);
        rv_files.setHasFixedSize(true);
        rv_files.setAdapter(fileListAdapter);
        rv_files.setLayoutManager(layoutManager);
        rv_files.setItemAnimator(new DefaultItemAnimator());
    }


    public void sendFileToServer(List<MultipleFileUploadPOJO> multipleFileUploadPOJOS) {
        if (multipleFileUploadPOJOS.size() > 0) {
            try {
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                reqEntity.addPart("request_action", new StringBody("insert_patient_gallery"));
                Date d = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(d);

                reqEntity.addPart("patient_id", new StringBody(patientPOJO.getId()));
                reqEntity.addPart("date", new StringBody(date));

                for (int i = 0; i < multipleFileUploadPOJOS.size(); i++) {
                    MultipleFileUploadPOJO multipleFileUploadPOJO = multipleFileUploadPOJOS.get(i);
                    if (multipleFileUploadPOJO.getType().equalsIgnoreCase("image")) {
                        reqEntity.addPart("type[" + i + "]", new StringBody("1"));
                        reqEntity.addPart("image[" + i + "]", new FileBody(new File(multipleFileUploadPOJO.getFile())));
                        reqEntity.addPart("video[" + i + "]", new StringBody(""));
                    } else {
                        reqEntity.addPart("type[" + i + "]", new StringBody("2"));
                        reqEntity.addPart("image[" + i + "]", new FileBody(new File(multipleFileUploadPOJO.getThumb())));
                        reqEntity.addPart("video[" + i + "]", new FileBody(new File(multipleFileUploadPOJO.getFile())));
                    }
                }
                new WebUploadService(reqEntity, this, new WebServicesCallBack() {
                    @Override
                    public void onGetMsg(String[] msg) {
                        try {
                            if (new JSONObject(msg[1]).optBoolean("success")) {
                                Intent returnIntent = new Intent(MultipleFileSelectActivity.this,PatientDashBoardActivity.class);
                                startActivity(returnIntent);
//                                returnIntent.putExtra("result", "");
//                                setResult(Activity.RESULT_OK, returnIntent);
                                finishAffinity();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, CALL_FILE_UPLOAD_API, true).execute(WebServicesUrls.PROBLEM_CRUD);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastClass.showShortToast(getApplicationContext(), "you select the corrupted file");
        }
    }

    public void saveBitmap() {

    }

    public void showAttachmentDialog() {
        final Dialog dialog1 = new Dialog(MultipleFileSelectActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_show_attachments);
        dialog1.setTitle("Select");
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout ll_camera = dialog1.findViewById(R.id.ll_camera);
        LinearLayout ll_gallery = dialog1.findViewById(R.id.ll_gallery);
        LinearLayout ll_video_camera = dialog1.findViewById(R.id.ll_video_camera);
        LinearLayout ll_video_gallery = dialog1.findViewById(R.id.ll_video_gallery);
        Button btn_cancel = dialog1.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        ll_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                startCamera();
            }
        });

        ll_video_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                openvideoCamera();
            }
        });

        ll_video_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                openVideoGallery();
            }
        });

        ll_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
                sendImageMessageClick();
            }
        });
    }

    String video_file = "";
    private static final int VIDEO_CAPTURE = 251;

    public void openvideoCamera() {
        File mediaFile = new File(FileUtils.getVideoChatDir() + File.separator + System.currentTimeMillis() + ".mp4");
        video_file = mediaFile.toString();
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        Uri videoUri = Uri.fromFile(mediaFile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileProvider", mediaFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        }
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        startActivityForResult(intent, VIDEO_CAPTURE);
    }

    public void startCamera() {
        String strMyImagePath = Environment.getExternalStorageDirectory() + File.separator + "temp.png";

        image_path_string = strMyImagePath;
        File file = new File(image_path_string);
        Uri outputFileUri = Uri.fromFile(file);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".fileProvider", file);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

        } else {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        }
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }


    public void sendImageMessageClick() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void openVideoGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
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
                        MultipleFileSelectActivity.this, selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TagUtils.getTag(), "selected path:-" + selectedImagePath);
//                    sendFileToServer("image",image_path_string,"");
                    MultipleFileUploadPOJO multipleFileUploadPOJO = new MultipleFileUploadPOJO(image_path_string, "", "image");
                    file_list.add(multipleFileUploadPOJO);
                    fileListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MultipleFileSelectActivity.this, "File Selected is corrupted", Toast.LENGTH_LONG).show();
                }
                System.out.println("Image Path =" + selectedImagePath);
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            File imgFile = new File(image_path_string);
            if (imgFile.exists()) {
                Bitmap bmp = BitmapFactory.decodeFile(image_path_string);
                bmp = Bitmap.createScaledBitmap(bmp, bmp.getWidth() / 4, bmp.getHeight() / 4, false);
                String strMyImagePath = FileUtils.getTempImageDir() + File.separator + System.currentTimeMillis() + ".png";
                File file_name = new File(strMyImagePath);
                FileOutputStream fos = null;

                try {
                    fos = new FileOutputStream(file_name);
                    Log.d(TagUtils.getTag(), "taking photos");
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    MultipleFileUploadPOJO multipleFileUploadPOJO = new MultipleFileUploadPOJO(file_name.toString(), "", "image");
                    file_list.add(multipleFileUploadPOJO);
                    fileListAdapter.notifyDataSetChanged();
//                    sendFileToServer("image",image_path_string,"");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == PICK_VIDEO_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (null == data)
                    return;
                Uri selectedImageUri = data.getData();
                System.out.println(selectedImageUri.toString());
                // MEDIA GALLERY
                String selectedImagePath = getPath(
                        MultipleFileSelectActivity.this, selectedImageUri);
                Log.d("sun", "" + selectedImagePath);
                if (selectedImagePath != null && selectedImagePath != "") {
                    image_path_string = selectedImagePath;
                    Log.d(TagUtils.getTag(), "selected path:-" + selectedImagePath);
//                    String type, String thumb, String file_path, String msg
                    File f = new File(image_path_string);
                    if (f.exists()) {
                        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(f.toString(), MediaStore.Video.Thumbnails.MINI_KIND);

                        String storage_file = Environment.getExternalStorageDirectory() + File.separator + System.currentTimeMillis() + ".png";
                        FileOutputStream fos = null;

                        try {
                            fos = new FileOutputStream(new File(storage_file));
                            Log.d(TagUtils.getTag(), "taking photos");
                            thumb.compress(Bitmap.CompressFormat.PNG, 100, fos);
                            fos.flush();
                            fos.close();
                            MultipleFileUploadPOJO multipleFileUploadPOJO = new MultipleFileUploadPOJO(image_path_string.toString(), storage_file, "video");
                            file_list.add(multipleFileUploadPOJO);
                            fileListAdapter.notifyDataSetChanged();
//                            sendFileToServer("video",image_path_string,storage_file);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d(TagUtils.getTag(), "file not exist");
                    }
                } else {
                    Toast.makeText(MultipleFileSelectActivity.this, "File Selected is corrupted", Toast.LENGTH_LONG).show();
                }
                System.out.println("Image Path =" + selectedImagePath);
            }
        }
        if (requestCode == VIDEO_CAPTURE) {
            if (resultCode == RESULT_OK) {
//                Toast.makeText(this, "Video saved to:\n" +
//                        data.getData(), Toast.LENGTH_LONG).show();
                Log.d(TagUtils.getTag(), "video file path:-" + video_file);
                File f = new File(video_file);
                if (f.exists()) {
                    Bitmap thumb = ThumbnailUtils.createVideoThumbnail(f.toString(), MediaStore.Video.Thumbnails.MINI_KIND);

                    String storage_file = Environment.getExternalStorageDirectory() + File.separator + System.currentTimeMillis() + ".png";
                    FileOutputStream fos = null;

                    try {
                        fos = new FileOutputStream(new File(storage_file));
                        Log.d(TagUtils.getTag(), "taking photos");
                        thumb.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.flush();
                        fos.close();
                        MultipleFileUploadPOJO multipleFileUploadPOJO = new MultipleFileUploadPOJO(video_file.toString(), storage_file, "video");
                        file_list.add(multipleFileUploadPOJO);
                        fileListAdapter.notifyDataSetChanged();
//                            sendFileToServer("video",image_path_string,storage_file);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TagUtils.getTag(), "file not exist");
                }
                video_file = "";
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
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

    public boolean copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());

            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onGetMsg(String[] msg) {
        String apicall = msg[0];
        String response = msg[1];
        switch (apicall) {
            case CALL_FILE_UPLOAD_API:
                parseFileUploadResponse(response);
                break;
        }
    }

    public void parseFileUploadResponse(String response) {
        Log.d(TagUtils.getTag(), "file upload response:-" + response);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", "");
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
