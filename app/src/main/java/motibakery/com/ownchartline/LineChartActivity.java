package motibakery.com.ownchartline;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import motibakery.com.ownchartline.LineChartClass.animation.ChartAnimationListener;
import motibakery.com.ownchartline.LineChartClass.gesture.ZoomType;
import motibakery.com.ownchartline.LineChartClass.listener.LineChartOnValueSelectListener;
import motibakery.com.ownchartline.LineChartClass.model.Axis;
import motibakery.com.ownchartline.LineChartClass.model.AxisValue;
import motibakery.com.ownchartline.LineChartClass.model.Line;
import motibakery.com.ownchartline.LineChartClass.model.LineChartData;
import motibakery.com.ownchartline.LineChartClass.model.PointValue;
import motibakery.com.ownchartline.LineChartClass.model.ValueShape;
import motibakery.com.ownchartline.LineChartClass.model.Viewport;
import motibakery.com.ownchartline.LineChartClass.util.ChartUtils;
import motibakery.com.ownchartline.LineChartClass.view.LineChartView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class LineChartActivity extends AppCompatActivity {

 /*  public static String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
            "Oct", "Nov", "Dec "};*/

 public static LineChartActivity contacnt;

  //  public static  int[] yAxisData = {50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 80, 18};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
        contacnt=this;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    /**
     * A fragment containing a line chart.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener{
        List<String> xBottomData=new ArrayList<>();
        List<Integer> YData=new ArrayList<>();
        List<Integer> leftDataList=new ArrayList<>();
        private LineChartView chart;
        private LineChartData data;
        private int numberOfLines = 1;
        private int maxNumberOfLines = 4;
        private int numberOfPoints = 12;

        float[][] randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];

        private boolean hasAxes = true;
        private boolean hasAxesNames = true;
        private boolean hasLines = true;
        private boolean hasPoints = true;
        private ValueShape shape = ValueShape.CIRCLE;
        private boolean isFilled = false;
        private boolean hasLabels = false;
        private boolean isCubic = false;
        private boolean hasLabelForSelected = false;
        private boolean pointsHaveDifferentColor;
        private boolean hasGradientToTransparent = false;
        int width=0;
        Button btn_week,btn_month,btn_year;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_line_chart, container, false);
            btn_week=(Button) rootView.findViewById(R.id.btn_week);
            btn_month=(Button) rootView.findViewById(R.id.btn_month);
            btn_year=(Button) rootView.findViewById(R.id.btn_year);
            btn_week.setOnClickListener(this);
            btn_month.setOnClickListener(this);
            btn_year.setOnClickListener(this);
            leftDataList.add(10);
            leftDataList.add(20);
            leftDataList.add(30);
            leftDataList.add(40);
            leftDataList.add(50);
            leftDataList.add(60);
            leftDataList.add(70);
            leftDataList.add(80);
            leftDataList.add(90);
            leftDataList.add(100);
            initData();
            isFilled=true;
           // hasPoints=false;
            hasGradientToTransparent=true;
            isCubic=true;
            HorizontalScrollView scroll_linechart = (HorizontalScrollView) rootView.findViewById(R.id.scroll_linechart);
            chart = (LineChartView) rootView.findViewById(R.id.chart);

            DisplayMetrics displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            width = displaymetrics.widthPixels;

             Log.e("test","==>"+(xBottomData.size() * width / 8));
           chart.setLayoutParams(new LinearLayout.LayoutParams(3000,chart.getLayoutParams().height));

            chart.setOnValueTouchListener(new ValueTouchListener());
          //  chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
            // Generate some random values.

            generateValues();

            generateData();

            // Disable viewport recalculations, see toggleCubic() method for more info.
        //    chart.setViewportCalculationEnabled(false);

            resetViewport(100);

            return rootView;
        }

        private void initData() {
            xBottomData.clear();
            xBottomData.add("Jan");
            xBottomData.add("Feb");
            xBottomData.add("Mar");
            xBottomData.add("Apr");
            xBottomData.add("May");
            xBottomData.add("June");
            xBottomData.add("July");
            xBottomData.add("Aug");
            xBottomData.add("Sept");
            xBottomData.add("Oct");
            xBottomData.add("Nov");
            xBottomData.add("Dec ");
           YData.clear();
            YData.add(70);
            YData.add(20);
            YData.add(30);
            YData.add(40);
            YData.add(50);
            YData.add(80);
            YData.add(50);
            YData.add(60);
            YData.add(70);
            YData.add(70);
            YData.add(70);
            YData.add(80);
        }

        // MENU
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.line_chart, menu);
        }

        private void generateValues() {
            for (int i = 0; i < maxNumberOfLines; ++i) {
                for (int j = 0; j <YData.size(); ++j) {
                    randomNumbersTab[i][j] = (float) YData.get(j);
                }
            }
        }

        private void reset() {
            chart.setLayoutParams(new LinearLayout.LayoutParams(2500,chart.getLayoutParams().height));
            numberOfLines = 1;
            hasAxes = true;
            hasAxesNames = true;
            hasLines = true;
            hasPoints = true;
            shape = ValueShape.CIRCLE;
            isFilled = false;
            hasLabels = false;
            isCubic = false;
            hasLabelForSelected = false;
            pointsHaveDifferentColor = false;

            chart.setValueSelectionEnabled(hasLabelForSelected);
            resetViewport(100);
        }

        private void resetViewport(int topValue) {
            // Reset viewport height range to (0,80)
            final Viewport v = new Viewport(chart.getMaximumViewport());
            v.bottom = 0;
            v.top = topValue;
            v.left = 0;
            v.right = numberOfPoints - 1;
            chart.setMaximumViewport(v);
            chart.setCurrentViewport(v);
        }

        public void generateData() {
            List<Line> lines = new ArrayList<Line>();
            int mz=getMax(YData);

            for (int i = 0; i < numberOfLines; ++i) {
                List<PointValue> values = new ArrayList<PointValue>();
                for (int j = 0; j<YData.size(); ++j) {
                    values.add(new PointValue(j,randomNumbersTab[i][j]));
                }
                //values.add(new PointValue(YData.size(), mz+5));

                Line line = new Line(values);
                line.setColor(ChartUtils.COLORS[i]);
                line.setShape(shape);
                line.setCubic(isCubic);
                line.setFilled(isFilled);
                line.setHasLabels(hasLabels);
                line.setHasLabelsOnlyForSelected(hasLabelForSelected);
                line.setHasLines(hasLines);
                line.setHasPoints(hasPoints);
                line.setHasGradientToTransparent(hasGradientToTransparent);
                if (pointsHaveDifferentColor){
                    line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
                }
                lines.add(line);
            }
            data = new LineChartData(lines);
            List yAxisValues = new ArrayList();
            List axisValues = new ArrayList();

            for (int i = 0; i < xBottomData.size(); i++) {
                axisValues.add(i, new AxisValue(i).setLabel(xBottomData.get(i)));
            }

            for (int i = 0; i <leftDataList.size(); i++) {
                yAxisValues.add(new PointValue(i, leftDataList.get(i)));
            }

            Axis axis = new Axis();
            axis.setValues(axisValues);

            axis.setTextSize(16);
            data.setAxisXBottom(axis);


            Axis yAxis = new Axis();
            yAxis.setTextColor(Color.parseColor("#03A9F4"));
            yAxis.setTextSize(16);
         //  data.setAxisYLeft(yAxis);
            chart.setLineChartData(data);
        }


        public int getMax(List<Integer> lists){
            int max = Integer.MIN_VALUE;
            for(int i=0; i<lists.size(); i++){
                if(lists.get(i) > max){
                    max = lists.get(i);
                }
            }
            return max;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_week:
                    chart.setLayoutParams(new LinearLayout.LayoutParams(1500,chart.getLayoutParams().height));
                    xBottomData.clear();
                    xBottomData.add(" 1");
                    xBottomData.add("2");
                    xBottomData.add("3");
                    xBottomData.add("4");
                    xBottomData.add("5");
                    xBottomData.add("6");
                    xBottomData.add("7");
                    YData.clear();
                    YData.add(80);
                    YData.add(5);
                    YData.add(0);
                    YData.add(0);
                    YData.add(0);
                    YData.add(0);
                    YData.add(80);
                    numberOfPoints=7;
                    randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
                    generateValues();
                  //  reset();
                    generateData();
                    int max_value=getMax(YData);
                    resetViewport(max_value+8);
                    break;
                case R.id.btn_month:

                    Calendar calendar = Calendar.getInstance();
                   int year= calendar.get(Calendar.YEAR);
                   int month= calendar.get(Calendar.MONTH)+1;
                 int day_of_month= getMonthDays(month,year);
                 chart.setLayoutParams(new LinearLayout.LayoutParams(5000,chart.getLayoutParams().height));
                    xBottomData.clear();
                    YData.clear();
                    for (int i = 1; i <=day_of_month ; i++) {
                        //month of date
                        xBottomData.add(""+i);
                        //day of value
                        YData.add((new Random()).nextInt((90 - 10) + 1) + 10);
                    }
                    numberOfPoints=day_of_month;
                    randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
                    generateValues();
                    generateData();
                    int max_value1=getMax(YData);
                    resetViewport(max_value1+8);
                    break;
                case R.id.btn_year:
                    chart.setLayoutParams(new LinearLayout.LayoutParams(2500,chart.getLayoutParams().height));
                    initData();
                    numberOfPoints=12;
                    randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
                    generateValues();
                    generateData();
                    int max_value2=getMax(YData);
                    resetViewport(max_value2+8);
                    break;

            }
        }

        private class ValueTouchListener implements LineChartOnValueSelectListener {

            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {
                // TODO Auto-generated method stub

            }

        }
    }
    public static int getMonthDays(int month, int year) {
        int daysInMonth ;
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            daysInMonth = 30;
        }
        else {
            if (month == 2) {
                daysInMonth = (year % 4 == 0) ? 29 : 28;
            } else {
                daysInMonth = 31;
            }
        }
        return daysInMonth;
    }
}
