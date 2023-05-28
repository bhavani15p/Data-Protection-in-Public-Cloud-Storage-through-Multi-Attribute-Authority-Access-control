package com.r3;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server extends Thread {

    

    ServerSocket serverSocket;


    int listenport;

    ServerGUI guiinst;

    void init(int lp,ServerGUI cp)
    {
        listenport = lp;
        guiinst = cp;
        try {
            serverSocket = new ServerSocket(listenport);
        } catch (IOException e) {
            System.err.println("Could not listen on port:" +listenport);
            System.exit(1);
        }


    }
    
   

    

    public void run()
    {

        try
        {

            guiinst.writetolog("Cloud Server started...");
            while(true)
            {
                Socket clientSocket = null;

                clientSocket = serverSocket.accept();

                guiinst.writetolog(" Recieved the connection from Client ");


                HandleClient hc = new HandleClient(clientSocket,guiinst);

                hc.start();

                
                
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
        
        
    }
    
   

}
