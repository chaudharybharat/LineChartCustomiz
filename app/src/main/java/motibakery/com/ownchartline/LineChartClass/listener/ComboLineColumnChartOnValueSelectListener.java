package motibakery.com.ownchartline.LineChartClass.listener;


import motibakery.com.ownchartline.LineChartClass.model.PointValue;
import motibakery.com.ownchartline.LineChartClass.model.SubcolumnValue;

public interface ComboLineColumnChartOnValueSelectListener extends OnValueDeselectListener {

    public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value);

    public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value);

}
