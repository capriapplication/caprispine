package com.caprispine.caprispine.activity.assessment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.adapter.ViewPagerAdapter;
import com.caprispine.caprispine.fragment.BodyViewFragment;
import com.caprispine.caprispine.pojo.patientassessment.BodyChartPOJO;
import com.caprispine.caprispine.views.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BodyChartViewActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;
    @BindView(R.id.btn_front)
    Button btn_front;
    @BindView(R.id.btn_back)
    Button btn_back;
    @BindView(R.id.btn_left)
    Button btn_left;
    @BindView(R.id.btn_right)
    Button btn_right;


    BodyChartPOJO bodyChartResultPOJO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_chart_view);
        ButterKnife.bind(this);

        bodyChartResultPOJO= (BodyChartPOJO) getIntent().getSerializableExtra("bodyChartResultPOJO");

        if(bodyChartResultPOJO!=null) {
            setupViewPager(viewPager);
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(3);
                }
            });
            btn_front.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(0);
                }
            });
            btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(1);
                }
            });
            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewPager.setCurrentItem(2);
                }
            });
        }
    }

    private void setupViewPager(CustomViewPager viewPager) {
        BodyViewFragment bodyFragment1 = new BodyViewFragment(bodyChartResultPOJO.getFrontImage());
        BodyViewFragment bodyFragment2 = new BodyViewFragment(bodyChartResultPOJO.getLeftImage());
        BodyViewFragment bodyFragment3 = new BodyViewFragment(bodyChartResultPOJO.getRightImage());
        BodyViewFragment bodyFragment4 = new BodyViewFragment(bodyChartResultPOJO.getBackImage());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(bodyFragment1, "ROM");
        adapter.addFrag(bodyFragment2, "ROM");
        adapter.addFrag(bodyFragment3, "ROM");
        adapter.addFrag(bodyFragment4, "ROM");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPagingEnabled(true);
    }
}
