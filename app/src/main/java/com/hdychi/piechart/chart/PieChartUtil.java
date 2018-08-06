package com.hdychi.piechart.chart;

public class PieChartUtil {
    public static float toArc(float degree) {
        return (float) (Math.PI * degree / 180.0);
    }
}