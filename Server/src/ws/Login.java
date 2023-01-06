
package ws;

import SO.LoginSO;
import javax.jws.*;
import transfer.Request;
import transfer.Response;

/**
 *
 * @author anape
 */
@WebService(endpointInterface = "ws.ILogin")
public class Login implements ILogin {

    @Override
    public Response login(Request request) {
        LoginSO so = new LoginSO();
        return so.login(request);
    }

}
