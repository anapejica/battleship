/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SO;

import Threads.Client;
import domain.Game;
import domain.GeneralDObject;
import util.ResponseStatus;
import domain.User;
import java.util.List;
import transfer.Request;
import transfer.Response;
import util.Operation;

/**
 *
 * @author anape
 */
public class KreirajIgruSO extends AbstractGenericOperation {

    Request request;
    Response response;

    public Response kreirajIgru(Request request) {
        this.request = request;
        this.response = new Response();
        abstractExecuteSO();
        return response;
    }

    @Override
    public boolean executeSO() {

        User user = request.getUser();
        Game game = new Game();
        game.setidUser(user.getIdUser());
        if (bbp.insertRecord(game)) {
            List<GeneralDObject> games = bbp.findRecords(new Game(), "WHERE idGame = (SELECT MAX(idGame) FROM game)");
            if (games.size() != 0) {
                Client.game = (Game) games.get(0);
                response.setResponseStatus(ResponseStatus.OK);
            } else {
                response.setResponseStatus(ResponseStatus.ERROR);
            }
        } else {
            response.setResponseStatus(ResponseStatus.ERROR);
        }
        response.setOperation(Operation.KREIRAJ_IGRU);

        return true;
    }

}
