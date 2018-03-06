package com.shitou.fileTest;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/5.
 */
public class FileTest {
    // 在windows操作系统之中，使用“\”作为路径分隔符，而在linux系统下使用“/”作为路径的分隔符
    public static final String separator = "\\";

    //    public static final String separator = "/";
    @Test
    public void createFile() {
        File file = new File("demo.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("创建新文件");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file.delete();
            System.out.println("删除存在文件");
        }
    }

    @Test
    public void createFileList() {
        File file = new File("D:" + File.separator + "test" + File.separator + "file" + File.separator + "aaa.txt");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {

            try {
                file.createNewFile();
                System.out.println("创建新文件");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file.delete();
            System.out.println("删除存在文件");
        }
    }

    @Test
    public void getFileInfo(){
        File file = new File("D://test1.txt");
        try {
            file.createNewFile();
            String name = file.getName();
            System.out.println(name);
            File parentFile = file.getParentFile();
            System.out.println(parentFile.getPath());
            System.out.println(file.isFile());
            System.out.println(parentFile.isFile());
            System.out.println(file.lastModified());
            System.out.println(file.length());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(sdf.format(Long.parseLong(file.lastModified()+"")));

            String date = sdf.format(new Date(file.lastModified()));
            System.out.println(date);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
