package com.wx.study.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MergeTwoDirectories {
    private File dir1;
    private File dir2;

    public MergeTwoDirectories(File dir1, File dir2) {
        this.dir1 = dir1;
        this.dir2 = dir2;
    }



    private void recursiveTraversalFiles(File file) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                recursiveTraversalFiles(file1);
            }
        } else {
            String path = file.getPath().replace("d2", "d1");
            Path path1 = Paths.get(path);
            Path path2 = Paths.get(file.getPath());
            Files.move(path1, path2);
        }
    }
}
