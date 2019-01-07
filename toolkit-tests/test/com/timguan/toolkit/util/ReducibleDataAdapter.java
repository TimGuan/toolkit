package com.timguan.toolkit.util;

import java.util.Date;

import com.alibaba.fastjson.JSON;

/**
 * spu生效活动数据按照优先级叠加顺序降维
 *
 * @author guankaiqiang
 * @date 2018/8/21
 */
public class ReducibleDataAdapter
    extends AbstractPeriodReducibleAdapter<Date, Integer, ReducibleDataDemo> {
    public ReducibleDataAdapter(ReducibleDataDemo o) {
        super(o);
    }

    @Override
    public Date fromXValue() {
        return instance.getActivityStartTime();
    }

    @Override
    public Date toXValue() {
        return instance.getActivityEndTime();
    }

    @Override
    public void savePeriod(Date from, Date to) {
        instance.setActivityStartTime(from);
        instance.setActivityEndTime(to);
    }

    @Override
    public Integer yValue() {
        return instance.getActivityId();
    }

    @Override
    public AbstractPeriodReducibleAdapter<Date, Integer, ReducibleDataDemo> deepCopy() {
        return new ReducibleDataAdapter(
            JSON.parseObject(JSON.toJSONString(instance), ReducibleDataDemo.class));
    }

    @Override
    public int compareTo(AbstractPeriodReducibleAdapter<Date, Integer, ReducibleDataDemo> o) {
        //数据按照优先级排序，高优先级的在前
        int result = o.getOrgInstance().getWeight().compareTo(instance.getWeight());
        if (result != 0) {
            return result;
        }
        //同级别的活动不会出现交叉
        return instance.getActivityEndTime().compareTo(o.getOrgInstance().getActivityEndTime());
    }
}
