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

public class ShoulderFragment extends Fragment {
    @BindView(R.id.scroll_windows)
    ScrollView scroll_windows;

    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.spinner3)
    Spinner spinner3;
    @BindView(R.id.spinner4)
    Spinner spinner4;
    @BindView(R.id.spinner5)
    Spinner spinner5;
    @BindView(R.id.spinner6)
    Spinner spinner6;
    @BindView(R.id.spinner7)
    Spinner spinner7;
    @BindView(R.id.spinner8)
    Spinner spinner8;
    @BindView(R.id.spinner9)
    Spinner spinner9;
    @BindView(R.id.spinner10)
    Spinner spinner10;
    @BindView(R.id.spinner11)
    Spinner spinner11;
    @BindView(R.id.spinner12)
    Spinner spinner12;
    @BindView(R.id.spinner13)
    Spinner spinner13;
    @BindView(R.id.spinner14)
    Spinner spinner14;
    @BindView(R.id.edtxt_1)
    EditText edtxt_1;
    @BindView(R.id.edtxt_2)
    EditText edtxt_2;
    @BindView(R.id.edtxt_3)
    EditText edtxt_3;
    @BindView(R.id.edtxt_4)
    EditText edtxt_4;
    @BindView(R.id.edtxt_5)
    EditText edtxt_5;
    @BindView(R.id.edtxt_6)
    EditText edtxt_6;
    @BindView(R.id.edtxt_7)
    EditText edtxt_7;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_shoulder,container,false);
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

    public Spinner getSpinner3() {
        return spinner3;
    }

    public Spinner getSpinner4() {
        return spinner4;
    }

    public Spinner getSpinner5() {
        return spinner5;
    }

    public Spinner getSpinner6() {
        return spinner6;
    }

    public Spinner getSpinner7() {
        return spinner7;
    }

    public Spinner getSpinner8() {
        return spinner8;
    }

    public Spinner getSpinner9() {
        return spinner9;
    }

    public Spinner getSpinner10() {
        return spinner10;
    }

    public Spinner getSpinner11() {
        return spinner11;
    }

    public Spinner getSpinner12() {
        return spinner12;
    }

    public Spinner getSpinner13() {
        return spinner13;
    }

    public Spinner getSpinner14() {
        return spinner14;
    }

    public EditText getEdtxt_1() {
        return edtxt_1;
    }

    public EditText getEdtxt_2() {
        return edtxt_2;
    }

    public EditText getEdtxt_3() {
        return edtxt_3;
    }

    public EditText getEdtxt_4() {
        return edtxt_4;
    }

    public EditText getEdtxt_5() {
        return edtxt_5;
    }

    public EditText getEdtxt_6() {
        return edtxt_6;
    }

    public EditText getEdtxt_7() {
        return edtxt_7;
    }
}
