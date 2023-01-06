
package SO;

import static SO.AbstractGenericOperation.bbp;
import Threads.Client;
import domain.Game;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.Request;
import transfer.Response;
import util.Operation;
import util.ResponseStatus;

/**
 *
 * @author anape
 */
public class KrajIgreSO extends AbstractGenericOperation {

    private Response response;
    private Request request;

    public Response krajIgre(Request request) {
        this.request = request;
        this.response = new Response();
        abstractExecuteSO();
        return response;
    }

    @Override
    public boolean executeSO() {
        try {
            Field game = Client.class.getDeclaredField("game");
            game.setAccessible(true);
            Game gameReflection = (Game) game.get(new Client());
            gameReflection.setend(true);
            if (this.request.getOperation() == Operation.SERVER_POBEDIO) {
                gameReflection.setidWinner(-1);
                // 20 is number of total fields
                gameReflection.setnumberOfFieldsHit(20 - Client.serverMap.brojNeotkrivenihPolja());
                gameReflection.setnumberOfFieldsLeft(Client.serverMap.brojNeotkrivenihPolja());
                gameReflection.setscore(Client.serverMap.brojNeotkrivenihPolja() * 5 - (20 - Client.serverMap.brojNeotkrivenihPolja()) * 2);
            } else {
                gameReflection.setidWinner(request.getUser().getIdUser());
                // 20 is number of total fields
                gameReflection.setnumberOfFieldsHit(20 - Client.userMap.brojNeotkrivenihPolja());
                gameReflection.setnumberOfFieldsLeft(Client.userMap.brojNeotkrivenihPolja());
                gameReflection.setscore(Client.userMap.brojNeotkrivenihPolja() * 5 - (20 - Client.userMap.brojNeotkrivenihPolja()) * 2);
            }

            if (bbp.updateRecord(gameReflection)) {
                response.setResponseStatus(ResponseStatus.OK);
            } else {
                response.setResponseStatus(ResponseStatus.ERROR);
            }
            response.setOperation(Operation.KRAJ);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(KrajIgreSO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(KrajIgreSO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(KrajIgreSO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(KrajIgreSO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}
