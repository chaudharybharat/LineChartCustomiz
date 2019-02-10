package motibakery.com.ownchartline.LineChartClass.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;

import motibakery.com.ownchartline.BuildConfig;
import motibakery.com.ownchartline.LineChartClass.computator.PreviewChartComputator;
import motibakery.com.ownchartline.LineChartClass.gesture.PreviewChartTouchHandler;
import motibakery.com.ownchartline.LineChartClass.model.ColumnChartData;
import motibakery.com.ownchartline.LineChartClass.renderer.PreviewColumnChartRenderer;


/**
 * Preview chart that can be used as overview for other ColumnChart. When you change Viewport of this chart, visible
 * area of other chart will change. For that you need also to use
 * {//@link Chart#setViewportChangeListener(lecho.lib.hellocharts.listener.ViewportChangeListener)}
 *
 * @author Leszek Wach
 */
public class PreviewColumnChartView extends ColumnChartView {
    private static final String TAG = "ColumnChartView";

    protected PreviewColumnChartRenderer previewChartRenderer;

    public PreviewColumnChartView(Context context) {
        this(context, null, 0);
    }

    public PreviewColumnChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreviewColumnChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        chartComputator = new PreviewChartComputator();
        previewChartRenderer = new PreviewColumnChartRenderer(context, this, this);
        touchHandler = new PreviewChartTouchHandler(context, this);
        setChartRenderer(previewChartRenderer);
        setColumnChartData(ColumnChartData.generateDummyData());
    }

    public int getPreviewColor() {
        return previewChartRenderer.getPreviewColor();
    }

    public void setPreviewColor(int color) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Changing preview area color");
        }

        previewChartRenderer.setPreviewColor(color);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        final int offset = computeHorizontalScrollOffset();
        final int range = computeHorizontalScrollRange() - computeHorizontalScrollExtent();
        if (range == 0) return false;
        if (direction < 0) {
            return offset > 0;
        } else {
            return offset < range - 1;
        }
    }
}
