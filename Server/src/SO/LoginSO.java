
package SO;

import domain.User;
import transfer.Request;
import transfer.Response;
import util.Operation;

/**
 *
 * @author anape
 */
public class LoginSO extends AbstractGenericOperation {

    Request request;
    Response response;

    public Response login(Request request) {
        this.request = request;
        this.response = new Response();
        abstractExecuteSO();
        return response;
    }

    @Override
    public boolean executeSO() {
        User user = (User) bbp.findRecord(this.request.getUser());
        this.response.setUser(user);
        this.response.setOperation(Operation.LOGIN);
        return true;
    }

}
