package com.roy.controller.lockscope;

import lombok.Getter;

class Data {
    @Getter
    private static int counter = 0;
    private static Object locker = new Object();

    public static int reset() {
        counter = 0;
        return counter;
    }

    //这样的话你拿的锁就是他所属的对象
    public synchronized void wrong() {
        counter++;
    }

    //一定要是个静态对象，具有唯一性
    public void right() {
        synchronized (locker) {
            counter++;
        }
    }
}