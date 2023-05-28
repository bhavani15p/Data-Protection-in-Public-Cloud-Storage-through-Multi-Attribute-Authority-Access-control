package com.r3;

import cpabe.Cpabe;
import java.math.BigInteger;
import java.net.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

public class NetworkListener extends Thread {

    int listenport;
    DatagramSocket welcomeSocket;
    GUI guiinst;
    String pubfile = "file_dir/publickey.txt";
    String mskfile = "file_dir/masterkey.txt";
    String prvfile = "file_dir/privatekey.txt";
    Cpabe ABE;
    String srvsplit;
    String cardno = null;
    String keys;
    boolean mode = false;// true - write false- read
    public static NetworkListener nwlisten;
    
    String N,E,D;

    void init(int lport, GUI gui) {
        listenport = lport;
        guiinst = gui;

        nwlisten = this;
          ABE = new Cpabe();
    /*   try {
          
           System.out.println("//start to setup");
           ABE.setup(pubfile, mskfile);
           System.out.println("//end to setup");

            String cont = Connector.readFileAsString(nwlisten.pubfile);

            FileUPLOAD fobj = new FileUPLOAD();

            fobj.filename = "pub_key";

            fobj.filecontent = cont.getBytes();

            guiinst.connect.sendMessage(fobj);

            cont = Connector.readFileAsString(nwlisten.mskfile);

            fobj = new FileUPLOAD();

            fobj.filename = "master_key";

            fobj.filecontent = cont.getBytes();

            guiinst.connect.sendMessage(fobj);

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        try {

            welcomeSocket = new DatagramSocket(listenport);
            guiinst.writetolog("Network Listener waiting for messages");


        } catch (Exception e) {
            e.printStackTrace();
        }





    }

    void sendDataReq(byte[] sendData, InetAddress ipadd, int port) {

        try {


            InetAddress ia = ipadd;

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ia, port);
            welcomeSocket.send(sendPacket);

            System.out.println("sent data packet");


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public void run() {

        try {




            byte[] receiveData = null;
            byte[] sendData = new byte[1024];



            while (true) {
                receiveData = new byte[5000];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                welcomeSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                System.out.println("RECEIVED: " + sentence);
                guiinst.writetolog(sentence);

                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                
                 if (receiveData[0] == 1 && receiveData[1] == 0) {
                    guiinst.writetolog("public key recieved");

                    byte[] tmp = new byte[receiveData.length - 2];
                    if (receiveData[0] == 1 && receiveData[1] == 0) {
                        for (int i = 2; i < receiveData.length; i++) {
                            tmp[i - 2] = receiveData[i];
                        }
                    }
                    
                    common.bytetofile(tmp,"file_dir/publickey.txt");

                } else if (receiveData[0] == 1 && receiveData[1] == 1) {
                    guiinst.writetolog("master key recieved");

                    byte[] tmp = new byte[receiveData.length - 2];
                    if (receiveData[0] == 1 && receiveData[1] == 1) {
                        for (int i = 2; i < receiveData.length; i++) {
                            tmp[i - 2] = receiveData[i];
                        }
                    }
                    
                    common.bytetofile(tmp,"file_dir/masterkey.txt");
                    
                } 

                //guiinst.writetolog("Recived Message from " + IPAddress);

                if (sentence.startsWith("UIDRESPONSE#")) {
                    String dt[] = sentence.split("#");

                    guiinst.cardno.setText(dt[1]);
                } else if (sentence.startsWith("UIDFAIL#")) {
                } else if (sentence.startsWith("PUBLICKEY#")) {
                    String dt[] = sentence.split("#");
                   
                    keys="N:"+dt[1]+ "E:"+dt[2] +"D:0000";
                    mode=false;
                } else if (sentence.startsWith("PRIVATEKEY#")) {
                    String dt[] = sentence.split("#");
                 
                     keys="N:"+dt[1]+ "E:0000 D:"+dt[2];
                    mode=true;
                } else if (sentence.startsWith("TRESPONSE#")){
                    String dt[] = sentence.split("#");
                     String k[] = dt[1].split(",");
                    N=k[0].trim();
                    E=k[1].trim();
                    D=k[2].trim();
                    
                }





            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
