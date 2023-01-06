
package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.Request;
import transfer.Response;

/**
 * Class is used to manipulate with sending and receiving responses and requests
 *
 * @author anape
 */
public class ConnectionHandler extends Thread {

    private Socket socket;
    private Primalac primalac;
    private Posaljilac posaljilac;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;

    /**
     * Method is used to create connection to server initialize threads and
     * start them
     */
    public ConnectionHandler(String address, int port) {
        try {
            socket = new Socket(address, port);
            toServer = new ObjectOutputStream(socket.getOutputStream());
            fromServer = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        posaljilac = new Posaljilac(toServer);
        primalac = new Primalac(fromServer);

        posaljilac.start();
        primalac.start();

    }

    /**
     * Method is used to retrieve all responses
     *
     * @return responses
     */
    public ArrayBlockingQueue<Response> getResponses() {
        return primalac.getResponses();
    }

    /**
     * Method is used to retrieve requests
     *
     * @return requests
     */
    public ArrayBlockingQueue<Request> getRequests() {
        return posaljilac.getRequests();
    }

    /**
     * Close all connections All threads stop and close socket connection
     */
    public void closeConnection() {
        if (primalac.isAlive()) {
            primalac.interrupt();
        }

        if (posaljilac.isAlive()) {
            posaljilac.interrupt();
        }

        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
