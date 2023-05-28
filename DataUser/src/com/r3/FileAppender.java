package com.r3;

import java.io.*;

public class FileAppender {


    public static void AppendtoFile(String filename, String content)
    {
         try {
            PrintStream out =
                new PrintStream(new AppendFileStream(filename));
            String NEWLINE = System.getProperty("line.separator");
            out.print(content+NEWLINE);
            out.close();
         }
         catch(Exception e) {
            System.out.println(e.toString());
         }



    }


}
class AppendFileStream extends OutputStream {
   RandomAccessFile fd;
   public AppendFileStream(String file) throws IOException {
     fd = new RandomAccessFile(file,"rw");
     fd.seek(fd.length());
     }
   public void close() throws IOException {
     fd.close();
     }
   public void write(byte[] b) throws IOException {
     fd.write(b);
     }
   public void write(byte[] b,int off,int len) throws IOException {
     fd.write(b,off,len);
     }
   public void write(int b) throws IOException {
     fd.write(b);
     }
}