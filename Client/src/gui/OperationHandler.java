/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import transfer.Response;

/**
 *
 * @author anape
 */
public class OperationHandler extends Thread {

    private Kontroler controller;
    private ArrayBlockingQueue<Response> responses;

    public OperationHandler(Kontroler controller) {
        this.controller = controller;
        this.responses = controller.getConnectionHandler().getResponses();
    }

    @Override
    public void run() {
        Response response;

        try {
            while ((response = responses.take()) != null) {
                final Response finalResponse = response;
                switch (finalResponse.getOperation()) {
                    case LOGIN:
                        Platform.runLater(() -> {
                            controller.loadUser(finalResponse);
                        });
                        break;
                    case KRAJ:
                        Platform.runLater(() -> {
//                            controller.exit(finalResponse);
                        });
                        break;
                    case KREIRAJ_IGRU:
                        Platform.runLater(() -> {
                            controller.kreirajIgru(finalResponse);
                        });
                        break;
                    case START_IGRA:
                        Platform.runLater(() -> {
                            controller.zapocniIgru(finalResponse);
                        });
                        break;
                    case USER_GADJA:
                        Platform.runLater(() -> {
                            controller.korisnikGadja(finalResponse);
                        });
                        break;
                    case SERVER_GADJA:
                        Platform.runLater(() -> {
                            controller.serverGadja(finalResponse);
                        });
                        break;
                    case USER_POBEDIO:
                    case SERVER_POBEDIO:
                        Platform.runLater(() -> {
                            controller.manageWin(finalResponse);
                        });
                        break;
                    case RANGLISTA:
                        Platform.runLater(() -> {
                            controller.prikaziRangListu(finalResponse);
                        });

                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(OperationHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
