package com.dantefung.concurrent.parallel;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://note.youdao.com/web/#/file/9c8566e5ff111012d51233a076dd0c03/markdown/WEB60bd8ae12d684445be4fa178d48a3c87/
 * 事实上，这段代码在 ForkJoinPool 中创建了一个并行流，在线程内部再次使用 ForkJoinPool 区域的公共池中的线程。
 */
public class CustomPootParallelStreams {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        parallelStreamProcess();
    }

    private static void parallelStreamProcess() throws ExecutionException, InterruptedException {
        int start = 1;
        int end = 10000;
        List<Integer> intList = IntStream.rangeClosed(start, end).boxed()
                .collect(Collectors.toList());
        System.out.println(intList.size());
        ForkJoinPool newCustomThreadPool = new ForkJoinPool(5);
        int actualTotal = newCustomThreadPool.submit(
                () -> {
                    int a = intList.stream().reduce(0, Integer::sum).intValue();
                    System.out.println("------" + a);
                    return a;
                }).get();
        System.out.println("actualTotal " + actualTotal);
    }
}
