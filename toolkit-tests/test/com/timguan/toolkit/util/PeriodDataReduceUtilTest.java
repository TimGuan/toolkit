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
public class PeriodDataReduceUtilTest {
    /**
     * 1:1-10号，优先接100
     * 3.10-15号，优先级100
     */
    @Test
    public void testReduce_TC1() {
        List<ReducibleDataDemo> orgDatas = new ArrayList<>();

        ReducibleDataDemo reducibleDataDemo1 = new ReducibleDataDemo();
        reducibleDataDemo1.setId(1);
        reducibleDataDemo1.setActivityId(1);
        reducibleDataDemo1.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataDemo1.setActivityEndTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataDemo1.setWeight(100);
        orgDatas.add(reducibleDataDemo1);

        ReducibleDataDemo reducibleDataDemo2 = new ReducibleDataDemo();
        reducibleDataDemo2.setId(2);
        reducibleDataDemo2.setActivityId(2);
        reducibleDataDemo2.setActivityStartTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataDemo2.setActivityEndTime(DateTime.parse("2018-08-15").toDate());
        reducibleDataDemo2.setWeight(100);
        orgDatas.add(reducibleDataDemo2);

        List<ReducibleDataAdapter> reducibleDataAdapters = orgDatas.stream()
            .map(ReducibleDataAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataDemo> reducedData = PeriodDataReduceUtil.reduce(
            reducibleDataAdapters);

        Assert.assertEquals(reducedData.size(), 2);
        Assert.assertEquals(reducedData.get(0).getActivityId().intValue(), 1);
        Assert.assertEquals(reducedData.get(0).getActivityStartTime(), DateTime.parse("2018-08-01").toDate());
        Assert.assertEquals(reducedData.get(0).getActivityEndTime(), DateTime.parse("2018-08-10").toDate());
        Assert.assertEquals(reducedData.get(1).getActivityId().intValue(), 2);
        Assert.assertEquals(reducedData.get(1).getActivityStartTime(), DateTime.parse("2018-08-10").toDate());
        Assert.assertEquals(reducedData.get(1).getActivityEndTime(), DateTime.parse("2018-08-15").toDate());

        System.out.println(reducedData.stream()
            .map(ReducibleDataDemo::toString)
            .collect(Collectors.joining("\n")));
    }

    /**
     * 1:1-10号，优先接100
     * 3.11-15号，优先级200
     */
    @Test
    public void testReduce_TC2() {
        List<ReducibleDataDemo> orgDatas = new ArrayList<>();

        ReducibleDataDemo reducibleDataDemo1 = new ReducibleDataDemo();
        reducibleDataDemo1.setActivityId(1);
        reducibleDataDemo1.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataDemo1.setActivityEndTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataDemo1.setWeight(100);
        orgDatas.add(reducibleDataDemo1);

        ReducibleDataDemo reducibleDataDemo2 = new ReducibleDataDemo();
        reducibleDataDemo2.setActivityId(2);
        reducibleDataDemo2.setActivityStartTime(DateTime.parse("2018-08-11").toDate());
        reducibleDataDemo2.setActivityEndTime(DateTime.parse("2018-08-15").toDate());
        reducibleDataDemo2.setWeight(200);
        orgDatas.add(reducibleDataDemo2);

        List<ReducibleDataAdapter> reducibleDataAdapters = orgDatas.stream()
            .map(ReducibleDataAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataDemo> reducedData = PeriodDataReduceUtil.reduce(
            reducibleDataAdapters);

        Assert.assertEquals(reducedData.size(), 2);
        Assert.assertEquals(reducedData.get(0).getActivityId().intValue(), 1);
        Assert.assertEquals(reducedData.get(0).getActivityStartTime(), DateTime.parse("2018-08-01").toDate());
        Assert.assertEquals(reducedData.get(0).getActivityEndTime(), DateTime.parse("2018-08-10").toDate());
        Assert.assertEquals(reducedData.get(1).getActivityId().intValue(), 2);
        Assert.assertEquals(reducedData.get(1).getActivityStartTime(), DateTime.parse("2018-08-11").toDate());
        Assert.assertEquals(reducedData.get(1).getActivityEndTime(), DateTime.parse("2018-08-15").toDate());

        System.out.println(reducedData.stream()
            .map(ReducibleDataDemo::toString)
            .collect(Collectors.joining("\n")));
    }

    /**
     * 1:1-10号，优先接100
     * 2:5-15号，优先级200
     */
    @Test
    public void testReduce_TC3() {
        List<ReducibleDataDemo> orgDatas = new ArrayList<>();

        ReducibleDataDemo reducibleDataDemo1 = new ReducibleDataDemo();
        reducibleDataDemo1.setActivityId(1);
        reducibleDataDemo1.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataDemo1.setActivityEndTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataDemo1.setWeight(100);
        orgDatas.add(reducibleDataDemo1);

        ReducibleDataDemo reducibleDataDemo2 = new ReducibleDataDemo();
        reducibleDataDemo2.setActivityId(2);
        reducibleDataDemo2.setActivityStartTime(DateTime.parse("2018-08-05").toDate());
        reducibleDataDemo2.setActivityEndTime(DateTime.parse("2018-08-15").toDate());
        reducibleDataDemo2.setWeight(200);
        orgDatas.add(reducibleDataDemo2);

        List<ReducibleDataAdapter> reducibleDataAdapters = orgDatas.stream()
            .map(ReducibleDataAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataDemo> reducedData = PeriodDataReduceUtil.reduce(
            reducibleDataAdapters);

        Assert.assertEquals(reducedData.size(), 2);
        Assert.assertEquals(reducedData.get(0).getActivityId().intValue(), 1);
        Assert.assertEquals(reducedData.get(0).getActivityStartTime(), DateTime.parse("2018-08-01").toDate());
        Assert.assertEquals(reducedData.get(0).getActivityEndTime(), DateTime.parse("2018-08-05").toDate());
        Assert.assertEquals(reducedData.get(1).getActivityId().intValue(), 2);
        Assert.assertEquals(reducedData.get(1).getActivityStartTime(), DateTime.parse("2018-08-05").toDate());
        Assert.assertEquals(reducedData.get(1).getActivityEndTime(), DateTime.parse("2018-08-15").toDate());

        System.out.println(reducedData.stream()
            .map(ReducibleDataDemo::toString)
            .collect(Collectors.joining("\n")));
    }

    /**
     * 1:1-10号，优先接100
     * 2:5-7号，优先级200
     */
    @Test
    public void testReduce_TC4() {
        List<ReducibleDataDemo> orgDatas = new ArrayList<>();

        ReducibleDataDemo reducibleDataDemo1 = new ReducibleDataDemo();
        reducibleDataDemo1.setActivityId(1);
        reducibleDataDemo1.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataDemo1.setActivityEndTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataDemo1.setWeight(100);
        orgDatas.add(reducibleDataDemo1);

        ReducibleDataDemo reducibleDataDemo2 = new ReducibleDataDemo();
        reducibleDataDemo2.setActivityId(2);
        reducibleDataDemo2.setActivityStartTime(DateTime.parse("2018-08-05").toDate());
        reducibleDataDemo2.setActivityEndTime(DateTime.parse("2018-08-07").toDate());
        reducibleDataDemo2.setWeight(200);
        orgDatas.add(reducibleDataDemo2);

        List<ReducibleDataAdapter> reducibleDataAdapters = orgDatas.stream()
            .map(ReducibleDataAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataDemo> reducedData = PeriodDataReduceUtil.reduce(
            reducibleDataAdapters);

        Assert.assertEquals(reducedData.size(), 3);
        Assert.assertEquals(reducedData.get(0).getActivityId().intValue(), 1);
        Assert.assertEquals(reducedData.get(0).getActivityStartTime(), DateTime.parse("2018-08-01").toDate());
        Assert.assertEquals(reducedData.get(0).getActivityEndTime(), DateTime.parse("2018-08-05").toDate());
        Assert.assertEquals(reducedData.get(1).getActivityId().intValue(), 2);
        Assert.assertEquals(reducedData.get(1).getActivityStartTime(), DateTime.parse("2018-08-05").toDate());
        Assert.assertEquals(reducedData.get(1).getActivityEndTime(), DateTime.parse("2018-08-07").toDate());
        Assert.assertEquals(reducedData.get(2).getActivityId().intValue(), 1);
        Assert.assertEquals(reducedData.get(2).getActivityStartTime(), DateTime.parse("2018-08-07").toDate());
        Assert.assertEquals(reducedData.get(2).getActivityEndTime(), DateTime.parse("2018-08-10").toDate());

        System.out.println(reducedData.stream()
            .map(ReducibleDataDemo::toString)
            .collect(Collectors.joining("\n")));
    }

    /**
     * 1:5-10号，优先接100
     * 2:1-7号，优先级200
     */
    @Test
    public void testReduce_TC5() {
        List<ReducibleDataDemo> orgDatas = new ArrayList<>();

        ReducibleDataDemo reducibleDataDemo1 = new ReducibleDataDemo();
        reducibleDataDemo1.setActivityId(1);
        reducibleDataDemo1.setActivityStartTime(DateTime.parse("2018-08-05").toDate());
        reducibleDataDemo1.setActivityEndTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataDemo1.setWeight(100);
        orgDatas.add(reducibleDataDemo1);

        ReducibleDataDemo reducibleDataDemo2 = new ReducibleDataDemo();
        reducibleDataDemo2.setActivityId(2);
        reducibleDataDemo2.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataDemo2.setActivityEndTime(DateTime.parse("2018-08-07").toDate());
        reducibleDataDemo2.setWeight(200);
        orgDatas.add(reducibleDataDemo2);

        List<ReducibleDataAdapter> reducibleDataAdapters = orgDatas.stream()
            .map(ReducibleDataAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataDemo> reducedData = PeriodDataReduceUtil.reduce(
            reducibleDataAdapters);

        Assert.assertEquals(reducedData.size(), 2);
        Assert.assertEquals(reducedData.get(0).getActivityId().intValue(), 2);
        Assert.assertEquals(reducedData.get(0).getActivityStartTime(), DateTime.parse("2018-08-01").toDate());
        Assert.assertEquals(reducedData.get(0).getActivityEndTime(), DateTime.parse("2018-08-07").toDate());
        Assert.assertEquals(reducedData.get(1).getActivityId().intValue(), 1);
        Assert.assertEquals(reducedData.get(1).getActivityStartTime(), DateTime.parse("2018-08-07").toDate());
        Assert.assertEquals(reducedData.get(1).getActivityEndTime(), DateTime.parse("2018-08-10").toDate());

        System.out.println(reducedData.stream()
            .map(ReducibleDataDemo::toString)
            .collect(Collectors.joining("\n")));
    }

    /**
     * 1:5-10号，优先接100
     * 2:1-15号，优先级200
     */
    @Test
    public void testReduce_TC6() {
        List<ReducibleDataDemo> orgDatas = new ArrayList<>();

        ReducibleDataDemo reducibleDataDemo1 = new ReducibleDataDemo();
        reducibleDataDemo1.setActivityId(1);
        reducibleDataDemo1.setActivityStartTime(DateTime.parse("2018-08-05").toDate());
        reducibleDataDemo1.setActivityEndTime(DateTime.parse("2018-08-10").toDate());
        reducibleDataDemo1.setWeight(100);
        orgDatas.add(reducibleDataDemo1);

        ReducibleDataDemo reducibleDataDemo2 = new ReducibleDataDemo();
        reducibleDataDemo2.setActivityId(2);
        reducibleDataDemo2.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataDemo2.setActivityEndTime(DateTime.parse("2018-08-15").toDate());
        reducibleDataDemo2.setWeight(200);
        orgDatas.add(reducibleDataDemo2);

        List<ReducibleDataAdapter> reducibleDataAdapters = orgDatas.stream()
            .map(ReducibleDataAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataDemo> reducedData = PeriodDataReduceUtil.reduce(
            reducibleDataAdapters);

        Assert.assertEquals(reducedData.size(), 1);
        Assert.assertEquals(reducedData.get(0).getActivityId().intValue(), 2);
        Assert.assertEquals(reducedData.get(0).getActivityStartTime(), DateTime.parse("2018-08-01").toDate());
        Assert.assertEquals(reducedData.get(0).getActivityEndTime(), DateTime.parse("2018-08-15").toDate());

        System.out.println(reducedData.stream()
            .map(ReducibleDataDemo::toString)
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
        List<ReducibleDataDemo> orgDatas = new ArrayList<>();

        ReducibleDataDemo reducibleDataDemo1 = new ReducibleDataDemo();
        reducibleDataDemo1.setId(1);
        reducibleDataDemo1.setActivityId(1);
        reducibleDataDemo1.setActivityStartTime(DateTime.parse("2018-08-01").toDate());
        reducibleDataDemo1.setActivityEndTime(DateTime.parse("2018-08-07").toDate());
        reducibleDataDemo1.setWeight(100);
        orgDatas.add(reducibleDataDemo1);

        ReducibleDataDemo reducibleDataDemo2 = new ReducibleDataDemo();
        reducibleDataDemo2.setId(2);
        reducibleDataDemo2.setActivityId(2);
        reducibleDataDemo2.setActivityStartTime(DateTime.parse("2018-08-05").toDate());
        reducibleDataDemo2.setActivityEndTime(DateTime.parse("2018-08-09").toDate());
        reducibleDataDemo2.setWeight(200);
        orgDatas.add(reducibleDataDemo2);

        ReducibleDataDemo reducibleDataDemo3 = new ReducibleDataDemo();
        reducibleDataDemo3.setId(3);
        reducibleDataDemo3.setActivityId(3);
        reducibleDataDemo3.setActivityStartTime(DateTime.parse("2018-08-08").toDate());
        reducibleDataDemo3.setActivityEndTime(DateTime.parse("2018-08-15").toDate());
        reducibleDataDemo3.setWeight(100);
        orgDatas.add(reducibleDataDemo3);

        List<ReducibleDataAdapter> reducibleDataAdapters = orgDatas.stream()
            .map(ReducibleDataAdapter::new)
            .collect(Collectors.toList());

        List<ReducibleDataDemo> reducedData = PeriodDataReduceUtil.reduce(
            reducibleDataAdapters);

        Assert.assertEquals(reducedData.size(), 3);
        Assert.assertEquals(reducedData.get(0).getActivityId().intValue(), 1);
        Assert.assertEquals(reducedData.get(0).getActivityStartTime(), DateTime.parse("2018-08-01").toDate());
        Assert.assertEquals(reducedData.get(0).getActivityEndTime(), DateTime.parse("2018-08-05").toDate());
        Assert.assertEquals(reducedData.get(1).getActivityId().intValue(), 2);
        Assert.assertEquals(reducedData.get(1).getActivityStartTime(), DateTime.parse("2018-08-05").toDate());
        Assert.assertEquals(reducedData.get(1).getActivityEndTime(), DateTime.parse("2018-08-09").toDate());
        Assert.assertEquals(reducedData.get(2).getActivityId().intValue(), 3);
        Assert.assertEquals(reducedData.get(2).getActivityStartTime(), DateTime.parse("2018-08-09").toDate());
        Assert.assertEquals(reducedData.get(2).getActivityEndTime(), DateTime.parse("2018-08-15").toDate());

        System.out.println(reducedData.stream()
            .map(ReducibleDataDemo::toString)
            .collect(Collectors.joining("\n")));
    }
}
