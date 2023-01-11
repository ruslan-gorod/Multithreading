package org.example;

import org.example.Factorial.MyFactorial;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        long n = 5;
        ForkJoinPool pool = new ForkJoinPool();
        Long result = pool.invoke(new MyFactorial(n));
        System.out.println("Factorial of " + n + " = " + result);
    }
}