package org.example.fibonacci;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertEquals;

public class FibonacciTaskTest {

    @Test
    public void testCompute() {
        LocalDateTime start = LocalDateTime.now();
        assertEquals(1134903170L, new ForkJoinPool().invoke(new FibonacciTask(45)).longValue());
        System.out.printf("FibonacciTask: %d seconds.%n",
                Duration.between(start, LocalDateTime.now()).toSeconds());
    }
}