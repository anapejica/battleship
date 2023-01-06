
package client;

import javafx.event.Event;
import javafx.event.EventHandler;

/**
 *
 * @author anape
 */
public class LoginHandler implements EventHandler{

    Kontroler controller;

    public LoginHandler(Kontroler controller) {
        this.controller = controller;
    }
    
    @Override
    public void handle(Event event) {
        controller.login();
    }
    
}
