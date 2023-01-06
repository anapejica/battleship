
package Start;

import Threads.Client;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anape
 */
public class Server extends Thread {

    private int portNumber;

    public Server(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public void run() {
        try {
            // Osluskivanje novih korisnika
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Osluskivac konekcija...");

            while (true) {
                // Novi korisnik konektovan
                Socket clientSocket = serverSocket.accept();

                Client client = new Client(clientSocket);
                client.start();
            }
        } catch (IOException ex) {

            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
