package com.wx.study.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class MoveFileTest {
    File file1;
    File file2;

    @BeforeEach
    void setup() throws IOException {
        file1 = new File("C:\\Users\\y'y\\Desktop\\d1");
        file2 = new File("C:\\Users\\y'y\\Desktop\\d2");
    }


    @Test
    void testMoveDir() {
        String pathname = file2.getPath();
        System.out.println(pathname);
        boolean success = file1.renameTo(new File(pathname));
        Assertions.assertTrue(success);
    }

    @Test
    void testMoveFile() {

    }
}
