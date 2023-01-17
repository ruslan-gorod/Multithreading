package org.example.MyFork;

import java.util.concurrent.RecursiveTask;

import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;

public class MyFork extends RecursiveTask<Double> {

    double[] array;

    public MyFork(double[] array) {
        this.array = array;
    }

    @Override
    protected Double compute() {
        if (1 >= array.length) {
            return stream(array).map(v -> v * v).sum();
        } else {
            int middle = (array.length) / 2;

            MyFork firstPart = new MyFork(copyOfRange(array, 0, middle));
            firstPart.fork();
            MyFork secondPart = new MyFork(copyOfRange(array, middle, array.length));

            double second = secondPart.compute();
            return firstPart.join() + second;
        }
    }
}
