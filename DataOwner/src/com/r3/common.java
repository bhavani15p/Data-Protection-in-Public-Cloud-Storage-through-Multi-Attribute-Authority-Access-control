package com.r3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class common {
    static byte[] filetobyte(String filename) {


        File file = new File(filename);
        byte fileContent[];
        try {
            //create FileInputStream object
            FileInputStream fin = new FileInputStream(file);

            /*
             * Create byte array large enough to hold the content of the file.
             * Use File.length to determine size of the file in bytes.
             */


            fileContent = new byte[(int) file.length()];

            /*
             * To read content of the file in byte array, use
             * int read(byte[] byteArray) method of java FileInputStream class.
             *
             */
            fin.read(fileContent);

            //create string from byte array
            String strFileContent = new String(fileContent);

            System.out.println("File content : ");
            System.out.println(strFileContent);
            return fileContent;
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the file " + ioe);
        }

        return null;
    }

    static void bytetofile(byte[] dt,String Filepath) {

        String strFilePath = Filepath;

        try {
            FileOutputStream fos = new FileOutputStream(strFilePath);
            String strContent = "Write File using Java FileOutputStream example !";

            /*
             * To write byte array to a file, use
             * void write(byte[] bArray) method of Java FileOutputStream class.
             *
             * This method writes given byte array to a file.
             */

            fos.write(dt);

            /*
             * Close FileOutputStream using,
             * void close() method of Java FileOutputStream class.
             *
             */

            fos.close();

        } catch (Exception ioe) {
            System.out.println("IOException : " + ioe);
        }

    }

}
