package com.dwk.bytimetask;

import lombok.extern.slf4j.Slf4j;
import sun.util.resources.LocaleData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * TimerTask类
 */
@Slf4j
public class TimeTaskByTimeTask {

    private static final String TIP_TIMING = "定时任务->";
    private static final String TIP_DELAYED = "延迟任务->";

    private static long num = 0L;

    public static void main(String[] args) {
//        startDelayed();
        startTiming();
    }

    /**延迟任务*/
    public static void startDelayed(){
        //延迟时间
        long time = 5000L;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info(TIP_DELAYED + "Timer类方式，第" + (++num) + "次执行");
            }
        },time);
    }

    /**定时任务*/
    public static void startTiming(){
        //间隔执行时间
        long time_start = 1000L;
        //延迟时间
        long time_delayed = 500L;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info(TIP_TIMING + "Timer类方式，第" + (++num) + "次执行,执行时间：" + LocalDateTime.now());
            }
        },time_delayed,time_start);

        //停止定时任务
        timer.cancel();
    }

    /**定时任务 - scheduleAtFixedRate方式*/
    public static void startTimingByFixedRate() throws ParseException {
        //设置任务第一次执行的时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse("");
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

            }
        },1,1);
    }

}
