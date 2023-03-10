
package Threads;

import Controller.Controller;
import domain.Game;
import domain.Map;
import util.ResponseStatus;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.Request;
import transfer.Response;
import util.Operation;

/**
 *
 * @author anape
 */
public class Client extends Thread {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean end = false;
    private Socket clientSocket;
    public static Map serverMap;
    public static Map userMap;
    public static Game game;

    public Client(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            out = new ObjectOutputStream(this.clientSocket.getOutputStream());
            in = new ObjectInputStream(this.clientSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Client() {
    }

    @Override
    public void run() {
        while (!end) {
            try {
                Request request = (Request) in.readObject();
                Response response = new Response();
                switch (request.getOperation()) {
                    case KREIRAJ_IGRU:
                        response = Controller.getInstance().kreirajIgru(request);
                        createGame(response);
                        break;
                    case LOGIN:
                        response = Controller.getInstance().login(request);
                        login(response);
                        break;
                    case START_IGRA:
                        startGame(request);
                        response = Controller.getInstance().zapocniIgru(request);
                        Client.serverMap = response.getMap();
                        break;
                    case USER_GADJA:
                        response = Controller.getInstance().korisnikGadja(request);
                        daLiJeKrajIgre(response);
                        break;
                    case SERVER_GADJA:
                        response = Controller.getInstance().serverGadja(request);
                        daLiJeKrajIgre(response);
                        break;
                    case KRAJ:
                        response = Controller.getInstance().krajIgre(request);
                        break;
                    case RANGLISTA:
                        response = Controller.getInstance().ranglista(request);
                        break;
                }
                sendResponse(response);
            } catch (Exception ex) {
                end = true;
                System.out.println("Client closed connection.");
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void login(Response controllerResponse) {
        controllerResponse.setOperation(Operation.LOGIN);
        if (controllerResponse.getUser() != null) {
            controllerResponse.setResponseStatus(ResponseStatus.OK);
        } else {
            controllerResponse.setResponseStatus(ResponseStatus.ERROR);
        }

    }

    private void sendResponse(Response response) {
        try {
            out.writeObject(response);
            out.reset();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createGame(Response response) {
        if (response.getResponseStatus() == ResponseStatus.OK) {
            serverMap = new Map();
            System.out.println("Map created!");
        }
    }

    private void startGame(Request request) {
        this.userMap = request.getMap();
    }

    private void daLiJeKrajIgre(Response response) {
        // We check if user or server won
        if (Client.serverMap.brojNeotkrivenihBrodova() == 0) {
            response.setOperation(Operation.USER_POBEDIO);
        }
        if (Client.userMap.brojNeotkrivenihBrodova() == 0) {
            response.setOperation(Operation.SERVER_POBEDIO);
        }
    }

}
