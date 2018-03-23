package com.shitou.out;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2018/3/5.
 */
public class OutStreamTest {
    @Test
    public void writeTxt() throws Exception{
        File file = new File("D:"+File.separator+"test"+File.separator+"out.txt");

        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        OutputStream outputStream = new FileOutputStream(file);
        String data = "hello world ! \r\n  hello world ! \n hello world !\n hello world !\n";
        outputStream.write(data.getBytes());
        outputStream.close();

    }

    @Test
    public void writeTxtAppend() throws Exception{
        File file = new File("D:"+File.separator+"test"+File.separator+"out.txt");

        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        OutputStream outputStream = new FileOutputStream(file);
        String data = "hello world ! \r\n  hello world ! \n hello world !\n hello world !\n";
        outputStream.write(data.getBytes(),0,5);
        outputStream.close();

    }
}
