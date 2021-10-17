package com.k.week04.ways;

import com.k.week04.utils.SquareUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class WayCondition {
    private static final ReentrantLock lock = new ReentrantLock(true);
    private static final Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        SquareThread thread = new SquareThread(1000);

        thread.start();

        lock.lock();
        try {
            condition.await();
        } finally {
            lock.unlock();
        }

        int result = thread.result;

        log.info("计算结果：{}", result);
        log.info("计算耗时：{}ms", System.currentTimeMillis() - start);
    }

    static class SquareThread extends Thread {
        private int origin;
        public SquareThread(int origin) {
            this.origin = origin;
        }

        @Getter
        private int result;

        @Override
        public void run() {
            lock.lock();
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                result = SquareUtils.getInstance().getSquare(origin);
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
