package org.example.Factorial;

import java.util.concurrent.RecursiveTask;

public class MyFactorial extends RecursiveTask<Long> {

    private final long n;

    public MyFactorial(long n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= 1) {
            return n;
        }
        MyFactorial f1 = new MyFactorial(n - 1);
        f1.fork();
        return f1.join() * n;
    }
}
