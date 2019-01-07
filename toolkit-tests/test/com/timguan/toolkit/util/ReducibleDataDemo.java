package com.timguan.toolkit.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ReducibleDataDemo implements Serializable {
    private Integer id;

    private Integer activityId;

    private Integer weight;

    private Date activityStartTime;

    private Date activityEndTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Date getActivityStartTime() {
        return activityStartTime;
    }

    public void setActivityStartTime(Date activityStartTime) {
        this.activityStartTime = activityStartTime;
    }

    public Date getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(Date activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    @Override
    public String toString() {
        return "ReducibleDataDemo{" +
            "id=" + id +
            ", activityId=" + activityId +
            ", weight=" + weight +
            ", activityStartTime=" + activityStartTime +
            ", activityEndTime=" + activityEndTime +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof ReducibleDataDemo)) { return false; }
        ReducibleDataDemo that = (ReducibleDataDemo)o;
        return Objects.equals(id, that.id) &&
            Objects.equals(activityId, that.activityId) &&
            Objects.equals(weight, that.weight) &&
            Objects.equals(activityStartTime, that.activityStartTime) &&
            Objects.equals(activityEndTime, that.activityEndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activityId, weight, activityStartTime, activityEndTime);
    }
}