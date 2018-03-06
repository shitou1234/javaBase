package com.shitou.exerciseDemo;

import java.io.*;

/**  使用文档  从一个文件复制到另一个文件
 * Created by Administrator on 2018/3/5.
 */
public class TestCopyDemo1 {
    public static void main(String[] args)throws  Exception {
        File inFile = new File("D://test//out.txt");
        File outFile = new File("D://test//outCopy.txt");
        long start = System.currentTimeMillis();

        if (!inFile.exists()){
            System.out.println("源文件不存在");
            System.exit(1);
        }
        if (!outFile.getParentFile().exists()){
            outFile.getParentFile().mkdirs();
        }
        InputStream inputStream = new FileInputStream(inFile);
        OutputStream outputStream = new FileOutputStream(outFile);
        int tmp = 0;
        byte[] data = new byte[4096];
        while((tmp = inputStream.read(data))!=-1){
            outputStream.write(data,0,tmp);
        }
        Long end = System.currentTimeMillis();
        System.out.println("花费时间"+ (end-start));
        inputStream.close();
        outputStream.close();

    }
}
