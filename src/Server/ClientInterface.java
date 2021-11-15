/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.rmi.RemoteException;

/**
 *
 * @author martin
 */
public interface ClientInterface extends java.rmi.Remote{
    
    public String notifyMe(String message) throws java.rmi.RemoteException;
    
    public void actualizarVentana(String empresa, Float precio, int tipo)throws RemoteException;
}
