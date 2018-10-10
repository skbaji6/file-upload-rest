package com.example.filedemo.utils;

import java.awt.print.Paper;

public class MarginPaper extends Paper {
    private double minX;
    private double minY;
    private double maxRX;
    private double maxBY;

    public void setMinImageableArea(final double x, final double y, final double w, final double h) {
        this.minX = x;
        this.minY = y;
        this.maxRX = x + w;
        this.maxBY = y + h;
        super.setImageableArea(minX, minY, maxRX, maxBY);
    }

    @Override
    public void setImageableArea(double x, double y, double w, double h) {

        if (x < minX) {
            x = minX;
        }
        if (y < minY) {
            y = minY;
        }
        if (x + w > maxRX) {
            w = maxRX - x;
        }
        if (y + h > maxBY) {
            h = maxBY - y;
        }

        super.setImageableArea(x, y, w, h);
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxRX() {
        return maxRX;
    }

    public double getMaxBY() {
        return maxBY;
    }
}
