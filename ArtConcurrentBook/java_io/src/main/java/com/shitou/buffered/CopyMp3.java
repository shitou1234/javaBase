package com.shitou.buffered;

import java.io.*;
/**
 * 分别用普通数据流和带缓冲区的数据流复制一个167M的数据文件
 * 通过用时比较两者的工作效率
 * @author Zues
 *
 */
public class CopyMp3 {
    private static File file = new File("E:\\123123123.mp4");
    private static File file_cp = new File("E:\\1_cp.mp4");

    // FileInputStream复制
    public void copy() throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileOutputStream out = new FileOutputStream(file_cp);
        byte[] buf = new byte[8192];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            out.write(buf);
        }
        in.close();
        out.close();
    }
    // BufferedStream复制
    public void copyByBuffer() throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file_cp));
        byte[] buf = new byte[8192];
        int len;
        while ((len = in.read(buf)) != -1) {
            out.write(buf);
        }
        in.close();
        out.close();
    }
    public static void main(String[] args) throws IOException {
        CopyMp3 copy=new CopyMp3();
        long time1=System.currentTimeMillis();
        copy.copy();
        long time2=System.currentTimeMillis();
        System.out.println("直接复制用时："+(time2-time1)+"毫秒");
        long time3=System.currentTimeMillis();
        copy.copyByBuffer();
        long time4=System.currentTimeMillis();

        System.out.println("缓冲区复制用时："+(time4-time3)+"毫秒");
    }
}
