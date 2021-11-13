package Cliente;

// A simple RMI interface file - M. Liu

import java.rmi.Remote;
import java.util.HashMap;

public interface Interface extends Remote {
/**
 * This remote method returns a message.
 * @return a HashMap with all the information.
     * @throws java.rmi.RemoteException
 */
   public HashMap<String,Float> getData() throws java.rmi.RemoteException;} //end interface
