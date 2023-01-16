package org.example;

import org.example.DirectoryInfo.DirectoryFileInfo;
import org.example.Employee.Employee;
import org.example.Employee.Helper;
import org.example.Factorial.MyFactorial;
import org.example.Quicksort.MySort;
import org.example.fibonacci.FibonacciTask;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static final int TEST_NUMBER = 123456;

    public static void main(String[] args) {
        testFactorial();
        System.out.println("--------------------------------------");
        testSort();
        System.out.println("--------------------------------------");
        testDirectoryAndFileInfo();
        System.out.println("--------------------------------------");
        testEmployees();
        System.out.println("--------------------------------------");
        testFibonacciTask();
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

    private static void testDirectoryAndFileInfo() {
        String dir = "C:\\Program Files";
        System.out.printf("Directory: %s:%n", dir);
        DirectoryFileInfo task = new DirectoryFileInfo(new File(dir));
        List<String> files = task.invoke();
        System.out.printf("Total files size = %d KB%n", task.getTotalSize() / 1024);
        System.out.printf("Total files count = %d%n", files.size());
        System.out.printf("Total directory count = %d%n", task.getDirectoryCount());
    }

    private static void testEmployees() {
        List<Employee> employees = Helper.createTestEmployees(1000L)
                .stream().filter(employee -> employee.getSalary() > 100)
                .collect(Collectors.toList());

        System.out.printf("Created: %d employees with salary > 100%n", employees.size());

        Employee employee = Objects.requireNonNull(employees.stream()
                .collect(Collectors.groupingBy(Employee::getSalary))
                .entrySet().stream().max(Map.Entry.comparingByKey())
                .orElse(null)).getValue().get(0);

        System.out.printf("Employee with max salary: %s%n", employee);
    }

    private static void testFibonacciTask() {
        long n = getRandomIntValue(10);
        ForkJoinPool pool = new ForkJoinPool();
        Long result = pool.invoke(new FibonacciTask(n));
        System.out.println("Fibonacci of " + n + " = " + result);
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