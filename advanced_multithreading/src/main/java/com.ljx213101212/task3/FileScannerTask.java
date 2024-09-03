package com.ljx213101212.task3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FileScannerTask extends RecursiveTask<FolderStats> {
    private File folder;

    public FileScannerTask(File folder) {
        this.folder = folder;
    }

    @Override
    protected FolderStats compute() {
        long totalSize = 0;
        int totalFiles = 0;
        int totalFolders = 0;
        List<FileScannerTask> tasks = new ArrayList<>();

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    FileScannerTask task = new FileScannerTask(file);
                    task.fork(); // Asynchronously execute this task
                    tasks.add(task);
                    totalFolders++;
                } else {
                    totalSize += file.length();
                    totalFiles++;
                }
            }

            for (FileScannerTask task : tasks) {
                FolderStats result = task.join(); // waits for the task to complete and retrieves its result
                totalSize += result.size;
                totalFiles += result.fileCount;
                totalFolders += result.folderCount;
            }
        }

        return new FolderStats(totalSize, totalFiles, totalFolders);
    }
}