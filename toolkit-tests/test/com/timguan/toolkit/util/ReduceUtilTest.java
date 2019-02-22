package com.timguan.toolkit.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author guankaiqiang
 * @date 2018/8/21
 */
public class ReduceUtilTest {
    /**
     * 1:1-10号，优先接100
     * 3.10-15号，优先级100
     */
    @Test
    public void testReduce_TC1() {
        List<ReducibleDataEntity> orgDatas = new ArrayList<>();

        ReducibleDataEntity reducibleDataEntity1 = new ReducibleDataEntity();
        reducibleDataEntity1.setId(1);
        reducibleDataEntity1.setActivityId(1);
        reducibleDataEntity1.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataEntity1.setActivityEndTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataEntity1.setWeight(100);
        orgDatas.add(reducibleDataEntity1);

        ReducibleDataEntity reducibleDataEntity2 = new ReducibleDataEntity();
        reducibleDataEntity2.setId(2);
        reducibleDataEntity2.setActivityId(2);
        reducibleDataEntity2.setActivityStartTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataEntity2.setActivityEndTime(DateTime.parse("2018-08-15").toDate());
        reducibleDataEntity2.setWeight(100);
        orgDatas.add(reducibleDataEntity2);

        List<ReducibleAdapter> reducibleAdapters = orgDatas.stream()
            .map(ReducibleAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataEntity> reducedData = ReduceUtil.reduce(
            reducibleAdapters);

        Assert.assertEquals(2, reducedData.size());
        Assert.assertEquals(1, reducedData.get(0).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-01").toDate(), reducedData.get(0).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-10").toDate(), reducedData.get(0).getActivityEndTime());
        Assert.assertEquals(2, reducedData.get(1).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-10").toDate(), reducedData.get(1).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-15").toDate(), reducedData.get(1).getActivityEndTime());

