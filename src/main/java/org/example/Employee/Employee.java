package org.example.Employee;

public class Employee {

    Long id;
    String name;
    boolean isHired;
    double salary;

    public Employee(Long id, String name, boolean isHired, double salary) {
        this.id = id;
        this.name = name;
        this.isHired = isHired;
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String toString() {
        return String.format("Name: %s, id: %s, salary: %,.2f", name, id, salary);
    }
}