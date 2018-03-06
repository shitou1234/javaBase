package com.shitou.writeTest;

import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

/**
 * Created by Administrator on 2018/3/5.
 */
public class WriterTest {

    @Test
    public void testWriter() throws Exception{
        File file = new File("D://test//out.txt");
        Writer writer = new FileWriter(file);
        String data = "hello world ....";
        writer.write(data);
        writer.close();
    }

}
