/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Server.ClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author martin
 */
public class ClientImpl extends UnicastRemoteObject implements ClientInterface{
    
    public ClientImpl() throws RemoteException {
      super( );
    }
    
    @Override
    public String notifyMe(String message){
      String returnMessage = "Call back received: " + message;
      System.out.println(returnMessage);
      return returnMessage;
    }   
    
}
