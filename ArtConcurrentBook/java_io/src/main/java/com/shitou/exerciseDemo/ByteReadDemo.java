package com.shitou.exerciseDemo;

import org.junit.Test;

import java.io.*;

/** 本文对java IO流的读取文件的方式进行比较全面的总结，一个是基本的读取方式，另一个是高效的读取方式。
 * http://blog.csdn.net/wenzhi20102321/article/details/52583551
 * Created by Administrator on 2018/3/6.
 */
public class ByteReadDemo {

    public static final String path = "D://test//out.txt";

    @Test
    public void readByInputStream (){
        File file = new File(path);
        if (file.isFile()){
            try {
                InputStream ins = new FileInputStream(file);
                byte[] byteArray = new byte[4096];
                int len = 0;
                StringBuffer buffer = new StringBuffer();
                while ((len = ins.read(byteArray))!=-1){
                    buffer.append(new String(byteArray,0,len,"gbk"));
                }
                ins.close();
                System.out.println(buffer.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

/**   java中BufferedInputStream类相比InputStream类，提高了输入效率，增加了输入缓冲区的功能。

    InputStream流是指将字节序列从外设或外存传递到应用程序的流。

    BufferedInputStream流是指读取数据时，数据首先保存进入缓冲区，其后的操作直接在缓冲区中完成。
 */
    @Test
    public void readByBuffered(){
        File file = new File(path);
        InputStream ins = null;
        BufferedInputStream bis = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StringBuffer buffer = new StringBuffer();
        try {
            ins = new FileInputStream(file);
            bis = new BufferedInputStream(ins);
            // 获得缓存读取流开始的位置????
//            int len = bis.read();
            int len = 0;
            System.out.println(len);
            byte[] bytes = new byte[1024];
            while ((len = bis.read(bytes))!=-1){
                bos.write(bytes,0,len);
                buffer.append(new String(bytes,0,len,"gbk"));
            }
            byte[] byteArray = bos.toByteArray();
//            System.out.println(new String(byteArray,"gbk"));
            System.out.println(buffer.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ins.close();
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
