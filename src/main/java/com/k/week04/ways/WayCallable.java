package com.k.week04.ways;

import com.k.week04.utils.SquareUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class WayCallable {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<Integer> callable = () -> SquareUtils.getInstance().getSquare(1000);

        Future<Integer> future = executor.submit(callable);

        log.info("计算结果：{}", future.get());
        log.info("计算耗时：{}ms", System.currentTimeMillis() - start);

        executor.shutdown();
    }
}
