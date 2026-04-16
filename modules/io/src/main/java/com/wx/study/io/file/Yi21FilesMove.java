package com.wx.study.io.file;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Yi21FilesMove {

    public static void main(String[] args) {
        checkTmp();
        Path sDir = Paths.get("C:\\Users\\y'y\\Desktop\\tmp_dir", "Yi21FilesMoveOrigin");
        Path tDir = Paths.get("C:\\Users\\y'y\\Desktop\\tmp_dir", "Yi21FilesMoveTarget");
        try {
            if(!Files.exists(sDir)) {
                Files.createDirectory(sDir);
            }
            tDir = Files.move(sDir, tDir, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("移動目錄成功: " + tDir);
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    private static void checkTmp() {
        File f = new File("C:\\Users\\y'y\\Desktop\\tmp_dir");
        if (!f.exists() && !f.mkdir()) {
            throw new RuntimeException("創建臨時目錄失敗");
        } else if(!f.isDirectory()) {
            throw new RuntimeException("臨時目錄路徑已被佔用, 且不爲目錄");
        }
    }

}