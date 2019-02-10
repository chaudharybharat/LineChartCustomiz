package motibakery.com.ownchartline.LineChartClass.listener;


import motibakery.com.ownchartline.LineChartClass.model.BubbleValue;

public interface BubbleChartOnValueSelectListener extends OnValueDeselectListener {

    public void onValueSelected(int bubbleIndex, BubbleValue value);

}
