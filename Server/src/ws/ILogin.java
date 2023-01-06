
package ws;

import javax.jws.*;
import transfer.Request;
import transfer.Response;

/**
 *
 * @author anape
 */
@WebService
public interface ILogin {

    @WebMethod
    public Response login(Request request);

}
