package com.roy.controller.concurrent;

import org.springframework.util.StopWatch;

/**
 * description： 测试stopwatch
 * author：dingyawu
 * date：created in 16:42 2020/11/6
 * history:
 */
public class StopWatchDemo {
    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        StopWatch clock = new StopWatch();
        clock.start("TaskOneName");
        Thread.sleep(1000 * 3);// 任务一模拟休眠3秒钟
        clock.stop();
        clock.start("TaskTwoName");
        Thread.sleep(1000 * 10);// 任务一模拟休眠10秒钟
        clock.stop();
        clock.start("TaskThreeName");
        Thread.sleep(1000 * 10);// 任务一模拟休眠10秒钟
        clock.stop();
        System.out.println(clock.prettyPrint());
    }

}
