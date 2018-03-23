package com.shitou.converTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2018/3/5.
 */
public class InputStreamReaderTest {
    public static void testReadFile(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(fis);
        int len = 0;
        while ((len=reader.read())!= -1){
            System.out.print((char)len);
        }
        fis.close();
        reader.close();
    }

    public static void testReadFileEncode(File file,String encode) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(fis,encode);
        int len = 0;
        while ((len=reader.read())!= -1){
            System.out.print((char)len);
        }
        fis.close();
        reader.close();
    }

    public static void main(String[] args) throws Exception {
        File file = new File("D://test//out.txt");
//        testReadFile(file);
        testReadFileEncode(file,"gbk");
//        testReadFileEncode(file,"utf-8");


    }
}
