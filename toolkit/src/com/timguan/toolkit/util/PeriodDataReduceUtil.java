package com.timguan.toolkit.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * 区间数据降维工具
 *
 * @author guankaiqiang
 * @date 2018/8/21
 */
public class PeriodDataReduceUtil {
    /**
     * 数据降维工具
     * 应用场景：同一时间重叠多个数据值，多数据值中存在优先级顺序，通过reduce，提前计算数据重叠达到每个时间点只返回唯一有效数据值
     *
     * @param adapterDataList 原始数据
     * @param <X>             X轴数据类型
     * @param <Y>             Y轴数据类型，E的特征属性
     * @param <E>             原始数据类型
     * @param <T>             原始数据的适配类型
     * @return 无重叠数据
     */
    public static <X extends Comparable<X>, Y, E, T extends AbstractPeriodReducibleAdapter<X, Y, E>> List<E> reduce(
        List<T> adapterDataList) {
        List<E> result = new ArrayList<>();
        //过滤掉无效数据 from==to，数据点为无效数据点
        adapterDataList = adapterDataList.stream()
            .filter(periodReducibleData ->
                periodReducibleData.fromXValue().compareTo(periodReducibleData.toXValue()) != 0)
            .collect(Collectors.toList());
        if (adapterDataList == null || adapterDataList.size() == 0) {
            return result;
        }
        //1.枚举所有数据变更点的X轴坐标点
        Set<X> xValueSet = new HashSet<>();
        for (AbstractPeriodReducibleAdapter<X, Y, E> periodReducibleData : adapterDataList) {
            xValueSet.add(periodReducibleData.fromXValue());
            xValueSet.add(periodReducibleData.toXValue());
        }
        //X轴坐标按从大到小排序
        List<X> xValueList = new ArrayList<>(xValueSet);
        Collections.sort(xValueList);
        Collections.reverse(xValueList);
        //源数据排序
        adapterDataList.sort(AbstractPeriodReducibleAdapter::compareTo);
        //2.获取每个X轴坐标点的生效数据
        Stack<Coordinate<X, Y, E>> coordinateStack = new Stack<>();
        for (X xValue : xValueList) {
            coordinateStack.push(getValueOfXCoordinate(xValue, adapterDataList));
        }
        //3.合并连续的数据
        List<Coordinate<X, Y, E>> effectiveCoordinateList = new LinkedList<>();
        Coordinate<X, Y, E> coordinate = coordinateStack.pop();
        effectiveCoordinateList.add(coordinate);
        Coordinate<X, Y, E> currentCoordinate = coordinateStack.pop();
        if (coordinateStack.isEmpty()) {
            effectiveCoordinateList.add(currentCoordinate);
        } else {
            while (!coordinateStack.isEmpty()) {
                if (coordinate.getYValue() != currentCoordinate.getYValue()) {
                    effectiveCoordinateList.add(currentCoordinate);
                } else {
                    //若相邻两条记录的Y坐标值一致，说明需要进行合并，currentCoordinate是个无效的坐标
                }
                coordinate = currentCoordinate;
                currentCoordinate = coordinateStack.pop();
            }
            effectiveCoordinateList.add(currentCoordinate);
        }
        for (int index = 0; index < effectiveCoordinateList.size() - 1; index++) {
            Coordinate<X, Y, E> start = effectiveCoordinateList.get(index);
            Coordinate<X, Y, E> end = effectiveCoordinateList.get(index + 1);
            if (start.getYValue() != null) {
                List<T> finalAdapterDataList = adapterDataList;
                AbstractPeriodReducibleAdapter<X, Y, E> orgData = adapterDataList.stream()
                    .filter(x -> x.yValue().equals(start.getYValue()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(
                        "can not find id: " + start.getYValue() + " from adapterDataList: " + finalAdapterDataList));
                AbstractPeriodReducibleAdapter<X, Y, E> newObject = orgData.deepCopy();
                newObject.savePeriod(start.getXValue(), end.getXValue());
                result.add(newObject.getOrgInstance());
            }
        }
        return result;
    }

    /**
     * @param xValue         X轴值
     * @param sortedDataList 按照优先级排序的源数据
     * @param <X>            X轴数据类型（比如：时间）
     * @param <Y>            Y轴数据类型，E的特征属性
     * @param <E>            原始数据类型
     * @param <T>            原始数据的适配类型
     * @return (X, Y(data)) 获取特定X轴点的坐标数据
     */
    private static <X extends Comparable<X>, Y, E, T extends AbstractPeriodReducibleAdapter<X, Y, E>>
    Coordinate<X, Y, E> getValueOfXCoordinate(X xValue, List<T> sortedDataList) {
        Coordinate<X, Y, E> coordinateValue = new Coordinate<>(xValue, null);
        if (sortedDataList != null && sortedDataList.size() > 0) {
            sortedDataList.stream()
                .filter(data -> data.fromXValue().compareTo(xValue) <= 0 && xValue.compareTo(data.toXValue()) < 0)
                .findFirst()
                .ifPresent(coordinateValue::setData);
        }
        return coordinateValue;
    }
}
