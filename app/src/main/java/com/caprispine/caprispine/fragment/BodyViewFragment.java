package com.caprispine.caprispine.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.caprispine.caprispine.R;
import com.caprispine.caprispine.webservice.WebServicesUrls;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 11-12-2017.
 */
@SuppressLint("ValidFragment")
public class BodyViewFragment extends Fragment {
    @BindView(R.id.iv_body)
    ImageView iv_body;

    String url;
    public BodyViewFragment(String url) {
        this.url= url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_body_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Glide.with(getActivity().getApplicationContext())
                .load(WebServicesUrls.BODY_CHART_BASE_URL+url)
                .into(iv_body);
    }
}
