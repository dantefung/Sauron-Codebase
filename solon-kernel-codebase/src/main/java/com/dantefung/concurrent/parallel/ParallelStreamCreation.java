package com.dantefung.concurrent.parallel;
 
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * https://note.youdao.com/web/#/file/9c8566e5ff111012d51233a076dd0c03/markdown/WEB60bd8ae12d684445be4fa178d48a3c87/
 * 事实上，这段代码在 ForkJoinPool 中创建了一个并行流，在线程内部再次使用 ForkJoinPool 区域的公共池中的线程。
 *
 * 因此，如果您正在运行多个并行流，那不要使用这个 Steam API 的并行方法，因为这可能会减慢其他流的速度，从而用更多的时间给出结果。
 *
 * 在这段程序中，我们将线程池计数设为 5，当然你可以根据你的 CPU 配置进行更改。如果你有更多的任务，那么你可以根据其他任务进行微调。
 *
 * 如果你只有一个并行流，那么你可以使用一个固定线程个数的线程池。
 *
 * 如果上述都不能满足，请等待 Java 的更新，并行流可以将 ForkJoinPool 作为输入来限制并行进程的数量
 */
public class ParallelStreamCreation {
    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(10, 20, 30, 40, 50);
        Stream<Integer> parallelStream = intList.parallelStream();
        parallelStream.forEach(value -> System.out.println(value));
    }
}
