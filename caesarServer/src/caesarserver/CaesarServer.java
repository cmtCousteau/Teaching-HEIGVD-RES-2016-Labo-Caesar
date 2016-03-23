/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caesarserver;

/**
 *
 * @author marco
 */
public class CaesarServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");

	MultiThreadedServer multi = new MultiThreadedServer(2323);
	multi.serveClients();
    }
    
}
