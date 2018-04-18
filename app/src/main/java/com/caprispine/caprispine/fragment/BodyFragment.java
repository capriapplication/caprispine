package com.caprispine.caprispine.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.views.CanvasView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 11-12-2017.
 */

@SuppressLint("ValidFragment")
public class BodyFragment extends Fragment {
    @BindView(R.id.canvasView)
    CanvasView canvas;
    @BindView(R.id.btn_redo)
    Button btn_redo;
    @BindView(R.id.btn_undo)
    Button btn_undo;
    @BindView(R.id.btn_clear)
    Button btn_clear;
    @BindView(R.id.btn_save)
    Button btn_save;
    String body_type = "";

    public BodyFragment(String body_type) {
        this.body_type = body_type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_body, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Drawable myDrawable = null;
        switch (body_type) {
            case "front":
                myDrawable = getResources().getDrawable(R.drawable.body1);
                break;
            case "left":
                myDrawable = getResources().getDrawable(R.drawable.body_left);
                break;
            case "right":
                myDrawable = getResources().getDrawable(R.drawable.body_right);
                break;
            case "back":
                myDrawable = getResources().getDrawable(R.drawable.body_back);
                break;

        }
        if(myDrawable!=null) {
            Bitmap anImage = ((BitmapDrawable) myDrawable).getBitmap();

            canvas.drawBitmap(anImage);
        }
        canvas.setMode(CanvasView.Mode.DRAW);
        canvas.setDrawer(CanvasView.Drawer.PEN);
        canvas.setBaseColor(Color.WHITE);
        canvas.setPaintStyle(Paint.Style.STROKE);
        canvas.setPaintStrokeColor(Color.RED);
        canvas.setPaintStrokeWidth(10F);

        btn_redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvas.redo();
            }
        });

        btn_undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvas.undo();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = canvas.getBitmap();
//                SaveImage(bitmap);
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvas.clear();
            }
        });
    }
    public Bitmap getBitmap(){
        return canvas.getBitmap();
    }

    public void setColor(String color){
        canvas.setPaintStrokeColor(Color.parseColor(color));
    }
}
