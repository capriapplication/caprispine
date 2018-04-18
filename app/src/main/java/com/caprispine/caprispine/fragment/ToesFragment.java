package com.caprispine.caprispine.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.caprispine.caprispine.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 23-08-2017.
 */

public class ToesFragment extends Fragment {
    @BindView(R.id.scroll_windows)
    ScrollView scroll_windows;
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.spinner8)
    Spinner spinner8;
    @BindView(R.id.spinner9)
    Spinner spinner9;
    @BindView(R.id.edtxt_1)
    EditText edtxt_1;
    @BindView(R.id.edtxt_2)
    EditText edtxt_2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_toes,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public Bitmap takeScreenShots() {
        int h = 0;
        Bitmap bitmap = null;
        //get the actual height of scrollview
        for (int i = 0; i < scroll_windows.getChildCount(); i++) {
            h += scroll_windows.getChildAt(i).getHeight();
            scroll_windows.getChildAt(i).setBackgroundResource(R.color.white);
        }
        // create bitmap with target size
        bitmap = Bitmap.createBitmap(scroll_windows.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scroll_windows.draw(canvas);
        return bitmap;
    }

    public Spinner getSpinner1() {
        return spinner1;
    }

    public Spinner getSpinner2() {
        return spinner2;
    }

    public Spinner getSpinner8() {
        return spinner8;
    }

    public Spinner getSpinner9() {
        return spinner9;
    }

    public EditText getEdtxt_1() {
        return edtxt_1;
    }

    public EditText getEdtxt_2() {
        return edtxt_2;
    }
}
