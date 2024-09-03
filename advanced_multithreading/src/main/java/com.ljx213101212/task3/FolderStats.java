package com.ljx213101212.task3;

public class FolderStats {
    long size;  // Total size of files in bytes
    int fileCount;  // Total number of files
    int folderCount;  // Total number of folders

    public FolderStats(long size, int fileCount, int folderCount) {
        this.size = size;
        this.fileCount = fileCount;
        this.folderCount = folderCount;
    }

    // Method to add two FolderStats objects together
    public void add(FolderStats other) {
        this.size += other.size;
        this.fileCount += other.fileCount;
        this.folderCount += other.folderCount;
    }

    public String formatSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return String.format("%.1f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }
}
