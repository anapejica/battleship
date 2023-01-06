
package gui.handlers;

import gui.Kontroler;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 *
 * @author anape
 */
public class NovaIgraHandler implements EventHandler {

    Kontroler controller;

    public NovaIgraHandler(Kontroler controller) {
        this.controller = controller;
    }

    @Override
    public void handle(Event event) {
        this.controller.novaIgra();
    }

}
