
package gui.handlers;

import gui.Kontroler;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 *
 * @author anape
 */
public class RangListaHandler implements EventHandler {

    private Kontroler controller;

    public RangListaHandler(Kontroler controller) {
        this.controller = controller;
    }

    @Override
    public void handle(Event event) {
        this.controller.prikaziRangListu();
    }

}
