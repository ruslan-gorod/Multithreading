package org.example.Employee;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Helper {
    public static List<Employee> createTestEmployees(long count) {
        return LongStream.range(0, count)
                .mapToObj(i -> new Employee(i, "Employee-" + i,
                        i % 2 == 0, Math.random() * 1000))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public static List<Employee> getHiredEmployees() {
        return hiredEmployees()
                .thenApplyAsync(employees -> employees.parallelStream()
                        .map(Helper::add10PercentToSalary)
                        .collect(Collectors.toList()))
                .thenApplyAsync(Helper::extractEmployees)
                .join();
    }

    private static CompletableFuture<List<Employee>> hiredEmployees() {
        return CompletableFuture.supplyAsync(Helper::getHiredEmployees);
    }

    private static CompletableFuture<Employee> add10PercentToSalary(Employee employee) {
        return CompletableFuture.supplyAsync(() -> {
            employee.setSalary(Math.random() * 2000);
            return employee;
        });
    }

    private static List<Employee> extractEmployees(List<CompletableFuture<Employee>> futures) {
        return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }
}
