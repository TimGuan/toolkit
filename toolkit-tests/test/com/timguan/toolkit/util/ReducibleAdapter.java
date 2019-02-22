package com.timguan.toolkit.util;

import java.util.Date;

import com.alibaba.fastjson.JSON;

/**
 * spu生效活动数据按照优先级叠加顺序降维
 *
 * @author guankaiqiang
 * @date 2018/8/21
 */
public class ReducibleAdapter implements Reducible<Date, Integer, ReducibleDataEntity> {
    private ReducibleDataEntity source;

    public ReducibleAdapter(ReducibleDataEntity o) {
        this.source = o;
    }

    @Override
    public Date fromXValue() {
        return source.getActivityStartTime();
    }

    @Override
    public Date toXValue() {
        return source.getActivityEndTime();
    }

    @Override
    public void savePeriod(Date from, Date to) {
        source.setActivityStartTime(from);
        source.setActivityEndTime(to);
    }

    @Override
    public ReducibleDataEntity getSource() {
        return this.source;
    }

    @Override
    public Integer yValue() {
        return source.getActivityId();
    }

    @Override
    public Reducible<Date, Integer, ReducibleDataEntity> deepCopy() {
        return new ReducibleAdapter(
            JSON.parseObject(JSON.toJSONString(source), ReducibleDataEntity.class));
    }

    @Override
    public int compareTo(Reducible<Date, Integer, ReducibleDataEntity> o) {
        //数据按照优先级排序，高优先级的在前
        int result = o.getSource().getWeight().compareTo(source.getWeight());
        if (result != 0) {
            return result;
        }
        //同级别的活动不会出现交叉
        return source.getActivityEndTime().compareTo(o.getSource().getActivityEndTime());
    }
}
