package com.caprispine.caprispine.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.caprispine.caprispine.R;
import com.caprispine.caprispine.pojo.MultipleFileUploadPOJO;

import java.io.File;
import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {
    private List<MultipleFileUploadPOJO> items;
    Activity activity;
    Fragment fragment;

    public FileListAdapter(Activity activity, Fragment fragment, List<MultipleFileUploadPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public FileListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_file_list, parent, false);
        return new FileListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FileListAdapter.ViewHolder holder, final int position) {

        File file=new File(items.get(position).getFile());
        if(file.exists()){
            if(items.get(position).getType().equals("image")){
                Glide.with(activity.getApplicationContext())
                        .load(items.get(position).getFile())
                        .into(holder.iv_file);
            }else{
                Glide.with(activity.getApplicationContext())
                        .load(items.get(position).getThumb())
                        .into(holder.iv_file);
            }
        }
        holder.iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(position);
                notifyDataSetChanged();
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
        public ImageView iv_close;
        public ImageView iv_file;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_close = (ImageView) itemView.findViewById(R.id.iv_close);
            iv_file = (ImageView) itemView.findViewById(R.id.iv_file);
        }
    }
}
