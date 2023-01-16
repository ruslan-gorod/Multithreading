package org.example.ProducerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Producer implements Runnable {
    BlockingQueue<Integer> queue;
    Semaphore semaphoreProducer;
    Semaphore semaphoreConsumer;
    int count;
    boolean usingSemaphore;

    public Producer(BlockingQueue<Integer> queue, int count) {
        this.queue = queue;
        this.count = count;
        this.usingSemaphore = false;
    }

    public Producer(Semaphore semaphoreProducer, Semaphore semaphoreConsumer, int count) {
        this.semaphoreProducer = semaphoreProducer;
        this.semaphoreConsumer = semaphoreConsumer;
        this.count = count;
        this.usingSemaphore = true;
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
                semaphoreProducer.acquire();
                System.out.printf("Produced with Semaphore: %d%n", i);
                semaphoreConsumer.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    private void runWithoutSemaphore() {
        for (int i = 1; i <= 5; i++) {
            try {
                queue.put(i);
                System.out.printf("Produced %d%n", i);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}