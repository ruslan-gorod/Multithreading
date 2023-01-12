package org.example;

import org.example.Factorial.MyFactorial;
import org.example.Quicksort.MySort;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class Main {

    private static final int TEST_NUMBER = 123456;

    public static void main(String[] args) {
        testFactorial();
        System.out.println("--------------------------------------");
        testSort();
        System.out.println("--------------------------------------");
    }

    private static void testFactorial() {
        long n = getRandomIntValue(20);
        ForkJoinPool pool = new ForkJoinPool();
        Long result = pool.invoke(new MyFactorial(n));
        System.out.println("Factorial of " + n + " = " + result);
    }

    private static void testSort() {
        ForkJoinPool pool = new ForkJoinPool();
        int[] testArray = getTestArray(TEST_NUMBER * getRandomIntValue(3));
        LocalDateTime start = LocalDateTime.now();
        pool.invoke(new MySort(testArray));
        System.out.printf("Sorted array with %d elements: %d milliseconds.%n",
                testArray.length,
                Duration.between(start, LocalDateTime.now()).toMillis());
        printTenElementsFromTestArray(testArray);
    }

    private static int getRandomIntValue(int number) {
        return (int) (Math.random() * number);
    }

    private static void printTenElementsFromTestArray(int[] testArray) {
        IntStream.of(testArray)
                .distinct().skip(TEST_NUMBER / 2)
                .mapToObj(i -> "" + i)
                .limit(10)
                .forEach(System.out::println);
    }

    private static int[] getTestArray(int size) {
        int[] testArray = new int[size];
        Arrays.setAll(testArray, i -> getRandomIntValue(TEST_NUMBER));
        return testArray;
    }
}