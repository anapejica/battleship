
package communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.Request;

/**
 *
 * @author anape
 */
public class Posaljilac extends Thread {

    private ArrayBlockingQueue<Request> requests;
    private ObjectOutputStream toServer;

    public Posaljilac(ObjectOutputStream toServer) {
        this.toServer = toServer;
        this.requests = new ArrayBlockingQueue<>(10);
    }

    public ArrayBlockingQueue<Request> getRequests() {
        return requests;
    }

    @Override
    public void run() {
        Request request;
        try {
            while ((request = requests.take()) != null) {
                toServer.writeObject(request);
            }
        } catch (InterruptedException ex) {
            // User exited program
            Logger.getLogger(Posaljilac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Posaljilac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