        System.out.println(reducedData.stream()
            .map(ReducibleDataEntity::toString)
            .collect(Collectors.joining("\n")));
    }

    /**
     * 1:1-10号，优先级100
     * 3.11-15号，优先级200
     */
    @Test
    public void testReduce_TC2() {
        List<ReducibleDataEntity> orgDatas = new ArrayList<>();

        ReducibleDataEntity reducibleDataEntity1 = new ReducibleDataEntity();
        reducibleDataEntity1.setActivityId(1);
        reducibleDataEntity1.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataEntity1.setActivityEndTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataEntity1.setWeight(100);
        orgDatas.add(reducibleDataEntity1);

        ReducibleDataEntity reducibleDataEntity2 = new ReducibleDataEntity();
        reducibleDataEntity2.setActivityId(2);
        reducibleDataEntity2.setActivityStartTime(DateTime.parse("2018-08-11").toDate());
        reducibleDataEntity2.setActivityEndTime(DateTime.parse("2018-08-15").toDate());
        reducibleDataEntity2.setWeight(200);
        orgDatas.add(reducibleDataEntity2);

        List<ReducibleAdapter> reducibleAdapters = orgDatas.stream()
            .map(ReducibleAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataEntity> reducedData = ReduceUtil.reduce(
            reducibleAdapters);

        System.out.println(reducedData.stream()
            .map(ReducibleDataEntity::toString)
            .collect(Collectors.joining("\n")));

        Assert.assertEquals(2, reducedData.size());
        Assert.assertEquals(1, reducedData.get(0).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-01").toDate(), reducedData.get(0).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-10").toDate(), reducedData.get(0).getActivityEndTime());
        Assert.assertEquals(2, reducedData.get(1).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-11").toDate(), reducedData.get(1).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-15").toDate(), reducedData.get(1).getActivityEndTime());
    }

    /**
     * 1:1-10号，优先接100
     * 2:5-15号，优先级200
     */
    @Test
    public void testReduce_TC3() {
        List<ReducibleDataEntity> orgDatas = new ArrayList<>();

        ReducibleDataEntity reducibleDataEntity1 = new ReducibleDataEntity();
        reducibleDataEntity1.setActivityId(1);
        reducibleDataEntity1.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataEntity1.setActivityEndTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataEntity1.setWeight(100);
        orgDatas.add(reducibleDataEntity1);

        ReducibleDataEntity reducibleDataEntity2 = new ReducibleDataEntity();
        reducibleDataEntity2.setActivityId(2);
        reducibleDataEntity2.setActivityStartTime(DateTime.parse("2018-08-05").toDate());
        reducibleDataEntity2.setActivityEndTime(DateTime.parse("2018-08-15").toDate());
        reducibleDataEntity2.setWeight(200);
        orgDatas.add(reducibleDataEntity2);

        List<ReducibleAdapter> reducibleAdapters = orgDatas.stream()
            .map(ReducibleAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataEntity> reducedData = ReduceUtil.reduce(
            reducibleAdapters);

        Assert.assertEquals(2, reducedData.size(), 2);
        Assert.assertEquals(1, reducedData.get(0).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-01").toDate(), reducedData.get(0).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-05").toDate(), reducedData.get(0).getActivityEndTime());
        Assert.assertEquals(2, reducedData.get(1).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-05").toDate(), reducedData.get(1).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-15").toDate(), reducedData.get(1).getActivityEndTime());

        System.out.println(reducedData.stream()
            .map(ReducibleDataEntity::toString)
            .collect(Collectors.joining("\n")));
    }

    /**
     * 1:1-10号，优先接100
     * 2:5-7号，优先级200
     */
    @Test
    public void testReduce_TC4() {
        List<ReducibleDataEntity> orgDatas = new ArrayList<>();

        ReducibleDataEntity reducibleDataEntity1 = new ReducibleDataEntity();
        reducibleDataEntity1.setActivityId(1);
        reducibleDataEntity1.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataEntity1.setActivityEndTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataEntity1.setWeight(100);
        orgDatas.add(reducibleDataEntity1);

        ReducibleDataEntity reducibleDataEntity2 = new ReducibleDataEntity();
        reducibleDataEntity2.setActivityId(2);
        reducibleDataEntity2.setActivityStartTime(DateTime.parse("2018-08-05").toDate());
        reducibleDataEntity2.setActivityEndTime(DateTime.parse("2018-08-07").toDate());
        reducibleDataEntity2.setWeight(200);
        orgDatas.add(reducibleDataEntity2);

        List<ReducibleAdapter> reducibleAdapters = orgDatas.stream()
            .map(ReducibleAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataEntity> reducedData = ReduceUtil.reduce(
            reducibleAdapters);

        Assert.assertEquals(3, reducedData.size());
        Assert.assertEquals(1, reducedData.get(0).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-01").toDate(), reducedData.get(0).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-05").toDate(), reducedData.get(0).getActivityEndTime());
        Assert.assertEquals(2, reducedData.get(1).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-05").toDate(), reducedData.get(1).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-07").toDate(), reducedData.get(1).getActivityEndTime());
        Assert.assertEquals(1, reducedData.get(2).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-07").toDate(), reducedData.get(2).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-10").toDate(), reducedData.get(2).getActivityEndTime());

        System.out.println(reducedData.stream()
            .map(ReducibleDataEntity::toString)
            .collect(Collectors.joining("\n")));
    }

    /**
     * 1:5-10号，优先接100
     * 2:1-7号，优先级200
     */
    @Test
    public void testReduce_TC5() {
        List<ReducibleDataEntity> orgDatas = new ArrayList<>();

        ReducibleDataEntity reducibleDataEntity1 = new ReducibleDataEntity();
        reducibleDataEntity1.setActivityId(1);
        reducibleDataEntity1.setActivityStartTime(DateTime.parse("2018-08-05").toDate());
        reducibleDataEntity1.setActivityEndTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataEntity1.setWeight(100);
        orgDatas.add(reducibleDataEntity1);

        ReducibleDataEntity reducibleDataEntity2 = new ReducibleDataEntity();
        reducibleDataEntity2.setActivityId(2);
        reducibleDataEntity2.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataEntity2.setActivityEndTime(DateTime.parse("2018-08-07").toDate());
        reducibleDataEntity2.setWeight(200);
        orgDatas.add(reducibleDataEntity2);

        List<ReducibleAdapter> reducibleAdapters = orgDatas.stream()
            .map(ReducibleAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataEntity> reducedData = ReduceUtil.reduce(
            reducibleAdapters);

        Assert.assertEquals(2, reducedData.size());
        Assert.assertEquals(2, reducedData.get(0).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-01").toDate(), reducedData.get(0).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-07").toDate(), reducedData.get(0).getActivityEndTime());
        Assert.assertEquals(1, reducedData.get(1).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-07").toDate(), reducedData.get(1).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-10").toDate(), reducedData.get(1).getActivityEndTime());

        System.out.println(reducedData.stream()
            .map(ReducibleDataEntity::toString)
            .collect(Collectors.joining("\n")));
    }

    /**
     * 1:5-10号，优先接100
     * 2:1-15号，优先级200
     */
    @Test
    public void testReduce_TC6() {
        List<ReducibleDataEntity> orgDatas = new ArrayList<>();

        ReducibleDataEntity reducibleDataEntity1 = new ReducibleDataEntity();
        reducibleDataEntity1.setActivityId(1);
        reducibleDataEntity1.setActivityStartTime(DateTime.parse("2018-08-05").toDate());
        reducibleDataEntity1.setActivityEndTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataEntity1.setWeight(100);
        orgDatas.add(reducibleDataEntity1);

        ReducibleDataEntity reducibleDataEntity2 = new ReducibleDataEntity();
        reducibleDataEntity2.setActivityId(2);
        reducibleDataEntity2.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataEntity2.setActivityEndTime(DateTime.parse("2018-08-15").toDate());
        reducibleDataEntity2.setWeight(200);
        orgDatas.add(reducibleDataEntity2);

        List<ReducibleAdapter> reducibleAdapters = orgDatas.stream()
            .map(ReducibleAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataEntity> reducedData = ReduceUtil.reduce(
            reducibleAdapters);

        Assert.assertEquals(1, reducedData.size());
        Assert.assertEquals(2, reducedData.get(0).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-01").toDate(), reducedData.get(0).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-15").toDate(), reducedData.get(0).getActivityEndTime());

        System.out.println(reducedData.stream()
            .map(ReducibleDataEntity::toString)
            .collect(Collectors.joining("\n")));
    }

    /**
     * 1:1-7号，优先接100
     * 2:5-9号，优先级200
     * 3.8-15号，优先级100
     * ==> 1-5：1，5-9：2，9-15：3
     */
    @Test
    public void testReduce_TC7() {
        List<ReducibleDataEntity> orgDatas = new ArrayList<>();

        ReducibleDataEntity reducibleDataEntity1 = new ReducibleDataEntity();
        reducibleDataEntity1.setId(1);
        reducibleDataEntity1.setActivityId(1);
        reducibleDataEntity1.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataEntity1.setActivityEndTime(DateTime.parse("2018-08-07").toDate());
        reducibleDataEntity1.setWeight(100);
        orgDatas.add(reducibleDataEntity1);

        ReducibleDataEntity reducibleDataEntity2 = new ReducibleDataEntity();
        reducibleDataEntity2.setId(2);
        reducibleDataEntity2.setActivityId(2);
        reducibleDataEntity2.setActivityStartTime(DateTime.parse("2018-08-05").toDate());
        reducibleDataEntity2.setActivityEndTime(DateTime.parse("2018-08-09").toDate());
        reducibleDataEntity2.setWeight(200);
        orgDatas.add(reducibleDataEntity2);

        ReducibleDataEntity reducibleDataEntity3 = new ReducibleDataEntity();
        reducibleDataEntity3.setId(3);
        reducibleDataEntity3.setActivityId(3);
        reducibleDataEntity3.setActivityStartTime(DateTime.parse("2018-08-08").toDate());
        reducibleDataEntity3.setActivityEndTime(DateTime.parse("2018-08-15").toDate());
        reducibleDataEntity3.setWeight(100);
        orgDatas.add(reducibleDataEntity3);

        List<ReducibleAdapter> reducibleAdapters = orgDatas.stream()
            .map(ReducibleAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataEntity> reducedData = ReduceUtil.reduce(
            reducibleAdapters);

        Assert.assertEquals(3, reducedData.size());
        Assert.assertEquals(1, reducedData.get(0).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-01").toDate(), reducedData.get(0).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-05").toDate(), reducedData.get(0).getActivityEndTime());
        Assert.assertEquals(2, reducedData.get(1).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-05").toDate(), reducedData.get(1).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-09").toDate(), reducedData.get(1).getActivityEndTime());
        Assert.assertEquals(3, reducedData.get(2).getActivityId().intValue());
        Assert.assertEquals(DateTime.parse("2018-08-09").toDate(), reducedData.get(2).getActivityStartTime());
        Assert.assertEquals(DateTime.parse("2018-08-15").toDate(), reducedData.get(2).getActivityEndTime());

        System.out.println(reducedData.stream()
            .map(ReducibleDataEntity::toString)
            .collect(Collectors.joining("\n")));
    }
}
