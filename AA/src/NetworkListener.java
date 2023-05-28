import java.math.BigInteger;
import java.net.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class NetworkListener extends Thread {

    int listenport;
    DatagramSocket welcomeSocket;
    GUI guiinst;
    String srvsplit;
    public static NetworkListener nwlisten;

    void init(int lport, GUI gui) {
        listenport = lport;
        guiinst = gui;

        nwlisten = this;


        try {

            welcomeSocket = new DatagramSocket(listenport);
            guiinst.writetolog("Network Listener waiting for messages");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    void sendDataReq(String sendData, InetAddress ipadd, int port) {

        try {



            byte[] data = new byte[1000];

            data = sendData.getBytes();
            InetAddress ia = ipadd;

            DatagramPacket sendPacket = new DatagramPacket(data, data.length, ia, port);
            welcomeSocket.send(sendPacket);

            System.out.println("sent data packet");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void sendkeys(InetAddress IPAddress, int port) {
        try {
            byte[] dt = common.filetobyte("file_dir/publickey.txt");

            int len = dt.length;

            byte[] key = new byte[2];
            key[0] = 1;
            key[1] = 0;

            byte[] nbyte = new byte[len + 2];

            nbyte[0] = key[0];
            nbyte[1] = key[1];

            for (int i = 0; i < dt.length; i++) {
                nbyte[i + 2] = dt[i];
            }

            System.out.println("Length:" + len);

            DatagramPacket dp = new DatagramPacket(nbyte, nbyte.length, IPAddress, port);

            welcomeSocket.send(dp);

            Thread.sleep(2000);


            byte[] mdt = common.filetobyte("file_dir/masterkey.txt");

            int mlen = mdt.length;

            byte[] mkey = new byte[2];
            mkey[0] = 1;
            mkey[1] = 1;

            byte[] mbyte = new byte[mlen + 2];

            mbyte[0] = mkey[0];
            mbyte[1] = mkey[1];

            for (int i = 0; i < mdt.length; i++) {
                mbyte[i + 2] = mdt[i];
            }

            System.out.println("Length:" + mlen);

            DatagramPacket mdp = new DatagramPacket(mbyte, mbyte.length, IPAddress, port);

            welcomeSocket.send(mdp);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {

        try {




            byte[] receiveData = new byte[5000];
            byte[] sendData = new byte[1024];



            while (true) {

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                welcomeSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                System.out.println("RECEIVED: " + sentence);
                guiinst.writetolog(sentence);

                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();


                //guiinst.writetolog("Recived Message from " + IPAddress);


                if (sentence.startsWith("TOKEN#")) {
                    String[] data = sentence.split("#");

                    if (data[1].equals("0000000000")) {

                        sendkeys(IPAddress, port);
                        String msgd = "TOKENFAIL#";
                        sendDataReq(msgd, IPAddress, port);


                    } else {

                        Database db = new Database();

                        String query = "select * from CA where cardno='" + data[1] + "'";

                        ResultSet rst = db.executeQuery(query);

                        String msg = null;

                        if (rst.next()) {

                            RSA rs = new RSA(8);

                            String cd = rs.getN() + "," + rs.getE() + "," + rs.getD();

                            String publickey = rs.getN() + "#" + rs.getE();

                            String privatekey = rs.getN() + "#" + rs.getD();

                            query = "insert into AA values('" + data[2] + "','" + data[1] + "','" + privatekey + "','" + publickey + "')";

                            db.executeUpdate(query);

                            msg = "TRESPONSE#" + cd + "#";
                            sendDataReq(msg, IPAddress, port);
                            sendkeys(IPAddress, port);
                        } else {
                            sendDataReq(msg, IPAddress, port);
                            msg = "TOKENFAIL#";
                        }

                    }

                } else if (sentence.startsWith("REQKEY#")) {

                    String[] data = sentence.split("#");

                    Database db = new Database();

                    String query = "select * from AA where username='" + data[0] + "'";

                    ResultSet rst = db.executeQuery(query);

                    String msg = null;

                    if (rst.next()) {
                        if (data[2].equals("NOCARD")) {
                            msg = "PUBLICKEY#" + rst.getString("publickey");
                        } else {
                            msg = "PRIVATEKEY#" + rst.getString("privatekey");
                        }
                    }
                    sendDataReq(msg, IPAddress, port);
                } else if (sentence.startsWith("USERREQKEY#")) {
                    byte[] dt = common.filetobyte("publickey.txt");

                    int len = dt.length;

                    byte[] key = new byte[2];
                    key[0] = 1;
                    key[1] = 0;

                    byte[] nbyte = new byte[len + 2];

                    nbyte[0] = key[0];
                    nbyte[1] = key[1];

                    for (int i = 0; i < dt.length; i++) {
                        nbyte[i + 2] = dt[i];
                    }

                    System.out.println("Length:" + len);

                    DatagramPacket dp = new DatagramPacket(nbyte, nbyte.length, IPAddress, port);

                    welcomeSocket.send(dp);

                    Thread.sleep(2000);


                    byte[] mdt = common.filetobyte("masterkey.txt");

                    int mlen = mdt.length;

                    byte[] mkey = new byte[2];
                    mkey[0] = 1;
                    mkey[1] = 1;

                    byte[] mbyte = new byte[mlen + 2];

                    mbyte[0] = mkey[0];
                    mbyte[1] = mkey[1];

                    for (int i = 0; i < mdt.length; i++) {
                        mbyte[i + 2] = mdt[i];
                    }

                    System.out.println("Length:" + mlen);

                    DatagramPacket mdp = new DatagramPacket(mbyte, mbyte.length, IPAddress, port);

                    welcomeSocket.send(mdp);



                }





            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
