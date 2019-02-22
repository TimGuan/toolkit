package com.timguan.toolkit.util;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.util.Pair;

/**
 * 区间数据降维工具
 *
 * @author guankaiqiang
 * @date 2018/8/21
 */
public class ReduceUtil {
    private ReduceUtil() {

    }

    /**
     * 数据降维工具
     * 应用场景：同一时间重叠多个数据值，多数据值中存在优先级顺序，通过reduce，提前计算数据重叠达到每个时间点只返回唯一有效数据值
     *
     * @param reducibleDataList 原始数据
     * @param <X>               X轴数据类型
     * @param <Y>               Y轴数据类型，E的特征属性
     * @param <E>               原始数据类型
     * @param <R>               原始数据的适配类型
     * @return 无重叠数据
     */
    public static <X extends Comparable<X>, Y, E, R extends Reducible<X, Y, E>> List<E> reduce(
        List<R> reducibleDataList) {
        List<E> result = new ArrayList<>();
        //过滤掉无效数据 from==to，数据点为无效数据点
        reducibleDataList = reducibleDataList.stream()
            .filter(periodReducibleData ->
                periodReducibleData.fromXValue().compareTo(periodReducibleData.toXValue()) != 0)
            .collect(Collectors.toList());
        if (reducibleDataList == null || reducibleDataList.isEmpty()) {
            return result;
        }
        //源数据排序
        reducibleDataList.sort(Reducible::compareTo);
        List<Pair<X, Reducible<X, Y, E>>> reducedDataPairList = getReducedDataPairList(reducibleDataList);
        for (int index = 0; index < reducedDataPairList.size() - 1; index++) {
            Pair<X, Reducible<X, Y, E>> start = reducedDataPairList.get(index);
            Pair<X, Reducible<X, Y, E>> end = reducedDataPairList.get(index + 1);
            if (start.getValue() != null) {
                List<R> finalReducibleDataList = reducibleDataList;
                Reducible<X, Y, E> orgData = reducibleDataList.stream()
                    .filter(x -> x.yValue().equals(start.getValue().yValue()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException(
                        "can not find id: " + start.getValue().yValue() + " from reducibleDataList: "
                            + finalReducibleDataList));
                Reducible<X, Y, E> newObject = orgData.deepCopy();
                newObject.savePeriod(start.getKey(), end.getKey());
                result.add(newObject.getSource());
            }
        }
        return result;
    }

    /**
     * 筛选出每个变化点的有效数据
     */
    private static <X extends Comparable<X>, Y, E, R extends Reducible<X, Y, E>>
    List<Pair<X, Reducible<X, Y, E>>> getReducedDataPairList(List<R> sortedReducibleDataList) {
        //1.枚举所有数据变更点的X轴坐标点
        List<X> xValueList = new ArrayList<>();
        for (Reducible<X, Y, E> periodReducibleData : sortedReducibleDataList) {
            xValueList.add(periodReducibleData.fromXValue());
            xValueList.add(periodReducibleData.toXValue());
        }
        //X轴坐标按从大到小排序
        xValueList = xValueList.stream()
            .distinct()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
        //2.获取每个X轴坐标点的生效数据
        Deque<Pair<X, Reducible<X, Y, E>>> reducibleDataPairDeque = new ArrayDeque<>();
        for (X xValue : xValueList) {
            reducibleDataPairDeque.push(getOnlyReducedDataAtXValue(xValue, sortedReducibleDataList));
        }
        //3.合并连续的数据
        List<Pair<X, Reducible<X, Y, E>>> effectiveReducibleDataList = new LinkedList<>();
        Pair<X, Reducible<X, Y, E>> lastPair = reducibleDataPairDeque.pop();
        effectiveReducibleDataList.add(lastPair);
        Pair<X, Reducible<X, Y, E>> currentPair = reducibleDataPairDeque.pop();
        if (reducibleDataPairDeque.isEmpty()) {
            effectiveReducibleDataList.add(currentPair);
        } else {
            while (!reducibleDataPairDeque.isEmpty()) {
                Y lastY = lastPair.getValue() != null ? lastPair.getValue().yValue() : null;
                Y currentY = currentPair.getValue() != null ? currentPair.getValue().yValue() : null;
                if (lastY != currentY) {
                    effectiveReducibleDataList.add(currentPair);
                } else {
                    //若相邻两条记录的Y坐标值一致，说明需要进行合并，currentPair是个无效数据
                }
                lastPair = currentPair;
                currentPair = reducibleDataPairDeque.pop();
            }
            effectiveReducibleDataList.add(currentPair);
        }
        return effectiveReducibleDataList;
    }

    /**
     * 获取xValue的唯一有效数据值
     */
    private static <X extends Comparable<X>, Y, E, R extends Reducible<X, Y, E>>
    Pair<X, Reducible<X, Y, E>> getOnlyReducedDataAtXValue(X xValue, List<R> sortedReducibleLists) {
        if (sortedReducibleLists != null && !sortedReducibleLists.isEmpty()) {
            Reducible<X, Y, E> reducibleData = sortedReducibleLists.stream()
                .filter(data -> data.fromXValue().compareTo(xValue) <= 0 && xValue.compareTo(data.toXValue()) < 0)
                .findFirst()
                .orElse(null);
            return new Pair<>(xValue, reducibleData);
        }
        return new Pair<>(xValue, null);
    }
}
