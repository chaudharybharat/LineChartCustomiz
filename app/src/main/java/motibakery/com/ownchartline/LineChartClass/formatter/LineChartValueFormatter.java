package motibakery.com.ownchartline.LineChartClass.formatter;


import motibakery.com.ownchartline.LineChartClass.model.PointValue;

public interface LineChartValueFormatter {

    public int formatChartValue(char[] formattedValue, PointValue value);
}
