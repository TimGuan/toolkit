package com.timguan.toolkit.util;

/**
 * 可降维数据抽象接口
 *
 * @author guankaiqiang
 * @date 2018/8/21
 */
public interface Reducible<X extends Comparable<X>, Y, T>
    extends Comparable<Reducible<X, Y, T>> {
    /**
     * 源数据
     * <p>
     * 例如：某时间段，对应的配置数据；
     * </p>
     *
     * @return
     */
    T getSource();

    /**
     * 横向数据起点
     * <p>
     * 例如：某时间段，对应的配置数据；对应到数据坐标中，横坐标即时间，纵坐标即业务数据；fromXValue即坐标系内某个线段的开始；
     * </p>
     *
     * @return 横向数据起点
     */
    X fromXValue();

    /**
     * 横向数据终点
     * <p>
     * 例如：某时间段，对应的配置数据；对应到数据坐标中，横坐标即时间，纵坐标即业务数据；toXValue即坐标系内某个线段的结束；
     * </p>
     *
     * @return 横向数据起点
     */
    X toXValue();

    /**
     * 纵向数据
     * <p>
     * 例如：业务数据的特征值；
     * </p>
     *
     * @return 业务数据的特征值
     */
    Y yValue();

    /**
     * 聚合后的回调，用于修改源数据内的数据的起止点
     */
    void savePeriod(X from, X to);

    /**
     * 聚合过程中可能产生数据分裂，对应的需要从源数据中进行深度copy
     *
     * @return 源数据的深度拷贝
     */
    Reducible<X, Y, T> deepCopy();
}
