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
import java.util.List;

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


public class LineChartActivity extends AppCompatActivity {

 /*  public static String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
            "Oct", "Nov", "Dec "};*/



  //  public static  int[] yAxisData = {50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 80, 18};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);
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

            resetViewport();

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
            resetViewport();
        }

        private void resetViewport() {
            // Reset viewport height range to (0,80)
            final Viewport v = new Viewport(chart.getMaximumViewport());
            v.bottom = 0;
            v.top = 100;
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

          /*  if (hasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames) {
                    axisX.setName("Axis X");
                    axisY.setName("Axis Y");
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }*/
            List yAxisValues = new ArrayList();
            List axisValues = new ArrayList();


            Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));

            for (int i = 0; i < xBottomData.size(); i++) {
                axisValues.add(i, new AxisValue(i).setLabel(xBottomData.get(i)));
            }

            for (int i = 0; i <leftDataList.size(); i++) {
                yAxisValues.add(new PointValue(i, leftDataList.get(i)));
            }

            Axis axis = new Axis();
            axis.setValues(axisValues);
            axis.setTextSize(16);
         //   axis.setTextColor(Color.parseColor("#03A9F4"));
            data.setAxisXBottom(axis);

            Axis yAxis = new Axis();
         //   yAxis.setName("Sales in millions");
            yAxis.setTextColor(Color.parseColor("#03A9F4"));
            yAxis.setTextSize(16);
           data.setAxisYLeft(yAxis);
          //  data.setBaseValue(Float.NEGATIVE_INFINITY);
            chart.setLineChartData(data);
        }

        /**
         * Adds lines to data, after that data should be set again with
         * {@link LineChartView#setLineChartData(LineChartData)}. Last 4th line has non-monotonically x values.
         */





        /**
         * To animate values you have to change targets values and then call {//@link Chart#startDataAnimation()}
         * method(don't confuse with View.animate()). If you operate on data that was set before you don't have to call
         * {@link LineChartView#setLineChartData(LineChartData)} again.
         */
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
                    YData.add(80);
                    YData.add(80);
                    YData.add(30);
                    YData.add(40);
                    YData.add(10);
                    YData.add(1);
                    numberOfPoints=7;
                    randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
                    generateValues();
                  //  reset();
                    generateData();
                    resetViewport();
                    break;
                case R.id.btn_month:
                    chart.setLayoutParams(new LinearLayout.LayoutParams(5000,chart.getLayoutParams().height));
                    xBottomData.clear();
                    xBottomData.add(" 1");
                    xBottomData.add("2");
                    xBottomData.add("3");
                    xBottomData.add("4");
                    xBottomData.add("5");
                    xBottomData.add("6");
                    xBottomData.add("7");
                    xBottomData.add("8");
                    xBottomData.add("9");
                    xBottomData.add("10");
                    xBottomData.add("11");
                    xBottomData.add("12");
                    xBottomData.add("13");
                    xBottomData.add("14");
                    xBottomData.add("15");
                    xBottomData.add("16");
                    xBottomData.add("17");
                    xBottomData.add("18");
                    xBottomData.add("19");
                    xBottomData.add("20");
                    xBottomData.add("21");
                    xBottomData.add("22");
                    xBottomData.add("23");
                    xBottomData.add("24");
                    xBottomData.add("25");
                    xBottomData.add("26");
                    xBottomData.add("27");
                    xBottomData.add("28");
                    xBottomData.add("29");
                    xBottomData.add("30");
                    xBottomData.add("31");
                    YData.clear();
                    YData.add(50);
                    YData.add(60);
                    YData.add(70);
                    YData.add(80);
                    YData.add(80);
                    YData.add(80);
                    YData.add(80);
                    YData.add(50);
                    YData.add(60);
                    YData.add(70);
                    YData.add(70);
                    YData.add(70);
                    YData.add(80);

                    YData.add(50);
                    YData.add(60);
                    YData.add(70);
                    YData.add(80);
                    YData.add(80);
                    YData.add(80);
                    YData.add(50);
                    YData.add(60);
                    YData.add(70);
                    YData.add(70);
                    YData.add(70);
                    YData.add(80);
                    YData.add(80);
                    YData.add(80);
                    YData.add(80);
                    YData.add(80);
                    YData.add(80);
                    YData.add(80);
                    numberOfPoints=31;
                    randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
                    generateValues();
                    generateData();
                    resetViewport();
                    break;
                case R.id.btn_year:
                    chart.setLayoutParams(new LinearLayout.LayoutParams(2500,chart.getLayoutParams().height));
                    initData();
                    numberOfPoints=12;
                    randomNumbersTab = new float[maxNumberOfLines][numberOfPoints];
                    generateValues();
                    generateData();
                    resetViewport();
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
}
