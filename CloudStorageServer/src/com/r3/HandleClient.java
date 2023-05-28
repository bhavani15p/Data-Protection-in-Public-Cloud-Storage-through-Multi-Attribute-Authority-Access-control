package com.r3;
import java.net.*;
import java.io.*;
import java.sql.ResultSet;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import java.util.Iterator;
import java.util.Vector;

public class HandleClient extends Thread {

    Socket clientSocket = null;
    String nodeid;
    ServerGUI guiinst;
    ObjectInputStream in;
    ObjectOutputStream out;

    HandleClient(Socket sc, ServerGUI g) {
        clientSocket = sc;

        guiinst = g;

    }

    void sendMessage(Message m) {


        try {
            out.writeObject(m);
            out.flush();

            //guiinst.writetolog("Sending the  message");

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

  

    public void run() {
        try {


            //guiinst.writetolog("Client handler started");

            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            System.out.println(" Got input stream");



            while (true) {

                System.out.println(" Waiting for messages");

                Message m = (Message) in.readObject();


                switch (m.type) {
                    case 1: // File upload Request
                    {
                        FileUPLOAD fl = (FileUPLOAD) m;

                        System.out.println("Got the File upload message");

                        guiinst.writetolog("Recived File upload message....");

                        String fname = common.getinstance().filepath + fl.filename;


                        guiinst.writetolog("creating a file " + fname + "....");


                        FileOutputStream fos = new FileOutputStream(fname);

                        fos.write(fl.filecontent);

                        guiinst.interfacer.uploadFileToCloud(fname, fl.filename);
                       // guiinst.interfacer.uploadFile(fl.filename);
                        
                        File file = new File(fname);
                        
                        file.delete();



                        break;
                    }
                    case 2: // File Download Request
                    {

                        FileDownload fd = (FileDownload) m;

                        guiinst.writetolog("Recived File download message....");

                        System.out.println("Recived File download message....");

                        guiinst.writetolog("File name =" + fd.filename);
                        System.out.println("File name =" + fd.filename);
                        
                      

                        guiinst.interfacer.downloadFileFromCloud(common.getinstance().filepath, fd.filename);
                        
                        Thread.sleep(4000);
                        
                        try {
                            FileDownloadRes fdres = new FileDownloadRes();
                            fdres.filename = fd.filename;
                            String fulfile = common.getinstance().filepath + fd.filename;
                            File fn = new File(fulfile);

                            FileInputStream fin = new FileInputStream(fn);
                            fdres.filecontents = new byte[(int) fn.length()];
                            fin.read(fdres.filecontents);
                            fin.close();

                            System.out.println("Filename:"+fdres.filename);
                            String data = new String(fdres.filecontents);
                             System.out.println("file content:"+data);
                             System.out.println("Type:"+fdres.type);
                            sendMessage(fdres);


                        } catch (Exception e) {
                            e.printStackTrace();

                            FileDownloadRes fdres = new FileDownloadRes();
                            fdres.error = -1;

                            sendMessage(fdres);

                        }


                        break;
                    }

                }



            }

        } catch (Exception e) {
            guiinst.writetolog("!!! Detected error ");

            e.printStackTrace();





        }



    }
}
