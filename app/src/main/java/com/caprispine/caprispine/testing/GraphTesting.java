package com.caprispine.caprispine.testing;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.caprispine.caprispine.R;
import com.caprispine.caprispine.Util.UtilityFunction;
import com.caprispine.caprispine.pojo.graph.DateValue;
import com.caprispine.caprispine.pojo.graph.GraphPOJO;
import com.caprispine.caprispine.pojo.graph.GraphResultPOJO;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GraphTesting extends AppCompatActivity {

    @BindView(R.id.graph)
    GraphView graph;
//
//    List<DateValue> dateValues = new ArrayList<>();
//    List<DateValue> dateValues1 = new ArrayList<>();
//    List<DateValue> dateValues2 = new ArrayList<>();
    GraphPOJO graphPOJO;
    int[] colors=new int[]{Color.BLUE,Color.GREEN,Color.YELLOW,Color.BLACK,Color.RED,Color.GRAY,Color.MAGENTA,Color.CYAN};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_testing);
        ButterKnife.bind(this);
        graphPOJO= (GraphPOJO) getIntent().getSerializableExtra("graphPOJO");


//        dateValues.add(new DateValue("10-04-2018", "5"));
//        dateValues.add(new DateValue("11-04-2018", "4"));
//        dateValues.add(new DateValue("12-04-2018", "1"));
//        dateValues.add(new DateValue("13-04-2018", "2"));
//        dateValues.add(new DateValue("14-04-2018", "3"));
//
//
//        dateValues1.add(new DateValue("10-04-2018", "6"));
//        dateValues1.add(new DateValue("11-04-2018", "adcads"));
//        dateValues1.add(new DateValue("12-04-2018", "4"));
//        dateValues1.add(new DateValue("13-04-2018", "2"));
//        dateValues1.add(new DateValue("14-04-2018", "5"));
//
//        dateValues2.add(new DateValue("10-04-2018", "1"));
//        dateValues2.add(new DateValue("11-04-2018", "2"));
//        dateValues2.add(new DateValue("12-04-2018", "3"));
//        dateValues2.add(new DateValue("13-04-2018", "4"));
//        dateValues2.add(new DateValue("14-04-2018", "5"));

//
//        List<List<DateValue>> lists=new ArrayList<>();
//        lists.add(dateValues);
//        lists.add(dateValues1);
//        lists.add(dateValues2);

        if(graphPOJO!=null){
            List<List<DateValue>> daLists=new ArrayList<>();
            for(GraphResultPOJO graphResultPOJO:graphPOJO.getGraphResultPOJOS()){
                daLists.add(graphResultPOJO.getDateValues());
            }

            dateList(daLists);
        }

//        dateList(lists);
    }

    public void dateList(List<List<DateValue>> daListList) {
        int size=0;
        if(daListList.size()>0){
            size=daListList.get(0).size();
        }
        Date df = null, dl = null;
        for(int j=0;j<daListList.size();j++) {
            List<DateValue> dateValues=daListList.get(j);
            List<DataPoint> dataPointList=new ArrayList<>();
            for (int i = 0; i < dateValues.size(); i++) {
                try {
                    Date d = UtilityFunction.getDate1(dateValues.get(i).getDate());
                    if (i == 0) {
                        df = d;
                    }

                    if (i == (dateValues.size()- 1)) {
                        dl = d;
                    }

                    int val = Integer.parseInt(dateValues.get(i).getValue());

                    dataPointList.add(new DataPoint(d, val));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            DataPoint[] dataPoints=new DataPoint[dataPointList.size()];
            for(int i=0;i<dataPointList.size();i++){
                dataPoints[i]=dataPointList.get(i);
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
            series.setColor(colors[j]);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(10);
            series.setThickness(8);
            graph.addSeries(series);
        }


// set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(size); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(df.getTime());
        graph.getViewport().setMaxX(dl.getTime());


        graph.getGridLabelRenderer().setNumVerticalLabels(11);
        graph.getViewport().setMaxY(200);
        graph.getViewport().setMinY(0);


        graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);

    }

    public void addGraphAsLabel() {
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();

// you can directly pass Date objects to DataPoint-Constructor
// this will convert the Date to double via Date#getTime()
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(d1, 1),
                new DataPoint(d2, 5),
                new DataPoint(d3, 3)
        });

        graph.addSeries(series);

// set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

// set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
    }
}
