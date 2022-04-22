package com.dwk.byAnnotations;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务， 注解方式
 */
@Service
@Slf4j
public class TimeTaskByAnnotations {

    private static final String TIP = "定时任务->";

    private static int TASK_ONE_NUM, TASK_TWO_NUM, TASK_THREE_NUM = 0;

    private ThreadPoolExecutor threadPool;

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**每秒执行一次，等待线程都执行完再执行下一次定时任务*/
    @Scheduled(cron = "0/1 * * * * ?")
    public void startOne() {
//        log.info(TIP + "线程池异步且上一个定时任务未执行完下一个顺延");
//        taskOne(10);
    }

    /**每秒执行一次，不等线程执行完 准时执行下一次任务*/
    @Scheduled(cron = "0/1 * * * * ?")
    public void startTwo(){
        log.info(TIP + "上一个定时任务未执行完成下一个任然执行");
        taskTwo(5);
    }

    /**每秒执行一次，单个任务 - 上一个定时任务未执行完成下一个顺延*/
    @Scheduled(cron = "0/1 * * * * ?")
    public void startThree(){
//        log.info(TIP + "单个任务，上一个定时任务未执行完成下一个顺延");
//        taskThree();
    }


    /**num = 线程池核心线程数 = 线程池最大线程数 = 任务数*/
    public void taskOne(int num){
        log.info("第" + (++TASK_ONE_NUM) + "次执行定时任务，时间：" + format.format(new Date()));

        //每次定时任务初始化一个线程池，定时任务结束后关闭 如果时间周期短可以全局创建但是任务执行完不能关闭
        threadPool = new ThreadPoolExecutor(num,
                num,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        //构造一个数组用于请求线程
        ArrayList<Object> list = new ArrayList<>();
        for(int i = 0; i < num; i++){
            list.add("1");
        }

        try {
            List returnList = new ArrayList();
            //supplyAsync方式执行需要有返回值，每个线程执行完将返回值插入returnList最后通过returnList和list的长度判断是否所有线程都执行结束
            CompletableFuture[] cfs = list.stream().map(urlMap -> CompletableFuture.supplyAsync(() -> {
                try {
                    log.info("=================================   异步线程" + Thread.currentThread().getName() + "开始执行");
                    Thread.sleep(3000);
                    log.info("=================================   异步线程" + Thread.currentThread().getName() + "执行结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }, threadPool)
                    .thenApply(h -> h)
                    .whenComplete((v, e) -> {
                        if (v) {
                            returnList.add("over");
                        }
                    })).toArray(CompletableFuture[]::new);
            //等待线程全部执行完成
            CompletableFuture.allOf(cfs).join();
            while (returnList.size() == list.size()) {
                log.info("打印所有线程执行结果：" + JSONArray.toJSONString(returnList));
                break;
            }
        } finally {
            log.info("第" + TASK_ONE_NUM + "次定时任务，关闭线程池！！！");
            threadPool.shutdown();
        }
    }

    /**num = 线程池核心线程数 = 线程池最大线程数 = 任务数*/
    public void taskTwo(int num){
        log.info("第" + (++TASK_TWO_NUM) + "次执行定时任务，时间：" + format.format(new Date()));

        //每次定时任务初始化一个线程池，定时任务结束后关闭 如果时间周期短可以全局创建但是任务执行完不能关闭
        threadPool = new ThreadPoolExecutor(num,
                num,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        //构造一个数组用于请求线程
        ArrayList<Object> list = new ArrayList<>();
        for(int i = 0; i < num; i++){
            list.add("1");
        }

        try {
            //runAsync异步执行，无返回值
            CompletableFuture[] cfs = list.stream().map(urlMap -> CompletableFuture.runAsync(() -> {
                try {
                    log.info("=================================   异步线程" + Thread.currentThread().getName() + "开始执行");
                    Thread.sleep(3000);
                    log.info("=================================   异步线程" + Thread.currentThread().getName() + "执行结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, threadPool)).toArray(CompletableFuture[]::new);
            //等待线程全部执行完成
            CompletableFuture.allOf(cfs).join();
        } finally {
            log.info("第" + TASK_TWO_NUM + "次定时任务，关闭线程池!!");
            threadPool.shutdown();
        }

    }

    public void taskThree(){
        log.info("第" + (++TASK_THREE_NUM) + "次执行定时任务，时间：" + format.format(new Date()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("第" + TASK_THREE_NUM + "定时任务结束，时间：" + format.format(new Date()));
    }

}
