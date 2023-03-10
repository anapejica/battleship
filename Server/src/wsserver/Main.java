
package wsserver;

import Start.Server;
import ws.*;
import javax.xml.ws.*;

/**
 *
 * @author anape
 */
public class Main {

    public static void main(String[] args) {
        try {
            Endpoint.publish("http://localhost:4789/ws/login", new Login());
            
            //Start server
            Server server = new Server(9999);
            server.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
