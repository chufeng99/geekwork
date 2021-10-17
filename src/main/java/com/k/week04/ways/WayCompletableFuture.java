package com.k.week04.ways;

import com.k.week04.utils.SquareUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class WayCompletableFuture {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();

        CompletableFuture future = new CompletableFuture();
        SquareThread thread = new SquareThread(future, 1000);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(thread);
        executor.shutdown();

        log.info("计算结果：{}", future.get());
        log.info("计算耗时：{}ms", System.currentTimeMillis() - start);
    }

    static class SquareThread extends Thread {
        private CompletableFuture future;
        private int origin;
        public SquareThread(CompletableFuture future, int origin) {
            this.future = future;
            this.origin = origin;
        }

        @SneakyThrows
        @Override
        public void run() {
            TimeUnit.MILLISECONDS.sleep(1000);
            future.complete(SquareUtils.getInstance().getSquare(origin));
        }
    }
}
