package Cliente;

// A simple RMI interface file - M. Liu

import Server.ClientInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface ServerInterface extends Remote {

// This remote method allows an object client to 
// register for callback
// @param callbackClientObject is a reference to the
//        object of the client; to be used by the server
//        to make its callbacks.     

    /**
     *
     * @param callbackClientObject
     * @param empresa
     * @param precio
     * @param tipo
     * @throws RemoteException
     */
    
    public void registerForCallback(ClientInterface callbackClientObject, String empresa, Float precio, int tipo) throws java.rmi.RemoteException;

// This remote method allows an object client to 
// cancel its registration for callback
    public void unregisterForCallback(ClientInterface callbackClientObject) throws java.rmi.RemoteException;
 
    
    
    
/**
 * This remote method returns a message.
 * @return a HashMap with all the information.
     * @throws java.rmi.RemoteException
 */
   public HashMap<String,Float> getData() throws java.rmi.RemoteException;

    
} //end interface
   
   