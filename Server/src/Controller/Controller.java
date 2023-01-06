
package Controller;

import SO.AbstractGenericOperation;
import SO.KreirajIgruSO;
import SO.KrajIgreSO;
import SO.LoginSO;
import SO.RanglistaSO;
import SO.ServerShootSO;
import SO.ZapocniIgruSO;
import SO.UserShootSO;
import transfer.Request;
import transfer.Response;

/**
 *
 * @author anape
 */
public class Controller {

    private static Controller instance;
    private AbstractGenericOperation so;

    private Controller() {

    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public Response login(Request request) {
        so = new LoginSO();
        return ((LoginSO) so).login(request);
    }

    public Response kreirajIgru(Request request) {
        so = new KreirajIgruSO();
        return ((KreirajIgruSO) so).kreirajIgru(request);
    }

    public Response zapocniIgru(Request request) {
        so = new ZapocniIgruSO();
        return ((ZapocniIgruSO) so).startGame(request);
    }

    public Response korisnikGadja(Request request) {
        so = new UserShootSO();
        return ((UserShootSO) so).userShoot(request);
    }

    public Response serverGadja(Request request) {
        so = new ServerShootSO();
        return ((ServerShootSO) so).serverShoot(request);
    }

    public Response krajIgre(Request request) {
        so = new KrajIgreSO();
        return ((KrajIgreSO) so).krajIgre(request);
    }

    public Response ranglista(Request request) {
        so = new RanglistaSO();
        return ((RanglistaSO) so).getRanglista(request);
    }

}
