package motibakery.com.ownchartline.LineChartClass.formatter;


import motibakery.com.ownchartline.LineChartClass.model.SubcolumnValue;

public interface ColumnChartValueFormatter {

    public int formatChartValue(char[] formattedValue, SubcolumnValue value);

}
