package com.k.week04.ways;

import com.k.week04.utils.SquareUtils;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class WayVolatile {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        SquareThread thread = new SquareThread(1000);

        thread.start();

        while (!thread.success) {
            TimeUnit.MILLISECONDS.sleep(10);
            log.info("sleep...");
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

        private volatile boolean success = false;

        @Getter
        private int result;

        @SneakyThrows
        @Override
        public void run() {
            TimeUnit.MILLISECONDS.sleep(1000);
            result = SquareUtils.getInstance().getSquare(origin);
            success = true;
        }
    }
}
