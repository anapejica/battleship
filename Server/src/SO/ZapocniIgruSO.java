/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SO;

import domain.Koordinate;
import domain.StanjePolja;
import domain.Map;
import domain.Brod;
import java.util.LinkedList;
import java.util.Random;
import transfer.Request;
import transfer.Response;
import util.Operation;
import util.ResponseStatus;
import util.Brodovi;

/**
 *
 * @author anape
 */
public class ZapocniIgruSO extends AbstractGenericOperation {

    private LinkedList<Brod> ships = Brodovi.getBrodovi();
    private Response response;
    private Request request;
    private boolean userPlaying;

    public Response startGame(Request request) {
        this.request = request;
        this.response = new Response();
        abstractExecuteSO();
        return response;
    }

    @Override
    public boolean executeSO() {
        Map serverMap = new Map();
        initializeServerMap(serverMap);
        this.response.setMap(serverMap);
        this.response.setOperation(Operation.START_IGRA);
        this.response.setResponseStatus(ResponseStatus.OK);
        this.response.setUserPlaying(userPlaying);
        return true;
    }

    private void initializeServerMap(Map serverMap) {
        for (int i = 0; i < ships.size(); i++) {
            placeShip(ships.get(i), serverMap);
        }
    }

    private boolean placeShip(Brod ship, Map serverMap) {
        Random random = new Random();
        do {
            int row = random.nextInt(10);
            int col = random.nextInt(10);
            boolean vertical = random.nextDouble() > 0.5;
            Koordinate coordinates = new Koordinate(row, col);
            ship.setCoordinates(coordinates);
            ship.setVertical(vertical);
        } while (!Brodovi.mozeLiBitiPostavljenBrod(ship, ships, serverMap));
        setMapFields(ship, serverMap);
        return true;
    }

    private void setMapFields(Brod ship, Map serverMap) {
        int y = ship.getCoordinates().getKolona();
        int x = ship.getCoordinates().getRed();
        if (!ship.isVertical()) {
            for (int i = y; i < y + ship.getLength(); i++) {
                serverMap.setBrodNaPoziciji(x, i, ship);
            }
        } else {
            for (int i = x; i < x + ship.getLength(); i++) {
                serverMap.setBrodNaPoziciji(i, y, ship);
            }
        }
    }

}
