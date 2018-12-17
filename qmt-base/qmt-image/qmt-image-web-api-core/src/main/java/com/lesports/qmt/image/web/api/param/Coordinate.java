package com.lesports.qmt.image.web.api.param;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by denghui on 2016/12/28.
 */
public class Coordinate {
    private int x;
    private int y;
    private int w;
    private int h;

    public Coordinate() {
    }

    public Coordinate(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("x", x)
                .append("y", y)
                .append("w", w)
                .append("h", h)
                .toString();
    }
}
