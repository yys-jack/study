package com.wx.study.io.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MoveFileTest {
    Path path1;
    Path path2;

    @BeforeEach
    void setup() throws IOException {
        // 使用临时目录进行测试
        Path tempDir = Files.createTempDirectory("moveTest");
        path1 = tempDir.resolve("d1");
        path2 = tempDir.resolve("d2");

        // 创建测试目录
        Files.createDirectory(path1);
    }


    @Test
    void testMoveDir() throws IOException {
        // 验证源目录存在
        Assertions.assertTrue(Files.exists(path1));

        // 执行移动操作
        Path targetPath = Files.move(path1, path2);

        // 验证移动后源目录不存在，目标目录存在
        Assertions.assertFalse(Files.exists(path1));
        Assertions.assertTrue(Files.exists(targetPath));
    }

    @Test
    void testMoveFile() throws IOException {
        // 创建一个测试文件
        Path file = path1.resolve("test.txt");
        Files.write(file, "test content".getBytes());

        // 移动文件
        Path targetFile = path1.resolve("test_moved.txt");
        Files.move(file, targetFile);

        // 验证移动结果
        Assertions.assertFalse(Files.exists(file));
        Assertions.assertTrue(Files.exists(targetFile));
    }
}
