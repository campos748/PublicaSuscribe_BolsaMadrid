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
    
    VCliente ventana;
    
    /**
     *
     * @param ventana
     * @throws RemoteException
     */
    public ClientImpl(VCliente ventana) throws RemoteException {
      super( );
      this.ventana = ventana;
    }
    
    @Override
    public String notifyMe(String message) throws RemoteException{
      String returnMessage = "Call back received: " + message;
      System.out.println(returnMessage);
      return returnMessage;
    }
    
    @Override
    public void actualizarVentana(String empresa, Float precio, int tipo) throws RemoteException{
        this.ventana.anadirAlerta(empresa, precio, tipo, (ClientInterface)this);
    }
    
}
