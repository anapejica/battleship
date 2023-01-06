
package gui.handlers;

import gui.Kontroler;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 *
 * @author anape
 */
public class BiranjeBrodaHandler implements EventHandler {

    Kontroler controller;

    public BiranjeBrodaHandler(Kontroler controller) {
        this.controller = controller;
    }

    @Override
    public void handle(Event event) {
        this.controller.odaberiBrod();
    }

}
