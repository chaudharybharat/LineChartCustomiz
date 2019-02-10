package motibakery.com.ownchartline.LineChartClass.listener;


import motibakery.com.ownchartline.LineChartClass.model.SliceValue;

public interface PieChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int arcIndex, SliceValue value);

}
