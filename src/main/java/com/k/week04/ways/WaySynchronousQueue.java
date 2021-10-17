package com.k.week04.ways;

import com.k.week04.utils.SquareUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WaySynchronousQueue {
    private static final SynchronousQueue<Integer> queue = new SynchronousQueue();

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        long start = System.currentTimeMillis();

        SquareThread thread = new SquareThread(1000);

        thread.start();

        int result = queue.take();

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

        @SneakyThrows
        @Override
        public void run() {
            TimeUnit.MILLISECONDS.sleep(1000);
            result = SquareUtils.getInstance().getSquare(origin);
            queue.add(result);
        }
    }
}
