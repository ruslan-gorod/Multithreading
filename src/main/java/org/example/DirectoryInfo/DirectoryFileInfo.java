package org.example.DirectoryInfo;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class DirectoryFileInfo extends RecursiveTask<List<String>> {
    private File path;
    private long totalSize = 0;
    private long directoryCount = 0;

    public DirectoryFileInfo(File file) {
        if (file.isDirectory() && file.canRead()) {
            this.path = file;
        }
    }

    @Override
    protected List<String> compute() {
        return readDirectoryInfo(path);
    }

    private List<String> readDirectoryInfo(File directory) {
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return Collections.emptyList();
        }
        List<String> fileList = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                directoryCount++;
                fileList.addAll(readDirectoryInfo(file));
            } else {
                String absolutePath = file.getAbsolutePath();
                fileList.add(absolutePath);
                try {
                    totalSize += Files.size(Path.of(absolutePath));
                } catch (IOException e) {
                    System.out.printf("Error read file:%s%n", absolutePath);
                    throw new RuntimeException(e);
                }
            }
        }

        return fileList;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public long getDirectoryCount() {
        return directoryCount;
    }
}