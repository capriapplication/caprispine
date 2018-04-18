package com.caprispine.caprispine.adapter.patientassessment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.TagUtils;
import com.caprispine.caprispine.activity.assessment.PhotoVideoActivity;
import com.caprispine.caprispine.pojo.patientassessment.PhotoVideoPOJO;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class FileListResultAdapter extends RecyclerView.Adapter<FileListResultAdapter.ViewHolder> {
    private List<PhotoVideoPOJO> items;
    Activity activity;
    Fragment fragment;

    public FileListResultAdapter(Activity activity, Fragment fragment, List<PhotoVideoPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public FileListResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_file_list, parent, false);
        return new FileListResultAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FileListResultAdapter.ViewHolder holder, final int position) {

        if (items.get(position).getType().equals("1")) {
            Log.d(TagUtils.getTag(),"image url:-"+WebServicesUrls.PHOTO_VIDEO_BASE_URL+items.get(position).getPhotoPath());
            Glide.with(activity.getApplicationContext())
                    .load(WebServicesUrls.PHOTO_VIDEO_BASE_URL+items.get(position).getPhotoPath())
                    .into(holder.iv_file);
        } else if(items.get(position).getType().equals("2")){
            Glide.with(activity.getApplicationContext())
                    .load(WebServicesUrls.PHOTO_VIDEO_BASE_URL+items.get(position).getPhotoPath())
                    .into(holder.iv_file);
        }
        holder.ll_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity instanceof PhotoVideoActivity) {
                    PhotoVideoActivity photoVideoActivity= (PhotoVideoActivity) activity;
                    if (items.get(position).getType().equals("image")) {
                        photoVideoActivity.downloadFile(items.get(position).getPhotoPath());
                    } else if (items.get(position).getType().equals("video")) {
                        photoVideoActivity.downloadFile(items.get(position).getVideoPath());
                    }
                }
            }
        });

        holder.itemView.setTag(items.get(position));
    }


    private final String TAG = getClass().getSimpleName();

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_file;
        public ImageView iv_file;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_file = (LinearLayout) itemView.findViewById(R.id.ll_file);
            iv_file = (ImageView) itemView.findViewById(R.id.iv_file);
        }
    }
}
