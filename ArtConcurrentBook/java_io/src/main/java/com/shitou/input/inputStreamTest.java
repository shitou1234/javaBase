package com.shitou.input;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/3/5.
 */
public class inputStreamTest {
    @Test
    public void inputTest() throws Exception{
        File file = new File("D://test//out.txt");
        if (file.exists()){
            InputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[1024];
            int len = inputStream.read(data);
            inputStream.close();
            System.out.println("读取到的数据是：【"+ new String(data,0,len,"gbk")+"】");
        }else {
            System.out.println("有问题");
        }
    }

    @Test
    public void inputTest2() throws Exception{
        File file = new File("D://test//out.txt");
        if (file.exists()){
            InputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[102400];
            int foot = 0;
            int temp =0;
            while((temp=inputStream.read())!=-1){
                data[foot++] = (byte) (temp);
            }
            inputStream.close();
            System.out.println("读取到的数据是：【"+ new String(data,0,foot)+"】");
        }else {
            System.out.println("有问题");
        }
    }

    @Test
    public void inputTest3() throws Exception{
        File file = new File("D://test//out.txt");
        InputStream ins = new FileInputStream(file);
        int temp = 0;
        while ((temp = ins.read())!=-1){
            System.out.println(temp);
        }
        ins.close();
    }

}
