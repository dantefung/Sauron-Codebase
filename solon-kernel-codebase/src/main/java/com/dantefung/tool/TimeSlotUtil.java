package com.dantefung.tool;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;


public class TimeSlotUtil {

    /**
     * 验证时间段是否有重合
     * @param timeIntervals
     * @return
     */
    public static boolean verifyRepeatTime(List<TimeIntervalModel> timeIntervals) {
        List<Pub<Integer,Integer,TimeIntervalModel>> intervals = Lists.newArrayList();
        //开始时间必须小于结束时间
        for(TimeIntervalModel data:timeIntervals){
            Integer start = LocalTime.parse(data.getStartTime()).toSecondOfDay();
            Integer end = LocalTime.parse(data.getEndTime()).toSecondOfDay();
            if(start > end){
                return false;
            }
            intervals.add(new Pub(start,end,data));
        }
        //验证时间段是否重合
        Iterator<Pub<Integer, Integer, TimeIntervalModel>> iterator = intervals.iterator();
        while (iterator.hasNext()){
            Pub<Integer, Integer, TimeIntervalModel> data = iterator.next();
            iterator.remove();
            for(Pub < Integer,Integer,TimeIntervalModel> compareData:intervals){
                boolean result = data.getF2()<=compareData.getF1() || compareData.getF2()<=data.getF1();
                if(!result) {
                    return false;
                }
            }
        }
        return true;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class Pub<F1,F2,F3> {
        private F1 f1;
        private F2 f2;
        private F3 f3;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
    public static class TimeIntervalModel{

        private String startTime;

        private String endTime;
    }

    public static void main(String[] args) throws Exception{
        List<TimeIntervalModel> timeIntervals = Lists.newArrayList();

        timeIntervals.add(new TimeIntervalModel(){{setStartTime("15:00");setEndTime("17:00");}} );
        timeIntervals.add(new TimeIntervalModel(){{setStartTime("00:00");setEndTime("07:00");}} );
        timeIntervals.add(new TimeIntervalModel(){{setStartTime("06:00");setEndTime("07:00");}} );

        verifyRepeatTime(timeIntervals);
    }
}


