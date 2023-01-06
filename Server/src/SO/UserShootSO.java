
package SO;

import Threads.Client;
import domain.Map;
import transfer.Request;
import transfer.Response;
import util.Operation;
import util.ResponseStatus;

/**
 *
 * @author anape
 */
public class UserShootSO extends AbstractGenericOperation {

    private Response response;
    private Request request;

    public Response userShoot(Request request) {
        this.request = request;
        this.response = new Response();
        abstractExecuteSO();
        return response;
    }

    @Override
    public boolean executeSO() {
        Boolean hit = Client.serverMap.updateMap(request.getCoordinates());
        this.response.setHit(hit);
        this.response.setOperation(Operation.USER_GADJA);
        this.response.setCoordinates(this.request.getCoordinates());
        this.response.setShip(hit ? Client.serverMap.getBrodNaPoziciji(
                this.request.getCoordinates().getRed(),
                this.request.getCoordinates().getKolona()) : null);
        this.response.setResponseStatus(ResponseStatus.OK);
        this.response.setUserPlaying(hit);
        return true;
    }

}
