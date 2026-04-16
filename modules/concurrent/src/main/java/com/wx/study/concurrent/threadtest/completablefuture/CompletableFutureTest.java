package com.wx.study.concurrent.threadtest.completablefuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

/**
 * https://www.baeldung.com/java-completablefuture
 * <p>
 * <p>
 * thenAccept()可以返回计算结果，也可以不返回结果
 * thenRun（）啥都不返回，也不处理上面的值
 * <p>
 * thenApply(s-> s + 1)我们可以使用这个方法来处理上一次调用的结果。
 * <p>
 * thenCompose()使用前一阶段作为参数。
 * 它将展平并直接返回带有结果的Future，
 *
 * Async后缀的方法使用调用线程运行下一个执行阶段,
 * 带有Executor参数的Async方法使用传递的Executor运行一个步骤
 *
 * @author wxli
 * @date 2021/7/29 17:11
 */
public class CompletableFutureTest {

    public Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }


    @Test
    public void test() throws InterruptedException, ExecutionException {
        Future<String> stringFuture = calculateAsync();
        String s = stringFuture.get();
        System.out.println(s);
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future
                = CompletableFuture.supplyAsync(() -> "Hello");

        assertEquals("Hello", future.get());
    }

    /**
     * 我们可以使用静态的CompletedFuture方法和一个代表这个计算结果的参数。
     * 因此，Future的get方法会阻塞，可以设置超时等待。
     */
    @Test
    public void test3() throws ExecutionException, InterruptedException {

        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> future = completableFuture
                .thenApply(s -> {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s + " World";
                });
        String s = future.get();
        System.out.println(s);
        assertEquals("Hello World", s);
    }

    /**
     * thenAccept()方法接收消费者并将其传递所述计算的结果。
     * 然后最后的future.get()调用返回一个Void类型的实例：
     */
    @Test
    public void test4() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<Void> future = completableFuture
                .thenAccept(s -> System.out.println("Computation returned: " + s));

        future.get();
    }

    /**
     * 如果我们既不需要计算的值，也不想在链的末尾返回某个值，
     * 那么我们可以将Runnable lambda传递给thenRun方法。
     */
    @Test
    public void test5() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<Void> future = completableFuture
                .thenRun(() -> System.out.println("Computation finished."));

    }

    /**
     * thenCompose方法按顺序链接两个Future。
     * <p>
     * 两种方法都接收一个函数并将其应用于计算结果，
     * 但thenCompose ( flatMap ) 方法接收一个函数，
     * 该函数返回另一个相同类型的对象。
     */
    @Test
    public void test6() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));

        assertEquals("Hello World", completableFuture.get());


    }


    /**
     * 如果我们要执行两个独立的期货，做自己的成绩的东西，
     * 我们可以使用thenCombine接受的方法的未来和功能有两个参数来处理这两种结果：
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test7() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(
                        () -> " World"), (s1, s2) -> s1 + s2);

        assertEquals("Hello World", completableFuture.get());
    }

    /**
     * 对两个Futures的结果做一些事情，但不需要将任何结果值传递到Future链中
     */
    @Test
    public void test8() throws ExecutionException, InterruptedException {
        CompletableFuture future = CompletableFuture.supplyAsync(() -> "Hello")
                .thenAcceptBoth(CompletableFuture.supplyAsync(() -> " World"),
                        (s1, s2) -> System.out.println(s1 + s2));
    }

    /**
     * 抛异常之后可以使用handle 设置默认值
     */
    @Test
    public void test9() throws ExecutionException, InterruptedException {
        String name = null;
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> {
            if (name == null) {
                throw new RuntimeException("Computation error!");
            }
            return "Hello, " + name;
        }).handle((s, t) -> s != null ? s : "Hello, Stranger!");

        System.out.println(completableFuture.get());
    }

    /**
     *
     */
    @Test
    public void test10() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.completeExceptionally(
                new RuntimeException("Calculation failed!"));

        completableFuture.get(); // ExecutionException
    }

}
