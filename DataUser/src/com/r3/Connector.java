package com.r3;

import cpabe.Cpabe;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;

public class Connector extends Thread {

    String serverip = "127.0.0.1";
    int port = 5000;
    Socket socket = null;
    GUI guiinst;
    ObjectOutputStream out;
    ObjectInputStream in;
    String nodeid;
    String Path = "file_dir/"; //in File upload change path here

    public Connector(GUI g) {
        guiinst = g;

    }

    public static String readFileAsString(String filePath)
            throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }

    void sendMessage(Message msg) {


        try {
            out.writeObject(msg);
            out.flush();

            System.out.println("Sending the message:");

        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    void handleMessage(Message msg) {

        switch (msg.type) {

            case 4: {
                FileDownloadRes fdres = (FileDownloadRes) msg;
                if (fdres.error == -1) {
                    System.out.println("File Download failure");
                } else {
                    try {
                        /* String contents = new String(fdres.filecontents);
                     
                         guiinst.writetolog("Contents:"+contents);*/
                        String fn = Path + fdres.filename;
                        File file = new File(fn);

                        file.delete();

                        common.bytetofile(fdres.filecontents, fn);
                        guiinst.writetolog("File Download success");
                        // FileAppender.AppendtoFile(fn, contents);

                        guiinst.writetolog("File Downloaded To:" + file.getAbsolutePath());

                        Cpabe abe = new Cpabe();

                        abe.keygen(guiinst.nwlisten.pubfile, guiinst.nwlisten.prvfile, guiinst.nwlisten.mskfile, common.decpolicy);

                        System.out.println("// start of decryption");
                        abe.dec(guiinst.nwlisten.pubfile, guiinst.nwlisten.prvfile, fn, fn + ".dec");
                        System.out.println("// End of decryption");
                        String data = readFileAsString(fn + ".dec");
                        guiinst.writetoeditor(data);
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                break;
            }




        }


    }

    public void run() {
        try {

            socket = new Socket(serverip, port);

            guiinst.writetolog(" connected to the Cloud Interface ");

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());



            while (true) {
                Message m = (Message) in.readObject();

                handleMessage(m);





            }





        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
