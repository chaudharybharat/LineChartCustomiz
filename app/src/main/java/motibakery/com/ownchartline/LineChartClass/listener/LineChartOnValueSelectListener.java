package motibakery.com.ownchartline.LineChartClass.listener;


import motibakery.com.ownchartline.LineChartClass.model.PointValue;

public interface LineChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int lineIndex, int pointIndex, PointValue value);

}
