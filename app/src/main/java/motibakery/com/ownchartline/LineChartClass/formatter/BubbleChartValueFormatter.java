package motibakery.com.ownchartline.LineChartClass.formatter;


import motibakery.com.ownchartline.LineChartClass.model.BubbleValue;

public interface BubbleChartValueFormatter {

    public int formatChartValue(char[] formattedValue, BubbleValue value);
}
