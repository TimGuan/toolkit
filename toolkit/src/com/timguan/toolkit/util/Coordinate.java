package com.timguan.toolkit.util;

import java.io.Serializable;

/**
 * X的数据Y (X,Y)
 *
 * @author guankaiqiang
 * @date 2018/8/21
 */
class Coordinate<X extends Comparable<X>, Y, E> implements Serializable, Comparable<Coordinate<X, Y, E>> {
    private X xValue;
    private AbstractPeriodReducibleAdapter<X, Y, E> data;

    Coordinate(X timePoint, AbstractPeriodReducibleAdapter<X, Y, E> data) {
        this.xValue = timePoint;
        this.data = data;
    }

    public void setData(AbstractPeriodReducibleAdapter<X, Y, E> data) {
        this.data = data;
    }

    X getXValue() {
        return xValue;
    }

    Y getYValue() {
        if (data != null) {
            return data.yValue();
        }
        return null;
    }

    @Override
    public int compareTo(Coordinate<X, Y, E> o) {
        return getXValue().compareTo(o.getXValue());
    }
}
