package com.dwk.bytimestamp;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 时间戳方式
 */
@Slf4j
public class TimeTaskByTimeStamp {

    private static final String TIP = "定时任务->";

    public static void main(String[] args) {

    }

    public void start(){
        try {
            AtomicLong startTime = new AtomicLong(System.currentTimeMillis());
            long stopTIme = 5000L;
            Thread thread = new Thread(() -> {
                long newTime = System.currentTimeMillis();
                if (newTime - startTime.get() > stopTIme){
                    log.info(TIP + "时间戳方式");
                    startTime.set(System.currentTimeMillis());
                }
            });
            Thread.sleep(stopTIme);
            thread.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
