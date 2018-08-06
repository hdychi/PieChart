package com.hdychi.piechart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PieChart extends View {
    private static final String EMPTY_MSG = "No Data Available";
    private static final float MSG_TEXT_SIZE = 50.0f;
    private static final float ITEM_TEXT_SIZE = 50.0F;
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 800;
    private static final int DEFAULT_LINE_OFFSET = 10;
    private List<PieChartItem> pieChartItems;
    private Paint mPaintForMsg;
    private Paint mPaintForArc;
    private Paint mPaintForName;
    private Paint mPaintForCount;
    private Paint mPaintForLine;
    private DecimalFormat decimalFormat;
    private Path path = new Path();


    //红，蓝，黄，绿 ，灰，橙，紫
    private int[] defaultColors = {Color.parseColor("#FF4500"),
            Color.parseColor("#4876FF"),Color.parseColor("#FFEC8B"),
            Color.GREEN,Color.parseColor("#B3B3B3"),
            Color.parseColor("#EE7600"),Color.parseColor("#BF3EFF")};

    public PieChart(Context context) {
        super(context);
        init();
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        pieChartItems = new ArrayList<>();
        decimalFormat = new DecimalFormat("#.00");

        mPaintForMsg = new Paint();
        mPaintForMsg.setTextAlign(Paint.Align.CENTER);
        mPaintForMsg.setTextSize(MSG_TEXT_SIZE);

        mPaintForName = new Paint();
        mPaintForName.setTextAlign(Paint.Align.CENTER);
        mPaintForName.setColor(Color.BLACK);
        mPaintForName.setTextSize(ITEM_TEXT_SIZE);

        mPaintForCount = new Paint();
        mPaintForCount.setTextAlign(Paint.Align.CENTER);
        mPaintForCount.setColor(Color.BLACK);
        mPaintForCount.setTextSize(ITEM_TEXT_SIZE);

        mPaintForArc = new Paint();
        mPaintForLine = new Paint();
        mPaintForLine.setStyle(Paint.Style.STROKE);
        mPaintForLine.setColor(Color.GRAY);
        mPaintForLine.setStrokeWidth(5);
        mPaintForLine.setAntiAlias(true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(getWidth() / 10 * 3, getHeight() / 10 * 3);
        int left = centerX - radius;
        int top = centerY - radius;
        int right = centerX + radius;
        int bottom = centerY + radius;
        if (pieChartItems.size() == 0) {
            canvas.drawText(EMPTY_MSG, centerX, centerY, mPaintForMsg);
            return;
        }
        int totalCnt = 0;
        for (PieChartItem item : pieChartItems) {
            totalCnt += item.getCount();
        }

        float nowAngle = 0.0f;
        for (int i = 0; i < pieChartItems.size(); i++) {
            PieChartItem pieChartItem = pieChartItems.get(i);
            mPaintForArc.setColor(defaultColors[i % defaultColors.length]);
            float percent = pieChartItem.getCount() / (float) totalCnt;
            float sweepAngle = percent * 360f;
            canvas.drawArc(left, top, right, bottom, nowAngle, -sweepAngle, true,
                    mPaintForArc);
            float lineLen = radius + Math.min(radius / 5 , radius / 5);
            float startX = (float) (centerX
                    + radius * 0.75 * Math.cos(Math.PI * (-nowAngle + sweepAngle / 2) / 180));
            float startY = (float) (centerY
                    - radius * 0.75 * Math.sin(Math.PI * (-nowAngle + sweepAngle / 2) / 180));
            float endX = (float) (centerX
                    + lineLen * Math.cos(Math.PI * (-nowAngle + sweepAngle / 2) / 180));
            float endY = (float) (centerY
                    - lineLen * Math.sin(Math.PI * (-nowAngle + sweepAngle / 2) / 180));
            path.reset();
            path.moveTo(startX,startY);
            path.lineTo(endX,endY);
            endX = endX + (endX < centerX ? -(lineLen - radius) / 2:(lineLen - radius) / 2);
            path.lineTo(endX,endY);
            canvas.drawPath(path,mPaintForLine);
            if (endX < centerX) {
                mPaintForName.setTextAlign(Paint.Align.RIGHT);
                mPaintForCount.setTextAlign(Paint.Align.RIGHT);
            }
            else{
                mPaintForName.setTextAlign(Paint.Align.LEFT);
                mPaintForCount.setTextAlign(Paint.Align.LEFT);
            }
            canvas.drawText(pieChartItem.getName(),endX,endY, mPaintForName);
            canvas.drawText(decimalFormat.format(percent * 100) + "%",
                    endX,endY + ITEM_TEXT_SIZE + DEFAULT_LINE_OFFSET,mPaintForCount);
            nowAngle -= sweepAngle;
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int oldWidth = Math.max(DEFAULT_WIDTH,getWidth());
        int oldHeight = Math.max(DEFAULT_HEIGHT,getHeight());

        int newWidth = resolveSize(oldWidth,widthMeasureSpec);
        int newHeight = resolveSize(oldHeight,heightMeasureSpec);
        setMeasuredDimension(newWidth,newHeight);
    }

    public void addItem(PieChartItem item) {
        pieChartItems.add(item);
    }

    public void clear() {
        pieChartItems.clear();
    }

    public void removeAt(int index) {
        pieChartItems.remove(index);
    }
}
