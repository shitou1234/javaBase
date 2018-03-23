package com.shitou.input;

import org.junit.Test;

import java.io.*;

/**
 * Created by Administrator on 2018/3/6.
 */
public class ObjectInputStreamTest {
    @Test
    public void testObject (){
        // TODO自动生成的方法存根
        ObjectOutputStream objectwriter=null;
        ObjectInputStream objectreader=null;

        try {
            objectwriter=new ObjectOutputStream(new FileOutputStream("D://test//student.txt"));
            objectwriter.writeObject(new Student("gg", 22));
            objectwriter.writeObject(new Student("tt", 18));
            objectwriter.writeObject(new Student("rr", 17));
            objectreader=new ObjectInputStream(new FileInputStream("D://test//student.txt"));
            for (int i = 0; i < 3; i++) {
                System.out.println(objectreader.readObject());
            }
        } catch (Exception e) {
            // TODO自动生成的 catch 块
            e.printStackTrace();
        }finally{
            try {
                objectreader.close();
                objectwriter.close();
            } catch (IOException e) {
                // TODO自动生成的 catch 块
                e.printStackTrace();
            }

        }

    }

}

