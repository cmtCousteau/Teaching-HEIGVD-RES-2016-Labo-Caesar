/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caesarclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Auteurs : Lucien / Marco.
 */
public class CaesarClient {

    static final Logger LOG = Logger.getLogger(CaesarClient.class.getName());
    final static int BUFFER_SIZE = 1024;
    
    
    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    
    -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public static String caesarDeconversion(String str, int key) {

        String strToReturn = "";
        int cTemp;

        for (char c : str.toCharArray()) {
            cTemp = (c - key);
            strToReturn += (char) cTemp;
        }
        return strToReturn;
    }
    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    
    -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    public static String caesarConversion(String str, int key) {

        String strToReturn = "";
        int cTemp;

        for (char c : str.toCharArray()) {
            cTemp = (c + key);
            strToReturn += (char) cTemp;
        }
        return strToReturn;
    }

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    
    -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    private static String userEntrance(String message) {
        Scanner saisieUtilisateur = new Scanner(System.in);

        LOG.info(message);
        String str = saisieUtilisateur.nextLine();

        return str;

    }

    /*-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    Establishe connexion with the server on the specified adress/port.
    -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=*/
    private static Socket connexion(String host, int port) {
        Socket clientSocket = null;

        try {
            clientSocket = new Socket(host, port);
        } catch (IOException ex) {
            Logger.getLogger(CaesarClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return clientSocket;
    }

    private static void closeConnexion(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(CaesarClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void sendMessage(String str, Socket socket, BufferedReader in, PrintWriter out) {

        int key = 0;
        String strKey = "";
     
        // Wait for the server key;
        try {
            strKey = in.readLine();
            LOG.info("Key : " + strKey);
            key = Integer.parseInt(strKey);
            
        } catch (IOException ex) {
            Logger.getLogger(CaesarClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Convert and send the message to the server.
        LOG.info("Message a envoyé : " + str );
        str = caesarConversion(str, key);
        out.println(str);
        out.flush();
        LOG.info("Message envoyé");
        // Wait server response.
        try {
            System.out.println("Server message : " + in.readLine());
        } catch (IOException ex) {
            Logger.getLogger(CaesarClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Socket socket = null;
        String str = "";
        PrintWriter out = null;
        BufferedReader in = null;

        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
        socket = connexion("10.192.92.30", 2323);
        
        // Create to object to read the server and to write to the server.
        try {
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(CaesarClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (true) {
            str = userEntrance("Enter your message : ");
            sendMessage(str, socket, in, out);

            if (str.equals("EXIT")) {
                break;
            }
        }

        System.out.println("Closing client...");
        closeConnexion(socket);
    }

}
