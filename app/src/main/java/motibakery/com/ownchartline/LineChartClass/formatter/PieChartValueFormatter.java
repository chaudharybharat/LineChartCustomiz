package motibakery.com.ownchartline.LineChartClass.formatter;


import motibakery.com.ownchartline.LineChartClass.model.SliceValue;

public interface PieChartValueFormatter {

    public int formatChartValue(char[] formattedValue, SliceValue value);
}
