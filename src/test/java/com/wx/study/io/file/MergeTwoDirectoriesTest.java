package com.wx.study.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public class MergeTwoDirectoriesTest {

    private MergeTwoDirectories mergeTwoDirectories;

    @BeforeEach
    void setUp() {
        File file1 = new File("");
        File file2 = new File("");
        mergeTwoDirectories = new MergeTwoDirectories(file1, file2);
    }



    @Test
    void testRecursiveTraversalFiles() {

    }
}
