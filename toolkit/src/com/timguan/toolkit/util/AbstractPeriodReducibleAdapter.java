package com.timguan.toolkit.util;

/**
 * 可降维的数据适配器
 *
 * @author guankaiqiang
 * @date 2018/8/21
 */
public abstract class AbstractPeriodReducibleAdapter<X extends Comparable<X>, Y, T>
    implements Comparable<AbstractPeriodReducibleAdapter<X, Y, T>> {
    protected T instance;

    public AbstractPeriodReducibleAdapter(T o) {
        this.instance = o;
    }

    public abstract X fromXValue();

    public abstract X toXValue();

    public abstract Y yValue();

    public abstract void savePeriod(X from, X to);

    public T getOrgInstance() {
        return instance;
    }

    public abstract AbstractPeriodReducibleAdapter<X, Y, T> deepCopy();
}
