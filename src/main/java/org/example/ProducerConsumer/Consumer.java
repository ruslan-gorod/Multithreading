package org.example.ProducerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Consumer implements Runnable {
    BlockingQueue<Integer> queue;
    Semaphore semaphoreConsumer;
    Semaphore semaphoreProducer;
    int count;
    boolean usingSemaphore;
    int taken = -1;

    public Consumer(BlockingQueue<Integer> queue, int count) {
        this.queue = queue;
        this.count = count;
        this.usingSemaphore = false;
    }

    public Consumer(Semaphore semaphoreConsumer, Semaphore semaphoreProducer, int count) {
        this.semaphoreConsumer = semaphoreConsumer;
        this.semaphoreProducer = semaphoreProducer;
        this.count = count;
        usingSemaphore = true;
    }

    @Override
    public void run() {
        if (usingSemaphore) {
            runWithSemaphore();
        } else {
            runWithoutSemaphore();
        }
    }

    private void runWithSemaphore() {
        int i = 1;
        while (i <= count) {
            try {
                semaphoreConsumer.acquire();
                System.out.printf("Consumed with Semaphore: _ %d%n", i);
                semaphoreProducer.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    private void runWithoutSemaphore() {
        while (taken != count) {
            try {
                taken = queue.take();
                System.out.printf("Consumed %d%n", taken);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
