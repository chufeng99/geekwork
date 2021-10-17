package com.k.week04.ways;

import com.k.week04.utils.SquareUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class WayFutureTask {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();

        SquareThread thread = new SquareThread(1000);
        FutureTask task = new FutureTask(thread);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(task);
        executor.shutdown();

        log.info("计算结果：{}", task.get());
        log.info("计算耗时：{}ms", System.currentTimeMillis() - start);
    }

    static class SquareThread implements Callable<Integer> {
        private int origin;
        public SquareThread(int origin) {
            this.origin = origin;
        }

        @SneakyThrows
        @Override
        public Integer call() {
            TimeUnit.MILLISECONDS.sleep(1000);
            return SquareUtils.getInstance().getSquare(origin);
        }
    }
}
