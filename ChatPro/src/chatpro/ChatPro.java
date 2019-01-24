/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatpro;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aya
 */

public class ChatPro {

    ServerSocket myServerSocket;
    Socket s;

     public ChatPro() {

        try {
            myServerSocket = new ServerSocket(5005);
            while (true) {
                s = myServerSocket.accept();
                new ChatHandler(s);
            }
        } catch (IOException ex) {
            Logger.getLogger(ChatPro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    }

    public static void main(String[] args) {
        // TODO code application logic here
        new ChatPro();

    }

}



class ChatHandler extends Thread {

    DataInputStream dis;
    PrintStream ps;
    static Vector<ChatHandler> clientsvector = new Vector<ChatHandler>();

    public ChatHandler(Socket cs) {
        try {
            dis = new DataInputStream(cs.getInputStream());
            ps = new PrintStream(cs.getOutputStream());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        clientsvector.add(this);
        start();
    }

    @Override
    public void run() {
        //super.run(); //To change body of generated methods, choose Tools | Templates.
        while (true) {
            String msg = null;
            try {
                msg = dis.readLine();
                sendMsgToAll(msg);
            } catch (IOException ex) {
                Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void sendMsgToAll(String msg) {
        for (ChatHandler ch : clientsvector) {
            ch.ps.println(msg);
        }
    }

}
