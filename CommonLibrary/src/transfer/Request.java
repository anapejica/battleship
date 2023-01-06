
package transfer;

import domain.Koordinate;
import domain.Map;
import domain.User;
import java.io.Serializable;
import util.Operation;

/**
 *
 * @author anape
 */
public class Request implements Serializable {

    private Koordinate coordinates;
    private User user;
    private Map map;
    private Operation operation;

    public Request() {
    }

    public Koordinate getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Koordinate coordinates) {
        this.coordinates = coordinates;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Operation getOperation() {
        return operation;
    }

}
