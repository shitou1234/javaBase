package com.shitou.readTest;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Created by Administrator on 2018/3/5.
 */
public class ReadTest {
    @Test
    public void testRead() throws Exception {
        File file = new File("D://test//out.txt");
        if (file.exists()){
            Reader in = new FileReader(file);
            char[] data = new char[1024];
            int len = in.read(data);
            in.close();
            System.out.println("读取数据内容：【" + new String(data, 0, len) + "】");
        }
    }
}
