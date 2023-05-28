import java.math.BigInteger;
import java.net.*;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;


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

            DatagramPacket sendPacket = new DatagramPacket(data, data.length, ipadd, port);
            welcomeSocket.send(sendPacket);

            System.out.println("sent data packet");


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
                guiinst.writetolog(sentence.trim());

                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();



                //guiinst.writetolog("Recived Message from " + IPAddress);


                if (sentence.startsWith("UID#")) {
                    String[] data = sentence.split("#");

                    Database db = new Database();

                    String query = "select * from CA where uid='" + data[1] + "'";

                    ResultSet rst = db.executeQuery(query);

                    String resp = null;

                    if (rst.next()) {
                        String cd = rst.getString("cardno");
                        resp = "UIDRESPONSE#"+cd.trim() + "#";
                    } else {
                        resp = "UIDFAIL#";
                    }

                   System.out.println("Msg:"+resp);

                    sendDataReq(resp, IPAddress, port);


                }





            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
