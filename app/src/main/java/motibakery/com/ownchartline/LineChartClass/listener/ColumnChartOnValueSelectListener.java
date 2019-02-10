package motibakery.com.ownchartline.LineChartClass.listener;


import motibakery.com.ownchartline.LineChartClass.model.SubcolumnValue;

public interface ColumnChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value);

}
