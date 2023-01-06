
package SO;

import Threads.Client;
import domain.Koordinate;
import domain.Map;
import java.util.Random;
import transfer.Request;
import transfer.Response;
import util.Operation;
import util.ResponseStatus;

/**
 *
 * @author anape
 */
public class ServerShootSO extends AbstractGenericOperation {

    private Response response;
    private Request request;

    public Response serverShoot(Request request) {
        this.request = request;
        this.response = new Response();
        abstractExecuteSO();
        return response;
    }

    @Override
    public boolean executeSO() {
        Random random = new Random();
        Boolean hit;
        Koordinate coordinates;
        do {
            coordinates = new Koordinate(random.nextInt(10), random.nextInt(10));
            hit = Client.userMap.updateMap(coordinates);
        } while (hit == null);
        this.response.setHit(hit);
        this.response.setOperation(Operation.SERVER_GADJA);
        this.response.setCoordinates(coordinates);
        this.response.setShip(hit ? Client.userMap.getBrodNaPoziciji(
                coordinates.getRed(),
                coordinates.getKolona()) : null);
        this.response.setResponseStatus(ResponseStatus.OK);
        this.response.setUserPlaying(!hit);
        return true;
    }

}
