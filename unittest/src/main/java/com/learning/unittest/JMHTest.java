package com.learning.unittest;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName JMHTest
 * @Description TODO
 * @Author hufei
 * @Date 2020/6/18 16:21
 * @Version 1.0
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3)
@Measurement(iterations = 3, time = 5)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.SECONDS)
public class JMHTest {

    static List<String> data = buidData(20);

    @Benchmark
    public static void testStringKey() {

    }

    @Benchmark
    public static void testObjectKey() {

    }

    private static List<String> buidData(int count) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JMHTest.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }

}
