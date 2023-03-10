
package SO;

import DatabaseBroker.DatabaseBroker;
import DatabaseBroker.IDatabaseBroker;
import domain.GeneralDObject;
import transfer.Request;
import transfer.Response;

/**
 *
 * @author anape
 */
public abstract class AbstractGenericOperation {

    static public IDatabaseBroker bbp = new DatabaseBroker();
    GeneralDObject gdo;

    synchronized public boolean abstractExecuteSO() {
        bbp.makeConnection();
        boolean signal = executeSO();
        if (signal == true) {
            bbp.commitTransation();
        } else {
            bbp.rollbackTransation();
        }
        bbp.closeConnection();
        return signal;
    }

    abstract public boolean executeSO();

}
