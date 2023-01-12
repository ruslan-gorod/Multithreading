package org.example.Quicksort;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MySort extends RecursiveAction {
    int[] array;
    int left;
    int right;

    public MySort(int[] array) {
        this(array, 0, array.length - 1);
    }

    private MySort(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (right - left < array.length) {
            Arrays.sort(array, left, right + 1);
        } else {
            int middle = getMiddleIndex(array, left, right);
            MySort firstPart = new MySort(array, left, middle - 1);
            MySort secondPart = new MySort(array, middle + 1, right);
            firstPart.fork();
            secondPart.compute();
            firstPart.join();
        }
    }

    int getMiddleIndex(int[] array, int left, int right) {
        int index = left - 1;
        int end = array[right];
        for (int j = left; j < right; j++) {
            if (array[j] < end) {
                index++;
                swap(array, index, j);
            }
        }
        index++;
        swap(array, index, right);
        return index;
    }

    void swap(int[] array, int first, int second) {
        int tmp = array[first];
        array[first] = array[second];
        array[second] = tmp;
    }
}
